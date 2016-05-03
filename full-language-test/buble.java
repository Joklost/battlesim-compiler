package com.BattleSim;
import java.io.*;
import java.util.*;
import java.util.Scanner;
// BattleSim automatically generated code file.
public class Declarations {
    public Declarations() {
    }
}
package com.BattleSim;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import static com.BattleSim.Declarations.*;
// BattleSim automatically generated code file.
public class Main {
    public static void BubbleSort () {
        int[] a = new int[7];
        a[0]=12;
        a[1]=24;
        a[2]=13;
        a[3]=9;
        a[4]=16;
        a[5]=(-4);
        a[6]=17;
        int n = 0;
        n=7;
        int i, c, t = 0;
        i=0;
        c=0;
        for (int i7950302_forloop=i; i7950302_forloop<(n-1); i7950302_forloop++) {
            for (int i7950303_forloop=c; i7950303_forloop<((n-i)-1); i7950303_forloop++) {
                if ((a[c]>a[(c+1)])) {
                    t=a[c];
                    a[c]=a[(c+1)];
                    a[(c+1)]=t;
                } 
                c=(c+1);
            }
            i=(i+1);
        }
    }
    public static void main(String[] args) {
        Declarations decl795030_declarationblock = new Declarations();
        BubbleSort();
    }
}
