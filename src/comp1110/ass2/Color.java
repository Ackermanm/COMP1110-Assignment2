package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.*;

public enum Color {
    GREEN, BLUE, RED, WHITE, NONE, FATHER;

    public char toChar() {
        if (this == GREEN) {
            return 'G';
        } else if (this == BLUE) {
            return 'B';
        } else if (this == RED) {
            return 'R';
        } else if (this == WHITE) {
            return 'W';
        } else if (this == NONE) {
            return 'N';
        } else {
            return 'F';
        }
    }


    public static Color toColor(char c) {
        switch (c) {
            case 'G':
                return GREEN;
            case 'B':
                return BLUE;
            case 'R':
                return RED;
            case 'W':
                return WHITE;
        }
        return NONE;
    }


    @Test
    public void testColor() {
        char a = 'G';
        char b = 'B';
        char c = 'R';
        char d = 'W';
        assertEquals(GREEN, toColor(a));
        assertEquals(BLUE, toColor(b));
        assertEquals(RED, toColor(c));
        assertEquals(WHITE, toColor(d));
    }
}
