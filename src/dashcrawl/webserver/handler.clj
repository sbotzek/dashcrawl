(ns dashcrawl.webserver.handler
  (:require
   [reitit.ring :as ring]
   [ring.middleware.content-type :refer [wrap-content-type]]
   [ring.middleware.webjars :refer [wrap-webjars]]
   [reitit.ring.middleware.dev :as ring.dev]))


(defn handler
  []
  (ring/ring-handler
   (ring/router
    []
    (:reitit.middleware/transform ring.dev/print-request-diffs))
   (ring/routes
    (ring/create-resource-handler
     {:path "/"})
    (wrap-content-type
     (wrap-webjars (constantly nil)))
    (ring/create-default-handler
     {:not-found
      (constantly {:status 404 :body "Page Not Found"})}))))
