(ns dashcrawl.webserver.config
  (:require [aero.core :as aero]
            [integrant.core :as ig]))

(defmethod aero/reader 'ig/ref
  [{:keys [profile] :as opts} tag value]
  (ig/ref value))

(defn load-config
  []
  (aero/read-config (clojure.java.io/resource "config.edn")))
