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
    public void test_a() {
        test("a000", Rotation.ZERO);
        test("a001", Rotation.ONE);
        test("a002", Rotation.TWO);
        test("a003", Rotation.THREE);
    }
    @Test
    public void test_b() {
        test("b120", Rotation.ZERO);
        test("b231", Rotation.ONE);
        test("b102", Rotation.TWO);
        test("b223", Rotation.THREE);
    }
    @Test
    public void test_c() {
        test("c110", Rotation.ZERO);
        test("c211", Rotation.ONE);
        test("c312", Rotation.TWO);
        test("c103", Rotation.THREE);
    }
    @Test
    public void test_d() {
        test("d220", Rotation.ZERO);
        test("d131", Rotation.ONE);
        test("d302", Rotation.TWO);
        test("d023", Rotation.THREE);
    }
    @Test
    public void test_e() {
        test("e100", Rotation.ZERO);
        test("e001", Rotation.ONE);
        test("e032", Rotation.TWO);
        test("e213", Rotation.THREE);
    }
    @Test
    public void test_f() {
        test("f020", Rotation.ZERO);
        test("f101", Rotation.ONE);
        test("f332", Rotation.TWO);
        test("f213", Rotation.THREE);
    }
    @Test
    public void test_g() {
        test("g300", Rotation.ZERO);
        test("g101", Rotation.ONE);
        test("g312", Rotation.TWO);
        test("g103", Rotation.THREE);
    }
    @Test
    public void test_h() {
        test("h210", Rotation.ZERO);
        test("h201", Rotation.ONE);
        test("h132", Rotation.TWO);
        test("h203", Rotation.THREE);
    }
    @Test
    public void test_i() {
        test("i110", Rotation.ZERO);
        test("i211", Rotation.ONE);
        test("i332", Rotation.TWO);
        test("i033", Rotation.THREE);
    }
    @Test
    public void test_j() {
        test("j210", Rotation.ZERO);
        test("j201", Rotation.ONE);
        test("j312", Rotation.TWO);
        test("j213", Rotation.THREE);
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
