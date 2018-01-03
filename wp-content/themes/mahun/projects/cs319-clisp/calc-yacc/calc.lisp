;; The main program for the integer calculator.

(load 'yacc)
(use-package 'yacc)

(load 'scanner)
(load 'parser)
(load 'eval)

;; repl: The read-eval-print loop for the calculator.
(defun repl ()
  (format *standard-output* "calc> ")
  (let ((line (read-line *standard-input* nil)))
    (cond ((null line) nil)
	  (t (handler-case
              (let* ((stream (make-string-input-stream line))
                     (expr (parse-with-lexer 
                            (lambda () (scan stream))
                            *expr-parser*)))
                (format *standard-output*  "~a~%" (eval-expr expr)))
              (error (x) (format *standard-output* "~a~%" x)))
	     (repl)))))

;; Start the read-eval-print loop.
(repl)
