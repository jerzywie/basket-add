(defproject basket-add "0.2.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [mixradio/radix "1.0.20"]
                 [clj-http "3.6.1"]
                 [enlive "1.1.6"]
                 [dk.ative/docjure "1.10.0"]]
  :main ^:skip-aot basket-add.core
  :target-path "target/%s"
  :uberjar-name "basket-add.jar"
  :profiles {:uberjar {:aot :all}})
