(load 'yacc)
(use-package 'yacc)

(load 'scanner)
(load 'parser)
(load 'eval)

(defun repl ()
  (format *standard-output* "calc> ")
  (let ((line (read-line *standard-input* nil)))
    (cond ((null line) nil)
	  (t (let* ((stream (make-string-input-stream line))
		    (expr (parse-with-lexer 
			   (lambda () (scan stream))
			   *expr-parser*)))
	       (format *standard-output*  "~a~%" (eval-expr expr)))
	     (repl)))))

(repl)
