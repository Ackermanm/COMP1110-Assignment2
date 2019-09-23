package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertTrue;

    public class rotationToNumberTest {
        @Rule
        public Timeout globalTimeout = Timeout.millis(1000);

        private void test(Rotation x, char expectValue) {
            String out = x.toString();
            assertTrue("Expected " + expectValue + " for input rotation" + x +
                    ", but got " + out + ".", out.equals(expectValue));
        }

        @Test
        public void testRotationToNumber() {
            test(Rotation.ZERO, '0');

            test(Rotation.ONE, '1');

            test(Rotation.TWO, '2');

            test(Rotation.THREE, '3');
        }
    } 
