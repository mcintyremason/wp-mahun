;; The lexical analyzer for the integer calculator. Tokens are represented by
;; multiple values. The first value is the token type (a symbol) and the second
;; is the token value. Only integer tokens have values.
;;
;; The token types are;
;;  PLUS      +
;;  MINUS     -
;;  TIMES     *
;;  DIV       /
;;  MOD       %
;;  LPAREN    (
;;  RPAREN    )
;;  INT-CONST [0-9]*
;;
;;  This lexical analyzer is designed to play well with cl-yacc.

;; peek-c: Returns the next character in the input stream without removing it
;; from the input stream.
(defun peek-c (stream)
  (peek-char nil stream nil))

;; read-c: Removes the next character in the input stream and returns it.
(defun read-c (stream)
  (read-char stream nil))

;; whitespace-char-p: Determines whether a character is whitespace (space, 
;; newline, or tab.
(defun whitespace-char-p (c)
  (case c 
	((#\Space #\Newline #\Tab) t)
	(t nil)))

;; int-const-token: Takes a list of digit characters in reverse order and
;; creates an integer token. The token has multiple values: the type (INT-CONST)
;; and the integer value.
(defun int-const-token (lexeme-list)
  (values 'INT-CONST 
	  (parse-integer (coerce (reverse lexeme-list) 'string))))

;; scan-int-const: Scans an integer constant, returning an integer token.
(defun scan-int-const (stream lexeme-list)
  (let ((first (peek-c stream)))
    (cond ((null first) (int-const-token lexeme-list))
	  ((digit-char-p first) 
	   (scan-int-const stream (cons (read-c stream) lexeme-list)))
	  (t (int-const-token lexeme-list)))))

;; scan: Returns the next token in the input stream. At end of file it returns nil.
(defun scan (stream)
  (let ((first (read-c stream)))
    (cond 
     ((null first) nil)
     ((whitespace-char-p first) (scan stream))            
     ((digit-char-p first) (scan-int-const stream (list first)))
     (t (ecase first 
          (#\+ 'PLUS)
          (#\- 'MINUS)
          (#\* 'TIMES)
          (#\/ 'DIV)
          (#\% 'MOD)
          (#\( 'LPAREN)
          (#\) 'RPAREN))))))
