package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import comp1110.ass2.Color.*;
import comp1110.ass2.Piece.*;

import java.util.HashSet;
import java.util.Set;

import static comp1110.ass2.Color.FATHER;
import static comp1110.ass2.Color.NONE;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {

    private static final Color[][] BLANK_BOARD_STATE = {
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
            {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},

    };

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

    public static boolean isPlacementStringValid(Color[][] boardstates, String placement) {
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

    public static Color[][] newBoardWithChallenge(String placement, String challenge){
        Color[][] boardstates = {
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},
        };
        //Update board states by challenge.
        int k = 0;
        for (int i =1; i<4; i++){
            for (int j = 3; j<6; j++){
                boardstates[i][j] = Color.toColor(challenge.charAt(k));
                k++;
            }
        }
        //Update board states with placement.
        if (placement.length() > 0) {
            if (FocusGame.isPlacementStringValid(boardstates,placement)){
                FocusGame.updateBoardstates(boardstates,placement);
            }
        }
        return boardstates;
    }

    public static Color[][] newBoardWithChallenge(String challenge){
        Color[][] boardstates = {
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},
        };
        //Update board states by challenge.
        int k = 0;
        for (int i =1; i<4; i++){
            for (int j = 3; j<6; j++){
                boardstates[i][j] = Color.toColor(challenge.charAt(k));
                k++;
            }
        }
        return boardstates;
    }

    public static Color[][] updateBoardstates (String placement) {
        Color[][] boardstates = {
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},
        };
        if (FocusGame.isPlacementStringValid(placement)) {
            for (int i = 0; i < placement.length(); i += 4) {
                String pieceString = placement.substring(i, i + 4);
                Piece piece = new Piece(pieceString);
                int originalY = piece.getLocation().getX();
                int originalX = piece.getLocation().getY();
                for (int j = 0; j < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getX(); j++) {
                    for (int k = 0; k < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getY(); k++) {
                        int x = piece.getLocation().getY() + j;
                        int y = piece.getLocation().getX() + k;
                        if (piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != null) {
                            if (boardstates[x][y] == NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != NONE) {
                                boardstates[x][y] = piece.getPieceType().colorFromOffSet(j, k, piece.getRotation());
                            }
                        }
                    }
                }
            }
        }
        return boardstates;
    }
    public static Color[][] updateBoardstates (Color[][] boardstates, String placement) {
        for (int i = 0 ; i< placement.length();i+=4 ){
            String pieceString = placement.substring(i,i+4);
            Piece piece = new Piece(pieceString);
            int originalY = piece.getLocation().getX();
            int originalX = piece.getLocation().getY();
            for (int j = 0; j < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getX() ; j++){
                for (int k = 0; k < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getY(); k++){
                    int x = piece.getLocation().getY()+j;
                    int y = piece.getLocation().getX()+k;
                    if (piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != null){
                        if (boardstates[x][y] == NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation())!=NONE)  {
                            boardstates[x][y] = piece.getPieceType().colorFromOffSet(j, k, piece.getRotation());
                        }
                    }
                }
            }
        }
        return boardstates;
    }
    public static void seeMap(Color[][] boardstates){
        for (int i = 0; i<5;i++){
            for (int j =0;j<9;j++){
                System.out.print(boardstates[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static boolean placeConsistentWithChallenge(Color[][] withChallenge, Color[][] noChallenge){
        for (int i =1; i<4; i++){
            for (int j = 3; j<6; j++){
                if (noChallenge[i][j]!=NONE){
                    if (noChallenge[i][j]!=withChallenge[i][j])
                        return false;
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
        Set<String> set = new HashSet<>();
        //Get pieces' types which are not in placement string.
        String allPieceType = "ABCDEFGHIJ";
        String notPlacedPieceType = allPieceType;
        for (int i = 0; i < placement.length(); i += 4) {
            String placedPieceType = Character.toString(placement.charAt(i)).toUpperCase();
            notPlacedPieceType = notPlacedPieceType.replace("" + placedPieceType + "", "");
        }
        //l is the piece types which have not been used.
        if (notPlacedPieceType != null && notPlacedPieceType != "") {
            for (int l = 0; l < notPlacedPieceType.length(); l++) {
                for (int i = -3; i <= 0; i++) {
                    for (int j = -3; j <= 0; j++) {
                        int tempCol = col + i;
                        int tempRow = row + j;
                        if (tempCol >= 0 && tempRow >= 0) {
                            for (Rotation r : Rotation.values()) {
                                String pieceString = Character.toString(notPlacedPieceType.charAt(l)).toLowerCase() + "" + tempCol + "" + "" + tempRow + "" + r.rotationToNumber();
                                Color[][] boardstates = updateBoardstates(placement);
                                Color[][] challengeBoardstates = newBoardWithChallenge(challenge);
                                Color[][] pieceStringBoardstates = updateBoardstates(pieceString);
                                if (placeConsistentWithChallenge(challengeBoardstates, boardstates)
                                        && isPlacementStringValid(boardstates, pieceString)
                                        && placeConsistentWithChallenge(challengeBoardstates, pieceStringBoardstates)
                                ) {
                                    Color[][] putPieceString = updateBoardstates(boardstates, pieceString);
                                    if (putPieceString[row][col] != NONE) {
                                        set.add(pieceString);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (set.size() == 0) {
            return null;
        } else return set;

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
    public static String getSolution(String challenge) { // FIXME Task 9: determine the solution to the game, given a particular challenge
        String state = "";
        String next = "";
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 9; x++) {
                if (isBlank(updateBoardstates(state), x, y)) {
                    next = nextPlacement(state, challenge, x, y);
                    state += next;
                }
            }
        }
        /*Set<String> startSet = getViablePiecePlacements("", challenge, 0, 0);
        int size = startSet.size();
        for (int i = 0; i < size; i++) {
            Color[][] boardstates = updateBoardstates(BLANK_BOARD_STATE, (String)startSet.toArray()[i]);
            solution += startSet.toArray()[i];
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (boardstates[x][y] != null && boardstates[x][y] != FATHER) {
                        Set<String> recursionSet = getViablePiecePlacements(solution, challenge, x, y);
                        if (recursionSet != null) {

                        }
                    }
                }
            }
        }*/

        return state;
    }

    public static Boolean isBlank(Color[][] boardstates, int x, int y) { // determine whether a position have color or not
        if (boardstates[y][x] == Color.NONE)
            return true;
        else
            return false;
    }

    public static int nextX(int x, int y) {
        int result = 0;
        if (x < 8) {
            result = x + 1;
        } else {
            result = 0;
        }
        return result;
    }

    public static int nextY(int x, int y) {
        int result = 0;
        if (x < 8) {
            result = y;
        } else {
            result = y + 1;
        }
        return result;
    }

    @Test
    public void testIsNextBlank() {  // test the state of next position
        Color[][] testboardstates = {
                {Color.NONE,Color.RED,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.BLUE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.NONE,Color.NONE,Color.NONE,Color.WHITE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE},
                {Color.FATHER,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.NONE,Color.FATHER},

        };
        assertTrue(isBlank(testboardstates, nextX(0, 0), nextY(0, 0)) == false);
        assertTrue(isBlank(testboardstates, nextX(1, 0), nextY(1, 0)) == true);
        assertTrue(isBlank(testboardstates, nextX(4, 1), nextY(4, 1)) == false);
        assertTrue(isBlank(testboardstates, nextX(8, 3), nextY(8, 3)) == false);
        assertTrue(isBlank(testboardstates, nextX(3, 3), nextY(3, 3)) == true);
    }

    public static int[] nextBlank(String state, String challenge, int x, int y) {
        Color[][] boardstates = updateBoardstates(state);
        int[] result = new int[2];
        while (!isBlank(boardstates, x, y)) {
            int temp = x;
            x = nextX(temp, y);
            y = nextY(temp, y);
        }
        result[0] = x;
        result[1] = y;
        return result;
    }

    /*public static String changeLastPlace() {
        return null;
    }*/

    public static boolean isNextBlankHasSet(String currentState, String next, String challenge, int currentX, int currentY) {

        return false;
    }

    public static String nextPlacement(String state, String challenge, int x, int y) {
        int length = state.length();
        String next = "";
        Color[][] boardstates = updateBoardstates(state);
        Set<String> set = getViablePiecePlacements(state, challenge, x, y);
        if (set != null) {
            int size = set.size();
            for (int i = 0; i < size; i++) {
                next = (String)set.toArray()[i];
                state += next;
                Set<String> set1 = getViablePiecePlacements(state, challenge, nextBlank(state, challenge, x, y)[0], nextBlank(state, challenge, x, y)[1]);
                if (set1 == null) {
                    state = state.substring(0, state.length()-4);
                } else {
                    break;
                }
            }
        }
        /*if (state.length() < 13) return next + nextPlacement(state, challenge);
        else return next;*/
        return next;
    }

    public static void main(String[] args) {
        Color[][] boardstates = BLANK_BOARD_STATE;
        boardstates = updateBoardstates("e003");
        int x = 0;
        int y = 0;
        do {
            int temp = x;
            x = nextX(temp, y);
            y = nextY(temp, y);
        }  while (!isBlank(boardstates, nextX(x, y), nextY(x, y)));
        System.out.println(x);
        System.out.println(y);
        Set<String> set1 = getViablePiecePlacements("e003", "RRRRRRRRR", 1, 0);
        System.out.println(set1);
        String t = nextPlacement("e003", "RRRRRRRRR", 1, 0);
        System.out.println(t);
        String sd = nextPlacement("", "RRRRRRRRR", 0, 0);
        System.out.println(sd);
    }
}
