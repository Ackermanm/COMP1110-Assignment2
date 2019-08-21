package comp1110.ass2;

public enum PieceType {
    a, b, c, d, e, f, g, h, i, j;

    public Color colorFromOffSet(int xoff, int yoff, Rotation rotation) {
        if (this == a || this == d || this == e || this == g) {
            if (xoff < 0 || yoff < 0 || xoff > 2 || yoff > 2) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return (yoff == 2) ? null : colors[xoff + 2 * yoff];
                case ONE:
                    return (xoff == 2) ? null : colors[2 * xoff + (1 - yoff)];
                case TWO:
                    return (yoff == 2) ? null : colors[(1 - xoff) + 2 * (2 - yoff)];
                case THREE:
                    return (xoff == 2) ? null : colors[2 * (2 - xoff) + yoff];
            }
        }
        if (this == b || this == c || this == j) {
            if (xoff < 0 || yoff < 0 || xoff > 3 || yoff > 3) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return (yoff == 2) ? null : colors[xoff + 2 * yoff];
                case ONE:
                    return (xoff == 2) ? null : colors[2 * xoff + (1 - yoff)];
                case TWO:
                    return (yoff == 2) ? null : colors[(1 - xoff) + 2 * (3 - yoff)];
                case THREE:
                    return (xoff == 2) ? null : colors[2 * (3 - xoff) + yoff];
            }
        }
        if (this == f) {
            if (xoff < 0 || yoff < 0 || xoff > 2 || yoff > 2) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return (yoff == 1) ? null : colors[yoff];
                case ONE:
                    return (xoff == 1) ? null : colors[xoff];
                case TWO:
                    return (yoff == 1) ? null : colors[yoff];
                case THREE:
                    return (xoff == 1) ? null : colors[xoff];
            }
        }
        if (this==h){
            if (xoff < 0 || yoff < 0 || xoff > 3 || yoff > 3) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return colors[xoff+3*yoff];
                case ONE:
                    return colors[3*xoff+(2-yoff)];
                case TWO:
                    return colors[(2-xoff)+3*(2-yoff)];
                case THREE:
                    return colors[3*(2-xoff)+yoff];
            }
        }
        if (this==i){
            if (xoff < 0 || yoff < 0 || xoff >1  || yoff > 1) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return colors[2*xoff+yoff];
                case ONE:
                    return colors[2*xoff-yoff];
                case TWO:
                    return colors[-xoff+2*(2-yoff)];
                case THREE:
                    return colors[2*(2-xoff)+(yoff-1)];
            }
        }
        return null;
    }
    private static Color[][] colormap = {
            {Color.GREEN,Color.NONE,Color.WHITE,Color.RED,Color.RED,Color.NONE},
            {Color.NONE,Color.WHITE,Color.BLUE,Color.WHITE,Color.GREEN,Color.NONE,Color.GREEN,Color.NONE},
            {Color.NONE,Color.RED,Color.NONE,Color.RED,Color.GREEN,Color.WHITE,Color.NONE,Color.BLUE},
            {Color.RED,Color.NONE,Color.RED,Color.NONE,Color.RED,Color.BLUE},
            {Color.BLUE,Color.RED,Color.BLUE,Color.RED,Color.BLUE,Color.NONE},
            {Color.WHITE,Color.WHITE,Color.WHITE},
            {Color.WHITE,Color.NONE,Color.BLUE,Color.BLUE,Color.NONE,Color.WHITE},
            {Color.RED,Color.WHITE,Color.WHITE,Color.GREEN,Color.NONE,Color.NONE,Color.GREEN,Color.NONE,Color.NONE},
            {Color.BLUE,Color.NONE,Color.BLUE,Color.WHITE},
            {Color.GREEN,Color.GREEN,Color.GREEN,Color.NONE,Color.WHITE,Color.NONE,Color.RED,Color.NONE}

    };
}
