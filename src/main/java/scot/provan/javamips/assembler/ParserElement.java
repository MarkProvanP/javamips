package scot.provan.javamips.assembler;

import scot.provan.javamips.assembler.Instruction;
import java.util.ArrayList;

/**
 * Created by Mark on 09/08/2015.
 */
public class ParserElement {
    public static class Program {
        private ArrayList<ParserElement> elements = new ArrayList<ParserElement>();
    }

    public static class Label extends ParserElement {

    }

    public static class Directive extends ParserElement {

    }

    public static class InstructionElement extends ParserElement {
        public static InstructionElement parse() {
            if (Parser.getToken() instanceof Token.RTypeInstructionToken) {
                return RTypeInstruction.parse();
            } else if (Parser.getToken() instanceof Token.ITypeInstructionToken) {
                return ITypeInstruction.parse();
            } else if (Parser.getToken() instanceof Token.JTypeInstructionToken) {
                return JTypeInstruction.parse();
            } else {
                return null;
            }
        }
    }

    public static class RTypeInstruction extends InstructionElement {
        private Token.RTypeInstructionToken instructionToken;
        private int rs;
        private int rt;
        private int rd;

        private RTypeInstruction(Token.RTypeInstructionToken instructionToken, int rs, int rt, int rd) {
            this.instructionToken = instructionToken;
            this.rs = rs;
            this.rt = rt;
            this.rd = rd;
        }
        public static RTypeInstruction parse() {
            Token.RTypeInstructionToken instructionToken = null;
            int staticRS = 0;
            int staticRT = 0;
            int staticRD = 0;

            if (Parser.getToken() instanceof Token.RTypeInstructionToken) {
                instructionToken = (Token.RTypeInstructionToken) Parser.getToken();
                Parser.advanceToken();
            }

            switch (Instruction.InstructionSyntax.get(instructionToken.getOriginal())) {
                case DST:
                    // Get $d
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $s
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                case ST:
                    // Get $s
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                case D:
                    // Get $d
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                case TD:
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $d
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                case DTSH:
                    // Get $d
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get shamt
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                case S:
                    // Get $s
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                default:
            }







            return new RTypeInstruction(instructionToken, staticRS, staticRT, staticRD);
        }
    }

    public static class ITypeInstruction extends InstructionElement {
        private Token.ITypeInstructionToken instructionToken;
        private int rs;
        private int rt;
        private Token.ConstantToken immediateToken;

        private ITypeInstruction(Token.ITypeInstructionToken instructionToken, int rs, int rt, Token.ConstantToken immediateToken) {
            this.instructionToken = instructionToken;
            this.rs = rs;
            this.rt = rt;
            this.immediateToken = immediateToken;
        }

        public static ITypeInstruction parse() {
            Token.ITypeInstructionToken instructionToken = null;
            int rs = 0;
            int rt = 0;
            Token.ConstantToken immediateToken = null;

            if (Parser.getToken() instanceof Token.ITypeInstructionToken) {
                instructionToken = (Token.ITypeInstructionToken) Parser.getToken();
                Parser.advanceToken();
            }

            switch (Instruction.InstructionSyntax.get(instructionToken.getOriginal())) {
                case TSC:
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rt = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $s
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rs = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get C
                    if (Parser.getToken() instanceof Token.ConstantToken) {
                        immediateToken = (Token.ConstantToken) Parser.getToken();
                        Parser.advanceToken();
                    }
                    break;
                case TCS:
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rt = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get C
                    if (Parser.getToken() instanceof Token.ConstantToken) {
                        immediateToken = (Token.ConstantToken) Parser.getToken();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $s
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rs = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    break;
                case TC:
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rt = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get C
                    if (Parser.getToken() instanceof Token.ConstantToken) {
                        immediateToken = (Token.ConstantToken) Parser.getToken();
                        Parser.advanceToken();
                    }
                    break;
                case STC:
                    // Get $s
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rs = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get $t
                    if (Parser.getToken() instanceof Token.RegisterToken) {
                        rt = ((Token.RegisterToken) Parser.getToken()).getRegister();
                        Parser.advanceToken();
                    }
                    // Check comma
                    if (Parser.getToken() instanceof Token.CommaToken) {
                        Parser.advanceToken();
                    }
                    // Get C
                    if (Parser.getToken() instanceof Token.ConstantToken) {
                        immediateToken = (Token.ConstantToken) Parser.getToken();
                        Parser.advanceToken();
                    }
                    break;
                default:
                    return null;
            }
            return new ITypeInstruction(instructionToken, rs, rt, immediateToken);
        }
    }

    public static class JTypeInstruction extends InstructionElement {
        private Token.JTypeInstructionToken instructionToken;
        private Token.ConstantToken address;

        private JTypeInstruction(Token.JTypeInstructionToken instructionToken, Token.ConstantToken address) {
            this.instructionToken = instructionToken;
            this.address = address;
        }

        public static JTypeInstruction parse() {
            Token.JTypeInstructionToken instructionToken = null;
            Token.ConstantToken address = null;

            if (Parser.getToken() instanceof Token.JTypeInstructionToken) {
                instructionToken = (Token.JTypeInstructionToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.ConstantToken) {
                address = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }

            return new JTypeInstruction(instructionToken, address);
        }
    }
}
