;;; The web server part of the server
(ns dashcrawl.webserver
  (:require [clojure.tools.logging :as log]
            [dashcrawl.webserver.handler :as handler]
            [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]))

(defmethod ig/init-key :server/http [_ {:keys [port host] :as opts}]
  (log/infof "Starting http server on port %s, host %s" port host)
  (jetty/run-jetty (handler/handler) (assoc opts :join? false)))

(defmethod ig/halt-key! :server/http [_ server]
  (log/info "Stopping http server")
  (.stop server))
