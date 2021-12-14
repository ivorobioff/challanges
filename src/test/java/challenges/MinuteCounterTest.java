package challenges;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinuteCounterTest {

    @Test
    public void testCounting() {
        MinuteCounter counter = new MinuteCounter();

        assertEquals(690, counter.count("12:30pm-12:00am"));
        assertEquals(1425, counter.count("1:23am-1:08am"));
        assertEquals(0, counter.count("11:00pm-11:00pm"));
        assertEquals(390, counter.count("10:15am-04:45pm"));
    }
}
