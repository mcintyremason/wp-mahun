; Mason McIntyre
; hw-1.lisp
; 01-15-2015

; Converts a temperature in Fahrenheit to Celsius.
(defun ftoc (temp) 
  (round (* (/ (float(- temp 32)) 9) 5)))

; Computes the sum and the multiplication of the two arguments passed, returning them in a list.
(defun sumprod (x y)
  (list (+ x y) (* x y)))

; Computes the zeros of a quadratic function, given the coeficients of the function
(defun roots (a b c)
  (let ((rad (- (* b b) (* 4 (* a c)))))
    (cond
      ((< rad 0) 
       ())
      ((= rad 0) 
       (list (float(/ (* b -1) (* a 2)))))
      ((> rad 0) 
       (list (float(/ (- (* b -1) (sqrt rad)) (* a 2))) (float(/ (+ (* b -1) (sqrt rad)) (* a 2)))))
    )
  )
)
