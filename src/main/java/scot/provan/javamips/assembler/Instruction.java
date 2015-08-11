package scot.provan.javamips.assembler;

import java.util.HashMap;

/**
 * Created by Mark on 07/08/2015.
 */
public class Instruction {
    public static final int OPCODE   = 0b111111_00000_00000_00000_00000_000000; // Opcode mask
    public static final int OPCODE_SHIFT = 26;
    //                                   332222_22222_21111_11111_10000_000000
    //                                 0b109876_54321_09876_54321_09876_543210
    public static final int RS       = 0b000000_11111_00000_00000_00000_000000; // RS mask
    public static final int RS_SHIFT = 21;
    public static final int RT       = 0b000000_00000_11111_00000_00000_000000; // RT mask
    public static final int RT_SHIFT = 16;
    public static final int RD       = 0b000000_00000_00000_11111_00000_000000; // RD mask
    public static final int RD_SHIFT = 11;
    public static final int SHAMT    = 0b000000_00000_00000_00000_11111_000000; // Shift amount mask
    public static final int SHAMT_SHIFT = 6;
    public static final int FUNCT    = 0b000000_00000_00000_00000_00000_111111; // Function mask
    public static final int FUNCT_SHIFT = 0;
    // R Type                           OPCODE  RS    RT    RD    SHAMT  FUNCT
    public static final int ADD      = 0b000000_00000_00000_00000_00000_100000; // Add
    public static final int ADDU     = 0b000000_00000_00000_00000_00000_100001; // Add unsigned
    public static final int SUB      = 0b000000_00000_00000_00000_00000_100010; // Subtract
    public static final int SUBU     = 0b000000_00000_00000_00000_00000_100011; // Subtract unsigned
    public static final int MULT     = 0b000000_00000_00000_00000_00000_011000; // Multiply
    public static final int MULTU    = 0b000000_00000_00000_00000_00000_011001; // Multiply unsigned
    public static final int DIV      = 0b000000_00000_00000_00000_00000_011010; // Divide
    public static final int DIVU     = 0b000000_00000_00000_00000_00000_011011; // Divide unsigned
    public static final int MFHI     = 0b000000_00000_00000_00000_00000_010000; // Move from high
    public static final int MFLO     = 0b000000_00000_00000_00000_00000_010010; // Move from low
    // TODO mfcZ
    // TODO mtcZ
    public static final int AND      = 0b000000_00000_00000_00000_00000_100100; // Bitwise AND
    public static final int OR       = 0b000000_00000_00000_00000_00000_100101; // Bitwise OR
    public static final int XOR      = 0b000000_00000_00000_00000_00000_100110; // Bitwise XOR
    public static final int NOR      = 0b000000_00000_00000_00000_00000_100111; // Bitwise NOR
    public static final int SLT      = 0b000000_00000_00000_00000_00000_101010; // Set on less than
    public static final int SLL      = 0b000000_00000_00000_00000_00000_000000; // Shift left logical immediate
    public static final int SRL      = 0b000000_00000_00000_00000_00000_000010; // Shift right logical immediate
    public static final int SRA      = 0b000000_00000_00000_00000_00000_000011; // Shift right arithmetic immediate
    public static final int SLLV     = 0b000000_00000_00000_00000_00000_000100; // Shift left logical
    public static final int SRLV     = 0b000000_00000_00000_00000_00000_000110; // Shift right logical
    public static final int SRAV     = 0b000000_00000_00000_00000_00000_000111; // Shift right arithmetic
    public static final int JR       = 0b000000_00000_00000_00000_00000_001000; // Jump register

