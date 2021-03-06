(defstruct i-cont)
(defstruct duple-cont k x)
(defstruct substi-cont k x)
(defstruct sum-cont k x)
(defstruct csum-cont k x)

(defun apply-cont (k y)
  (etypecase k
             (i-cont y)
             (duple-cont (apply-cont (duple-cont-k k) (cons (duple-cont-x k) y)))
             (substi-cont (apply-cont (substi-cont-k k) (cons (substi-cont-x k) y)))
             (sum-cont (apply-cont (sum-cont-k k) (+ (sum-cont-x k) y)))
             (csum-cont (sum-scps (csum-cont-x k) (make-sum-cont :k (csum-cont-k k) :x y)))))

(defun sum-lcps (lst)
  (setq k (make-i-cont))
  (cond ((null lst) (apply-cont k 0))
        ((numberp lst) (apply-cont k lst))
        ((symbolp lst) (apply-cont k 0))
        (t (loop while (not (null lst)) do
                 (progn
                   (cond ((numberp (car lst)) (progn 
                                                (setq k (make-sum-cont :k k :x (car lst))) 
                                                (setq lst (cdr lst))))
                         ((symbolp (car lst)) (setq lst (cdr lst))) 
                         ((consp lst) (progn 
                                        (setq k (make-csum-cont :k k :x (car lst))) 
                                        (setq lst (cdr lst)))))))
           (apply-cont k 0)))
  )

(defun sum-scps (lst &optional (k (make-i-cont)))
  (cond ((null lst) (apply-cont k 0))
        ((numberp lst) (apply-cont k lst))
        ((symbolp lst) (apply-cont k 0))   
        ((numberp (car lst)) (sum-scps (cdr lst) (make-sum-cont :k k :x (car lst))))
        ((symbolp (car lst)) (sum-scps (cdr lst) k))
        ((consp lst) (sum-scps (cdr lst) (make-csum-cont :k k :x (car lst))))
        )
  )

(defun sum-cps (lst &optional (k #'identity))
  (cond ((null lst) (funcall k 0))
        ((numberp lst) (funcall k lst))
        ((symbolp lst) (funcall k 0))   
        ((numberp (car lst)) (sum-cps (cdr lst) (lambda (x) (funcall k (+ (car lst) x)))))
        ((symbolp (car lst)) (sum-cps (cdr lst) k))
        ((consp lst) (sum-cps (car lst) (lambda (x) (sum-cps (cdr lst) (lambda (y) (funcall k (+ x y)))))))
        (t (sum-cps (cdr lst) k))))

(defun sum (lst)
  (cond ((null lst) 0)
        ((numberp lst) lst)
        ((symbolp lst) 0)
        ((numberp (car lst)) (+ (car lst) (sum (cdr lst))))
        ((symbolp (car lst)) (sum (cdr lst)))
        ((consp lst) (+ (sum (car lst)) (sum (cdr lst))))
        (t (sum (cdr lst)))
        )      
  )

(defun substi-lcps (lst x y)
  (setq k (make-i-cont))
  (loop while (not (null lst)) do
        (progn
          (if (eq (car lst) x) (setq k (make-substi-cont :k k :x y))
            (setq k (make-substi-cont :k k :x (car lst))))
          (setq lst (cdr lst))))
  (apply-cont k lst)
  )

(defun substi-scps (lst x y &optional (k (make-i-cont)))
  (cond ((null lst) (apply-cont k nil))
        ((eq (car lst) x) (substi-scps (cdr lst) x y (make-substi-cont :k k :x y)))
        (t (substi-scps (cdr lst) x y (make-substi-cont :k k :x (car lst))))))

(defun substi-cps (lst x y &optional (k #'identity))
  (cond ((null lst) (funcall k lst))
        ((eq (car lst) x) (substi-cps (cdr lst) x y (lambda (z) (funcall k (cons y z)))))
        (t (substi-cps (cdr lst) x y (lambda(z) (funcall k (cons (car lst) z)))))))

(defun substi (lst x y)
  (cond ((null lst) nil)
        ((eq (car lst) x) (cons y (substi (cdr lst) x y)))
        (t (cons (car lst) (substi (cdr lst) x y)))))

(defun duple-lcps (n x)
  (setq k (make-i-cont))
  (loop while (not (= n 1)) do
        (progn
          (setq k (make-duple-cont :k k :x x))
          (setq n (- n 1))))
  (apply-cont k (list x))
  )

(defun duple-scps (n x &optional (k (make-i-cont)))
  (cond ((= n 0) (apply-cont k nil))
        ((= n 1) (duple-scps (- n 1) (list x) (make-duple-cont :k k :x x)))
        (t (duple-scps (- n 1) x (make-duple-cont :k k :x x)))))

(defun duple-cps (n x &optional (k #'identity))
  (if (= n 0) (funcall k nil)
    (duple-cps (- n 1) x (lambda (y) (funcall k (cons x y))))))

(defun duple (n x)
  (cond ((= n 0) nil)
        ((= n 1) (list x))
        (t (cons x (duple (- n 1) x)))))
