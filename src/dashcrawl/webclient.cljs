;;; The game's web client
(ns dashcrawl.webclient
  (:require
   [reagent.core :as r]
   [reagent.dom :as dom]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 :app/initialize
 (fn [_ _]
   {:db {:message "Hello World! 2"}}))

(rf/reg-event-db
 :set-message
 (fn [db [_ message]]
   (assoc db :message message)))

(rf/reg-sub
 :message
 (fn [db _]
   (:message db)))

(defn webclient
  []
  (let [message-field (r/atom nil)
        message (rf/subscribe [:message])]
    (fn []
      [:div
       [:h1 "Set the Message"]
       [:div @message]
       [:br]
       [:form {:on-submit (fn update-message [e]
                            (.preventDefault e)
                            (rf/dispatch [:set-message @message-field]))}
        [:input {:type :text :on-change (fn update-field[e] (reset! message-field (.. e -target -value)))}]
        [:input {:type :submit :value "Set Message"}]]])))

(defn mount-root
  []
  (dom/render [#'webclient] (.getElementById js/document "app")))

(defn init!
  []
  (rf/dispatch-sync [:app/initialize])
  (mount-root))
