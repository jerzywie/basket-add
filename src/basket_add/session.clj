(ns basket-add.session
  (:require [clj-http.cookies :refer [cookie-store]]))

(def cs (clj-http.cookies/cookie-store))
