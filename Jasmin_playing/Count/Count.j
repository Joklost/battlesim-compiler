; public class Count {
;     public static void main(String args[]) {
;         int i;
;         for(i = 0; i < 10; i++) {
;             System.out.println(Java.lang.String.valueOf(i));
;         }
;     }
; }

.class public Count
.super java/lang/Object

; the instance initialization method (as for HelloWorld)
.method public <init>()V
    ; just call Object's initializer
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V

    ; set limits used by this method
    .limit stack 3
    .limit locals 4

    ; setup local variables:

    ;    1 - the PrintStream object held in java.lang.System.out
    getstatic java/lang/System/out Ljava/io/PrintStream;
    astore_1

    ;    2 - the integer 10 - the counter used in the loop
    bipush 10
    istore_2

    ; now loop 10 times printing out number
Loop:

    ; compute 10 - <local variable 2>, convert this integer to a string,
    ; and store the string result in local variable 3
    bipush 10
    iload_2
    isub    ; stack now contains (10 - <locl variable 2>) - convert to string...
    invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
    astore_3

    ; now print the string in local variable 3
    aload_1 ; push the PrintStream object
    aload_3 ; push the string we just created - then ...
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    ; decrement the counter and loop
    iinc 2 -1
    iload_2
    ifne Loop
    return

 .end method