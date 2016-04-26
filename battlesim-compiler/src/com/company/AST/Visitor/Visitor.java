package com.company.AST.Visitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by joklost on 03-04-16.
 */

public abstract class Visitor {
    public static boolean debug = false;

    private static Class<? extends Object> objectClass = Object.class;

    public abstract void defaultVisit(Object o);

    private void debugMsg(String s) {
        if (this.debug) {
            System.out.println(s);
        }
    }

    public final void dispatch(Object o) {
        Method m = getBestMethodFor(o);
        try {
            m.invoke(this, o);
        } catch (IllegalAccessException e) {
            e.printStackTrace(System.err);
            throw new Error("Method " + m + " aborting, bad access: " + e);
        } catch (InvocationTargetException e){
            // This exception is thrown if the reflectively called method
            // throws anything for any reason.
            e.printStackTrace(System.err);
            throw new Error("Method " + m + " aborting: " + e);
        }
    }

    protected Method getBestMethodFor(Object o) {
        Class<? extends Object> nodeClass = o.getClass();
        Method ans = null;

        // Try the superclasses
        for (Class<? extends Object> c = nodeClass; c != objectClass && ans == null; c = c.getSuperclass()) {
            debugMsg("Looking for class match for " + c.getName());

            try {
                ans = getClass().getMethod("visit", c);
            } catch (NoSuchMethodException e){

            }
        }

        // Try the interfaces. THe code below will find the last
        // interface listed for
        // which "this" visitor can handle this type

        Class<? extends Object> iClass = nodeClass;
        while (ans == null && iClass != objectClass) {
            debugMsg("Looking for interface match in " + iClass.getName());
            Class<? extends Object>[] interfaces = iClass.getInterfaces();

            for (int i = 0; i < interfaces.length; i++) {
                debugMsg("   trying interface " + interfaces[i]);
                try {
                    ans = getClass().getMethod("visit", interfaces[i]);
                } catch (NoSuchMethodException e) {

                }
            }
            iClass = iClass.getSuperclass();
        }

        if (ans == null) {
            try {
                debugMsg("Giving up...");
                ans = getClass().getMethod("defaultVisit", new Object().getClass());
            } catch (NoSuchMethodException e) {
                debugMsg("Cannot happen -- could not find defaultVisit(Object)");
                e.printStackTrace(System.err);
                System.exit(-1);
            }
        }
        debugMsg("Best method for " + o + " is " + ans);
        return ans;
    }
}
