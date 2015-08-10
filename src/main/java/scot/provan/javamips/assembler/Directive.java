package scot.provan.javamips.assembler;

import java.util.HashMap;

/**
 * Created by Mark on 10/08/2015.
 */
public class Directive {
    public enum DirectiveSyntaxType {
        N, STR, LIST, OPTADDR, SYMSIZE, SYM
    }

    public static HashMap<String, DirectiveSyntaxType> DirectiveSyntax = new HashMap<String, DirectiveSyntaxType>();
    static {
        DirectiveSyntax.put(".align",   DirectiveSyntaxType.N);
        DirectiveSyntax.put(".ascii",   DirectiveSyntaxType.STR);
        DirectiveSyntax.put(".asciiz",  DirectiveSyntaxType.STR);
        DirectiveSyntax.put(".byte",    DirectiveSyntaxType.LIST);
        DirectiveSyntax.put(".data",    DirectiveSyntaxType.OPTADDR);
        DirectiveSyntax.put(".double",  DirectiveSyntaxType.LIST);
        DirectiveSyntax.put(".extern",  DirectiveSyntaxType.SYMSIZE);
        DirectiveSyntax.put(".float",   DirectiveSyntaxType.LIST);
        DirectiveSyntax.put(".globl",   DirectiveSyntaxType.SYM);
        DirectiveSyntax.put(".half",    DirectiveSyntaxType.LIST);
        DirectiveSyntax.put(".kdata",   DirectiveSyntaxType.OPTADDR);
        DirectiveSyntax.put(".ktext",   DirectiveSyntaxType.OPTADDR);
        DirectiveSyntax.put(".space",   DirectiveSyntaxType.N);
        DirectiveSyntax.put(".text",    DirectiveSyntaxType.OPTADDR);
        DirectiveSyntax.put(".word",    DirectiveSyntaxType.LIST);
    }
}
