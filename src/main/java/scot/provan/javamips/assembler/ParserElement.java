package scot.provan.javamips.assembler;

import scot.provan.javamips.assembler.Instruction;
import java.util.ArrayList;

/**
 * Created by Mark on 09/08/2015.
 */
public class ParserElement {
    public static class Program {
        private ArrayList<ParserElement> elements = new ArrayList<ParserElement>();
        private Program(ArrayList<ParserElement> elements) {
            this.elements = elements;
        }
        public static Program parse() {
            ArrayList<ParserElement> elements = new ArrayList<ParserElement>();

            while (Parser.getToken() != null) {
                System.out.printf("Program / Parser.getToken() info %s\n", Parser.getToken().getInfo());
                if (Parser.getToken() instanceof Token.DirectiveToken) {
                    elements.add(DirectiveElement.parse());
                } else if (Parser.getToken() instanceof Token.LabelToken) {
                    elements.add(LabelElement.parse());
                } else if (Parser.getToken() instanceof Token.PseudoTypeInstructionToken) {
                    // PseudoTypeInstruction.parse() return an ArrayList of the real instructions, so use the ArrayList
                    // addAll() method instead of just add().
                    elements.addAll(PseudoTypeInstruction.parse());
                } else if (Parser.getToken() instanceof Token.InstructionToken) {
                    elements.add(InstructionElement.parse());
                } else {
                    System.err.printf("Error! Token %s cannot be parsed as first child of Program\n", Parser.getToken().getInfo());
                    System.exit(1);
                }
            }

            return new Program(elements);
        }
    }

    public static class LabelElement extends ParserElement {
        private Token.LabelToken labelToken;
        private LabelElement(Token.LabelToken labelToken) {
            this.labelToken = labelToken;
        }

        public static LabelElement parse() {
            Token.LabelToken labelToken = null;
            if (Parser.getToken() instanceof Token.LabelToken) {
                labelToken = (Token.LabelToken) Parser.getToken();
                Parser.advanceToken();
            }

            return new LabelElement(labelToken);
        }
    }

    public static class DirectiveElement extends ParserElement {
        private Token.DirectiveToken directiveToken;

        private DirectiveElement(Token.DirectiveToken directiveToken) {
            this.directiveToken = directiveToken;
        }

        public static DirectiveElement parse() {
            if (Parser.getToken() instanceof Token.DirectiveToken) {
                Token.DirectiveToken directiveToken = (Token.DirectiveToken) Parser.getToken();
                switch (Directive.DirectiveSyntax.get(directiveToken.getOriginal())) {
                    case N:
                        return NTypeDirective.parse();
                    case STR:
                        return STRTypeDirective.parse();
                    case LIST:
                        return ListTypeDirective.parse();
                    case OPTADDR:
                        return OptAddrTypeDirective.parse();
                    case SYMSIZE:
                        return SymSizeTypeDirective.parse();
                    case SYM:
                        return SymTypeDirective.parse();
                    default:
                        return null;
                }
            } else {
                return null;
            }
        }
    }

    public static class NTypeDirective extends DirectiveElement {
        private int n;

        private NTypeDirective(Token.DirectiveToken directiveToken, int n) {
            super(directiveToken);
            this.n = n;
        }

        public static NTypeDirective parse() {
            Token.DirectiveToken directiveToken = null;
            int n = 0;

            if (Parser.getToken() instanceof Token.DirectiveToken) {
                directiveToken = (Token.DirectiveToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.NumberToken) {
                n = ((Token.NumberToken) Parser.getToken()).getValue();
                Parser.advanceToken();
            }

            return new NTypeDirective(directiveToken, n);
        }
    }

    public static class STRTypeDirective extends DirectiveElement {
        private String str;

        private STRTypeDirective(Token.DirectiveToken directiveToken, String str) {
            super(directiveToken);
            this.str = str;
        }

