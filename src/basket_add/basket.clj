(ns basket-add.basket
  (:require [basket-add.session :refer [cs]]
            [clj-http.client :as client]))


(def add-url "http://www.essential-trading.coop/extranet/do/___addItem.aspx?code=%s&qnt=%f&line=0&dojo.preventCache=%d")

(defn add-to-basket
  [code quantity]
  (println "Adding code:" code "qty:" quantity)
  (let [cachebuster (System/currentTimeMillis)
        item-url (format add-url code quantity cachebuster)
        resp (client/get item-url {:cookie-store cs})]
    resp))

(defn add-all-to-basket
  [order-items throttle-ms]
  (dorun
   (map (fn [{:keys [code essential-cases]}] (add-to-basket code essential-cases) (Thread/sleep throttle-ms)) order-items)))
