;;; The game's server
(ns dashcrawl.server
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [aero.core :as aero]
            [dashcrawl.webserver]
            [nrepl.server :as nrepl]))

;;; Config
(defmethod aero/reader 'ig/ref
  [{:keys [profile] :as opts} tag value]
  (ig/ref value))

(defn load-config
  []
  (aero/read-config (clojure.java.io/resource "config.edn")))

;;; Run a repl
(defmethod ig/init-key :server/repl [_ {:keys [port bind] :as opts}]
  (log/infof "Starting repl server on port %s, bind %s" port bind)
  (nrepl/start-server opts))

(defmethod ig/halt-key! :server/repl [_ server]
  (log/info "Stopping repl server")
  (nrepl/stop-server server))

(defonce system (atom nil))

(defn stop-server
  "Stops the server"
  []
  (some-> (deref system)
          (ig/halt!))
  (shutdown-agents))

(defn start-server
  "Starts up the server"
  []
  (->> (load-config)
       (ig/prep)
       (ig/init)
       (reset! system)))

(defn -main
  []
  (start-server))
