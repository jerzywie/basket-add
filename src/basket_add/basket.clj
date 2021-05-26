(ns basket-add.basket
  (:require [basket-add.session :refer [cs]]
            [clj-http.client :as client]
            [clojure.string :as s]))


(def add-url "http://www.essential-trading.coop/extranet/do/___addItem.aspx?code=%s&qnt=%f&line=0&dojo.preventCache=%s")

(defn process-body
  "Extract desired information from body.
   Assumes a body returned when HTTP Status = 200 (OK), for example:
  99 lines in basket<br />Balance: &pound;1,711.81<br/><a title=\"View the contents of your basket\" href=\"/extranet/CurrentBasket.aspx\">View basket</a>"
  [body]
  (let [[lines raw-balance] (s/split body #"<br[ ]*/>")
        balance (s/replace raw-balance "&pound;" "£")]
    (str lines ". " balance)))

(defn add-to-basket
  [code quantity counter]

  (try
    ((print "Line "@counter ": Adding code:" code "qty:" quantity)
     (let [cachebuster (str (System/currentTimeMillis) @counter)
           item-url (format add-url code quantity cachebuster)
           resp (client/get item-url {:cookie-store cs})
           {status :status body :body} resp]
       (println " ... Status " (if (= status 200) (str "OK. " (process-body body)) (str status " ****** ERROR" body)))
       (swap! counter inc)
       status))
    (catch Exception e (str "Error: " (.getMessage e)))))

(defn add-all-to-basket
  [order-items throttle-ms counter]
  (dorun
   (map (fn [{:keys [code essential-cases]}]
          (add-to-basket code essential-cases counter)
          (Thread/sleep throttle-ms))
        order-items)))
