;; Mason McIntyre
;; 02-15-2015 
;; The evaluator for the boolean calculator.

;; imp-and: Evaluate an and expression.
(defun imp-and (left right)
  (ecase left
         (TRUE
          (ecase right
                 (TRUE 'TRUE)
                 (FALSE 'FALSE)))
         (FALSE
          (ecase right
                 (TRUE 'FALSE)
                 (FALSE 'FALSE)))))

;; imp-and: Evaluate an and expression.
(defun imp-or (left right)
  (ecase left
         (TRUE
          (ecase right
                 (TRUE 'TRUE)
                 (FALSE 'TRUE)))
         (FALSE
          (ecase right
                 (TRUE 'TRUE)
                 (FALSE 'FALSE)))))

;; imp-xor: Evaluate an and expression.
(defun imp-xor (left right)
  (ecase left
         (TRUE
          (ecase right
                 (TRUE 'FALSE)
                 (FALSE 'TRUE)))
         (FALSE
          (ecase right
                 (TRUE 'TRUE)
                 (FALSE 'FALSE)))))

;; imp-not: Evaluate a not expression.
(defun imp-not (arg)
  (ecase arg
         (TRUE 'FALSE)
         (FALSE 'TRUE)))

;; eval-expr: Traverses an expression tree and computes the value.
(defun eval-expr (expr)
  (etypecase expr
	     (boolean-const-expr (boolean-const-expr-value expr))
             (unary-expr (let ((val (eval-expr (unary-expr-rand expr))))
                         (ecase (unary-expr-op expr)
                                (NOT (imp-not val)))))
             (binary-expr
              (let ((left-val (eval-expr (binary-expr-left expr)))
                    (right-val (eval-expr (binary-expr-right expr))))
                (ecase (binary-expr-op expr)
                       (AND (imp-and left-val right-val))
                       (OR (imp-or left-val right-val))
                       (XOR (imp-xor left-val right-val))
                       )))))

