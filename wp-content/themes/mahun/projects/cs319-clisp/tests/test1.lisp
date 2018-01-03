(defstruct i-cont)
(defstruct power-cont k m)
(defstruct product-cont k x)
(defstruct product1-cont k x)


(defun apply-cont (k y)
  (etypecase k
             (i-cont y)
             (power-cont (apply-cont (power-cont-k k) (* (power-cont-m k) y)))
             (product-cont (apply-cont (product-cont-k k) (* (product-cont-x k) y)))
             (product1-cont (product-scps (product1-cont-x k) (product1-cont-k k)))
             )
)

(defun power (m n)
  (if (= n 0) 1
      (* m (power m (- n 1)))))

(defun power-cps (m n &optional (k #'identity))
  (if (= n 0) (funcall k 1)
    (power-cps m (- n 1) (lambda (x) (funcall k (* m x)))))
) 

(defun power-scps (m n &optional (k (make-i-cont)))
  (if (= n 0) (apply-cont k 1)
    (power-scps m (- n 1) (make-power-cont :k k :m m)))
) 

(defun power-lcps (m n)
  (setq k (make-i-cont))
  (loop while (not (= n 0)) do
        (setq k (make-power-cont :k k :m m))
        (setq n (- n 1))
        )
  (apply-cont k 1)
)

(defun product-scps (lst &optional (k (make-i-cont)))
  (cond
   ((null lst) (apply-cont k 1))
   ((integerp lst) (apply-cont k lst))
   ((integerp (car lst)) (product-scps (cdr lst) (make-product-cont :k k :x (car lst))))
   ((consp lst) (product-scps (cdr lst) (make-product1-cont :k k :x (car lst)))))
)

(defun product-cps (lst &optional (k #'identity))
  (cond
   ((null lst) (funcall k 1))
   ((integerp lst) (funcall k lst))
   ((consp lst) (product-cps (car lst) (lambda (x) (product-cps (cdr lst) (lambda (y) (funcall k (* x y))))))))
)

(defun product (lst)
  (if(null lst) 1
        (* (car lst) (product (cdr lst)))))

(defun powers-of-two-cps (lst &optional (k #'identity))
  (cond 
   ((null lst) (funcall k 1))
   ((integerp lst) (power-cps 2 lst))
   ((consp lst) (powers-of-two-cps (car lst) (lambda (x) (powers-of-two-cps (cdr lst) (lambda (y) (funcall k (cons x y)))))))
   )
)

   ;((consp lst) (product-cps (car lst) (lambda (x) (product-cps (cdr lst) (lambda (y) (funcall k (* x y))))))))

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
