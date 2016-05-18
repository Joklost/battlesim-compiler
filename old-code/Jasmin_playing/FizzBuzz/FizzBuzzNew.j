;==================================
; Declearing the class
; ... or just some magic
;==================================
.class public FizzBuzzNew
.super java/lang/Object

;==================================
; Default constructor
;==================================
.method public <init>()V

    aload_0

    invokespecial java/lang/Object/<init>()V

    return

.end method

;==================================
; main function
;==================================
.method public static main([Ljava/lang/String;)V

    ; set limits used by this method
    .limit stack 5
    .limit locals 4

    ; setup local variables:
    ;    1 - the PrintStream object held in java.lang.System.out
    getstatic java/lang/System/out Ljava/io/PrintStream;
    astore_1

    ;    2 - the integer 25 - the counter used in the loop
    bipush 25
    istore_2

    ;==================================
    ; Start the loop
    ;==================================
    Loop:

    ; compute 26 - <local variable 2>, convert this integer to a string,
    ; and store the string result in local variable 3
    bipush 26
    iload_2
    isub    ; stack now contains (26 - <locl variable 2>)

    ;==================================
    ; Test if fizz, FizzBuzz or buzz
    ; if it is fizz we test if it is also
    ; buzz. If not fizz we test if it is
    ; then buzz. If none we goto PrintStream
    ;==================================
        
    ;==================================
    ; Test if fizz
    ; then goto testFizzBuzz
    ;==================================
    ; TODO: Not impelemented yet        
    ; get top of stack
    ; top of stack mod 3
    ; if result != 0 goto: TestFizzBuzz
    goto TestBuzz ; else test if buzz

    ;==================================
    ; Test if FizzBuzz
    ; then goto testFizzBuzz
    ;==================================
    TestFizzBuzz:
    ; TODO: Not impelemented yet        
    ; pop top of stack (??? To get rid of top of stack mod 3 ???)
    ; top of stack mod 5
    ; if result == 0 goto: PrintFizzBuzz
    goto PrintFizz
    
    ;==================================
    ; Test if Buzz
    ; then goto PrintBuzz
    ;==================================
    TestBuzz:
    ; TODO: Not impelemented yey
    ; pop top of stack (??? To get rid of top of stack mod 3 ???)
    ; top of stack mod 5
    ; if result == 0 goto: PrintBuzz
    
    ;==================================
    ; if none of the following
    ; goto PrintString
    ;==================================
    goto PrintString

    ;==================================
    ; Print fizz
    ; only happens if explecit goto
    ; when finished go back in loop
    ;==================================
    PrintFizz:
    ; Push the output stream and the string "Hello World" onto the stack,
    ; then invoke the println method:
    ldc "fizz"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    goto Loop

    ;==================================
    ; Print buzz
    ; only happens if explecit goto
    ; when finished go back in loop
    ;==================================
    PrintBuzz:
    ; print buzz
    ; This is not impelemented yet
    goto Loop

    ;==================================
    ; Print fizzbuzz
    ; only happens if explecit goto
    ; when finished go back in loop
    ;==================================
    PrintFizzBuzz:
    ; print FizzBuzz
    ; This is not impelemented yet
    goto Loop

    ;==================================
    ; Print the number
    ; happens if nothing else is specified
    ; then go back in loop
    ;==================================
    PrintString:
    ; convert to string...
    invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
    astore_3

    ; now print the string in local variable 3
    aload_1 ; push the PrintStream object
    aload_3 ; push the string we just created - then ...
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    ;==================================
    ; When done printing decrement the
    ; counter loop
    ;==================================
    iinc 2 -1
    iload_2
    ifne Loop

    ;==================================
    ; When the function is done
    ; return from it
    ;==================================
    return

 .end method