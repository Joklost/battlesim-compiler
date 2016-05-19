; !!!!! This does not work!
; !!!!! Gets compile error:
;  Error: Main method not found in class HelloWeb, please define the main method as:
;   public static void main(String[] args)
; or a JavaFX application class must extend javafx.application.Application

; Program in Java: (doesn't work either)

; import java.applet.*;
; import java.awt.*;
;
; public class HelloWeb extends Applet
; {
;     private Font font;
;
;     // during initialization, obtain Helvetica BOLD 48 point
;     public void init() {
;         font = new Font("Helvetica", Font.BOLD, 48);
;     }
;
;     // to repaint the applet window, draw the string Hello World
;     // using the font we created earlier.
;     public void paint(Graphics g)
;     {
;         g.setFont(font);
;         g.drawString("Hello Web!", 25, 50);
;     }
; }

.class public HelloWeb
.super java/applet/Applet

; declare a private field called font, holding a java.awt.Font object:
.field private font Ljava/awt/Font;

; init() method - creates new font for use in paint() method
.method public init()V
    .limit stack 6     ; up to six items on the stack

    aload_0            ; this
    ;
    ; new Font("Helvetica", Font.BOLD, 48)
    ;
    new java/awt/Font  ; make new Font instance
    dup                ; dup the instance and call its constructor
    ldc "Helvetica"    ; (passing the constructor the font name as a string,
    iconst_1           ; the Font.BOLD flag,
    bipush 48          ; and the font size 48)
    invokespecial java/awt/Font/<init>(Ljava/lang/String;II)V

    ; stack currently contains (this, font).
    ; now use putfield to assign the font item to this.font
    putfield HelloWeb/font Ljava/awt/Font;
    return
.end method

; paint() method - redraws applets window
.method public paint(Ljava/awt/Graphics;)V
    .limit stack 4
    .limit locals 2    ; two locals ( 0 = this, 1 = Graphics object)
    ;
    ; This is like g.setFont(this.font);
    ;
    aload_1            ; Graphics object g
    aload_0            ; this ...
    getfield HelloWeb/font Ljava/awt/Font; ; this.font
    invokevirtual java/awt/Graphics/setFont(Ljava/awt/Font;)V

    ; now do: g.drawString("HelloWorld!", 25, 50);
    aload_1            ; Graphics object g
    ldc "Hello Web!"
    bipush 25
    bipush 50
    invokevirtual java/awt/Graphics/drawString(Ljava/lang/String;II)V
    return
.end method

; standard constructor - just calls Applet's constructor
.method public <init>()V
    aload_0
    invokespecial java/applet/Applet/<init>()V
    return
.end method