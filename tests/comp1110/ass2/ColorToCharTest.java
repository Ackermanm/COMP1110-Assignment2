package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertTrue;

public class ColorToCharTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(Color x, char expectValue) {
        char out = x.toChar();
        assertTrue("Expected " + expectValue + " for input color " + x +
                ", but got " + out + ".", out == expectValue);
    }

    @Test
    public void testGreen() {
        test(Color.GREEN, 'G');
    }

    @Test
    public void testNone() {
        test(Color.NONE, 'N');
    }

    @Test
    public void testBlue() {
        test(Color.BLUE, 'B');
    }

    @Test
    public void testRed() {
        test(Color.RED, 'R');
    }

    @Test
    public void testWhite() {
        test(Color.WHITE, 'W');
    }

    @Test
    public void testFather() {
        test(Color.FATHER, 'F');
    }
}

