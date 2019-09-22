package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public enum Rotation {
    ZERO, ONE, TWO, THREE;
    public String rotationToNumber(){
        switch (this){
            case ZERO:
                return "0";
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
                return "3";
        }
        return "0";
    }


    @Test
    public void testRotation() {
        assertEquals("0", ZERO);
        assertEquals("1", ONE);
        assertEquals("2", TWO);
        assertEquals("3", THREE);
    }
}
