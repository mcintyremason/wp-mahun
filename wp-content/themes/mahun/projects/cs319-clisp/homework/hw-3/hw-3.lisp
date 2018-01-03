;; Mason McIntyre
;; hw-3.lisp
;; 01-23-2015

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
    (cons (funcall (lambda (x) (power 2 x)) (car lst)) (powers-of-two  (cdr lst)))))

(defun wrap (lst)
  (if (null lst) ()
    (cons (list (car lst)) (wrap (cdr lst)))))

(defun add-pairs (lst1 lst2)
  (cond ((null lst1) ())
        ((null lst2) ())
        (t (cons (+ (car lst1) (car lst2)) (add-pairs (cdr lst1) (cdr lst2))))))

(defun zip (lst1 lst2)
  (cond ((null lst1) ())
        ((null lst2) ())
        (t (cons (list (car lst1) (car lst2)) (zip (cdr lst1) (cdr lst2))))))

(defun duplicate (n x)
  (if (= n 0) ()
    (cons x (duplicate (- n 1) x))))

(defun swap (lst)
  (if (null lst) ()
    (cons (list (cadar lst) (caar lst)) (swap (cdr lst)))))
