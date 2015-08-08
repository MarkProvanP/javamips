package scot.provan.javamips.assembler;

/**
 * Created by Mark on 07/08/2015.
 */
public class Instruction {
    public static int OPCODE   = 0b111111_00000_00000_00000_00000_000000; // Opcode mask
    public static int OPCODE_SHIFT = 26;
    //                             332222_22222_21111_11111_10000_000000
    //                           0b109876_54321_09876_54321_09876_543210
    public static int RS       = 0b000000_11111_00000_00000_00000_000000; // RS mask
    public static int RS_SHIFT = 21;
    public static int RT       = 0b000000_00000_11111_00000_00000_000000; // RT mask
    public static int RT_SHIFT = 16;
    public static int RD       = 0b000000_00000_00000_11111_00000_000000; // RD mask
    public static int RD_SHIFT = 11;
    public static int SHAMT    = 0b000000_00000_00000_00000_11111_000000; // Shift amount mask
    public static int SHAMT_SHIFT = 6;
    public static int FUNCT    = 0b000000_00000_00000_00000_00000_111111; // Function mask
    public static int FUNCT_SHIFT = 0;
    // R Type                     OPCODE  RS    RT    RD    SHAMT  FUNCT
    public static int ADD      = 0b000000_00000_00000_00000_00000_100000; // Add
    public static int ADDU     = 0b000000_00000_00000_00000_00000_100001; // Add unsigned
    public static int SUB      = 0b000000_00000_00000_00000_00000_100010; // Subtract
    public static int SUBU     = 0b000000_00000_00000_00000_00000_100011; // Subtract unsigned
    public static int MULT     = 0b000000_00000_00000_00000_00000_011000; // Multiply
    public static int MULTU    = 0b000000_00000_00000_00000_00000_011001; // Multiply unsigned
    public static int DIV      = 0b000000_00000_00000_00000_00000_011010; // Divide
    public static int DIVU     = 0b000000_00000_00000_00000_00000_011011; // Divide unsigned
    public static int MFHI     = 0b000000_00000_00000_00000_00000_010000; // Move from high
    public static int MFLO     = 0b000000_00000_00000_00000_00000_010010; // Move from low
    // TODO mfcZ
    // TODO mtcZ
    public static int AND      = 0b000000_00000_00000_00000_00000_100100; // Bitwise AND
    public static int OR       = 0b000000_00000_00000_00000_00000_100101; // Bitwise OR
    public static int XOR      = 0b000000_00000_00000_00000_00000_100110; // Bitwise XOR
    public static int NOR      = 0b000000_00000_00000_00000_00000_100111; // Bitwise NOR
    public static int SLT      = 0b000000_00000_00000_00000_00000_101010; // Set on less than
    public static int SLL      = 0b000000_00000_00000_00000_00000_000000; // Shift left logical immediate
    public static int SRL      = 0b000000_00000_00000_00000_00000_000010; // Shift right logical immediate
    public static int SRA      = 0b000000_00000_00000_00000_00000_000011; // Shift right arithmetic immediate
    public static int SLLV     = 0b000000_00000_00000_00000_00000_000100; // Shift left logical
    public static int SRLV     = 0b000000_00000_00000_00000_00000_000110; // Shift right logical
    public static int SRAV     = 0b000000_00000_00000_00000_00000_000111; // Shift right arithmetic
    public static int JR       = 0b000000_00000_00000_00000_00000_001000; // Jump register

