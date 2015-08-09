package scot.provan.javamips.assembler;

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

    public static class Instruction extends ParserElement {
        public static Instruction parse() {
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

    public static class RTypeInstruction extends Instruction {
        private int funct;
        private int rs;
        private int rt;
        private int rd;

        private RTypeInstruction(int funct, int rs, int rt, int rd) {
            this.funct = funct;
            this.rs = rs;
            this.rt = rt;
            this.rd = rd;
        }
        public static RTypeInstruction parse() {
            int staticFunct = 0;
            int staticRS = 0;
            int staticRT = 0;
            int staticRD = 0;

            if (Parser.getToken() instanceof Token.RTypeInstructionToken) {
                staticFunct = ((Token.RTypeInstructionToken) Parser.getToken()).getOpcodeOrFunction();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }

            return new RTypeInstruction(staticFunct, staticRS, staticRT, staticRD);
        }
    }

    public static class ITypeInstruction extends Instruction {

    }

    public static class JTypeInstruction extends Instruction {

    }
}
