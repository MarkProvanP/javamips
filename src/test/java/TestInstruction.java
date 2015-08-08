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
    }
}
