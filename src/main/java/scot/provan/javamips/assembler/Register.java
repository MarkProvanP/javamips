package scot.provan.javamips.assembler;

import java.util.HashMap;

/**
 * Created by Mark on 07/08/2015.
 */
public class Register {
    public static HashMap<String, Integer> R = new HashMap<String, Integer>();

    static {
        R.put("$zero",  0);
        R.put("$at",    1);
        R.put("$v0",    2);
        R.put("$v1",    3);
        R.put("$a0",    4);
        R.put("$a1",    5);
        R.put("$a2",    6);
        R.put("$a3",    7);
        R.put("$t0",    8);
        R.put("$t1",    9);
        R.put("$t2",   10);
        R.put("$t3",   11);
        R.put("$t4",   12);
        R.put("$t5",   13);
        R.put("$t6",   14);
        R.put("$t7",   15);
        R.put("$s0",   16);
        R.put("$s1",   17);
        R.put("$s2",   18);
        R.put("$s3",   19);
        R.put("$s4",   20);
        R.put("$s5",   21);
        R.put("$s6",   22);
        R.put("$s7",   23);
        R.put("$t8",   24);
        R.put("$t9",   25);
        R.put("$k0",   26);
        R.put("$k1",   27);
        R.put("$gp",   28);
        R.put("$sp",   29);
        R.put("$fp",   30);
        R.put("$ra",   31);
    }
}
