; Mason McIntyre
; 02-04-2015
; hw-5.lisp

(defun split (str delim)
  (labels ((splith (chars delims word words) 
                 (if (null chars) (cons word words) 
                   (if (member (car chars) delims) (splith (cdr chars) delims nil (cons word words))
                     (splith (cdr chars) delims (cons (car chars) word) words)))))          
          (funcall #'reverse (mapcar (lambda (lst) (coerce (reverse lst) 'string)) (splith (coerce str 'list) (coerce delim 'list) () ())))))

(defun nnintersection (s1 s2)
(cond ((null s1) nil)
        ((null s2) nil)
        ((member (car s1) s2) (cons (car s1) (nnintersection (cdr s1) s2)))
        (t (if (null (cdr s1)) (nnintersection s1 (cdr s2)) 
             (nnintersection (cdr s1) s2)))))

(defun nnunion (s1 s2)
(cond ((null s1) s2)
        ((null s2) s1)
        ((member (car s1) s2) (nnunion (cdr s1) s2))
        (t (if (null (cdr s1)) (cons (car s2) (nnunion s1 (cdr s2))) 
             (cons (car s1) (nnunion (cdr s1) s2))))))

(defun nndifference (s1 s2)
(cond ((null s1) nil)
        ((null s2) s1)
        ((eq (car s1) (car s2)) (nndifference (cdr s1) (cdr s2)))
        ((member (car s1) s2) (nndifference s1 (cdr s2)))
        (t (cons (car s1) (nndifference (cdr s1) s2)))))


(defun sintersection (s1 s2)
(cond ((null s1) nil)
        ((null s2) nil)
        ((eq (car s1) (car s2)) (cons (car s1) (sintersection (cdr s1) (cdr s2))))
        ((string< (car s1) (car s2)) (sintersection (cdr s1) s2))
        ((string< (car s2) (car s1)) (sintersection s1 (cdr s2)))
        ((null (cdr s1)) (if (not (null (cdr s2))) (sintersection s1 (cdr s2)))) 
        ((null (cdr s2)) (if (not (null (cdr s1))) (sintersection (cdr s1) s2)))))

(defun sunion (s1 s2)
(cond ((null s1) s2)
        ((null s2) s1)
        ((eq (car s1) (car s2)) (cons (car s1) (sunion (cdr s1) (cdr s2))))
        ((string< (car s1) (car s2)) (cons (car s1) (sunion (cdr s1) s2)))
        ((string< (car s2) (car s1)) (cons (car s2) (sunion s1 (cdr s2))))
        ((null (cdr s1)) (if (not (null (cdr s2))) (sunion s1 (cdr s2)))) 
        ((null (cdr s2)) (if (not (null (cdr s1))) (sunion (cdr s1) s2)))))


(defun sdifference (s1 s2)
(cond ((null s1) nil)
        ((null s2) s1)
        ((eq (car s1) (car s2)) (sdifference (cdr s1) (cdr s2)))
        ((and (null (cdr s1)) (not (null (cdr s2)))) (sdifference s1 (cdr s2))) 
        ((and (null (cdr s1)) (null (cdr s2))) (cons (car s1) (sdifference (cdr s1) (cdr s2))))
        ((and (not (null (cdr s1))) (null (cdr s2))) (cons (car s1) (sdifference (cdr s1) s2)))
        ((string< (car s2) (car s1)) (sdifference s1 (cdr s2)))
        (t (cons (car s1) (sdifference (cdr s1) s2)))))