        public static STRTypeDirective parse() {
            Token.DirectiveToken directiveToken = null;
            String str = null;

            if (Parser.getToken() instanceof Token.DirectiveToken) {
                directiveToken = (Token.DirectiveToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.StringToken) {
                str = ((Token.StringToken) Parser.getToken()).getValue();
                Parser.advanceToken();
            }

            return new STRTypeDirective(directiveToken, str);
        }
    }

    public static class ListTypeDirective extends DirectiveElement {
        private ArrayList<Integer> list = new ArrayList<Integer>();

        private ListTypeDirective(Token.DirectiveToken directiveToken, ArrayList<Integer> list) {
            super(directiveToken);
            this.list = list;
        }

        public static ListTypeDirective parse() {
            Token.DirectiveToken directiveToken = null;
            ArrayList<Integer> list = new ArrayList<Integer>();

            if (Parser.getToken() instanceof Token.DirectiveToken) {
                directiveToken = (Token.DirectiveToken) Parser.getToken();
                Parser.advanceToken();
            }

            while (Parser.getToken() instanceof Token.NumberToken) {
                list.add(((Token.NumberToken) Parser.getToken()).getValue());
                Parser.advanceToken();
            }

            return new ListTypeDirective(directiveToken, list);
        }
    }

    public static class OptAddrTypeDirective extends DirectiveElement {
        private Token.ConstantToken address = null;

        private OptAddrTypeDirective(Token.DirectiveToken directiveToken, Token.ConstantToken address) {
            super(directiveToken);
            this.address = address;
        }

        public static OptAddrTypeDirective parse() {
            Token.DirectiveToken directiveToken = null;
            Token.ConstantToken address = null;

            if (Parser.getToken() instanceof Token.DirectiveToken) {
                directiveToken = (Token.DirectiveToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.ConstantToken) {
                address = (Token.NumberToken) Parser.getToken();
                Parser.advanceToken();
            }

            return new OptAddrTypeDirective(directiveToken, address);
        }
    }

    public static class SymSizeTypeDirective extends DirectiveElement {
        private Token.ConstantToken sym = null;
        private int size;

        private SymSizeTypeDirective(Token.DirectiveToken directiveToken, Token.ConstantToken sym, int size) {
            super(directiveToken);
            this.sym = sym;
            this.size = size;
        }

        public static SymSizeTypeDirective parse() {
            Token.DirectiveToken directiveToken = null;
            Token.ConstantToken sym = null;
            int size = 0;

            if (Parser.getToken() instanceof Token.DirectiveToken) {
                directiveToken = (Token.DirectiveToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.ConstantToken) {
                sym = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.NumberToken) {
                size = ((Token.NumberToken) Parser.getToken()).getValue();
                Parser.advanceToken();
            }

            return new SymSizeTypeDirective(directiveToken, sym, size);
        }
    }

    public static class SymTypeDirective extends DirectiveElement {
        private Token.ConstantToken sym = null;

        private SymTypeDirective(Token.DirectiveToken directiveToken, Token.ConstantToken sym) {
            super(directiveToken);
            this.sym = sym;
        }

        public static SymTypeDirective parse() {
            Token.DirectiveToken directiveToken = null;
            Token.ConstantToken sym = null;

            if (Parser.getToken() instanceof Token.DirectiveToken) {
                directiveToken = (Token.DirectiveToken) Parser.getToken();
                Parser.advanceToken();
            }

            if (Parser.getToken() instanceof Token.ConstantToken) {
                sym = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }

            return new SymTypeDirective(directiveToken, sym);
        }
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

