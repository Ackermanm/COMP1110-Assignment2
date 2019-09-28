package comp1110.ass2;

import static comp1110.ass2.Color.*;

import static comp1110.ass2.Rotation.*;
public enum PieceType {
    A, B, C, D, E, F, G, H, I, J;

    public Color colorFromOffSet(int xoff, int yoff, Rotation rotation) {
        if (this == A || this == D || this == E || this == G) {
            if (xoff < 0 || yoff < 0 || xoff > 2 || yoff > 2) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return (xoff >= 2) ? null : colors[xoff + 2 * yoff];
                case ONE:
                    return (yoff >= 2) ? null : colors[2 * xoff + (1 - yoff)];
                case TWO:
                    return (xoff >= 2) ? null : colors[(1 - xoff) + 2 * (2 - yoff)];
                case THREE:
                    return (yoff >= 2) ? null : colors[2 * (2 - xoff) + yoff];
            }
        }
        if (this == B || this == C || this == J) {
            if (xoff < 0 || yoff < 0 || xoff > 3 || yoff > 3) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return (xoff >= 2) ? null : colors[xoff + 2 * yoff];
                case ONE:
                    return (yoff >= 2) ? null : colors[2 * xoff + (1 - yoff)];
                case TWO:
                    return (xoff >= 2) ? null : colors[(1 - xoff) + 2 * (3 - yoff)];
                case THREE:
                    return (yoff >= 2) ? null : colors[2 * (3 - xoff) + yoff];
            }
        }
        if (this == F) {
            if (xoff < 0 || yoff < 0 || xoff > 2 || yoff > 2) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return (xoff >= 1) ? null : colors[yoff];
                case ONE:
                    return (yoff >= 1) ? null : colors[xoff];
                case TWO:
                    return (xoff >= 1) ? null : colors[yoff];
                case THREE:
                    return (yoff >= 1) ? null : colors[xoff];
            }
        }
        if (this==H){
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
        if (this==I){
            if (xoff < 0 || yoff < 0 || xoff >1  || yoff > 1) return null;
            Color[] colors = colormap[this.ordinal()];
            switch (rotation) {
                case ZERO:
                    return colors[xoff+2*yoff];
                case ONE:
                    return colors[2*xoff-yoff+1];
                case TWO:
                    return colors[1-xoff+2*(1-yoff)];
                case THREE:
                    return colors[2*(1-xoff)+yoff];
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
    public Location getRightCornerLocation(int originalX, int originalY, Rotation rotation){
        Location rithtCornerLoation = new Location();//leftAndRightLoaction[0] and [1] are the lower right corner of this piece.
        if (this == A || this == D || this == E || this == G){
            if (rotation == ZERO || rotation == TWO){
                rithtCornerLoation.setX(originalX+1);
                rithtCornerLoation.setY(originalY+2);
                return rithtCornerLoation;
            }
            if (rotation == ONE || rotation == THREE){
                rithtCornerLoation.setX(originalX+2);
                rithtCornerLoation.setY(originalY+1);
                return rithtCornerLoation;
            }

        }
        if (this == B || this == C || this == J){
            if (rotation == ZERO || rotation == TWO){
                rithtCornerLoation.setX(originalX+1);
                rithtCornerLoation.setY(originalY+3);
                return rithtCornerLoation;
            }
            if (rotation == ONE || rotation == THREE){
                rithtCornerLoation.setX(originalX+3);
                rithtCornerLoation.setY(originalY+1);
                return rithtCornerLoation;
            }
        }
        if (this == F){
            if (rotation == ZERO || rotation == TWO){
                rithtCornerLoation.setX(originalX);
                rithtCornerLoation.setY(originalY+2);
                return rithtCornerLoation;
            }
            if (rotation == ONE || rotation == THREE){
                rithtCornerLoation.setX(originalX+2);
                rithtCornerLoation.setY(originalY);
                return rithtCornerLoation;
            }
        }
        if (this==H){
            rithtCornerLoation.setX(originalX+2);
            rithtCornerLoation.setY(originalY+2);
            return rithtCornerLoation;
        }
        if (this == I){
            rithtCornerLoation.setX(originalX+1);
            rithtCornerLoation.setY(originalY+1);
            return rithtCornerLoation;
        }
        return rithtCornerLoation;
    }

    public Location getLengthAndHeight(Rotation rotation){
        Location rithtCornerLoation = new Location();//leftAndRightLoaction[0] and [1] are the lower right corner of this piece.
        if (this == A || this == D || this == E || this == G){
            if (rotation == ZERO || rotation == TWO){
                rithtCornerLoation.setX(2);
                rithtCornerLoation.setY(3);
                return rithtCornerLoation;
            }
            if (rotation == ONE || rotation == THREE){
                rithtCornerLoation.setX(3);
                rithtCornerLoation.setY(2);
                return rithtCornerLoation;
            }

        }
        if (this == B || this == C || this == J){
            if (rotation == ZERO || rotation == TWO){
                rithtCornerLoation.setX(2);
                rithtCornerLoation.setY(4);
                return rithtCornerLoation;
            }
            if (rotation == ONE || rotation == THREE){
                rithtCornerLoation.setX(4);
                rithtCornerLoation.setY(2);
                return rithtCornerLoation;
            }
        }
        if (this == F){
            if (rotation == ZERO || rotation == TWO){
                rithtCornerLoation.setX(1);
                rithtCornerLoation.setY(3);
                return rithtCornerLoation;
            }
            if (rotation == ONE || rotation == THREE){
                rithtCornerLoation.setX(3);
                rithtCornerLoation.setY(1);
                return rithtCornerLoation;
            }
        }
        if (this==H){
            rithtCornerLoation.setX(3);
            rithtCornerLoation.setY(3);
            return rithtCornerLoation;
        }
        if (this == I){
            rithtCornerLoation.setX(2);
            rithtCornerLoation.setY(2);
            return rithtCornerLoation;
        }
        return rithtCornerLoation;
    }
}