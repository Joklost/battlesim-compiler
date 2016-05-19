; Java code:
; public class HelloWorld {
;     public static void main(String args[]) {
;         System.out.println("Hello World!");
;     }
; }

.class public HelloWorld
.super java/lang/Object

; specify the constructor method for the Example class

.method public <init>()V
    ; just call Object's constrctor
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

; specify the "main" method - this prints "Hello World"

.method public static main([Ljava/lang/String;)V
    ; set limits used by this method
    .limit stack 2

    ; Push the output stream and the string "Hello World" onto the stack,
    ; then invoke the println method:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Hello World!"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    return
.end method
