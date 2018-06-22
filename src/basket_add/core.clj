(ns basket-add.core
  (:require [basket-add.login :refer [essential-login]]
            [basket-add.basket :refer [add-all-to-basket]]
            [basket-add.xls :refer [get-order]])
  (:gen-class))

(defn -main
  "Upload Albany order spreadsheet to Essential basket"
  [& args]
  (if (< (count args) 3)
    (println "Usage parameters: spreadsheet username password")
    (try
      (let [xls (first args)
           u (second args)
           p (nth args 2)]
       (println "Adding the contents of " xls " to essential basket...")
       (essential-login u p)
       (add-all-to-basket (get-order xls) 100))
      (catch Exception e (str "Error: " (.getMessage e))))))