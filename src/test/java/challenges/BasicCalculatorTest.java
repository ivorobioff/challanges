package challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicCalculatorTest {

    @Test
    public void testSimple() {
        BasicCalculator calculator = new BasicCalculator();

        assertEquals(47.0, calculator.calculate("15*3+10/5"));
    }
}
