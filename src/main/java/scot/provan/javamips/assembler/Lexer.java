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
    private static int currentCharNo = 0;
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

        tokenStartCharNo = currentCharNo;
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
        } else if (c == ',') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            return new Token.CommaToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
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
            return new Token.NumberToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == '+') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            return new Token.PlusToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == '(') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            return new Token.LeftParenToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == ')') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            return new Token.RightParenToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == '-') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            if (isCharNumber(c)) {
                while (isCharNumber(c)) {
                    sb.append(c);
                    c = getChar();
                    tokenEndCharNo = currentCharNo;
                    currentCharNo++;
                }
                return new Token.NumberToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
            } else {
                return new Token.MinusToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
            }

        } else if (c == '\"') {
            tokenStartCharNo = currentCharNo;
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
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            return new Token.StringToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        } else if (c == '\'') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            if (c == '\\') {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
            }
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            if (c == '\'') {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
                return new Token.CharToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
            }
        } else if (c == '$') {
            sb.append(c);
            c = getChar();
            tokenEndCharNo = currentCharNo;
            currentCharNo++;
            while (isCharLetter(c) || isCharNumber(c)) {
                sb.append(c);
                c = getChar();
                tokenEndCharNo = currentCharNo;
                currentCharNo++;
            }
            return Token.createRegisterToken(sb.toString(), currentLineNo, tokenStartCharNo, tokenEndCharNo);
        }

        return null;
    }


    public static boolean isCharNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    public static boolean isCharLetter(char c) {
        return ((c >= 65 && c <= 90) || (c >= 97 && c <= 122));
    }

    public static boolean isCharHexDigit(char c) {
        return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'));
    }

    /**
     * Unescapes a string that contains standard Java escape sequences.
     * <ul>
     * <li><strong>\b \f \n \r \t \" \'</strong> :
     * BS, FF, NL, CR, TAB, double and single quote.</li>
     * <li><strong>\X \XX \XXX</strong> : Octal character
     * specification (0 - 377, 0x00 - 0xFF).</li>
     * <li><strong>\\uXXXX</strong> : Hexadecimal based Unicode character.</li>
     * </ul>
     *
     * @param st
     *            A string optionally containing standard java escape sequences.
     * @return The translated string.
     */
    public static String unescapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}
