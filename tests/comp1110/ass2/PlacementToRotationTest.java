package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class PlacementToRotationTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(String placement, Rotation expectValue) {
        char out = placement.charAt(3);
        assertTrue("Expected " + expectValue + " for input placement " + placement +
                ", but got " + out + ".", getRotation(out) == expectValue);
    }

    @Test
    public void PlacementToRotationTest() {
        test("a000", Rotation.ZERO);
        test("a001", Rotation.ONE);
        test("a002", Rotation.TWO);
        test("a003", Rotation.THREE);

        test("b000", Rotation.ZERO);
        test("b001", Rotation.ONE);
        test("b002", Rotation.TWO);
        test("b003", Rotation.THREE);

        test("c000", Rotation.ZERO);
        test("c001", Rotation.ONE);
        test("c002", Rotation.TWO);
        test("c003", Rotation.THREE);

        test("d000", Rotation.ZERO);
        test("d001", Rotation.ONE);
        test("d002", Rotation.TWO);
        test("d003", Rotation.THREE);
    }


    public Rotation getRotation(char x) {
        if (x == '0') {
            return Rotation.ZERO;
        }
        if (x == '1') {
            return Rotation.ONE;
        }
        if (x == '2') {
            return Rotation.TWO;
        }
        if (x == '3') {
            return Rotation.THREE;
        } else {
            return null;
        }
    }
}
