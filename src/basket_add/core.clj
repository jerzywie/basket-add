(ns basket-add.core
  (:require [basket-add.login :refer [essential-login]]
            [basket-add.basket :refer [add-all-to-basket]]
            [basket-add.xls :refer [get-order]])
  (:gen-class))

(def throttle-ms 100)
(def default-start-line 3)
(def counter (atom 0))

(defn -main
  "Upload Albany order spreadsheet to Essential basket"
  [& args]
  (if (< (count args) 2)
    (do (println "Usage parameters: spreadsheet username [start-line-number]")
        (println "                  (Password will be prompted for.)"))
    (try
      (let [xls (first args)
            username (second args)
            start-line (if (= (count args) 3) (Integer. (nth args 2)) default-start-line)]
        (print "Enter password: ")
        (flush)
        (let [pwd-secret (read-line)]
          (println "Adding the contents of " xls " to essential basket. Starting at line " start-line "...")
          (reset! counter start-line)
          (essential-login username pwd-secret)
          (add-all-to-basket (get-order xls start-line) throttle-ms counter)
          (println (- @counter start-line) "items added to basket. Balances are ex-VAT.")
          (if (not= start-line default-start-line) (println "Click the \"Update all\" button on the Essential CurrentBasket page to get the basket into a consistent state."))))
      (catch Exception e (str "Error: " (.getMessage e))))))
