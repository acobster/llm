{ pkgs ? import <nixpkgs> { } }:

let
  venvDir = "./.venv";
  pythonEnv = pkgs.python3.withPackages(ps: [ ]);
in
pkgs.mkShell {
  packages = [
    pkgs.aider-chat
    pkgs.babashka
    pkgs.clojure
    pkgs.nodejs
    pkgs.nodePackages.npm
    pkgs.ollama
    pkgs.yarn-berry

    pythonEnv
  ];

  shellHook = ''
    # Python virtual environment setup
    python -m venv ./.venv
    source ./.venv/bin/activate
    
    # Add node_modules/.bin to PATH for local npm executables
    export PATH="$PWD/node_modules/.bin:$PATH"
    
    # Display helpful information
    echo "Node.js environment ready"
    echo "Run 'npm run dev' to start shadow-cljs development server"
  '';
}
