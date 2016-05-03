package com.BattleSim;
import java.io.*;
import java.util.*;
import java.util.Scanner;
// BattleSim automatically generated code file.
public class Coord {
    double X = 0.0;
    double Y = 0.0;
    public String ToString () {
        return (((("("+X)+",")+Y)+")");
    }
}
package com.BattleSim;
import java.io.*;
import java.util.*;
import java.util.Scanner;
// BattleSim automatically generated code file.
public class Soldier {
    int Magazines = 0;
    Coord Pos = new Coord();
    public void SetMagazines (int mags) {
        Magazines=mags;
    }
    public void Teleport (Coord target) {
        Pos=target;
    }
}
package com.BattleSim;
import java.io.*;
import java.util.*;
import java.util.Scanner;
// BattleSim automatically generated code file.
public class Declarations {
    public static     boolean da = false;
    public static     boolean no = false;
    public static     double k = 0.0;
    public static     int foo = 0;
    public static     String s = "";
    public static     int a = 0;
    public static     ArrayList<String> list = new ArrayList<>();
    public static     int i = 0;
    public static     Soldier Charlie = new Soldier();
    public static     String[] array = new String[3];
    public Declarations() {
        i=5;
    }
}
package com.BattleSim;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import static com.BattleSim.Declarations.*;
// BattleSim automatically generated code file.
public class Main {
    public static void PrintLine (String s) {
        System.out.println(s);
    }
    public static void Print (String s) {
        System.out.print(s);
    }
    public static String Input () {
        String s = "";
        Scanner sc = new Scanner(System.in);
        s = sc.nextLine();
        return s;
    }
    public static boolean IsIntegerParseable (String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }
    public static boolean IsDecimalParseable (String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }
    public static int ConvertToInteger (String s) {
        int i = 0;
        if (!IsIntegerParseable(s)) {
            System.err.println("String '" + s + "' is not an Integer.");
            return 0;
        } else {
            i = Integer.parseInt(s);
        }
        return i;
    }
    public static double ConvertToDecimal (String s) {
        double d = 0.0;
        if (!IsDecimalParseable(s)) {
            System.err.println("String '" + s + "' is not a Decimal.");
            return 0;
        } else {
            d = Double.parseDouble(s);
        }
        return d;
    }
    public static int Factorial (int n) {
        if ((n<=1)) {
            return 1;
        } 
        return (Factorial((n-1))*n);
    }
    public static int ReturnInt () {
        return 42;
    }
    public static double ReturnDecimal () {
        return 3.5;
    }
    public static void TestWhile () {
        i=0;
        while ((i<10)) {
            PrintLine(((i+1)+""));
            i=(i+1);
        }
        i=10;
        while ((i>0)) {
            PrintLine((i+""));
            i=(i-1);
        }
    }
    public static void TestArrayForeach () {
        array[0]="Hej";
        array[1]="Hej igen";
        array[2]="Hej tredje gang";
        for (String say : array) {
            PrintLine(say);
        }
    }
    public static void TestInput () {
        while (true) {
            Print("Enter string: ");
            s=Input();
            PrintLine(("Your string was: "+s));
            a=ConvertToInteger(s);
        }
    }
    public static void math (int n) {
        k=19.8;
        foo=42;
        PrintLine("TEST MATH");
        PrintLine(((42+n)+""));
        PrintLine(((42-n)+""));
        PrintLine(((42/n)+""));
        PrintLine(((42*n)+""));
        PrintLine(((42%n)+""));
        PrintLine(((42+(n++))+""));
        PrintLine(((42+(n--))+""));
        PrintLine(((42-(n++))+""));
        PrintLine(((42-(n--))+""));
        PrintLine(((42/(n++))+""));
        PrintLine(((42/(n--))+""));
        PrintLine(((42*(n++))+""));
        PrintLine(((42*(n--))+""));
        PrintLine(((42%(n++))+""));
        PrintLine(((42%(n--))+""));
        PrintLine(((42.0+k)+""));
        PrintLine(((42.0-k)+""));
        PrintLine(((42.0/k)+""));
        PrintLine(((42.0*k)+""));
        PrintLine(((42.0%k)+""));
        PrintLine(((42.0+(k++))+""));
        PrintLine(((42.0+(k--))+""));
        PrintLine(((42.0-(k++))+""));
        PrintLine(((42.0-(k--))+""));
        PrintLine(((42.0/(k++))+""));
        PrintLine(((42.0/(k--))+""));
        PrintLine(((42.0*(k++))+""));
        PrintLine(((42.0*(k--))+""));
        PrintLine(((42.0%(k++))+""));
        PrintLine(((42.0%(k--))+""));
        PrintLine(((n+k)+""));
        PrintLine(((n-k)+""));
        PrintLine(((n/k)+""));
        PrintLine(((n*k)+""));
        PrintLine(((n%k)+""));
        PrintLine(((n+(k++))+""));
        PrintLine(((n+(k--))+""));
        PrintLine(((n-(k++))+""));
        PrintLine(((n-(k--))+""));
        PrintLine(((n/(k++))+""));
        PrintLine(((n/(k--))+""));
        PrintLine(((n*(k++))+""));
        PrintLine(((n*(k--))+""));
        PrintLine(((n%(k++))+""));
        PrintLine(((n%(k--))+""));
        PrintLine((((n++)+k)+""));
        PrintLine((((n--)+k)+""));
        PrintLine((((n++)-k)+""));
        PrintLine((((n--)-k)+""));
        PrintLine((((n++)/k)+""));
        PrintLine((((n--)/k)+""));
        PrintLine((((n++)*k)+""));
        PrintLine((((n--)*k)+""));
        PrintLine((((n++)%k)+""));
        PrintLine((((n--)%k)+""));
        PrintLine((foo+""));
        foo+=8;
        PrintLine((foo+""));
        foo-=8;
        PrintLine((foo+""));
        foo/=8;
        PrintLine((foo+""));
        foo%=8;
        PrintLine((foo+""));
        foo*=8;
        PrintLine((foo+""));
        PrintLine((foo+""));
        k+=8;
        PrintLine((k+""));
        k-=8;
        PrintLine((k+""));
        k/=8;
        PrintLine((k+""));
        k%=8;
        PrintLine((k+""));
        k*=8;
        PrintLine((k+""));
    }
    public static void TestBool () {
        no=false;
        da=true;
        PrintLine((no+""));
        PrintLine((da+""));
        PrintLine(((da&&da)+""));
        PrintLine(((no&&no)+""));
        PrintLine(((da&&no)+""));
        PrintLine(((da||da)+""));
        PrintLine(((no||no)+""));
        PrintLine(((da||no)+""));
        PrintLine((((!da)&&da)+""));
        PrintLine((((!no)&&da)+""));
        PrintLine((((!no)&&(!no))+""));
        PrintLine((((!da)&&(!da))+""));
        PrintLine(((!(!(!(!(!no)))))+""));
        PrintLine((((!(!da))||no)+""));
        PrintLine((((!(!da))||(da&&(!no)))+""));
    }
    public static void TestIf () {
        da=true;
        no=false;
        if (da) {
            PrintLine("da");
        } 
        if (no) {
            PrintLine("no");
        } 
        if (no) {
            PrintLine("no");
        } else {
            PrintLine("else");
        }
        if ((da&&(!no))) {
            PrintLine("da AND NOT no");
        } 
        if (((!da)||no)) {
            PrintLine("NOT da OR no");
        } else if ((((!no)||da)||(1==2))) {
            PrintLine("Else If ((NOT no) OR da) OR (1 == 2)");
        } 
        if (no) {
            PrintLine("no");
        } else if (da) {
            PrintLine("Else If da");
        } 
        if ((1==1)) {
            PrintLine("1 == 1");
        } 
        if (((true||da)||no)) {
            PrintLine("(true OR da) OR no");
        } 
        if ((true||(1==2))) {
            PrintLine("true OR 1 == 2");
        } 
        if ((!no)) {
            PrintLine("NOT no");
        } 
        if ((no||da)) {
            PrintLine("no OR da");
        } 
        if ((da&&da)) {
            PrintLine("da AND da");
        } 
        if (((1==1)&&(true==da))) {
            PrintLine("1 == 1 AND true == da");
        } 
        if ((true==(!false))) {
            PrintLine("true == NOT false");
        } 
        if ((!(true==false))) {
            PrintLine("NOT (true == false)");
        } 
        if ((!(no==da))) {
            PrintLine("NOT (no == da)");
        } 
        if ((!(no==no))) {
            PrintLine("(no == no)");
        } else {
            PrintLine("else");
        }
        if ((1>42)) {
            PrintLine("Suprice 1 er større end 42");
        } else {
            PrintLine("1 er sjovt nok ikke større end 42");
        }
        if (no) {
            PrintLine("no");
        } else if (no) {
            PrintLine("Still no");
        } else if (no) {
            PrintLine("Not at all");
        } else {
            PrintLine("True");
            PrintLine("Multiline statments in if testes");
        }
    }
    public static void TestSwitch () {
        int swinr = 0;
        swinr=ReturnInt();
        switch (swinr) {
            case 1:
                PrintLine("The number was oddly enough one");
                break;
            case 42:
                PrintLine((("Yeah, the number was "+swinr)+""));
                break;
            default:
                PrintLine("This is the default");
                break;
        }
    }
    public static void TestFor () {
        int i = 0;
        i=0;
        for (int i795030_forloop=i; i795030_forloop<10; i795030_forloop++) {
            PrintLine((("Num: "+i)+""));
        }
        i=10;
        for (int i795030_forloop=i; i795030_forloop>10; i795030_forloop--) {
            PrintLine((("Num: "+i)+""));
        }
    }
    public static void TestScope () {
        String hejsa = "";
        hejsa="hejsa";
        boolean scope1 = false;
        boolean scope2 = false;
        boolean scope3 = false;
        boolean scope4 = false;
        boolean scope5 = false;
        scope1=true;
        scope2=true;
        scope3=true;
        scope4=true;
        scope5=true;
        while (scope1) {
            PrintLine(hejsa);
            scope1=false;
            while (scope2) {
                PrintLine(hejsa);
                scope2=false;
                while (scope3) {
                    PrintLine(hejsa);
                    scope3=false;
                    while (scope4) {
                        PrintLine(hejsa);
                        scope4=false;
                        while (scope5) {
                            PrintLine(hejsa);
                            scope5=false;
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        Declarations decl795030_declarationblock = new Declarations();
        PrintLine((Factorial(5)+""));
        PrintLine("Factorial success");
        PrintLine((ReturnInt()+""));
        PrintLine("return Integer success");
        PrintLine((ReturnDecimal()+""));
        PrintLine("return dedimal success");
        TestWhile();
        PrintLine("while success");
        TestArrayForeach();
        PrintLine("array and foreach success");
        math(12);
        PrintLine("math tested success");
        TestBool();
        PrintLine("bool tested success");
        TestIf();
        PrintLine("If tested success");
        TestSwitch();
        PrintLine("Switch tested success");
        TestFor();
        PrintLine("For loops tested success");
        Print("New line \n");
        Print("Test print");
        PrintLine("");
        TestScope();
        PrintLine("Test ting");
        Charlie.Magazines=1;
        PrintLine((Charlie.Magazines+""));
        Charlie.SetMagazines(42);
        PrintLine((Charlie.Magazines+""));
        Coord newCharliePos = new Coord();
        newCharliePos.X=3.0;
        newCharliePos.Y=4.0;
        Charlie.Teleport(newCharliePos);
    }
}