    //                             332222_22222_21111_1111110000000000
    //                           0b109876_54321_09876_5432109876543210
    public static int IMMEDIATE= 0b000000_00000_00000_1111111111111111; // Immediate mask
    public static int IMMEDIATE_SHIFT = 0;
    // I Type                      OPCODE RS    RT    IMMEDIATE
    public static int ADDI     = 0b001000_00000_00000_0000000000000000; // Add immediate
    public static int ADDIU    = 0b001001_00000_00000_0000000000000000; // Add immediate unsigned
    public static int LW       = 0b100011_00000_00000_0000000000000000; // Load word
    public static int LH       = 0b100001_00000_00000_0000000000000000; // Load halfword
    public static int LHU      = 0b100101_00000_00000_0000000000000000; // Load halfword unsigned
    public static int LB       = 0b100000_00000_00000_0000000000000000; // Load byte
    public static int LBU      = 0b100100_00000_00000_0000000000000000; // Load byte unsigned
    public static int SW       = 0b101011_00000_00000_0000000000000000; // Store word
    public static int SH       = 0b101001_00000_00000_0000000000000000; // Store half
    public static int SB       = 0b101000_00000_00000_0000000000000000; // Store byte
    public static int LUI      = 0b001111_00000_00000_0000000000000000; // Load upper immediate
    public static int ANDI     = 0b001100_00000_00000_0000000000000000; // Bitwise AND immediate
    public static int ORI      = 0b001101_00000_00000_0000000000000000; // Bitwise OR immediate
    public static int SLTI     = 0b001010_00000_00000_0000000000000000; // Set on less than immediate
    public static int BEQ      = 0b000100_00000_00000_0000000000000000; // Branch on equal
    public static int BNE      = 0b000101_00000_00000_0000000000000000; // Branch on not equal

    //                             332222_22222211111111110000000000
    //                           0b109876_54321098765432109876543210
    public static int ADDRESS  = 0b000000_11111111111111111111111111; // Address mask
    public static int ADDRESS_SHIFT = 0;
    // J Type                      OPCODE ADDRESS
    public static int J        = 0b000010_00000000000000000000000000; // Jump
    public static int JAL      = 0b000011_00000000000000000000000000; // Jump and link

    public static int getOpcodeShifted(int instruction) {
        return (instruction & OPCODE) >> OPCODE_SHIFT;
    }

    public static int addOpcodeShifted(int instruction, int opcodeShifted) {
        return instruction & (opcodeShifted << OPCODE_SHIFT);
    }

    public static int getRSShifted(int instruction) {
        return (instruction & RS) >> RS_SHIFT;
    }

    public static int addRSShifted(int instruction, int rsShifted) {
        return instruction & (rsShifted << RS_SHIFT);
    }

    public static int getRTShifted(int instruction) {
        return (instruction & RT) >> RT_SHIFT;
    }

    public static int addRTShifted(int instruction, int rtShifted) {
        return instruction & (rtShifted << RT_SHIFT);
    }

    public static int getRDShifted(int instruction) {
        return (instruction & RD) >> RD_SHIFT;
    }

    public static int addRDShifted(int instruction, int rdShifted) {
        return instruction & (rdShifted << RD_SHIFT);
    }

    public static int getShamtShifted(int instruction) {
        return (instruction & SHAMT) >> SHAMT_SHIFT;
    }

    public static int addShamtShifted(int instruction, int shamtShifted) {
        return instruction & (shamtShifted << SHAMT_SHIFT);
    }

    public static int getFunctShifted(int instruction) {
        return (instruction & FUNCT) >> FUNCT_SHIFT;
    }

    public static int addFunctShifted(int instruction, int functShifted) {
        return instruction & (functShifted << FUNCT_SHIFT);
    }

    public static int getImmediateShifted(int instruction) {
        return (instruction & IMMEDIATE) >> IMMEDIATE_SHIFT;
    }

    public static int addImmediateShifted(int instruction, int immediateShifted) {
        return instruction & (immediateShifted << IMMEDIATE_SHIFT);
    }

    public static int getAddressShifted(int instruction) {
        return (instruction & ADDRESS) >> ADDRESS_SHIFT;
    }

    public static int addAddressShifted(int instruction, int addressShifted) {
        return instruction & (addressShifted << ADDRESS_SHIFT);
    }
}
