;; The parser for the integer calculator. It uses cl-yacc.

;; Expression structures. 
(defstruct int-const-expr value)
(defstruct binary-expr op left right)

;; This function takes an operator (represented by a symbol) as an
;; argument and returns an action function for cl-yacc productions
;; that creates binary expression with that operator.
(defun binary (op) 
  (lambda (x y z) (make-binary-expr :op op :left x :right z)))

;; The parser.
(define-parser *expr-parser*
  (:start-symbol expr)
  (:terminals (INT-CONST
	       PLUS MINUS 
	       TIMES DIV MOD
	       LPAREN RPAREN))
  (:precedence ((:LEFT TIMES DIV MOD) 
		(:LEFT PLUS MINUS)))
  (expr 
   (expr PLUS expr (binary 'PLUS))
   (expr MINUS expr (binary 'MINUS))
   (expr TIMES expr (binary 'TIMES))
   (expr DIV expr (binary 'DIV))
   (expr MOD expr (binary 'MOD))
   (INT-CONST (lambda (x) (make-int-const-expr :value x)))
   (LPAREN expr RPAREN (lambda (x y z) y))))
