#!/usr/bin/env bb

(ns claude-client
  (:require [babashka.curl :as curl]
            [cheshire.core :as json]
            [clojure.string :as str]
            [clojure.tools.cli :as cli]))

(defonce api-key (System/getenv "ANTHROPIC_API_KEY"))

(when-not api-key
  (println "Error: ANTHROPIC_API_KEY environment variable not set")
  (System/exit 1))

(defn read-prompt-from-stdin []
  (println "Enter your prompt (press Ctrl+D when finished):")
  (str/join "\n" (line-seq (java.io.BufferedReader. *in*))))

(defn get-prompt [args]
  (if (or (empty? args) (= args ["-"]))
    (read-prompt-from-stdin)
    (str/join " " args)))

(defn call-anthropic-api [prompt & {system-prompt :system}]
  (try
    (let [body (cond-> {:model "claude-3-7-sonnet-20250219"
                        :max_tokens 4096
                        :messages [{:role "user"
                                    :content prompt}]}
                 system-prompt (assoc :system system-prompt))
          response (curl/post "https://api.anthropic.com/v1/messages"
                              {:headers {"x-api-key" api-key
                                         "anthropic-version" "2023-06-01"
                                         "content-type" "application/json"}
                               :body (json/generate-string body)})
          body (json/parse-string (:body response) true)
          content (get-in body [:content 0 :text])]
      content)
    (catch Exception e
      (println "Error calling Anthropic API:" (ex-message e))
      (when-let [body (try (json/parse-string (-> e ex-data :body) true) (catch Exception _ nil))]
        (println "API Error:" (or (:error body) body)))
      (System/exit 1))))

(defn- print-response [{:keys [show-prompt json edn]} prompt response]
  (let [output (cond-> {:response response} show-prompt (assoc :prompt prompt))]
    (cond
      edn (prn output)
      json (println (json/generate-string output))
      :else (do
              (when show-prompt
                (println "## Prompt")
                (println)
                (println prompt)
                (println)
                (println "## Response")
                (println))
              (println response)))))

(def SYSTEM-PROMPT-CODE
  (str "You are a programming assistant. Output code, and only code,"
       " in the language specified. If no language is specified, assume Python."
       " Do not include any other output before or after the program code."
       " Do not include backticks or other characters as guards."
       " Include helpful comments in the code, but keep them concise."
       " Include docstrings for functions."))

(def SYSTEM-PROMPT-CLOJURE
  (str "You are a programming assistant for an experience Clojure developer."
       " Output Clojure code only. Do not include backticks or other characters as guards."
       " Do not include any other output before or after the program code."
       " For especially complex bits of code, include comments, but keep them terse."
       " Include docstrings for functions."))

(def SYSTEM-PROMPT-JS
  (str "You are a programming assistant for an experience JavaScript developer."
       " Output JavaScript code only. Do not include backticks or other characters as guards."
       " Do not include any other output before or after the program code."
       " For especially complex bits of code, include comments, but keep them terse."
       " Include doc comments for functions."))

(def SYSTEM-PROMPT-TERSE
  "Be as terse as possible while still being helpful.")

(def SYSTEM-PROMPT-GOOFY
  (str "You are the same old Claude, the helpful AI assistant,"
       " but just a little more goofy than usual."))

(defn- cli-options []
  [["-s" "--system SYSTEM"
    "System prompt. More specific prompts, such as --code, take precedent."]
   ["-o" "--code" "Coding assistant, defaulting to Python"]
   ["-c" "--clojure" "Coding assistant in Clojure"]
   ["-a" "--js" "Coding assistant in JavaScript"]
   ["-t" "--terse" "Terse mode"]
   ["-y" "--goofy" "🤪"]
   ["-h" "--help" "Show this help text"]
   ["-p" "--show-prompt" "Include prompt in output"]
   ["-e" "--edn" "Output response as EDN"]
   ["-j" "--json" "Output response as JSON"]])

(defn -main [& args]
  (let [{:keys [options arguments errors summary]}
        (cli/parse-opts args (cli-options))]
    (cond
      (:help options)
      (println summary)

      (and (:edn options) (:json options))
      (println "Please specify either --edn or --json, not both.")

      :else
      (let [system-prompt (cond
                            (:clojure options) SYSTEM-PROMPT-CLOJURE
                            (:code options) SYSTEM-PROMPT-CODE
                            (:terse options) SYSTEM-PROMPT-TERSE
                            (:js options) SYSTEM-PROMPT-JS
                            (:goofy options) SYSTEM-PROMPT-GOOFY
                            :else (:system options))
            prompt (get-prompt arguments)
            response (call-anthropic-api prompt :system system-prompt)]
        (print-response options prompt response)))))

(when (seq *command-line-args*)
  (apply -main *command-line-args*))
