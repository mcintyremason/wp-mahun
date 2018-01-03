;; Mason McIntyre                                                                                                                                                                              
;; 02-15-2015  

;; The parser for the boolean calculator. It uses cl-yacc.

;; Expression structures. 
(defstruct boolean-const-expr value)
(defstruct binary-expr op left right)
(defstruct unary-expr op rand)

;; This function takes an operator (represented by a symbol) as an
;; argument and returns an action function for cl-yacc productions
;; that creates binary expression with that operator.
(defun binary (op) 
  (lambda (x y z) (make-binary-expr :op op :left x :right z)))

(defun unary (op)
  (lambda (x y) (make-unary-expr :op op :rand y)))

;; The parser.
(define-parser *expr-parser*
  (:start-symbol expr)
  (:terminals (IDENT
               TRUE FALSE
               AND OR XOR NOT
	       LPAREN RPAREN))
  (:precedence ((:RIGHT NOT) 
               (:LEFT AND)
               (:LEFT OR XOR)
               ))
  (expr 
   (unary-expr #'identity)
   (expr AND expr (binary 'AND))
   (expr OR expr (binary 'OR))
   (expr XOR expr (binary 'XOR)))
  (unary-expr 
   (primary-expr #'identity)
   (NOT expr (unary 'NOT)))
  (primary-expr
   (TRUE (lambda (x) (make-boolean-const-expr :value 'TRUE)))
   (FALSE (lambda (x) (make-boolean-const-expr :value 'FALSE)))
   (IDENT (lambda (x) (make-var-expr :value x)))
   (LPAREN expr RPAREN (lambda (x y z) y))))
