import org.junit.Test;
import static org.junit.Assert.*;
import scot.provan.javamips.assembler.Instruction;

/**
 * Created by Mark on 08/08/2015.
 */
public class TestInstruction {

    @Test
    public void testPasses() {
        String expected = "Hello, JUnit!";
        String hello = "Hello, JUnit!";
        assertEquals(hello, expected);
    }

    /**
     * Test the funct numbers of the instruction binary literals against the hexadecimal values in the documentation.
     */
    @Test
    public void testRTypeFuncts() {
        assertEquals(0x20, Instruction.getFunctShifted(Instruction.R_ADD));
        assertEquals(0x21, Instruction.getFunctShifted(Instruction.R_ADDU));
        assertEquals(0x22, Instruction.getFunctShifted(Instruction.R_SUB));
        assertEquals(0x23, Instruction.getFunctShifted(Instruction.R_SUBU));
        assertEquals(0x18, Instruction.getFunctShifted(Instruction.R_MULT));
        assertEquals(0x19, Instruction.getFunctShifted(Instruction.R_MULTU));
        assertEquals(0x1A, Instruction.getFunctShifted(Instruction.R_DIV));
        assertEquals(0x1B, Instruction.getFunctShifted(Instruction.R_DIVU));
        assertEquals(0x10, Instruction.getFunctShifted(Instruction.R_MFHI));
        assertEquals(0x12, Instruction.getFunctShifted(Instruction.R_MFLO));
        assertEquals(0x24, Instruction.getFunctShifted(Instruction.R_AND));
        assertEquals(0x25, Instruction.getFunctShifted(Instruction.R_OR));
        assertEquals(0x26, Instruction.getFunctShifted(Instruction.R_XOR));
        assertEquals(0x27, Instruction.getFunctShifted(Instruction.R_NOR));
        assertEquals(0x2A, Instruction.getFunctShifted(Instruction.R_SLT));
        assertEquals(0x00, Instruction.getFunctShifted(Instruction.R_SLL));
        assertEquals(0x02, Instruction.getFunctShifted(Instruction.R_SRL));
        assertEquals(0x03, Instruction.getFunctShifted(Instruction.R_SRA));
        assertEquals(0x04, Instruction.getFunctShifted(Instruction.R_SLLV));
        assertEquals(0x06, Instruction.getFunctShifted(Instruction.R_SRLV));
        assertEquals(0x07, Instruction.getFunctShifted(Instruction.R_SRAV));
        assertEquals(0x08, Instruction.getFunctShifted(Instruction.R_JR));
    }

    @Test
    public void testITypeOpcodes() {
        assertEquals(0x08, Instruction.getOpcodeShifted(Instruction.I_ADDI));
        assertEquals(0x09, Instruction.getOpcodeShifted(Instruction.I_ADDIU));
        assertEquals(0x23, Instruction.getOpcodeShifted(Instruction.I_LW));
        assertEquals(0x21, Instruction.getOpcodeShifted(Instruction.I_LH));
        assertEquals(0x25, Instruction.getOpcodeShifted(Instruction.I_LHU));
        assertEquals(0x20, Instruction.getOpcodeShifted(Instruction.I_LB));
        assertEquals(0x24, Instruction.getOpcodeShifted(Instruction.I_LBU));
        assertEquals(0x2B, Instruction.getOpcodeShifted(Instruction.I_SW));
        assertEquals(0x29, Instruction.getOpcodeShifted(Instruction.I_SH));
        assertEquals(0x28, Instruction.getOpcodeShifted(Instruction.I_SB));
        assertEquals(0x0F, Instruction.getOpcodeShifted(Instruction.I_LUI));
        assertEquals(0x0C, Instruction.getOpcodeShifted(Instruction.I_ANDI));
        assertEquals(0x0D, Instruction.getOpcodeShifted(Instruction.I_ORI));
        assertEquals(0x0A, Instruction.getOpcodeShifted(Instruction.I_SLTI));
        assertEquals(0x04, Instruction.getOpcodeShifted(Instruction.I_BEQ));
        assertEquals(0x05, Instruction.getOpcodeShifted(Instruction.I_BNE));
    }

    @Test
    public void testJTypeOpcodes() {
        assertEquals(0x02, Instruction.getOpcodeShifted(Instruction.J_J));
        assertEquals(0x03, Instruction.getOpcodeShifted(Instruction.J_JAL));
    }
}
