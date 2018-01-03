;; The evaluator for the integer calculator.

;; eval-expr: Traverses an expression tree and computes the value.
(defun eval-expr (expr)
  (etypecase expr
	     (int-const-expr (int-const-expr-value expr))
	     (binary-expr
	      (let ((left-val (eval-expr (binary-expr-left expr)))
		    (right-val (eval-expr (binary-expr-right expr))))
		(ecase (binary-expr-op expr)
		       (PLUS (+ left-val right-val))
		       (MINUS (- left-val right-val))
		       (TIMES (* left-val right-val))
		       (DIV (truncate (/ left-val right-val)))
		       (MOD (mod left-val right-val)))))))
