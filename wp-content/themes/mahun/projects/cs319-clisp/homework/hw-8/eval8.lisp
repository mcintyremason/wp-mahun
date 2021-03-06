;; Functions to evaluate expressions for imp2.

;; eval-expr: Evaluate an expression.
(defun eval-expr (expr env)
  (etypecase expr
	     (int-const-expr (int-const-expr-value expr))
	     (var-expr (rval-lookup (var-expr-name expr) env))
	     (unary-expr
	      (let ((val (eval-expr (unary-expr-rand expr) env)))
		(ecase (unary-expr-op expr)
		       (PLUS val)
		       (MINUS (- val)))))
	     (binary-expr
	      (let ((left-val (eval-expr (binary-expr-left expr) env))
		    (right-val (eval-expr (binary-expr-right expr) env)))
		(ecase (binary-expr-op expr)
		       (PLUS (+ left-val right-val))
		       (MINUS (- left-val right-val))
		       (TIMES (* left-val right-val))
		       (DIV (truncate (/ left-val right-val)))
		       (MOD (mod left-val right-val))
		       (LT (if (< left-val right-val) 'TRUE 'FALSE))
		       (GT (if (> left-val right-val) 'TRUE 'FALSE))
		       (LE (if (<= left-val right-val) 'TRUE 'FALSE))
		       (GE (if (>= left-val right-val) 'TRUE 'FALSE))
		       (EQ (if (= left-val right-val) 'TRUE 'FALSE))
		       (NE (if (/= left-val right-val) 'TRUE 'FALSE)))))))
