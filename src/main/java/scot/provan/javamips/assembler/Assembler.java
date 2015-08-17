package scot.provan.javamips.assembler;

import java.util.ArrayList;

/**
 * Created by Mark on 07/08/2015.
 */
public class Assembler {
    public static void main(String[] args) throws Lexer.LexerException {
        Lexer.setUp();
        Token t;
        ArrayList<Token> tokens = new ArrayList<Token>();
        while ((t = Lexer.lex()) != null) {
            System.out.println(t.getInfo());
            tokens.add(t);
        }
        System.out.println("finished lexing");
        Parser.setTokens(tokens);
        ParserElement.Program prog = ParserElement.Program.parse();
        System.out.println("Finished parsing");
        prog.printDetails();
        ArrayList<Integer> data = prog.pack();
        for (int i : data) {
            System.out.printf("%s\t%s\n", Integer.toHexString(i), Integer.toBinaryString(i));
        }
    }
}
