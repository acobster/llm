(ns messaging-app.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["react-native" :as rn]
            ["react-native-vector-icons/MaterialIcons" :default MaterialIcons]))

(def view (r/adapt-react-class rn/View))
(def text (r/adapt-react-class rn/Text))
(def scroll-view (r/adapt-react-class rn/ScrollView))
(def flat-list (r/adapt-react-class rn/FlatList))
(def text-input (r/adapt-react-class rn/TextInput))
(def touchable-opacity (r/adapt-react-class rn/TouchableOpacity))
(def safe-area-view (r/adapt-react-class (.-SafeAreaView rn)))
(def status-bar (r/adapt-react-class rn/StatusBar))
(def image (r/adapt-react-class rn/Image))
(def icon (r/adapt-react-class MaterialIcons))

(defn header []
  (let [current-chat-contact @(rf/subscribe [:current-chat-contact])]
    [view {:style {:height 60
                   :padding-horizontal 15
                   :flex-direction "row"
                   :align-items "center"
                   :background-color "#075E54"
                   :elevation 5}}
     [touchable-opacity {:style {:padding 5}
                         :on-press #(js/console.log "Back pressed")}
      [icon {:name "arrow-back" :size 24 :color "white"}]]
     [view {:style {:width 40 :height 40 :border-radius 20 :background-color "#128C7E" :justify-content "center" :align-items "center" :margin-horizontal 10}}
      [text {:style {:color "white" :font-weight "bold"}} (first (:name current-chat-contact))]]
     [text {:style {:color "white" :font-size 18 :font-weight "500"}} (:name current-chat-contact)]]))

(defn message-item [{:keys [text sender timestamp]}]
  (let [current-user-id (get-in @(rf/subscribe [:current-user]) [:id])
        is-current-user (= sender current-user-id)
        time-str (.toLocaleTimeString (js/Date. timestamp) "en-US" #js {:hour "2-digit" :minute "2-digit"})]
    [view {:style {:align-items (if is-current-user "flex-end" "flex-start")
                   :margin-vertical 5
                   :padding-horizontal 10}}
     [view {:style {:max-width "80%"
                    :padding 10
                    :border-radius 10
                    :background-color (if is-current-user "#DCF8C6" "white")
                    :elevation 1}}
      [text {:style {:font-size 16}} text]
      [text {:style {:font-size 12
                     :color "#999"
                     :align-self "flex-end"
                     :margin-top 5}} time-str]]]))

(defn message-list []
  (let [messages @(rf/subscribe [:current-chat-messages])]
    [flat-list {:data messages
                :key-extractor #(.-id %)
                :render-item (fn [item]
                               (r/as-element [message-item (js->clj (.-item item) :keywordize-keys true)]))
                :style {:flex 1}}]))

(defn message-input []
  (let [message (r/atom "")]
    (fn []
      [view {:style {:flex-direction "row"
                     :align-items "center"
                     :padding 10
                     :border-top-width 1
                     :border-color "#EEE"
                     :background-color "white"}}
       [text-input {:style {:flex 1
                            :border-radius 20
                            :padding-horizontal 15
                            :padding-vertical 10
                            :background-color "#F0F0F0"
                            :margin-right 10}
                    :placeholder "Type a message"
                    :value @message
                    :on-change-text #(reset! message %)}]
       [touchable-opacity {:style {:width 40
                                   :height 40
                                   :border-radius 20
                                   :background-color "#075E54"
                                   :justify-content "center"
                                   :align-items "center"}
                           :on-press (fn []
                                       (when-not (empty? @message)
                                         (rf/dispatch [:send-message @message])
                                         (reset! message "")))}
        [icon {:name "send" :size 20 :color "white"}]]])))

(defn chat-screen []
  [view {:style {:flex 1 :background-color "#ECE5DD"}}
   [header]
   [message-list]
   [message-input]])

(defn contact-item [{:keys [id name]}]
  [touchable-opacity {:style {:padding 15
                              :border-bottom-width 1
                              :border-color "#EEE"
                              :flex-direction "row"
                              :align-items "center"}
                      :on-press #(rf/dispatch [:change-chat id])}
   [view {:style {:width 50 :height 50 :border-radius 25 :background-color "#128C7E" :justify-content "center" :align-items "center" :margin-right 15}}
    [text {:style {:color "white" :font-size 20 :font-weight "bold"}} (first name)]]
   [view {:style {:flex 1}}
    [text {:style {:font-size 16 :font-weight "500"}} name]]])

(defn contacts-list []
  (let [contacts @(rf/subscribe [:contacts])]
    [flat-list {:data contacts
                :key-extractor #(.-id %)
                :render-item (fn [item]
                               (r/as-element [contact-item (js->clj (.-item item) :keywordize-keys true)]))
                :style {:flex 1}}]))

(defn contacts-screen []
  [view {:style {:flex 1 :background-color "white"}}
   [view {:style {:height 60
                  :padding-horizontal 15
                  :justify-content "center"
                  :background-color "#075E54"}}
    [text {:style {:color "white" :font-size 20 :font-weight "500"}} "Contacts"]]
   [contacts-list]])

(defn app-container []
  (let [current-chat @(rf/subscribe [:current-chat])]
    [safe-area-view {:style {:flex 1}}
     [status-bar {:background-color "#054D44" :bar-style "light-content"}]
     (if current-chat
       [chat-screen]
       [contacts-screen])]))
