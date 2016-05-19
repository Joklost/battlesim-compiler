.class public Main
.super java/lang/Object
.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method
.method public static UnimportantFunction(Ljava/lang/String;)V
    .limit stack 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "I never prints"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method
.method public static main([Ljava/lang/String;)V
    .limit stack 256
    .limit locals 256
    ldc 42
    istore 1

ldc 4
ldc 2
iadd
istore 1

    ldc2_w 4.2
    dstore 2

ldc2_w 4.2
ldc2_w 2.4
dadd
dstore 2

ldc 4
ldc 1
isub
istore 1

ldc2_w 4.2
ldc2_w 1.0
dsub
dstore 2

ldc 4
ldc 1
irem
istore 1

ldc 42
ldc 1
iadd
istore 1

ldc 5
ldc -1
iadd
istore 1

    ldc 2
    istore 1

    ldc2_w 2.0
    dstore 2

ldc 41
ldc 1
iadd
istore 1

ldc 5
ldc 2
iadd
istore 1

bipush 1
istore 4
bipush 10
istore 5
iload 4
ineg
istore 4
Loop4:
iload 5
bipush -1
iadd
istore 5
        getstatic java/lang/System/out Ljava/io/PrintStream;
        ldc "Loop testes"
        invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
iload 5
iload 4
iadd
ifne Loop4
bipush 10
istore 6
bipush 1
istore 7
iload 6
iload 7
istore 6
istore 7
iload 6
ineg
istore 6
Loop6:
iload 7
bipush -1
iadd
istore 7
        getstatic java/lang/System/out Ljava/io/PrintStream;
        ldc "Loop testes DownTo"
        invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
iload 7
iload 6
iadd
ifne Loop6
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/println(I)V
    return
.end method
