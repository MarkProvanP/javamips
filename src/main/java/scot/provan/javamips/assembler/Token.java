package scot.provan.javamips.assembler;

/**
 * Created by Mark on 08/08/2015.
 */
public class Token {
    private String original;
    private int lineNo;
    private int charStart;
    private int charEnd;

    public String getOriginal() {
        return original;
    }

    public Token(String original, int lineNo, int charStart, int charEnd) {
        this.original = original;
        this.lineNo = lineNo;
        this.charStart = charStart;
        this.charEnd = charEnd;
    }

    public String getInfo() {
        return String.format("%s - \"%s\" on line: %d between char: %d and %d",
                this.getClass().getSimpleName(),
                this.original,
                this.lineNo,
                this.charStart,
                this.charEnd);
    }

    public interface ConstantToken {

    }

    public static class IdentToken extends Token implements ConstantToken {
        public IdentToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class LabelToken extends Token {
        public LabelToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class DirectiveToken extends Token {
        public DirectiveToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class InstructionToken extends Token {
        private int opcodeOrFunction;

        public int getOpcodeOrFunction() {
            return opcodeOrFunction;
        }

        public InstructionToken(String original, int lineNo, int charStart, int charEnd, int opcodeOrFunction) {
            super(original, lineNo, charStart, charEnd);
            this.opcodeOrFunction = opcodeOrFunction;
        }
    }

    public static class SyscallToken extends Token {
        public SyscallToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class CommaToken extends Token {
        public CommaToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class PlusToken extends Token {
        public PlusToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class MinusToken extends Token {
        public MinusToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class LeftParenToken extends Token {
        public LeftParenToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class RightParenToken extends Token {
        public RightParenToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class NumberToken extends Token implements ConstantToken {
        private int value;
        public int getValue() {
            return value;
        }
        public NumberToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
            if (original.length() > 2 && original.charAt(1) == 'x') {
                this.value = Integer.parseInt(original.substring(2), 16);
            } else {
                this.value = Integer.parseInt(original);
            }
        }


        public String getInfo() {
            return String.format("%s, with number value: %s",
                    super.getInfo(),
                    String.valueOf(this.value));
        }
    }

    public static class StringToken extends Token {
        private String value;
        public String getValue() {
            return value;
        }
        public StringToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
            String unescaped = Lexer.unescapeJavaString(original);
            this.value = unescaped.substring(1, unescaped.length() - 1);
        }

        public String getInfo() {
            return String.format("%s, with string value: %s",
                    super.getInfo(),
                    this.value);
        }
    }

    public static class CharToken extends Token {
        private char value;
        public CharToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
            String unescaped = Lexer.unescapeJavaString(original);
            this.value = unescaped.charAt(1);
        }

        public String getInfo() {
            return String.format("%s, with string value: %s",
                    super.getInfo(),
                    String.valueOf(this.value));
        }
    }

    public static class RTypeInstructionToken extends InstructionToken {
        public RTypeInstructionToken(String original, int lineNo, int charStart, int charEnd, int funct) {
            super(original, lineNo, charStart, charEnd, funct);
        }
    }

    public static class ITypeInstructionToken extends InstructionToken {
        public ITypeInstructionToken(String original, int lineNo, int charStart, int charEnd, int opcode) {
            super(original, lineNo, charStart, charEnd, opcode);
        }
    }

    public static class JTypeInstructionToken extends InstructionToken {
        private JTypeInstructionToken(String original, int lineNo, int charStart, int charEnd, int opcode) {
            super(original, lineNo, charStart, charEnd, opcode);
        }
    }

    public static class RegisterToken extends Token {
        private int register;
        public int getRegister() {
            return register;
        }
        private RegisterToken(String original, int lineNo, int charStart, int charEnd, int register) {
            super(original, lineNo, charStart, charEnd);
            this.register = register;
        }
    }

    public static Token createWordToken(String word, int lineNo, int charStart, int charEnd) {
        if (Instruction.Instructions.containsKey(word)) {
            return Token.createInstructionToken(word, lineNo, charStart, charEnd);
        } else if (word.equals("syscall")) {
            return new SyscallToken(word, lineNo, charStart, charEnd);
        } else {
            return new Token.IdentToken(word, lineNo, charStart, charEnd);
        }
    }

    public static InstructionToken createInstructionToken(String original, int lineNo, int charStart, int charEnd) {
        if (Instruction.Instructions.containsKey(original)) {
            if (Instruction.RTypes.containsKey(original)) {
                return new RTypeInstructionToken(original, lineNo, charStart, charEnd, Instruction.RTypes.get(original));
            } else if (Instruction.ITypes.containsKey(original)) {
                return new ITypeInstructionToken(original, lineNo, charStart, charEnd, Instruction.ITypes.get(original));
            } else if (Instruction.JTypes.containsKey(original)) {
                return new JTypeInstructionToken(original, lineNo, charStart, charEnd, Instruction.JTypes.get(original));
            }
        }
        return null;
    }

    public static Token createRegisterToken(String original, int lineNo, int charStart, int charEnd) {
        if (Register.R.containsKey(original)) {
            return new RegisterToken(original, lineNo, charStart, charEnd, Register.R.get(original));
        } else {
            return null;
        }
    }
}
