package scot.provan.javamips.assembler;

/**
 * Created by Mark on 08/08/2015.
 */
public class Token {
    private String original;
    private int lineNo;
    private int charStart;
    private int charEnd;

    public Token(String original, int lineNo, int charStart, int charEnd) {
        this.original = original;
        this.lineNo = lineNo;
        this.charStart = charStart;
        this.charEnd = charEnd;
    }

    public static class IdentToken extends Token {
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

    public static class NumberToken extends Token {
        public NumberToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
        }
    }

    public static class StringToken extends Token {
        public StringToken(String original, int lineNo, int charStart, int charEnd) {
            super(original, lineNo, charStart, charEnd);
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
        public JTypeInstructionToken(String original, int lineNo, int charStart, int charEnd, int opcode) {
            super(original, lineNo, charStart, charEnd, opcode);
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
}
