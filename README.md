# basket-add.core

Add items from Albany spreadsheet to Essential basket

## Installation

Download from https://github.com/basket-add

## Usage

    $ java -jar basket-add.jar [args]

Run the project directly, via `:main-opts` (`-m basket-add.core`):

    $ clojure -M:run-m

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Build an uberjar:

    $ clojure -X:uberjar

Run that uberjar:

    $ java -jar target/basket-add.jar

If you remove `version` from `build.clj`, the uberjar will become `target/basket-add.core-standalone.jar`.

## Options

Usage parameters: spreadsheet username [line-number]

  line-number: optional line in the spreadsheet at which to start the add - useful if something goes wrong and you have a partially processed spreadsheet.

It will prompt you for the password

## License

Copyright © 2018-2024 @jerzywie

Distributed under the Eclipse Public License version 1.0.
