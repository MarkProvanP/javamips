package scot.provan.javamips.assembler;

/**
 * Created by Mark on 07/08/2015.
 */
public class Assembler {
    public static void main(String[] args) throws Lexer.LexerException {
        Lexer.setUp();
        Token t;
        while ((t = Lexer.lex()) != null) {
            System.out.println(t);
        }
    }
}
