;; The main program for the imp1 interpreter.

(load 'yacc)
(use-package 'yacc)

(load 'scanner8)
(load 'parser8)
(load 'eval8)
(load 'exec8)

;; interp: Opens the source file and executes the program with
;; an (initially) empty environment.
(defun interp (file)
  (let ((stream (open file)))
    (exec-program (parse-with-lexer (lambda () (scan stream))
				    *prog-parser*)
		  nil)))

;; Run the interpreter on a file given by a command line argument.
(interp (car *args*))
