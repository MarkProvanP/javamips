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
        R.put("$0",     0);
        R.put("$1",     1);
        R.put("$2",     2);
        R.put("$3",     3);
        R.put("$4",     4);
        R.put("$5",     5);
        R.put("$6",     6);
        R.put("$7",     7);
        R.put("$8",     8);
        R.put("$9",     9);
        R.put("$10",   10);
        R.put("$11",   11);
        R.put("$12",   12);
        R.put("$13",   13);
        R.put("$14",   14);
        R.put("$15",   15);
        R.put("$16",   16);
        R.put("$17",   17);
        R.put("$18",   18);
        R.put("$19",   19);
        R.put("$20",   20);
        R.put("$21",   21);
        R.put("$22",   22);
        R.put("$23",   23);
        R.put("$24",   24);
        R.put("$25",   25);
        R.put("$26",   26);
        R.put("$27",   27);
        R.put("$28",   28);
        R.put("$29",   29);
        R.put("$30",   30);
        R.put("$31",   31);
    }
}
