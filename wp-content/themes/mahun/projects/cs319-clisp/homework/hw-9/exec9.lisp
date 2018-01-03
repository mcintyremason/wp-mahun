(defun lval-lookup (var env)
  (assoc var env))

(defun rval-lookup (var env)
  (let ((lval (lval-lookup var env)))
    (if (null lval) 
	nil
      (cadr lval))))

(defun assign (var val env)
  (let ((lval (lval-lookup var env)))
    (if (null lval)
	(error (format nil "Undeclared variable: ~a" var))
      (progn
	(setf (cadr lval) val)
	env))))

(defun exec-stmt-list (stmts env)
  (if (null stmts)
      env
    (exec-stmt-list (cdr stmts) (exec-stmt (car stmts) env))))

(defun make-write-format (expr env)
  (if (null expr)
      "~8D"
    (format nil "~~~aD" (eval-expr expr env))))

(defun make-writeln-format (expr env)
  (if (null expr)
      "~8D~%"
    (format nil "~~~aD~~%" (eval-expr expr env))))

(defun declare-vars (vars vals env)
  (if (null vals)
      (append (mapcar (lambda (x) (list x nil)) vars) env)
    (append (mapcar #'list vars vals) env)))

(defun declare-procs (decls env)
  (append (mapcar (lambda (x) (list (proc-decl-name x) x)) decls) 
	  env))

(defun exec-stmt (stmt env)
  (etypecase 
   stmt
   (write-stmt (progn
		 (format *standard-output* 
			 (make-write-format (write-stmt-width stmt) 
					    env)
			 (eval-expr (write-stmt-arg stmt) env))
		 env))
   (writeln-stmt (if (null (writeln-stmt-arg stmt))
		     (progn (format *standard-output* "~%") env)
		   (progn (format *standard-output* 
				  (make-writeln-format 
				   (writeln-stmt-width stmt) 
				   env)
				  (eval-expr (writeln-stmt-arg stmt) 
					     env))
			  env)))
   (assign-stmt (assign (assign-stmt-lval stmt)
			(eval-expr (assign-stmt-rval stmt) env) env))
   (if-stmt (if (eq (eval-expr (if-stmt-test stmt) env) 'TRUE)
		(exec-stmt (if-stmt-body stmt) env)
	      env))
   (if-else-stmt (if (eq (eval-expr (if-else-stmt-test stmt) env) 
			 'TRUE)
		     (exec-stmt (if-else-stmt-if-part stmt) env)
		   (exec-stmt (if-else-stmt-else-part stmt) env)))
   (cmpd-stmt (exec-stmt-list (cmpd-stmt-stmts stmt) env))
   (while-stmt (if (eq (eval-expr (while-stmt-test stmt) env) 'TRUE)
		   (exec-stmt stmt (exec-stmt (while-stmt-body stmt) 
					      env))
		 env))
   (proc-call-stmt 
    (let* ((decl (rval-lookup (proc-call-stmt-name stmt) env))
	   (actual-vals (mapcar (lambda (x) (eval-expr x env)) 
				(proc-call-stmt-actuals stmt)))
	   (formals (proc-decl-formals decl))
	   (locals (proc-decl-locals decl)))
      (exec-stmt (proc-decl-body decl) 
		 (declare-vars locals 
			       nil 
			       (declare-vars formals 
					     actual-vals 
					     env)))
      env))
   (empty-stmt env)))

(defun exec-program (prog env)
  (exec-stmt (program-body prog) 
	     (declare-vars (program-globals prog) 
			   nil 
			   (declare-procs (program-proc-decls prog) 
					  env))))
