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
    pkgs.ollama

    pythonEnv
  ];

  shellHook = ''
    python -m venv ./.venv
    source ./.venv/bin/activate
  '';
}
