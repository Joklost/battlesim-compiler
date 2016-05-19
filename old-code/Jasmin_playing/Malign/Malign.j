; WARNING: This one is never supposed to work
;   we try to use an int as an pointer to an Object
;   javac will die but jasmin don't give a shit.

; Java code
; class Malign {
;     public static void main(String args[]) {
;         int x = 100;
;         x.clone();
;     }
; }

.class public Malign
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    bipush 100
    invokevirtual java/lang/Object/clone()Ljava/lang/Object;
    return
.end method