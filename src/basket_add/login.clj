(ns basket-add.login
  (:require [basket-add.session :refer [cs]]
            [radix.config :refer [config]]
            [clj-http.client :as client]
            [net.cgrand.enlive-html :as html]))

(def login-url (config :login-url))
(def basket-url (config :basket-url))

(defn get-page [url]
  "Downloads a document as an html-string."
  (let [resp (client/get url)]
    (if (= 200 (:status resp)) (:body resp) nil)))

(defn extract-login-page-input-fields
  "Extracts all form input elements."
  [html-str]
  (html/select (html/html-snippet html-str) [:input]))

(defn get-post-keys-values
  "Extracts form-post keys and values."
  [input-elements]
  (zipmap
   (map #(-> % :attrs :name) input-elements)
   (map #(-> % :attrs :value) input-elements)))

(defn- sub-with
  "Helper function to replace values in map."
  [a b] b)

(defn insert-u-and-p
  "Insert the username and password"
  [u p form-vars-coll]
  (update-in
   (update-in form-vars-coll ["ctl00$MainContent$txtUser"] sub-with u)
    ["ctl00$MainContent$txtPassword"] sub-with p))

(defn remove-unwanted-keys
  "Remove the unwanted key"
  [c] (remove #(= "ctl00$MainContent$cmdForgot" (key %)) c))

(defn get-login-vars
  "Extracts form login variables"
  [username password]
  (->> login-url
       get-page
       extract-login-page-input-fields
       get-post-keys-values
       (insert-u-and-p username password)
       remove-unwanted-keys))

;; this gets the login-response
(defn perform-login
  "Do the login and capture the session cookies.
   This only needs to be called for the side-effect
   capturing the session cookies."
  [form-vars]
  (client/post login-url {:form-params form-vars :cookie-store cs}))

(defn essential-login
  [username password]
  (perform-login (get-login-vars username password)))
