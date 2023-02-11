(ns user
  (:require
   [clojure.tools.namespace.repl :as tools.repl]
   [dashcrawl.server :as server]
   [integrant.core :as ig]
   [integrant.repl :as ig.repl :refer [clear go halt prep init reset reset-all]]))

(ig.repl/set-prep! #(ig/prep (server/load-config)))

(tools.repl/set-refresh-dirs "src")
(def refresh tools.repl/refresh)
