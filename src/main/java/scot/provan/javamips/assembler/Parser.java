package scot.provan.javamips.assembler;

import java.util.ArrayList;

/**
 * Created by Mark on 09/08/2015.
 */
public class Parser {
    private static ArrayList<Token> tokens = new ArrayList<Token>();
    private static int tokenPosition = 0;

    public static boolean hasAnotherToken() {
        return (tokens.size() > tokenPosition && tokens.get(tokenPosition) != null);
    }

    public static boolean hasRelativeAnotherToken(int relative) {
        return (tokenPosition + relative >= 0
                && tokenPosition + relative < tokens.size());
    }

    public static Token getToken() {
        if (hasRelativeAnotherToken(0)) {
            return tokens.get(tokenPosition);
        } else {
            return null;
        }
    }

    /**
     * This method returns tokens relative to the current token pointer,
     * e.g. -1 would return the previous and 1 would return the next. If this
     * would be out of bounds, null is returned.
     * @param relative
     * @return
     */
    public static Token getRelativeToken(int relative) {
        if (hasRelativeAnotherToken(relative)) {
            return tokens.get(tokenPosition + relative);
        } else {
            return null;
        }
    }

    public static void advanceToken() {
        if (hasAnotherToken()) {
            tokenPosition++;
        }
    }

    public static void setTokens(ArrayList<Token> testTokens) {
        tokens = testTokens;
        tokenPosition = 0;
    }
}
