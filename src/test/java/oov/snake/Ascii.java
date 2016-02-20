package oov.snake;

//import org.junit.Test;

/**
 * ========== ItCorp v. 1.0 class library ==========
 * <p/>
 * http://www.it.ru/
 * <p/>
 * &copy; Copyright 1990-2013, by ItCorp.
 * <p/>
 * ========== Ascii.java ==========
 * <p/>
 * $Revision: 47 $<br/>
 * $Author: Olezha $<br/>
 * $HeadURL: file:///D:/work/local_repository/snake/trunk/src/test/java/oov/snake/Ascii.java $<br/>
 * $Id: Ascii.java 47 2014-07-11 12:25:33Z Olezha $
 * <p/>
 * 07.09.13 6:33: Original version (OOBUKHOV)<br/>
 */
public class Ascii {
//    @Test
    public void print(){
        for (int i = 9000; i < 10000; i++) {
            char c = (char)i;
            System.out.println("i = "+i+"; c = " + c);
        }
    }
}
