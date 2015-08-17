package scot.provan.javamips.assembler;

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
        private int shamt;

        protected RTypeInstruction(Token.RTypeInstructionToken instructionToken, int rs, int rt, int rd, int shamt) {
            this.instructionToken = instructionToken;
            this.rs = rs;
            this.rt = rt;
            this.rd = rd;
            this.shamt = shamt;
        }
        public static RTypeInstruction parse() {
            Token.RTypeInstructionToken instructionToken = null;
            int staticRS = 0;
            int staticRT = 0;
            int staticRD = 0;
            int staticSHAMT = 0;

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
                        staticSHAMT = ((Token.RegisterToken) Parser.getToken()).getRegister();
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

            return new RTypeInstruction(instructionToken, staticRS, staticRT, staticRD, staticSHAMT);
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
                case SC:
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
            Token.RTypeInstructionToken addToken = Token.createPseudoRToken(Instruction.R_ADD);
            RTypeInstruction addInstruction = buildDST(addToken, staticRT, staticRS, Reg(0));
            realInstructions.add(addInstruction);
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
            Token.RTypeInstructionToken addToken = Token.createPseudoRToken(Instruction.R_ADD);
            RTypeInstruction addInstruction = buildDST(addToken, staticRT, Reg(0), Reg(0));
            realInstructions.add(addInstruction);
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
            // not $rt, $rs -> nor $rt, $rs, $zero
            Token.RTypeInstructionToken norToken = Token.createPseudoRToken(Instruction.R_NOR);
            RTypeInstruction norInstruction = buildDST(norToken, staticRT, staticRS, Reg(0));
            realInstructions.add(norInstruction);
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
            Token.ITypeInstructionToken luiToken = Token.createPseudoIToken(Instruction.I_LUI);
            Token.ITypeInstructionToken oriToken = Token.createPseudoIToken(Instruction.I_ORI);
            ITypeInstruction luiInstruction = buildTC(luiToken, staticRD, labelAddr);
            ITypeInstruction oriInstruction = buildTSC(oriToken, staticRD, staticRD, labelAddr);
            realInstructions.add(luiInstruction);
            realInstructions.add(oriInstruction);
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
            if (Parser.getToken() instanceof Token.NumberToken) {
                labelAddr = (Token.NumberToken) Parser.getToken();
                Parser.advanceToken();
            }
            // la %rd, LabelAddr -> lui %rd, LabelAddr[31:16]; ori %rd, %rd, LabelAddr[15:0]
            Token.ITypeInstructionToken luiToken = Token.createPseudoIToken(Instruction.I_LUI);
            Token.ITypeInstructionToken oriToken = Token.createPseudoIToken(Instruction.I_ORI);
            ITypeInstruction luiInstruction = buildTC(luiToken, staticRD, labelAddr);
            ITypeInstruction oriInstruction = buildTSC(oriToken, staticRD, staticRD, labelAddr);
            realInstructions.add(luiInstruction);
            realInstructions.add(oriInstruction);
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
            Token.ITypeInstructionToken beqToken = Token.createPseudoIToken(Instruction.I_BEQ);
            ITypeInstruction beqInstruction = buildSTC(beqToken, Reg(0), Reg(0), label);
            realInstructions.add(beqInstruction);
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
            Token.ITypeInstructionToken bgezalToken = Token.createPseudoIToken(Instruction.RGM_BGEZAL);
            ITypeInstruction realInstruction = buildSC(bgezalToken, Reg(0), label);
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
            Token.RTypeInstructionToken sltToken = Token.createPseudoRToken(Instruction.R_SLT);
            Token.ITypeInstructionToken bneToken = Token.createPseudoIToken(Instruction.I_BNE);
            RTypeInstruction sltInstruction = buildDST(sltToken, Reg("$at"), staticRT, staticRS);
            ITypeInstruction bneInstruction = buildSTC(bneToken, Reg("$at"), Reg(0), label);
            realInstructions.add(sltInstruction);
            realInstructions.add(bneInstruction);
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
            // blt $rs, $rt, Label -> slt $at, $rt, $rt; bne $at, $zero, Label
            Token.RTypeInstructionToken sltToken = Token.createPseudoRToken(Instruction.R_SLT);
            Token.ITypeInstructionToken bneToken = Token.createPseudoIToken(Instruction.I_BNE);
            RTypeInstruction sltInstruction = buildDST(sltToken, Reg("$at"), staticRS, staticRT);
            ITypeInstruction bneInstruction = buildSTC(bneToken, Reg("$at"), Reg(0), label);
            realInstructions.add(sltInstruction);
            realInstructions.add(bneInstruction);
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
            // bge $rs, $rt, Label -> slt $at, $rs, $rt; beq $at, $zero, Label
            Token.RTypeInstructionToken sltToken = Token.createPseudoRToken(Instruction.R_SLT);
            Token.ITypeInstructionToken beqToken = Token.createPseudoIToken(Instruction.I_BEQ);
            RTypeInstruction sltInstruction = buildDST(sltToken, Reg("$at"), staticRS, staticRT);
            ITypeInstruction beqInstruction = buildSTC(beqToken, Reg("$at"), Reg(0), label);
            realInstructions.add(sltInstruction);
            realInstructions.add(beqInstruction);
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
            // ble $rs, $rt, Label -> slt $at, $rt, $rs; beq $at, $zero, Label
            Token.RTypeInstructionToken sltToken = Token.createPseudoRToken(Instruction.R_SLT);
            Token.ITypeInstructionToken beqToken = Token.createPseudoIToken(Instruction.I_BEQ);
            RTypeInstruction sltInstruction = buildDST(sltToken, Reg("$at"), staticRT, staticRS);
            ITypeInstruction beqInstruction = buildSTC(beqToken, Reg("$at"), Reg(0), label);
            realInstructions.add(sltInstruction);
            realInstructions.add(beqInstruction);
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
            Token.RTypeInstructionToken sltuToken = Token.createPseudoRToken(Instruction.R_SLTU);
            Token.ITypeInstructionToken bneToken = Token.createPseudoIToken(Instruction.I_BNE);
            RTypeInstruction sltuInstruction = buildDST(sltuToken, Reg("$at"), staticRT, staticRS);
            ITypeInstruction bneInstruction = buildSTC(bneToken, Reg("$at"), Reg(0), label);
            realInstructions.add(sltuInstruction);
            realInstructions.add(bneInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBGTZ() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
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
            Token.RTypeInstructionToken sltToken = Token.createPseudoRToken(Instruction.R_SLT);
            Token.ITypeInstructionToken bneToken = Token.createPseudoIToken(Instruction.I_BNE);
            RTypeInstruction sltInstruction = buildDST(sltToken, Reg("$at"), Reg(0), staticRS);
            ITypeInstruction bneInstruction = buildSTC(bneToken, Reg("$at"), Reg(0), label);
            realInstructions.add(sltInstruction);
            realInstructions.add(bneInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSBEQZ() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRS = 0;
            // Get $rs
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRS = ((Token.RegisterToken) Parser.getToken()).getRegister();
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
            // beqz $rs, Label -> beq $rs, $zero, Label
            Token.ITypeInstructionToken beqToken = Token.createPseudoIToken(Instruction.I_BEQ);
            ITypeInstruction beqInstruction = buildSTC(beqToken, staticRS, Reg(0), label);
            realInstructions.add(beqInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSMUL() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRD = 0;
            int staticRS = 0;
            int staticRT = 0;
            // Get $rd
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
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
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // mul $d, $s, $t -> mult $s, $t; mflo $d
            Token.RTypeInstructionToken multToken = Token.createPseudoRToken(Instruction.R_MULT);
            Token.RTypeInstructionToken mfloToken = Token.createPseudoRToken(Instruction.R_MFLO);
            RTypeInstruction multInstruction = buildST(multToken, staticRS, staticRT);
            RTypeInstruction mfloInstruction = buildD(mfloToken, staticRD);
            realInstructions.add(multInstruction);
            realInstructions.add(mfloInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSDIV() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRD = 0;
            int staticRS = 0;
            int staticRT = 0;
            // Get $rd
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
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
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // div $d, $s, $t -> div $s, $t; mflo $d
            Token.RTypeInstructionToken divToken = Token.createPseudoRToken(Instruction.R_DIV);
            Token.RTypeInstructionToken mfloToken = Token.createPseudoRToken(Instruction.R_MFLO);
            RTypeInstruction divInstruction = buildST(divToken, staticRS, staticRT);
            RTypeInstruction mfloInstruction = buildD(mfloToken, staticRD);
            realInstructions.add(divInstruction);
            realInstructions.add(mfloInstruction);
            return realInstructions;
        }

        private static ArrayList<InstructionElement> parsePSREM() {
            ArrayList<InstructionElement> realInstructions = new ArrayList<InstructionElement>();
            Token.PseudoTypeInstructionToken pseudoToken = (Token.PseudoTypeInstructionToken) Parser.getToken();
            Parser.advanceToken();
            int staticRD = 0;
            int staticRS = 0;
            int staticRT = 0;
            // Get $rd
            if (Parser.getToken() instanceof Token.RegisterToken) {
                staticRD = ((Token.RegisterToken) Parser.getToken()).getRegister();
                Parser.advanceToken();
            }
            // Check comma
            if (Parser.getToken() instanceof Token.CommaToken) {
                Parser.advanceToken();
            }
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
            Token.ConstantToken label = null;
            if (Parser.getToken() instanceof Token.ConstantToken) {
                label = (Token.ConstantToken) Parser.getToken();
                Parser.advanceToken();
            }
            // rem $d, $s, $t -> div $s, $t; mfhi $d
            Token.RTypeInstructionToken divToken = Token.createPseudoRToken(Instruction.R_DIV);
            Token.RTypeInstructionToken mfhiToken = Token.createPseudoRToken(Instruction.R_MFLO);
            RTypeInstruction divInstruction = buildST(divToken, staticRS, staticRT);
            RTypeInstruction mfhiInstruction = buildD(mfhiToken, staticRD);
            realInstructions.add(divInstruction);
            realInstructions.add(mfhiInstruction);
            return realInstructions;
        }
    }

    // DST, TSC, ST, TCS, TC, D, TD, DTSH, DTS, STC, C, S, SC

    public static RTypeInstruction buildDST(Token.RTypeInstructionToken token, int rd, int rs, int rt) {
        return new RTypeInstruction(token, rs, rt, rd, 0);
    }

    public static ITypeInstruction buildTSC(Token.ITypeInstructionToken token, int rt, int rs, Token.ConstantToken c) {
        return new ITypeInstruction(token, rs, rt, c);
    }

    public static RTypeInstruction buildST(Token.RTypeInstructionToken token, int rs, int rt) {
        return new RTypeInstruction(token, rs, rt, 0, 0);
    }

    public static ITypeInstruction buildTCS(Token.ITypeInstructionToken token, int rt, Token.ConstantToken c, int rs) {
        return new ITypeInstruction(token, rs, rt, c);
    }

    public static ITypeInstruction buildTC(Token.ITypeInstructionToken token, int rt, Token.ConstantToken c) {
        return new ITypeInstruction(token, 0, rt, c);
    }

    public static RTypeInstruction buildD(Token.RTypeInstructionToken token, int rd) {
        return new RTypeInstruction(token, 0, 0, rd, 0);
    }

    public static RTypeInstruction buildTD(Token.RTypeInstructionToken token, int rt, int rd) {
        return new RTypeInstruction(token, 0, rt, rd, 0);
    }

    public static RTypeInstruction buildDTSH(Token.RTypeInstructionToken token, int rd, int rt, int shamt) {
        return new RTypeInstruction(token, 0, rt, rd, shamt);
    }

    public static RTypeInstruction buildDTS(Token.RTypeInstructionToken token, int rd, int rt, int rs) {
        return new RTypeInstruction(token, rs, rt, rd, 0);
    }

    public static ITypeInstruction buildSTC(Token.ITypeInstructionToken token, int rs, int rt, Token.ConstantToken c) {
        return new ITypeInstruction(token, rs, rt, c);
    }

    public static ITypeInstruction buildC(Token.ITypeInstructionToken token, Token.ConstantToken c) {
        return new ITypeInstruction(token, 0, 0, c);
    }

    public static RTypeInstruction buildS(Token.RTypeInstructionToken token, int rs) {
        return new RTypeInstruction(token, rs, 0, 0, 0);
    }

    public static ITypeInstruction buildSC(Token.ITypeInstructionToken token, int rs, Token.ConstantToken c) {
        return new ITypeInstruction(token, rs, 0, c);
    }

    public static int Reg(String register) {
        return Register.R.get(register);
    }

    public static int Reg(int regNo) {
        return Register.R.get(String.format("$%d", regNo));
    }

}