    //                                   332222_22222_21111_1111110000000000
    //                                 0b109876_54321_09876_5432109876543210
    public static final int IMMEDIATE= 0b000000_00000_00000_1111111111111111; // Immediate mask
    public static final int IMMEDIATE_SHIFT = 0;
    // I Type                            OPCODE RS    RT    IMMEDIATE
    public static final int ADDI     = 0b001000_00000_00000_0000000000000000; // Add immediate
    public static final int ADDIU    = 0b001001_00000_00000_0000000000000000; // Add immediate unsigned
    public static final int LW       = 0b100011_00000_00000_0000000000000000; // Load word
    public static final int LH       = 0b100001_00000_00000_0000000000000000; // Load halfword
    public static final int LHU      = 0b100101_00000_00000_0000000000000000; // Load halfword unsigned
    public static final int LB       = 0b100000_00000_00000_0000000000000000; // Load byte
    public static final int LBU      = 0b100100_00000_00000_0000000000000000; // Load byte unsigned
    public static final int SW       = 0b101011_00000_00000_0000000000000000; // Store word
    public static final int SH       = 0b101001_00000_00000_0000000000000000; // Store half
    public static final int SB       = 0b101000_00000_00000_0000000000000000; // Store byte
    public static final int LUI      = 0b001111_00000_00000_0000000000000000; // Load upper immediate
    public static final int ANDI     = 0b001100_00000_00000_0000000000000000; // Bitwise AND immediate
    public static final int ORI      = 0b001101_00000_00000_0000000000000000; // Bitwise OR immediate
    public static final int SLTI     = 0b001010_00000_00000_0000000000000000; // Set on less than immediate
    public static final int BEQ      = 0b000100_00000_00000_0000000000000000; // Branch on equal
    public static final int BNE      = 0b000101_00000_00000_0000000000000000; // Branch on not equal

    //                                   332222_22222211111111110000000000
    //                                 0b109876_54321098765432109876543210
    public static final int ADDRESS  = 0b000000_11111111111111111111111111; // Address mask
    public static final int ADDRESS_SHIFT = 0;
    // J Type                            OPCODE ADDRESS
    public static final int J        = 0b000010_00000000000000000000000000; // Jump
    public static final int JAL      = 0b000011_00000000000000000000000000; // Jump and link

    // Pseudo types - these instructions are meaningless, but they have to be unique.
    public static final int PS_MOVE   = 0b000000_11111_00001_11111_11111_000000;
    public static final int PS_CLEAR  = 0b000000_11111_00010_11111_11111_000000;
    public static final int PS_NOT    = 0b000000_11111_10010_11111_11111_000000; // Oops, missed this one.
    public static final int PS_LA     = 0b000000_11111_00011_11111_11111_000000;
    public static final int PS_LI     = 0b000000_11111_00100_11111_11111_000000;
    public static final int PS_B      = 0b000000_11111_00101_11111_11111_000000;
    public static final int PS_BAL    = 0b000000_11111_00110_11111_11111_000000;
    public static final int PS_BGT    = 0b000000_11111_00111_11111_11111_000000;
    public static final int PS_BLT    = 0b000000_11111_01001_11111_11111_000000;
    public static final int PS_BGE    = 0b000000_11111_01010_11111_11111_000000;
    public static final int PS_BLE    = 0b000000_11111_01011_11111_11111_000000;
    public static final int PS_BGTU   = 0b000000_11111_01100_11111_11111_000000;
    public static final int PS_BGTZ   = 0b000000_11111_01101_11111_11111_000000;
    public static final int PS_BEQZ   = 0b000000_11111_01110_11111_11111_000000;
    public static final int PS_MUL    = 0b000000_11111_01111_11111_11111_000000;
    public static final int PS_DIV    = 0b000000_11111_10000_11111_11111_000000;
    public static final int PS_REM    = 0b000000_11111_10001_11111_11111_000000;

    public static int getOpcodeShifted(int instruction) {
        return (instruction & OPCODE) >>> OPCODE_SHIFT;
    }

    public static int addOpcodeShifted(int instruction, int opcodeShifted) {
        return instruction & (opcodeShifted << OPCODE_SHIFT);
    }

    public static int getRSShifted(int instruction) {
        return (instruction & RS) >>> RS_SHIFT;
    }

    public static int addRSShifted(int instruction, int rsShifted) {
        return instruction & (rsShifted << RS_SHIFT);
    }

    public static int getRTShifted(int instruction) {
        return (instruction & RT) >>> RT_SHIFT;
    }

