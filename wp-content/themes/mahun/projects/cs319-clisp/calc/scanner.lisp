;; scanner.lisp - The lexical analyzer for the calculator program.

;; A token is a property list with :type and :value keys. If the value is 
;; not important, then nil is stored.

;; *buffer* - Holds the next token. If the value is nil then
;;            the buffer is empty.
(defvar *buffer* nil)

;; peek-t - Returns, but does not discard, the next token.
(defun peek-t ()
  (if (null *buffer*) (fill-buffer))  
  *buffer*)

;; read-t - Returns and discards the next token.
(defun read-t ()
  (prog1 *buffer* (setq *buffer* nil)))

;; read-c - Reads a character from standard input, returning nil 
;;          at end of file.
(defun read-c ()
  (read-char *standard-input* nil nil))

;; peek-c - Peeks at the next character in standard input without
;;          removing it from the stream. Returns nil at end of file.
(defun peek-c ()
  (peek-char nil *standard-input* nil nil))

;; read-digits - Reads digits as characters and returns a list.
(defun read-digits ()
  (let ((c (peek-c)))
    (cond
     ((null c) nil)
     ((digit-char-p c) (read-c) (cons c (read-digits)))
     (t nil))))

;; make-integer - Converts a list of characters (digits) into an integer.
(defun make-integer (digits)
  (read-from-string (coerce digits 'string)))

;; store-token - Stores a token in the buffer.
(defun store-token (type &optional (value nil))
  (setq *buffer* (list :type type :value value)))

;; fill-buffer - Fills the buffer with the next token.
(defun fill-buffer ()
  (let ((c (peek-c)))
    (cond ((null c) (store-token 'eof))
          ((char= c #\space) (read-c) (fill-buffer))
          ((digit-char-p c) 
           (store-token 'integer (make-integer (read-digits))))
          ((char= c #\newline) (read-c) (store-token 'eol))
          ((char= c #\() (read-c) (store-token 'left-paren))
          ((char= c #\)) (read-c) (store-token 'right-paren))
          ((char= c #\+) (read-c) (store-token 'plus))
          ((char= c #\-) (read-c) (store-token 'minus))
          ((char= c #\*) (read-c) (store-token 'times))
          ((char= c #\/) (read-c) (store-token 'div))
          ((char= c #\%) (read-c) (store-token 'mod))
          (t (flush-line) (error (format nil "Syntax error: invalid character [~a]" c))))))

;; flush-line - Discards the remainder of an input line. This function
;;              is called after an error.
(defun flush-line ()
  (let ((c (read-c)))
    (cond ((null c) nil)
          ((char= c #\newline) nil)
          (t (flush-line)))))

