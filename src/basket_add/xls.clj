(ns basket-add.xls
  (:use [dk.ative.docjure.spreadsheet] :reload-all))

(def order-cols {:A :code :B :description :C :case-size :D :essential-cases})

(defn get-order
 [spreadsheet-name start-line]
 (let [wb (load-workbook spreadsheet-name)
       order-sheet (select-sheet "Collated Order" wb)]
   (drop (- start-line 1) (select-columns order-cols order-sheet))))
