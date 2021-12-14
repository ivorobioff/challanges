package challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicCalculatorTest {

    @Test
    public void testCalculations() {
        BasicCalculator calculator = new BasicCalculator();

        assertEquals(12.0, calculator.calculate("12"));
        assertEquals(12.0, calculator.calculate("(12)"));
        assertEquals(36.0, calculator.calculate("(12)(3)"));
        assertEquals(24.0, calculator.calculate("45-10*2-1"));
        assertEquals(200.0, calculator.calculate("100*2"));
        assertEquals(812.0, calculator.calculate("812/2*(5-3)"));
        assertEquals(14.0, calculator.calculate("7-4-1+8(3)/2"));
        assertEquals(47.0, calculator.calculate("15*3+10/5"));
        assertEquals(-3900.0, calculator.calculate("8-7*(12+100/2)*9-2"));
        assertEquals(1428.0, calculator.calculate("2(2+4(2+3)(3+2))*7"));
    }
}
