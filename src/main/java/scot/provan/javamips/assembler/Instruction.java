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
    public static final int R_ADD    = 0b000000_00000_00000_00000_00000_100000; // Add
    public static final int R_ADDU   = 0b000000_00000_00000_00000_00000_100001; // Add unsigned
    public static final int R_AND    = 0b000000_00000_00000_00000_00000_100100; // Bitwise AND
    public static final int R_DADD   = 0b000000_00000_00000_00000_00000_101100; // Doubleword add
    public static final int R_DADDU  = 0b000000_00000_00000_00000_00000_101101; // Doubleword add unsigned
    public static final int R_DDIV   = 0b000000_00000_00000_00000_00000_011110; // Doubleword divide
    public static final int R_DDIVU  = 0b000000_00000_00000_00000_00000_011111; // Doubleword divide unsigned
    public static final int R_DIV    = 0b000000_00000_00000_00000_00000_011010; // Divide
    public static final int R_DIVU   = 0b000000_00000_00000_00000_00000_011011; // Divide unsigned
    public static final int R_DMULT  = 0b000000_00000_00000_00000_00000_011100; // Doubleword multiply
    public static final int R_DMULTU = 0b000000_00000_00000_00000_00000_011101; // Doubleword multiply
    public static final int R_DSLL   = 0b000000_00000_00000_00000_00000_111000; // Doubleword shift left logical
    public static final int R_DSLL32 = 0b000000_00000_00000_00000_00000_111100; // Doubleword shift left logical plus 32
    public static final int R_DSLLV  = 0b000000_00000_00000_00000_00000_010100; // Doubleword shift left logical variable
    public static final int R_DSRA   = 0b000000_00000_00000_00000_00000_111011; // Doubleword shift right arithmetic
    public static final int R_DSRA32 = 0b000000_00000_00000_00000_00000_111111; // Doubleword shift right arithmetic plus 32
    public static final int R_DSRAV  = 0b000000_00000_00000_00000_00000_010111; // Doubleword shift right arithmetic variable
    public static final int R_DSRL   = 0b000000_00000_00000_00000_00000_111010; // Doubleword shift right logical
    public static final int R_DSRL32 = 0b000000_00000_00000_00000_00000_111110; // Doubleword shift right logical plus 32
    public static final int R_RSRLV  = 0b000000_00000_00000_00000_00000_010110; // Doubleword shift right logical variable
    public static final int R_DSUB   = 0b000000_00000_00000_00000_00000_101110; // Doubleword subtract
    public static final int R_DSUBU  = 0b000000_00000_00000_00000_00000_101111; // Doubleword subtract unsigned
    public static final int R_JALR   = 0b000000_00000_00000_00000_00000_001001; // Jump and link register
    public static final int R_JR     = 0b000000_00000_00000_00000_00000_001000; // Jump register
    public static final int R_MFHI   = 0b000000_00000_00000_00000_00000_010000; // Move from high
    public static final int R_MFLO   = 0b000000_00000_00000_00000_00000_010010; // Move from low
    public static final int R_MOVN   = 0b000000_00000_00000_00000_00000_001011; // Move conditional on not zero
    public static final int R_MOVZ   = 0b000000_00000_00000_00000_00000_001010; // Move conditional on zero
    public static final int R_MTHI   = 0b000000_00000_00000_00000_00000_010001; // Move to high
    public static final int R_MTLO   = 0b000000_00000_00000_00000_00000_010011; // Move to low
    public static final int R_MULT   = 0b000000_00000_00000_00000_00000_011000; // Multiply
    public static final int R_MULTU  = 0b000000_00000_00000_00000_00000_011001; // Multiply unsigned
    public static final int R_NOR    = 0b000000_00000_00000_00000_00000_100111; // Bitwise NOR
    public static final int R_OR     = 0b000000_00000_00000_00000_00000_100101; // Bitwise OR
    public static final int R_SLL    = 0b000000_00000_00000_00000_00000_000000; // Shift left logical immediate
    public static final int R_SLLV   = 0b000000_00000_00000_00000_00000_000100; // Shift left logical variable
    public static final int R_SLT    = 0b000000_00000_00000_00000_00000_101010; // Set on less than
    public static final int R_SLTU   = 0b000000_00000_00000_00000_00000_101011; // Set on less than unsigned
    public static final int R_SRA    = 0b000000_00000_00000_00000_00000_000011; // Shift right arithmetic immediate
    public static final int R_SRAV   = 0b000000_00000_00000_00000_00000_000111; // Shift right arithmetic variable
    public static final int R_SRL    = 0b000000_00000_00000_00000_00000_000010; // Shift right logical immediate
    public static final int R_SRLV   = 0b000000_00000_00000_00000_00000_000110; // Shift right logical variable
    public static final int R_SUB    = 0b000000_00000_00000_00000_00000_100010; // Subtract
    public static final int R_SUBU   = 0b000000_00000_00000_00000_00000_100011; // Subtract unsigned
    public static final int R_SYNC   = 0b000000_00000_00000_00000_00000_001111; // Synchronise shared memory
    // trap
    public static final int TRAPCODE = 0b000000_00000_00000_11111_11111_000000; // Trap code mask
    public static final int R_TEQ    = 0b000000_00000_00000_00000_00000_110100; // Trap if equal
    public static final int R_TGE    = 0b000000_00000_00000_00000_00000_110000; // Trap if greater or equal
    public static final int R_TGEU   = 0b000000_00000_00000_00000_00000_110001; // Trap if greater or equal unsigned
    public static final int R_TLT    = 0b000000_00000_00000_00000_00000_110010; // Trap if less than
    public static final int R_TLTU   = 0b000000_00000_00000_00000_00000_110011; // Trap if less than unsigned
    public static final int R_TNE    = 0b000000_00000_00000_00000_00000_110110; // Trap if not equal
    public static final int R_XOR    = 0b000000_00000_00000_00000_00000_100110; // Bitwise XOR
    // TODO mfcZ
    // TODO mtcZ











    //                                   332222_22222_21111_1111110000000000
    //                                 0b109876_54321_09876_5432109876543210
    public static final int IMMEDIATE= 0b000000_00000_00000_1111111111111111; // Immediate mask
    public static final int IMMEDIATE_SHIFT = 0;
    // I Type                            OPCODE RS    RT    IMMEDIATE
    public static final int I_ADDI   = 0b001000_00000_00000_0000000000000000; // Add immediate
    public static final int I_ADDIU  = 0b001001_00000_00000_0000000000000000; // Add immediate unsigned
    public static final int I_ANDI   = 0b001100_00000_00000_0000000000000000; // Bitwise R_AND immediate
    public static final int I_BEQ    = 0b000100_00000_00000_0000000000000000; // Branch on equal
    public static final int I_BEQL   = 0b010100_00000_00000_0000000000000000; // Branch on equal likely
    public static final int I_BGTZ   = 0b000111_00000_00000_0000000000000000; // Branch on greater than zero
    public static final int I_BGTZL  = 0b010111_00000_00000_0000000000000000; // Branch on greater than zero likely
    public static final int I_BLEZ   = 0b000110_00000_00000_0000000000000000; // Branch on less than or equal to zero
    public static final int I_BLEZL  = 0b010110_00000_00000_0000000000000000; // Branch on less than or equal to zero likely
    public static final int I_BNE    = 0b000101_00000_00000_0000000000000000; // Branch on not equal
    public static final int I_BNEL   = 0b010101_00000_00000_0000000000000000; // Branch on not equal likely
    public static final int I_DADDI  = 0b011000_00000_00000_0000000000000000; // Doubleword add immediate
    public static final int I_DADDIU = 0b011001_00000_00000_0000000000000000; // Doubleword add immediate unsigned
    public static final int I_LB     = 0b100000_00000_00000_0000000000000000; // Load byte
    public static final int I_LBU    = 0b100100_00000_00000_0000000000000000; // Load byte unsigned
    public static final int I_LD     = 0b110111_00000_00000_0000000000000000; // Load doubleword
    public static final int I_LDC0   = 0b110100_00000_00000_0000000000000000; // Load doubleword to coprocessor 0
    public static final int I_LDC1   = 0b110101_00000_00000_0000000000000000; // Load doubleword to coprocessor 1
    public static final int I_LDC2   = 0b110110_00000_00000_0000000000000000; // Load doubleword to coprocessor 2
    public static final int I_LDC3   = 0b110111_00000_00000_0000000000000000; // Load doubleword to coprocessor 3
    public static final int I_LDL    = 0b011010_00000_00000_0000000000000000; // Load doubleword left
    public static final int I_LDR    = 0b011011_00000_00000_0000000000000000; // Load doubleword right
    public static final int I_LH     = 0b100001_00000_00000_0000000000000000; // Load halfword
    public static final int I_LHU    = 0b100101_00000_00000_0000000000000000; // Load halfword unsigned
    public static final int I_LL     = 0b110000_00000_00000_0000000000000000; // Load linked word
    public static final int I_LLD    = 0b110100_00000_00000_0000000000000000; // Load linked doubleword
    public static final int I_LUI    = 0b001111_00000_00000_0000000000000000; // Load upper immediate
    public static final int I_LW     = 0b100011_00000_00000_0000000000000000; // Load word
    public static final int I_LWC0   = 0b110000_00000_00000_0000000000000000; // Load word to coprocessor 0
    public static final int I_LWC1   = 0b110001_00000_00000_0000000000000000; // Load word to coprocessor 1
    public static final int I_LWC2   = 0b110010_00000_00000_0000000000000000; // Load word to coprocessor 2
    public static final int I_LWC3   = 0b110011_00000_00000_0000000000000000; // Load word to coprocessor 3
    public static final int I_LWL    = 0b100010_00000_00000_0000000000000000; // Load word left
    public static final int I_LWR    = 0b100110_00000_00000_0000000000000000; // Load word right
    public static final int I_LWU    = 0b100111_00000_00000_0000000000000000; // Load word unsigned
    public static final int I_ORI    = 0b001101_00000_00000_0000000000000000; // Bitwise R_OR immediate
    public static final int I_PREF   = 0b110011_00000_00000_0000000000000000; // Prefetch
    public static final int I_SB     = 0b101000_00000_00000_0000000000000000; // Store byte
    public static final int I_SC     = 0b111000_00000_00000_0000000000000000; // Store conditional word
    public static final int I_SCD    = 0b111100_00000_00000_0000000000000000; // Store conditional doubleword
    public static final int I_SD     = 0b111111_00000_00000_0000000000000000; // Store doubleword
    public static final int I_SDC1   = 0b111101_00000_00000_0000000000000000; // Store doubleword from coprocessor 1
    public static final int I_SDC2   = 0b111110_00000_00000_0000000000000000; // Store doubleword from coprocessor 2
    public static final int I_SDL    = 0b101100_00000_00000_0000000000000000; // Store doubleword left
    public static final int I_SDR    = 0b101101_00000_00000_0000000000000000; // Store doubleword left
    public static final int I_SH     = 0b101001_00000_00000_0000000000000000; // Store half
    public static final int I_SLTI   = 0b001010_00000_00000_0000000000000000; // Set on less than immediate
    public static final int I_SLTIU  = 0b001011_00000_00000_0000000000000000; // Set on less than immediate unsigned
    public static final int I_SW     = 0b101011_00000_00000_0000000000000000; // Store word
    public static final int I_SWC1   = 0b111001_00000_00000_0000000000000000; // Store word from coprocessor 1
    public static final int I_SWC2   = 0b111010_00000_00000_0000000000000000; // Store word from coprocessor 2
    public static final int I_SWC3   = 0b111011_00000_00000_0000000000000000; // Store word from coprocessor 3
    public static final int I_SWL    = 0b101010_00000_00000_0000000000000000; // Store word left
    public static final int I_SWR    = 0b101110_00000_00000_0000000000000000; // Store word left
    public static final int I_XORI   = 0b001110_00000_00000_0000000000000000; // XOR immediate

    //                                     332222_22222_21111_1111110000000000
    //                                   0b109876_54321_09876_5432109876543210
    // REGIMM type
    public static final int REGIMM     = 0b111111_00000_11111_0000000000000000; // REGIMM mask
    public static final int RGM_BGEZ   = 0b000001_00000_00001_0000000000000000; // Branch on greater than or equal to zero
    public static final int RGM_BGEZAL = 0b000001_00000_10001_0000000000000000; // Branch on greater than or equal to zero and link
    public static final int RGM_BGEZALL= 0b000001_00000_10011_0000000000000000; // Branch on greater than or equal to zero and link likely
    public static final int RGM_BGEZL  = 0b000001_00000_00011_0000000000000000; // Branch on greater than or equal to zero likely
    public static final int RGM_BLTZ   = 0b000001_00000_00000_0000000000000000; // Branch on less than zero
    public static final int RGM_BLTZAL = 0b000001_00000_10000_0000000000000000; // Branch on less than zero and link
    public static final int RGM_BLTZALL= 0b000001_00000_10010_0000000000000000; // Branch on less than zero and link likely
    public static final int RGM_BLTZL  = 0b000001_00000_00010_0000000000000000; // Branch on less than zero likely
    public static final int RGM_TEQI   = 0b000001_00000_01100_0000000000000000; // Trap if equal immediate
    public static final int RGM_TGEI   = 0b000001_00000_01000_0000000000000000; // Trap if greater or equal immediate
    public static final int RGM_TGEIU  = 0b000001_00000_01001_0000000000000000; // Trap if greater or equal immediate unsigned
    public static final int RGM_TLTI   = 0b000001_00000_01010_0000000000000000; // Trap if less than immediate
    public static final int RGM_TLTIU  = 0b000001_00000_01011_0000000000000000; // Trap if less than immediate unsigned
    public static final int RGM_TNEI   = 0b000001_00000_01110_0000000000000000; // Trap if not equal immediate

    //
    //
    // SPECIAL type
    public static final int SPECIAL    = 0b111111_00000000000000000000_111111;
    public static final int SPL_BREAK  = 0b000000_00000000000000000000_001101;
    public static final int SYSCALL    = 0b000000_00000000000000000000_001100;

    //
    //
    // Coprosessor operation type
    public static final int COPX = 0b111111_00000000000000000000000000;
    public static final int COP0 = 0b010000_00000000000000000000000000;
    public static final int COP1 = 0b010001_00000000000000000000000000;
    public static final int COP2 = 0b010010_00000000000000000000000000;
    public static final int COP3 = 0b010011_00000000000000000000000000;
    public static final int COPIN= 0b000000_11111111111111111111111111;


    //                                   332222_22222211111111110000000000
    //                                 0b109876_54321098765432109876543210
    public static final int ADDRESS  = 0b000000_11111111111111111111111111; // Address mask
    public static final int ADDRESS_SHIFT = 0;
    // J Type                            OPCODE ADDRESS
    public static final int J_J      = 0b000010_00000000000000000000000000; // Jump
    public static final int J_JAL    = 0b000011_00000000000000000000000000; // Jump and link


    // Pseudo types - these numbers are meaningless, but they have to be unique.
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
        DST, TSC, ST, TCS, TC, D, TD, DTSH, DTS, STC, C, S, SC
    }
    public static HashMap<String, InstructionSyntaxType> InstructionSyntax = new HashMap<String, InstructionSyntaxType>();
    static {
        // R types
        RTypes.put("add", R_ADD);   InstructionSyntax.put("add",   InstructionSyntaxType.DST);
        RTypes.put("addu", R_ADDU);  InstructionSyntax.put("addu",  InstructionSyntaxType.DST);
        RTypes.put("sub", R_SUB);   InstructionSyntax.put("sub",   InstructionSyntaxType.DST);
        RTypes.put("subu", R_SUBU);  InstructionSyntax.put("subu",  InstructionSyntaxType.DST);
        RTypes.put("mult", R_MULT);  InstructionSyntax.put("mult",  InstructionSyntaxType.ST);
        RTypes.put("multu", R_MULTU); InstructionSyntax.put("multu", InstructionSyntaxType.ST);
        RTypes.put("div", R_DIV);   InstructionSyntax.put("div",   InstructionSyntaxType.ST);
        RTypes.put("divu", R_DIVU);  InstructionSyntax.put("divu",  InstructionSyntaxType.ST);
        RTypes.put("mfhi", R_MFHI);  InstructionSyntax.put("mfhi",  InstructionSyntaxType.D);
        RTypes.put("mflo", R_MFLO);  InstructionSyntax.put("mflo",  InstructionSyntaxType.D);
        RTypes.put("and", R_AND);   InstructionSyntax.put("and",   InstructionSyntaxType.DST);
        RTypes.put("or", R_OR);    InstructionSyntax.put("or",    InstructionSyntaxType.DST);
        RTypes.put("xor", R_XOR);   InstructionSyntax.put("xor",   InstructionSyntaxType.DST);
        RTypes.put("nor", R_NOR);   InstructionSyntax.put("nor",   InstructionSyntaxType.DST);
        RTypes.put("slt", R_SLT);   InstructionSyntax.put("slt",   InstructionSyntaxType.DST);
        RTypes.put("sll", R_SLL);   InstructionSyntax.put("sll",   InstructionSyntaxType.DTSH);
        RTypes.put("srl", R_SRL);   InstructionSyntax.put("srl",   InstructionSyntaxType.DTSH);
        RTypes.put("sra", R_SRA);   InstructionSyntax.put("sra",   InstructionSyntaxType.DTSH);
        RTypes.put("sllv", R_SLLV);  InstructionSyntax.put("sllv",  InstructionSyntaxType.DTS);
        RTypes.put("srlv", R_SRLV);  InstructionSyntax.put("srlv",  InstructionSyntaxType.DTS);
        RTypes.put("srav", R_SRAV);  InstructionSyntax.put("srav",  InstructionSyntaxType.DTS);
        RTypes.put("jr", R_JR);    InstructionSyntax.put("j",     InstructionSyntaxType.C);
        // I types
        ITypes.put("addi", I_ADDI);  InstructionSyntax.put("addi",  InstructionSyntaxType.TSC);
        ITypes.put("addiu", I_ADDIU); InstructionSyntax.put("addiu", InstructionSyntaxType.TSC);
        ITypes.put("lw", I_LW);    InstructionSyntax.put("lw",    InstructionSyntaxType.TCS);
        ITypes.put("lh", I_LH);    InstructionSyntax.put("lh",    InstructionSyntaxType.TCS);
        ITypes.put("lhu", I_LHU);   InstructionSyntax.put("lhu",   InstructionSyntaxType.TCS);
        ITypes.put("lb", I_LB);    InstructionSyntax.put("lb",    InstructionSyntaxType.TCS);
        ITypes.put("lbu", I_LBU);   InstructionSyntax.put("lbu",   InstructionSyntaxType.TCS);
        ITypes.put("sw", I_SW);    InstructionSyntax.put("sw",    InstructionSyntaxType.TCS);
        ITypes.put("sh", I_SH);    InstructionSyntax.put("sh",    InstructionSyntaxType.TCS);
        ITypes.put("sb", I_SB);    InstructionSyntax.put("sb",    InstructionSyntaxType.TCS);
        ITypes.put("lui", I_LUI);   InstructionSyntax.put("lui",   InstructionSyntaxType.TC);
        ITypes.put("andi", I_ANDI);  InstructionSyntax.put("andi",  InstructionSyntaxType.TSC);
        ITypes.put("ori", I_ORI);   InstructionSyntax.put("ori",   InstructionSyntaxType.TSC);
        ITypes.put("slti", I_SLTI);  InstructionSyntax.put("slti",  InstructionSyntaxType.TSC);
        ITypes.put("beq", I_BEQ);   InstructionSyntax.put("beq",   InstructionSyntaxType.STC);
        ITypes.put("bgezal", RGM_BGEZAL);InstructionSyntax.put("bgezal",InstructionSyntaxType.TC);
        ITypes.put("bne", I_BNE);   InstructionSyntax.put("bne",   InstructionSyntaxType.STC);
        // J types
        JTypes.put("j", J_J);     InstructionSyntax.put("j",     InstructionSyntaxType.C);
        JTypes.put("jal", J_JAL);   InstructionSyntax.put("jal",   InstructionSyntaxType.C);
        // Pseudo types
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
        // Other types

        Instructions.putAll(RTypes);
        Instructions.putAll(ITypes);
        Instructions.putAll(JTypes);
        Instructions.putAll(PseudoTypes);
    }


}
