; public class FizzBuzz {
;     public static void main(String args[]) {
;         int i;
;         for(i = 1; i < 25+1; i++) {
;             if (i % 3 == 0 && i % 5 != 0)
;                 System.out.println("fizz");
;             else if (i % 3 != 0 && i % 5 == 0)
;                 System.out.println("buzz");
;             else if (i % 3 == 0 && i % 5 == 0)
;                 System.out.println("fizzbuzz");
;             else
;                 System.out.println(i);
;         }
;     }
; }

.class public FizzBuzz
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

    ;    2 - the integer 25 - the counter used in the loop
    bipush 25
    istore_2

    ; now loop 25 times printing out number
Loop:

    ; compute 26 - <local variable 2>, convert this integer to a string,
    ; and store the string result in local variable 3
    bipush 26
    iload_2
    isub    ; stack now contains (26 - <locl variable 2>)

    ; Test if fizzbuzz, fizz, buzz or no condition

    goto PrintString

PrintFizz:
    ; Print fizz
    goto Loop
PrintBuzz:
    ; print buzz
    goto Loop
PrintFizzBuzz:
    ; print FizzBuzz
    goto Loop

    PrintString:
    ; convert to string...
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