package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class CharToColorTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(char x, Color expected) {
        Color a;
        a = Color.toColor(x);
        assertTrue("Expected " + expected + " for input char " + x +
                ", but got " + a + ".", a == expected);
    }

    @Test
    public void testG() {
        test('G', Color.GREEN);
    }

    @Test
    public void testB() {
        test('B', Color.BLUE);
    }

    @Test
    public void testW() {
        test('W', Color.WHITE);
    }

    @Test
    public void testR() {
        test('R', Color.RED);
    }

    @Test
    public void testN() {
        test('N', Color.NONE);
    }
}