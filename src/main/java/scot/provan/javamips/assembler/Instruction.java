package scot.provan.javamips.assembler;

/**
 * Created by Mark on 07/08/2015.
 */
public class Instruction {
    static int OPCODE   = 0b111111_00000_00000_00000_00000_000000; // Opcode mask
    //                      332222_22222_21111_11111_10000_000000
    //                    0b109876_54321_09876_54321_09876_543210
    static int RS       = 0b000000_11111_00000_00000_00000_000000; // RS mask
    static int RT       = 0b000000_00000_11111_00000_00000_000000; // RT mask
    static int RD       = 0b000000_00000_00000_11111_00000_000000; // RD mask
    static int SHAMT    = 0b000000_00000_00000_00000_11111_000000; // Shift amount mask
    static int FUNCT    = 0b000000_00000_00000_00000_00000_111111; // Function mask
    // R Type               OPCODE  RS    RT    RD   SHAMT  FUNCT
    static int ADD      = 0b000000_00000_00000_00000_00000_100000; // Add
    static int ADDU     = 0b000000_00000_00000_00000_00000_100001; // Add unsigned
    static int SUB      = 0b000000_00000_00000_00000_00000_100010; // Subtract
    static int SUBU     = 0b000000_00000_00000_00000_00000_100011; // Subtract unsigned
    static int MULT     = 0b000000_00000_00000_00000_00000_011000; // Multiply
    static int MULTU    = 0b000000_00000_00000_00000_00000_011001; // Multiply unsigned
    static int DIV      = 0b000000_00000_00000_00000_00000_011010; // Divide
    static int DIVU     = 0b000000_00000_00000_00000_00000_011011; // Divide unsigned
    static int MFHI     = 0b000000_00000_00000_00000_00000_100000; // Move from high
    static int MFLO     = 0b000000_00000_00000_00000_00000_100010; // Move from low
    // TODO mfcZ
    // TODO mtcZ
    static int AND      = 0b000000_00000_00000_00000_00000_100100; // Bitwise AND
    static int OR       = 0b000000_00000_00000_00000_00000_100101; // Bitwise OR
    static int XOR      = 0b000000_00000_00000_00000_00000_100110; // Bitwise XOR
    static int NOR      = 0b000000_00000_00000_00000_00000_100111; // Bitwise NOR
    static int SLT      = 0b000000_00000_00000_00000_00000_101010; // Set on less than
    static int SLL      = 0b000000_00000_00000_00000_00000_000000; // Shift left logical immediate
    static int SRL      = 0b000000_00000_00000_00000_00000_000010; // Shift right logical immediate
    static int SRA      = 0b000000_00000_00000_00000_00000_000011; // Shift right arithmetic immediate
    static int SLLV     = 0b000000_00000_00000_00000_00000_000100; // Shift left logical
    static int SRLV     = 0b000000_00000_00000_00000_00000_000110; // Shift right logical
    static int SRAV     = 0b000000_00000_00000_00000_00000_000111; // Shift right arithmetic
    static int JR       = 0b000000_00000_00000_00000_00000_000100; // Jump register

    //                      332222_22222_21111_1111110000000000
    //                    0b109876_54321_09876_5432109876543210
    static int IMMEDIATE= 0b000000_00000_00000_1111111111111111; // Immediate mask
    // I Type               OPCODE  RS    RT    IMMEDIATE
    static int ADDI     = 0b001000_00000_00000_0000000000000000; // Add immediate
    static int ADDIU    = 0b001001_00000_00000_0000000000000000; // Add immediate unsigned
    static int LW       = 0b100011_00000_00000_0000000000000000; // Load word
    static int LH       = 0b100001_00000_00000_0000000000000000; // Load halfword
    static int LHU      = 0b100101_00000_00000_0000000000000000; // Load halfword unsigned
    static int LB       = 0b100000_00000_00000_0000000000000000; // Load byte
    static int LBU      = 0b100100_00000_00000_0000000000000000; // Load byte unsigned
    static int SW       = 0b101011_00000_00000_0000000000000000; // Store word
    static int SH       = 0b101001_00000_00000_0000000000000000; // Store half
    static int SB       = 0b101000_00000_00000_0000000000000000; // Store byte
    static int LUI      = 0b001111_00000_00000_0000000000000000; // Load upper immediate
    static int ANDI     = 0b001100_00000_00000_0000000000000000; // Bitwise AND immediate
    static int ORI      = 0b001101_00000_00000_0000000000000000; // Bitwise OR immediate
    static int SLTI     = 0b001010_00000_00000_0000000000000000; // Set on less than immediate
    static int BEQ      = 0b000100_00000_00000_0000000000000000; // Branch on equal
    static int BNE      = 0b000101_00000_00000_0000000000000000; // Branch on not equal

    //                      332222_22222211111111110000000000
    //                    0b109876_54321098765432109876543210
    static int ADDRESS  = 0b000000_11111111111111111111111111; // Address mask
    // J Type               OPCODE  ADDRESS
    static int J        = 0b000010_00000000000000000000000000; // Jump
    static int JAL      = 0b000011_00000000000000000000000000; // Jump and link
}
