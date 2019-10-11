package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IsNextBlank {
    @Test
    public void testIsNextBlank() {  // test the state of next position
        Color[][] testboardstates = {
                {Color.NONE,Color.RED,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.BLUE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.WHITE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},

        };
        assertTrue(FocusGame.isBlank(testboardstates, FocusGame.nextX(0, 0), FocusGame.nextY(0, 0)) == false);
        assertTrue(FocusGame.isBlank(testboardstates, FocusGame.nextX(1, 0), FocusGame.nextY(1, 0)) == true);
        assertTrue(FocusGame.isBlank(testboardstates, FocusGame.nextX(4, 1), FocusGame.nextY(4, 1)) == false);
        assertTrue(FocusGame.isBlank(testboardstates, FocusGame.nextX(8, 3), FocusGame.nextY(8, 3)) == false);
        assertTrue(FocusGame.isBlank(testboardstates, FocusGame.nextX(3, 3), FocusGame.nextY(3, 3)) == true);
    }
}
