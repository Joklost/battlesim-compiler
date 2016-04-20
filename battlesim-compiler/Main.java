
import java.io.*;


import java.util.Scanner;


// BattleSim automatically generated code file.


public class Main {

    
public static void Print(String s) {

        
System.out.print(s);

    
}

    
public static void PrintLine(String s) {

        
System.out.println(s);
    
}

    
public static String Input() {

        
Scanner sc = new Scanner(System.in);

        
return sc.nextLine();

    
}

    
public static int ConvertToInteger(String s) {

        
if (!isIntegerParsable(s)) {

            
System.err.println("String " + s + " is not an Integer.");

            
return 0;

        
} else {

            
return Integer.parseInt(s);

        
}

    
}

    
public static boolean isIntegerParsable(String str) {

        
try {

            
Integer.parseInt(str);

            
return true;

        
} catch (NumberFormatException nfe) {}

        
return false;

    
}

    
public static 
int
 
Factorial
 (
int
 
n
) {

        
if (
(
n
<=
1
)
) {

            
return 
1
;

        
} 


        
return 
(
Factorial
(
(
n
-
1
)
)
*
n
)
;

    
}

    
public static 
int
 
Three
 (
) {

        
return 
3
;

    
}

    
public static 
void
 
PrintTen
 (
) {

        
int
 
i
;

        
i
=
0
;

        
while (
(
i
<
10
)
) {

            
PrintLine
(
(
i
+
""
)
)
;

            
i
=
(
i
+
1
)
;

        
}

        
PrintLine
(
(
Three
(
)
+
""
)
)
;

    
}

    
public static 
void
 
math
 (
int
 
n
) {

        
double
 
k
;

        
k
=
19.8
;

        
PrintLine
(
"TEST MATH"
)
;

        
PrintLine
(
(
(
42
%
n
)
+
""
)
)
;

        
PrintLine
(
(
(
42
/
n
)
+
""
)
)
;

        
PrintLine
(
(
(
n
++)
+
""
)
)
;

        
PrintLine
(
(
(
(
n
++)
+
(
n
++)
)
+
""
)
)
;

        
PrintLine
(
(
(
n
--)
+
""
)
)
;

        
PrintLine
(
(
(
k
/
n
)
+
""
)
)
;

        
PrintLine
(
(
(
n
/
k
)
+
""
)
)
;

        
PrintLine
(
(
(
n
*
k
)
+
""
)
)
;

        
PrintLine
(
(
(
(
n
++)
*
(
k
--)
)
+
""
)
)
;

        
PrintLine
(
(
(
(
k
++)
%
(
n
++)
)
+
""
)
)
;

        
PrintLine
(
(
(
(-
20
)
+
k
)
+
""
)
)
;

        
PrintLine
(
(
(
n
+
(-
200
)
)
+
""
)
)
;

        
PrintLine
(
(
(
(
n
--)
+
(-
900
)
)
+
""
)
)
;

        
PrintLine
(
(
(
(
(
(
n
--)
+
(-
900
)
)
+
""
)
+
100
)
+
""
)
)
;

        
PrintLine
(
(
(
(
(
n
--)
+
(-
900
)
)
+
""
)
+
100
)
)
;

        
PrintLine
(
(
(
(
(
n
--)
+
(-
900
)
)
+
""
)
+
(
(-
3
)
+
""
)
)
)
;

        
PrintLine
(
(
(
(
n
--)
%
(
k
++)
)
+
""
)
)
;

    
}

    
public static 
void
 
testFuckingOperator
 (
) {

        
boolean
 
no
 = false
;

        
boolean
 
da
 = false
;

        
no
=
false
;

        
da
=
true
;

        
PrintLine
(
"Test bool 'n shit"
)
;

        
PrintLine
(
(
no
+
""
)
)
;

        
PrintLine
(
(
da
+
""
)
)
;

        
PrintLine
(
(
(
da
&&
da
)
+
""
)
)
;

        
PrintLine
(
(
(
da
&&
no
)
+
""
)
)
;

        
PrintLine
(
(
(
da
||
no
)
+
""
)
)
;

        
PrintLine
(
(
(
no
&&
no
)
+
""
)
)
;

        
PrintLine
(
(
(
(!
da
)
&&
da
)
+
""
)
)
;

        
PrintLine
(
(
(
(!
no
)
&&
da
)
+
""
)
)
;

        
PrintLine
(
(
(!
(!
(!
(!
(!
no
)
)
)
)
)
+
""
)
)
;

        
PrintLine
(
(
(
(!
(!
da
)
)
||
no
)
+
""
)
)
;

        
PrintLine
(
(
(
(!
(!
da
)
)
||
(
da
&&
(!
no
)
)
)
+
""
)
)
;

    
}

    
public static void main(String[] args) {

        
String
 
s
;

        
int
 
a
, 
b
;

        
PrintLine
(
"Skriv tal fra 0 til 9"
)
;

        
PrintTen
(
)
;

        
b
=
Three
(
)
;

        
PrintLine
(
"Factorial of 10"
)
;

        
PrintLine
(
(
Factorial
(
10
)
+
""
)
)
;

        
math
(
8
)
;

        
testFuckingOperator
(
)
;

    
}


}