    public static int addRTShifted(int instruction, int rtShifted) {
        return instruction & (rtShifted << RT_SHIFT);
    }

    public static int getRDShifted(int instruction) {
        return (instruction & RD) >>> RD_SHIFT;
    }

    public static int addRDShifted(int instruction, int rdShifted) {
        return instruction & (rdShifted << RD_SHIFT);
    }

    public static int getShamtShifted(int instruction) {
        return (instruction & SHAMT) >>> SHAMT_SHIFT;
    }

    public static int addShamtShifted(int instruction, int shamtShifted) {
        return instruction & (shamtShifted << SHAMT_SHIFT);
    }

    public static int getFunctShifted(int instruction) {
        return (instruction & FUNCT) >>> FUNCT_SHIFT;
    }

    public static int addFunctShifted(int instruction, int functShifted) {
        return instruction & (functShifted << FUNCT_SHIFT);
    }

    public static int getImmediateShifted(int instruction) {
        return (instruction & IMMEDIATE) >>> IMMEDIATE_SHIFT;
    }

    public static int addImmediateShifted(int instruction, int immediateShifted) {
        return instruction & (immediateShifted << IMMEDIATE_SHIFT);
    }

    public static int getAddressShifted(int instruction) {
        return (instruction & ADDRESS) >>> ADDRESS_SHIFT;
    }

    public static int addAddressShifted(int instruction, int addressShifted) {
        return instruction & (addressShifted << ADDRESS_SHIFT);
    }

