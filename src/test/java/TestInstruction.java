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
        assertEquals(0x20, Instruction.getFunctShifted(Instruction.ADD));
        assertEquals(0x21, Instruction.getFunctShifted(Instruction.ADDU));
        assertEquals(0x22, Instruction.getFunctShifted(Instruction.SUB));
        assertEquals(0x23, Instruction.getFunctShifted(Instruction.SUBU));
        assertEquals(0x18, Instruction.getFunctShifted(Instruction.MULT));
        assertEquals(0x19, Instruction.getFunctShifted(Instruction.MULTU));
        assertEquals(0x1A, Instruction.getFunctShifted(Instruction.DIV));
        assertEquals(0x1B, Instruction.getFunctShifted(Instruction.DIVU));
        assertEquals(0x10, Instruction.getFunctShifted(Instruction.MFHI));
        assertEquals(0x12, Instruction.getFunctShifted(Instruction.MFLO));
        assertEquals(0x24, Instruction.getFunctShifted(Instruction.AND));
        assertEquals(0x25, Instruction.getFunctShifted(Instruction.OR));
        assertEquals(0x26, Instruction.getFunctShifted(Instruction.XOR));
        assertEquals(0x27, Instruction.getFunctShifted(Instruction.NOR));
        assertEquals(0x2A, Instruction.getFunctShifted(Instruction.SLT));
        assertEquals(0x00, Instruction.getFunctShifted(Instruction.SLL));
        assertEquals(0x02, Instruction.getFunctShifted(Instruction.SRL));
        assertEquals(0x03, Instruction.getFunctShifted(Instruction.SRA));
        assertEquals(0x04, Instruction.getFunctShifted(Instruction.SLLV));
        assertEquals(0x06, Instruction.getFunctShifted(Instruction.SRLV));
        assertEquals(0x07, Instruction.getFunctShifted(Instruction.SRAV));
        assertEquals(0x08, Instruction.getFunctShifted(Instruction.JR));
    }

    @Test
    public void testITypeOpcodes() {
        assertEquals(0x08, Instruction.getOpcodeShifted(Instruction.ADDI));
        assertEquals(0x09, Instruction.getOpcodeShifted(Instruction.ADDIU));
        assertEquals(0x23, Instruction.getOpcodeShifted(Instruction.LW));
        assertEquals(0x21, Instruction.getOpcodeShifted(Instruction.LH));
        assertEquals(0x25, Instruction.getOpcodeShifted(Instruction.LHU));
        assertEquals(0x20, Instruction.getOpcodeShifted(Instruction.LB));
        assertEquals(0x24, Instruction.getOpcodeShifted(Instruction.LBU));
        assertEquals(0x2B, Instruction.getOpcodeShifted(Instruction.SW));
        assertEquals(0x29, Instruction.getOpcodeShifted(Instruction.SH));
        assertEquals(0x28, Instruction.getOpcodeShifted(Instruction.SB));
        assertEquals(0x0F, Instruction.getOpcodeShifted(Instruction.LUI));
        assertEquals(0x0C, Instruction.getOpcodeShifted(Instruction.ANDI));
        assertEquals(0x0D, Instruction.getOpcodeShifted(Instruction.ORI));
        assertEquals(0x0A, Instruction.getOpcodeShifted(Instruction.SLTI));
        assertEquals(0x04, Instruction.getOpcodeShifted(Instruction.BEQ));
        assertEquals(0x05, Instruction.getOpcodeShifted(Instruction.BNE));
    }

    @Test
    public void testJTypeOpcodes() {
        assertEquals(0x02, Instruction.getOpcodeShifted(Instruction.J));
        assertEquals(0x03, Instruction.getOpcodeShifted(Instruction.JAL));
    }
}
