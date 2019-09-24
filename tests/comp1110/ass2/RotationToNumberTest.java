package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class RotationToNumberTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(Rotation x, char expectValue) {
        char out = rotationToChar(x);
        assertTrue("Expected " + expectValue + " , but got " + out + ".",
                expectValue == out);
    }

    @Test
    public void testZERO() {
        test(Rotation.ZERO, '0');
    }
    @Test
    public void testONE() {
        test(Rotation.ONE, '1');
    }
    @Test
    public void testTWO() {
        test(Rotation.TWO, '2');
    }
    @Test
    public void testTHREE() {
        test(Rotation.THREE, '3');
    }


    public char rotationToChar(Rotation x) {
        if (x == Rotation.ZERO) {
            return '0';
        }
        if (x == Rotation.ONE) {
            return '1';
        }
        if (x == Rotation.TWO) {
            return '2';
        }
        if (x == Rotation.THREE) {
            return '3';
        }else{
            return ' ';
        }
    }
}
