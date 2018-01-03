;; Mason McIntyre
;; hw-4.lisp
;; 01-28-2015

(defun power (m n)
  (if (= n 0) 1
    (* m (power m (- n 1)))))

(defun sum (lst)
  (if(null lst) 0
    (+ (car lst) (sum (cdr lst)))))

(defun product (lst)
  (if(null lst) 1
    (* (car lst) (product (cdr lst)))))

(defun powers-of-two (lst)
  (if (null lst) ()
    (mapcar (lambda (x) (power 2 x)) lst)))

(defun wrap (lst)
  (mapcar #'list lst))

(defun add-pairs (lst1 lst2)
  (mapcar #'+ lst1 lst2))

(defun zip (lst1 lst2)
  (mapcar #'list lst1 lst2))

(defun duplicate (n x)
  (if (= n 0) ()
    (cons x (duplicate (- n 1) x))))

(defun swap (lst)
  (mapcar (lambda (x) (list (cadr x) (car x))) lst))

