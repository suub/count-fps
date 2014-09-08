(ns count-fps.core
  (:require [error-codes.core :as ec]
            [clojure.set :as set]))

(def data-path "/home/mn/clojure/ocr-engine-results/")
(def vlid "179395")

(defn read-ocr-edits []
  (read-string (slurp (str data-path "abby/edits/" vlid ".txt"))))
(defn read-corrected-ocr-edits []
  (read-string (slurp (str data-path "abby_verbessert/edits/" vlid ".txt"))))

(comment (ec/augment-error-code "a" "b" [[1 1] [0 0]]))

(defn read-ground-truth []
  (slurp (str data-path "abby_verbessert/ground-truth/" vlid ".txt")))
(defn read-ocr-text []
  (slurp (str data-path "abby/ocr-results/" vlid ".txt")))
(defn read-ocr-postcorrected []
  (slurp (str data-path "abby_verbessert/ocr-results/" vlid ".txt")))

(defn visualize-comparison []
  (let [ground-truth (read-ground-truth)]
    (set/difference
     (into #{} (map #(ec/augment-error-code ground-truth (read-ocr-postcorrected) %) (read-corrected-ocr-edits)))
     (into #{} (map #(ec/augment-error-code ground-truth (read-ocr-text) %) (read-ocr-edits))))))
