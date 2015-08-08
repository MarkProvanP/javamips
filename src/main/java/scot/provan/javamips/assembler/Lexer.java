package scot.provan.javamips.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Mark on 08/08/2015.
 */
public class Lexer {
    private static class LexSource {
        private BufferedReader reader;

        public LexSource() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        public LexSource(File inputFile) throws FileNotFoundException {
            reader = new BufferedReader(new FileReader(inputFile));
        }

        public char getChar() {
            try {
                return (char) reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (char) -1;
        }
    }
    public static class LexerException extends Exception { }

    private static LexSource lxb;
    private static boolean setUp;
    private static int currentCharNo = 1;
    private static int currentLineNo = 1;
    private static int tokenStartCharNo;
    private static int tokenEndCharNo;
    private static char c = ' ';

    public static void setUp() {
        lxb = new LexSource();
        setUp = true;
    }

    public static void setUp(File inputFile) {
        try {
            lxb = new LexSource(inputFile);
            setUp = true;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public static char getChar() {
        return lxb.getChar();
    }

    public static Token lex() throws LexerException {
        if (!setUp) {
            throw new LexerException();
        }

        StringBuilder sb = new StringBuilder();
        tokenStartCharNo = currentCharNo;

        while (c == ' ' || c == '\t' || c == '\n' || c == '#') {
            if (c == '#') {
                while (c != '\n') {
                    c = getChar();
                }
                currentLineNo++;
                currentCharNo = 1;
            } else if (c == '\n') {
                currentLineNo++;
                currentCharNo = 1;
            } else if (c == ' ' || c == '\t') {
                currentCharNo++;
            }
            c = getChar();
        }

        if (isCharLetter(c)) {
            while (isCharLetter(c) || isCharNumber(c) || c == '.' || c == '_') {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
            }
            if (c == ':') {
                sb.append(c);
                c = getChar();
                return new Token.LabelToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
            } else {
                return Token.createWordToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
            }
        } else if (c == '.') {
            sb.append(c);
            c = getChar();
            while (isCharLetter(c) || isCharNumber(c)) {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
            }
            return new Token.DirectiveToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        } else if (isCharNumber(c)) {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            if (c == 'x') {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
                while (isCharHexDigit(c)) {
                    sb.append(c);
                    c = getChar();
                    tokenEndCharNo = currentCharNo;
                    currentCharNo++;
                }
            } else {
                while (isCharNumber(c)) {
                    sb.append(c);
                    c = getChar();
                    tokenEndCharNo = currentCharNo;
                    currentCharNo++;
                }
            }
            return new Token.NumberToken(sb.toString(), currentCharNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == '-') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            while (isCharNumber(c)) {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
            }
            return new Token.NumberToken(sb.toString(), currentCharNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == '\"') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            while (c != '\"') {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
            }
            return new Token.StringToken(sb.toString(), currentCharNo, tokenStartCharNo, tokenEndCharNo);
        }

        return null;
    }


    public static boolean isCharNumber(char c) {
        return (c >= 48 && c <= 57);
    }

    public static boolean isCharPunctuation(char c) {
        return ((c >= '!' && c <= '/')
                || (c >= ':' && c <= '@')
                || (c >= '[' && c <= '`')
                || (c >= '{' && c <= '~'));
    }

    public static boolean isCharSinglePunctuation(char c) {
        return (c == '{' || c == '}' || c == ',' || c == '(' || c == ')'
                || c == ';' || c == '*' || c == '/');
    }

    public static boolean isCharSecondPunctuation(char c) {
        return (c == '=');
    }

    public static boolean isCharLetter(char c) {
        return ((c >= 65 && c <= 90) || (c >= 97 && c <= 122));
    }

    public static boolean isCharHexDigit(char c) {
        return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'));
    }
}
