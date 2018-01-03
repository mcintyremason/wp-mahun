;; tree.lisp - Data structures for the parse tree. 

;; Nodes of the parse tree are stored as property lists. Every expression
;; has a :type property.

;; make-binary-expr - Creates an expression with a binary operator.
(defun make-binary-expr (op lrand rrand)
  (list :type 'binary :op op :lrand lrand :rrand rrand))

;; make-integer-expr - Creates an expression that stores an integer.
(defun make-integer-expr (value)
  (list :type 'integer :value value))

;; make-empty-expr - Creates an expression that stores nothing. This is used
;;                   for empty lines in the input.
(defun make-empty-expr ()
  (list :type 'empty))