        protected RTypeInstruction(Token.RTypeInstructionToken instructionToken, int rs, int rt, int rd) {
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

        protected ITypeInstruction(Token.ITypeInstructionToken instructionToken, int rs, int rt, Token.ConstantToken immediateToken) {
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

        protected JTypeInstruction(Token.JTypeInstructionToken instructionToken, Token.ConstantToken address) {
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

    public abstract static class PseudoTypeInstruction {
        public static ArrayList<InstructionElement> parse() {
            if (Parser.getToken() instanceof Token.PseudoTypeInstructionToken) {
                Token.PseudoTypeInstructionToken pseudoInstructionToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
                switch (pseudoInstructionToken.getOpcodeOrFunction()) {
                    case Instruction.PS_MOVE:
                        return parsePSMOVE();
                    case Instruction.PS_CLEAR:
                        return parsePSCLEAR();
                    case Instruction.PS_NOT:
                        return parsePSNOT();
                    case Instruction.PS_LA:
                        return parsePSLA();
                    case Instruction.PS_LI:
                        return parsePSLI();
                    case Instruction.PS_B:
                        return parsePSB();
                    case Instruction.PS_BAL:
                        return parsePSBAL();
                    case Instruction.PS_BGT:
                        return parsePSBGT();
                    case Instruction.PS_BLT:
                        return parsePSBLT();
                    case Instruction.PS_BGE:
                        return parsePSBGE();
                    case Instruction.PS_BLE:
                        return parsePSBLE();
                    case Instruction.PS_BGTU:
                        return parsePSBGTU();
                    case Instruction.PS_BGTZ:
                        return parsePSBGTZ();
                    case Instruction.PS_BEQZ:
                        return parsePSBEQZ();
                    case Instruction.PS_MUL:
                        return parsePSMUL();
                    case Instruction.PS_DIV:
                        return parsePSDIV();
                    case Instruction.PS_REM:
                        return parsePSREM();
                }
            }
            return null;
        }

        private static ArrayList<InstructionElement> parsePSMOVE() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRT = 0;
            int staticRS = 0;
            // Get $t
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
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
            // move $rt, $rs -> add $rt, $rs, $zero
            Token.RTypeInstructionToken realInstructionToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.ADD);
            RTypeInstruction realInstruction = new RTypeInstruction(realInstructionToken, staticRS, staticRT, 0);
            realInstructions.add(realInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSCLEAR() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRT = 0;
            // Get $t
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // move $rt, $rs -> add $rt, $rs, $zero
            Token.RTypeInstructionToken realInstructionToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.ADD);
            RTypeInstruction realInstruction = new RTypeInstruction(realInstructionToken, 0, staticRT, 0);
            realInstructions.add(realInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSNOT() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRT = 0;
            int staticRS = 0;
            // Get $t
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
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
            // move $rt, $rs -> add $rt, $rs, $zero
            Token.RTypeInstructionToken realInstructionToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.NOR);
            RTypeInstruction realInstruction = new RTypeInstruction(realInstructionToken, staticRS, staticRT, 0);
            realInstructions.add(realInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSLA() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRD = 0;
            Token.IdentToken labelAddr = null;
            // Get $d
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get LabelAddr
            if (Parser.getToken() instanceof Token.IdentToken) {
                labelAddr = (Token.IdentToken) Parser.getToken();
                Parser.advanceToken();
            }
            // la %rd, LabelAddr -> lui %rd, LabelAddr[31:16]; ori %rd, %rd, LabelAddr[15:0]
            Token.ITypeInstructionToken firstRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.LUI);
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.ORI);
            ITypeInstruction firstInstruction = new ITypeInstruction(firstRealToken, 0, staticRD, labelAddr);
            ITypeInstruction secondInstruction = new ITypeInstruction(secondRealToken, staticRD, staticRD, labelAddr);
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSLI() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRD = 0;
            Token.NumberToken labelAddr = null;
            // Get $d
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get LabelAddr
            if (Parser.getToken() instanceof Token.IdentToken) {
                labelAddr = (Token.NumberToken) Parser.getToken();
                Parser.advanceToken();
            }
            // la %rd, LabelAddr -> lui %rd, LabelAddr[31:16]; ori %rd, %rd, LabelAddr[15:0]
            Token.ITypeInstructionToken firstRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.LUI);
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.ORI);
            ITypeInstruction firstInstruction = new ITypeInstruction(firstRealToken, 0, staticRD, labelAddr);
            ITypeInstruction secondInstruction = new ITypeInstruction(secondRealToken, staticRD, staticRD, labelAddr);
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSB() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // b Label -> beq $zero, $zero, Label
            Token.ITypeInstructionToken realToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BEQ
            );
            ITypeInstruction realInstruction = new ITypeInstruction(realToken, 0, 0, label);
            realInstructions.add(realInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBAL() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // b Label -> beq $zero, $zero, Label
            Token.ITypeInstructionToken realToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BGEZAL
            );
            ITypeInstruction realInstruction = new ITypeInstruction(
                    realToken,
                    Register.R.get("$zero"),
                    Register.R.get("$zero"),
                    label);
            realInstructions.add(realInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBGT() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            int staticRT = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get $rt
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // bgt $rs, $rt, Label -> slt $at, $rt, $rs; bne $at, $zero, Label
            Token.RTypeInstructionToken firstRealToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.SLT
            );
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BNE
            );
            RTypeInstruction firstInstruction = new RTypeInstruction(
                    firstRealToken,
                    Register.R.get("$at"),
                    staticRT,
                    staticRS
            );
            ITypeInstruction secondInstruction = new ITypeInstruction(
                    secondRealToken,
                    Register.R.get("$at"),
                    Register.R.get("$zero"),
                    label
            );
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBLT() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            int staticRT = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get $rt
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // bgt $rs, $rt, Label -> slt $at, $rt, $rs; bne $at, $zero, Label
            Token.RTypeInstructionToken firstRealToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.SLT
            );
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BNE
            );
            RTypeInstruction firstInstruction = new RTypeInstruction(
                    firstRealToken,
                    Register.R.get("$at"),
                    staticRS,
                    staticRT
            );
            ITypeInstruction secondInstruction = new ITypeInstruction(
                    secondRealToken,
                    Register.R.get("$at"),
                    Register.R.get("$zero"),
                    label
            );
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBGE() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            int staticRT = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get $rt
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // bgt $rs, $rt, Label -> slt $at, $rt, $rs; bne $at, $zero, Label
            Token.RTypeInstructionToken firstRealToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.SLT
            );
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BEQ
            );
            RTypeInstruction firstInstruction = new RTypeInstruction(
                    firstRealToken,
                    Register.R.get("$at"),
                    staticRT,
                    staticRS
            );
            ITypeInstruction secondInstruction = new ITypeInstruction(
                    secondRealToken,
                    Register.R.get("$at"),
                    Register.R.get("$zero"),
                    label
            );
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBLE() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            int staticRT = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get $rt
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // bgt $rs, $rt, Label -> slt $at, $rt, $rs; bne $at, $zero, Label
            Token.RTypeInstructionToken firstRealToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.SLT
            );
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BEQ
            );
            RTypeInstruction firstInstruction = new RTypeInstruction(
                    firstRealToken,
                    Register.R.get("$at"),
                    staticRS,
                    staticRT
            );
            ITypeInstruction secondInstruction = new ITypeInstruction(
                    secondRealToken,
                    Register.R.get("$at"),
                    Register.R.get("$zero"),
                    label
            );
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBGTU() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            int staticRT = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            // Get $rt
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRT = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // bgt $rs, $rt, Label -> slt $at, $rt, $rs; bne $at, $zero, Label
            Token.RTypeInstructionToken firstRealToken = new Token.RTypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.SLTU
            );
            Token.ITypeInstructionToken secondRealToken = new Token.ITypeInstructionToken(
                    Token.PSEUDO_ORIGINAL,
                    Token.PSEUDO_LINENO,
                    Token.PSEUDO_CHARSTART,
                    Token.PSEUDO_CHAREND,
                    Instruction.BNE
            );
            RTypeInstruction firstInstruction = new RTypeInstruction(
                    firstRealToken,
                    Register.R.get("$at"),
                    staticRT,
                    staticRS
            );
            ITypeInstruction secondInstruction = new ITypeInstruction(
                    secondRealToken,
                    Register.R.get("$at"),
                    Register.R.get("$zero"),
                    label
            );
            realInstructions.add(firstInstruction);
            realInstructions.add(secondInstruction);
            return realInstructions;
        }
    }
}
