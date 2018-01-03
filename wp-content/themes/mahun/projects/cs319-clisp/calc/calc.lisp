;; calc - A calculator for integer operations.

(load 'scanner)
(load 'parser)
(load 'tree)
(load 'eval)

;; repl - The read-eval-print loop.
(defun repl ()
  ;; Print a prompt.
  (format *standard-output* "> ")
  (handler-case
   ;; Look at the next token.
   (let* ((token (peek-t))
          (type (getf token :type)))
     (if (not (eq type 'eof))
         (progn
           ;; Parse a line of input, evaluate it, and print the result.
           (let ((v (eval-expr (parse-line))))
             (if (not (null v))
                 ;; A null value indicates an empty line.
                 (format *standard-output* "~a~%" v)))
           ;; Loop.
           (repl))))
   (error (e) 
          ;; Print the error message and resume.
          (format *standard-output* "~a~%" e)
          (repl))))

(repl)
