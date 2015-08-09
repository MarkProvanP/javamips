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

            //  DST, TSC, ST, TCS, TC, D, TD, DTSH, DTS, STC, C, S
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

    }

    public static class JTypeInstruction extends InstructionElement {

    }
}
