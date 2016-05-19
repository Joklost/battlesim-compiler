// Gets the folling error (is expected)
/*
    Malign.java:4: error: int cannot be dereferenced
            x.clone();
             ^
    1 error
*/

class Malign {
    public static void main(String args[]) {
        int x = 100;
        x.clone();
    }
}
