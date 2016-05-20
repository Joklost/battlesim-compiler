; Jasmin setup
; Class name
.class public Main
; The class extends java/lang/Object
.super java/lang/Object
; instance initialization method, used to initialize the class
.method public <init>()V
    ; Just call Object's instructor
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method
.method public static PrintHelloWorld(Ljava/lang/String;)V
    .limit stack 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Hello World!"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method
; Specify the 'main' method
.method public static main([Ljava/lang/String;)V
    ; set limits used by this method
    .limit stack 10
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Hello World!"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method