    public static HashMap<String, Integer> RTypes = new HashMap<String, Integer>();
    public static HashMap<String, Integer> ITypes = new HashMap<String, Integer>();
    public static HashMap<String, Integer> JTypes = new HashMap<String, Integer>();
    public static HashMap<String, Integer> PseudoTypes = new HashMap<String, Integer>();
    public static HashMap<String, Integer> Instructions = new HashMap<String, Integer>();
    public enum InstructionSyntaxType {
        DST, TSC, ST, TCS, TC, D, TD, DTSH, DTS, STC, C, S
    }
    public static HashMap<String, InstructionSyntaxType> InstructionSyntax = new HashMap<String, InstructionSyntaxType>();
    static {
        RTypes.put("add",   ADD);   InstructionSyntax.put("add",   InstructionSyntaxType.DST);
        RTypes.put("addu",  ADDU);  InstructionSyntax.put("addu",  InstructionSyntaxType.DST);
        RTypes.put("sub",   SUB);   InstructionSyntax.put("sub",   InstructionSyntaxType.DST);
        RTypes.put("subu",  SUBU);  InstructionSyntax.put("subu",  InstructionSyntaxType.DST);
        RTypes.put("mult",  MULT);  InstructionSyntax.put("mult",  InstructionSyntaxType.ST);
        RTypes.put("multu", MULTU); InstructionSyntax.put("multu", InstructionSyntaxType.ST);
        RTypes.put("div",   DIV);   InstructionSyntax.put("div",   InstructionSyntaxType.ST);
        RTypes.put("divu",  DIVU);  InstructionSyntax.put("divu",  InstructionSyntaxType.ST);
        RTypes.put("mfhi",  MFHI);  InstructionSyntax.put("mfhi",  InstructionSyntaxType.D);
        RTypes.put("mflo",  MFLO);  InstructionSyntax.put("mflo",  InstructionSyntaxType.D);
        RTypes.put("and",   AND);   InstructionSyntax.put("and",   InstructionSyntaxType.DST);
        RTypes.put("or",    OR);    InstructionSyntax.put("or",    InstructionSyntaxType.DST);
        RTypes.put("xor",   XOR);   InstructionSyntax.put("xor",   InstructionSyntaxType.DST);
        RTypes.put("nor",   NOR);   InstructionSyntax.put("nor",   InstructionSyntaxType.DST);
        RTypes.put("slt",   SLT);   InstructionSyntax.put("slt",   InstructionSyntaxType.DST);
        RTypes.put("sll",   SLL);   InstructionSyntax.put("sll",   InstructionSyntaxType.DTSH);
        RTypes.put("srl",   SRL);   InstructionSyntax.put("srl",   InstructionSyntaxType.DTSH);
        RTypes.put("sra",   SRA);   InstructionSyntax.put("sra",   InstructionSyntaxType.DTSH);
        RTypes.put("sllv",  SLLV);  InstructionSyntax.put("sllv",  InstructionSyntaxType.DTS);
        RTypes.put("srlv",  SRLV);  InstructionSyntax.put("srlv",  InstructionSyntaxType.DTS);
        RTypes.put("srav",  SRAV);  InstructionSyntax.put("srav",  InstructionSyntaxType.DTS);
        RTypes.put("jr",    JR);    InstructionSyntax.put("j",     InstructionSyntaxType.C);
        ITypes.put("addi",  ADDI);  InstructionSyntax.put("addi",  InstructionSyntaxType.TSC);
        ITypes.put("addiu", ADDIU); InstructionSyntax.put("addiu", InstructionSyntaxType.TSC);
        ITypes.put("lw",    LW);    InstructionSyntax.put("lw",    InstructionSyntaxType.TCS);
        ITypes.put("lh",    LH);    InstructionSyntax.put("lh",    InstructionSyntaxType.TCS);
        ITypes.put("lhu",   LHU);   InstructionSyntax.put("lhu",   InstructionSyntaxType.TCS);
        ITypes.put("lb",    LB);    InstructionSyntax.put("lb",    InstructionSyntaxType.TCS);
        ITypes.put("lbu",   LBU);   InstructionSyntax.put("lbu",   InstructionSyntaxType.TCS);
        ITypes.put("sw",    SW);    InstructionSyntax.put("sw",    InstructionSyntaxType.TCS);
        ITypes.put("sh",    SH);    InstructionSyntax.put("sh",    InstructionSyntaxType.TCS);
        ITypes.put("sb",    SB);    InstructionSyntax.put("sb",    InstructionSyntaxType.TCS);
        ITypes.put("lui",   LUI);   InstructionSyntax.put("lui",   InstructionSyntaxType.TC);
        ITypes.put("andi",  ANDI);  InstructionSyntax.put("andi",  InstructionSyntaxType.TSC);
        ITypes.put("ori",   ORI);   InstructionSyntax.put("ori",   InstructionSyntaxType.TSC);
        ITypes.put("slti",  SLTI);  InstructionSyntax.put("slti",  InstructionSyntaxType.TSC);
        ITypes.put("beq",   BEQ);   InstructionSyntax.put("beq",   InstructionSyntaxType.STC);
        ITypes.put("bne",   BNE);   InstructionSyntax.put("bne",   InstructionSyntaxType.STC);
        JTypes.put("j",     J);     InstructionSyntax.put("j",     InstructionSyntaxType.C);
        JTypes.put("jal",   JAL);   InstructionSyntax.put("jal",   InstructionSyntaxType.C);
        PseudoTypes.put("move",     PS_MOVE);
        PseudoTypes.put("clear",    PS_CLEAR);
        PseudoTypes.put("not",      PS_NOT);
        PseudoTypes.put("la",       PS_LA);
        PseudoTypes.put("li",       PS_LI);
        PseudoTypes.put("b",        PS_B);
        PseudoTypes.put("bal",      PS_BAL);
        PseudoTypes.put("bgt",      PS_BGT);
        PseudoTypes.put("blt",      PS_BLT);
        PseudoTypes.put("bge",      PS_BGE);
        PseudoTypes.put("ble",      PS_BLE);
        PseudoTypes.put("bgtu",     PS_BGTU);
        PseudoTypes.put("bgtz",     PS_BGTZ);
        PseudoTypes.put("beqz",     PS_BEQZ);
        PseudoTypes.put("mul",      PS_MUL);
        PseudoTypes.put("div",      PS_DIV);
        PseudoTypes.put("rem",      PS_REM);
        Instructions.putAll(RTypes);
        Instructions.putAll(ITypes);
        Instructions.putAll(JTypes);
        Instructions.putAll(PseudoTypes);
    }
}
