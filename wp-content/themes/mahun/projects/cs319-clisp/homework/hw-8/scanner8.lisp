;; The lexical analyzer for imp2. Tokens are represented by multiple values. 
;; The first value is the token type (a symbol) and the second
;; is the token value. Integer tokens and identifiers have values.
;;
;; The token types are;
;;  PLUS      +
;;  MINUS     -
;;  TIMES     *
;;  DIV       div
;;  MOD       mod
;;  LPAREN    (
;;  RPAREN    )
;;  LT        <
;;  LE        <=
;;  GT        >
;;  GE        >=
;;  EQ        =
;;  NE        <>
;;  SEMICOLON ;
;;  IF        if
;;  THEN      then
;;  ELSE      else
;;  BEGIN     begin
;;  END       end
;;  WHILE     while
;;  DO        do
;;  WRITE     write
;;  WRITELN   writeln
;;  INT-CONST [0-9]*
;;  IDENT     [a-zA-Z][a-zA-Z0-9]*
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
  (values 'INT-CONST (parse-integer (coerce (reverse lexeme-list) 'string))))

;; scan-int-const: Scans an integer constant, returning an integer token.
(defun scan-int-const (stream lexeme-list)
  (let ((first (peek-c stream)))
    (cond ((null first) (int-const-token lexeme-list))
	  ((digit-char-p first) 
	   (read-c stream)
	   (scan-int-const stream (cons first lexeme-list)))
	  (t (int-const-token lexeme-list)))))

;; check-reserved: Takes a list of characters returned by scan-ident, Determines 
;; whether the potential identifier is really a reserved word,  and returns the 
;; appropriate token.
(defun check-reserved (lexeme-list)
  (let ((reserved '(DIV MOD WRITE WRITELN IF THEN ELSE WHILE DO FOR TO BEGIN END))
	(lexeme (intern 
                 (string-upcase (coerce (reverse lexeme-list) 'string)))))
    (if (member lexeme reserved)
	lexeme
      (values 'IDENT lexeme))))

;; scan-ident: Scans for an identifier or reserved word and returns a token.
(defun scan-ident (stream lexeme-list)
  (let ((first (peek-c stream)))
    (cond ((null first) (check-reserved lexeme-list))
	  ((alphanumericp first)
	   (read-c stream)
	   (scan-ident stream (cons first lexeme-list)))
	  (t (check-reserved lexeme-list)))))

;; scan-colon: This function is called after a colon has been seen. It determines
;; whether it is followed by an equals sign. If so, it returns an ASSIGN token. 
;; Otherwise, it's an error.
(defun scan-colon (stream)
  (case (peek-c stream) 
	(#\= (read-c stream) 'ASSIGN)
	(t 'COLON)))

;; scan-lt: This function is called after < has been seen. If the < is followed
;; by = it returns a LE token. If it is followed by > it returns a NE token.
;; Otherwise, it returns a LT token.
(defun scan-lt (stream)
  (case (peek-c stream) 
	(#\= (read-c stream) 'LE)
	(#\> (read-c stream) 'NE)
	(t 'LT)))

;; scan-gt: This function is called after > has been seen. It determines
;; whether it is followed by an equals sign. If so, it returns a GE token. 
;; Otherwise, it returns a GT token.
(defun scan-gt (stream)
  (case (peek-c stream) 
	(#\= (read-c stream) 'GE)
	(t 'GT)))

;; scan: Returns the next token in the input stream. At end of file it returns nil.
(defun scan (stream)
  (let ((first (read-c stream)))
    (cond ((null first) nil)
	  ((whitespace-char-p first) (scan stream))
	  ((digit-char-p first) (scan-int-const stream (list first)))
	  ((alpha-char-p first) (scan-ident stream (list first)))
	  (t (ecase first
		    (#\+ 'PLUS)
		    (#\- 'MINUS)
		    (#\* 'TIMES)
		    (#\( 'LPAREN)
		    (#\) 'RPAREN)
		    (#\; 'SEMICOLON)
		    (#\) 'RPAREN)
                    (#\, 'COMMA)
		    (#\= 'EQ)
		    (#\< (scan-lt stream))
		    (#\> (scan-gt stream))
		    (#\: (scan-colon stream)))))))
