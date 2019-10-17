package comp1110.ass2;

/**
 * Create different enum types to represent piece colors.
 */
public enum Color {
    GREEN, BLUE, RED, WHITE, NONE, B;

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

}