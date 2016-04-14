.class public Calculator
.super java/lang/Object

; default constructor
.method public <init>()V
   aload_0
   invokespecial java/lang/Object/<init>()V
   return
.end method

; centigrade to Farenheit conversion
; F = 9 * C / 5 + 32
.method public c2f(F)F
   .limit stack 4
   .limit locals 2
   fload 1
   ldc 9.0
   fmul
   ldc 5.0
   fdiv
   ldc 32.0
   fadd
   freturn
.end method

; Farenheit to centigrade conversion
; C = 5 * (f - 32) / 9
.method public f2c(F)F
   .limit stack 4
   .limit locals 2
   fload 1
   ldc 32.0
   fsub
   ldc 5.0
   fmul
   ldc 9.0
   fdiv
   freturn
.end method
