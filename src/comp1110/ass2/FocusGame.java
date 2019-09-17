package comp1110.ass2;
import comp1110.ass2.Color.*;
import comp1110.ass2.Piece.*;
import java.util.Set;

import static comp1110.ass2.Color.NONE;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {

    private Color[][] boardstates = {
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},

    };

    private int m;

    private Piece[][] pieces = new Piece[5][9];
    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) { // FIXME Task 2: determine whether a piece placement is well-formed

        return (piecePlacement.length() == 4)
                && (piecePlacement.charAt(0) >= 'a' && piecePlacement.charAt(0) <= 'j')
                && (piecePlacement.charAt(1) >= '0' && piecePlacement.charAt(1) <= '8')
                && (piecePlacement.charAt(2) >= '0' && piecePlacement.charAt(2) <= '4')
                && (piecePlacement.charAt(3) >= '0' && piecePlacement.charAt(3) <= '3')? true : false;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) { // FIXME Task 3: determine whether a placement is well-formed

        if (placement.equals("") || placement.length() % 4 != 0) {
            return false;
        } else {
            boolean check = true; //check every piece is well placed
            for (int i = 0; i < placement.length(); i = i + 4) {
                if (!(isPiecePlacementWellFormed(placement.substring(i, i + 4)))) {
                    check = false;
                    break;
                }
            }

            int counter = 0; // count the amount of duplicate piece
            for (int i = 0; i < placement.length(); i = i + 4) {
                for (int j = i + 4; j < placement.length(); j = j + 4) {
                    if (placement.charAt(i) == placement.charAt(j)) {
                        counter++;
                    }
                }
            }
            return check && (counter == 0) ? true : false;
        }
    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     *   rules of the game:
     *   - pieces must be entirely on the board
     *   - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementStringValid(String placement) {
        // FIXME Task 5: determine whether a placement string is valid
        Color[][] boardstates = {
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},

        };
        for (int i = 0 ; i< placement.length();i+=4 ){
            String pieceString = placement.substring(i,i+4);
            Piece piece = new Piece(pieceString);
            int originalY = piece.getLocation().getX();
            int originalX = piece.getLocation().getY();
            if (originalX < 0 || originalY < 0 || originalX >= 5 || originalY >= 9){
                return false;
            }
            int rightCornerX = piece.getPieceType().getRightCornerLocation(originalX,originalY,piece.getRotation()).getX();
            int rightCornerY = piece.getPieceType().getRightCornerLocation(originalX,originalY,piece.getRotation()).getY();
            if (rightCornerX < 0 || rightCornerY < 0 || rightCornerX >= 5 || rightCornerY >= 9){
                return false;
            }
            for (int j = 0; j < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getX() ; j++){
                for (int k = 0; k < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getY(); k++){
                    int x = piece.getLocation().getY()+j;
                    int y = piece.getLocation().getX()+k;
                    if (piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != null){
                        if (boardstates[x][y] == NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation())!=NONE)  {
                            boardstates[x][y] = piece.getPieceType().colorFromOffSet(j, k, piece.getRotation());
                        }
                        else if (boardstates[x][y] != NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation())!=NONE){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     *
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     *                  which represents the color of the 3*3 central board area
     *                  squares indexed as follows:
     *                  [0] [1] [2]
     *                  [3] [4] [5]
     *                  [6] [7] [8]
     *                  each character may be any of
     *                  - 'R' = RED square
     *                  - 'B' = Blue square
     *                  - 'G' = Green square
     *                  - 'W' = White square
     * @param col      The cell's column.
     * @param row      The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        return null;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     *
     * A given challenge can only solved with a single placement of pieces.
     *
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     *   orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        return null;
    }
}
