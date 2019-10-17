package comp1110.ass2;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import static comp1110.ass2.Color.NONE;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {

    private static Color[][] boardstates = {
            {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
            {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
            {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
            {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
            {Color.B, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.B},

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
                && (piecePlacement.charAt(3) >= '0' && piecePlacement.charAt(3) <= '3') ? true : false;
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
     * <p>
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     * @author Yafei Liu(u6605935)
     */
    public static boolean isPlacementStringValid(String placement) {
        // FIXME Task 5: determine whether a placement string is valid
        Color[][] boardstates = {
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.B, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.B},

        };
        for (int i = 0; i < placement.length(); i += 4) {
            String pieceString = placement.substring(i, i + 4);
            Piece piece = new Piece(pieceString);
            int originalY = piece.getLocation().getX();
            int originalX = piece.getLocation().getY();
            if (originalX < 0 || originalY < 0 || originalX >= 5 || originalY >= 9) {
                return false;
            }
            int rightCornerX = piece.getPieceType().getRightCornerLocation(originalX, originalY, piece.getRotation()).getX();
            int rightCornerY = piece.getPieceType().getRightCornerLocation(originalX, originalY, piece.getRotation()).getY();
            if (rightCornerX < 0 || rightCornerY < 0 || rightCornerX >= 5 || rightCornerY >= 9) {
                return false;
            }
            for (int j = 0; j < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getX(); j++) {
                for (int k = 0; k < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getY(); k++) {
                    int x = piece.getLocation().getY() + j;
                    int y = piece.getLocation().getX() + k;
                    if (piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != null) {
                        if (boardstates[x][y] == NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != NONE) {
                            boardstates[x][y] = piece.getPieceType().colorFromOffSet(j, k, piece.getRotation());
                        } else if (boardstates[x][y] != NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != NONE) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * True if placement can be put on this board. The board already have some pieces on it which means the board is not
     * clean. I check whether placement can be put on this board.
     * @param boardstates A board states that have already put some placement on it.
     * @param placement A String that will be put on the board.
     * @return True if placement can be put on this board.
     * @author Yafei Liu(u6605935)
     */
    public static boolean isPlacementStringValid(Color[][] boardstates, String placement) {
        for (int i = 0; i < placement.length(); i += 4) {
            String pieceString = placement.substring(i, i + 4);
            Piece piece = new Piece(pieceString);
            int originalY = piece.getLocation().getX();
            int originalX = piece.getLocation().getY();
            if (originalX < 0 || originalY < 0 || originalX >= 5 || originalY >= 9) {
                return false;
            }
            int rightCornerX = piece.getPieceType().getRightCornerLocation(originalX, originalY, piece.getRotation()).getX();
            int rightCornerY = piece.getPieceType().getRightCornerLocation(originalX, originalY, piece.getRotation()).getY();
            if (rightCornerX < 0 || rightCornerY < 0 || rightCornerX >= 5 || rightCornerY >= 9) {
                return false;
            }
            for (int j = 0; j < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getX(); j++) {
                for (int k = 0; k < piece.getPieceType().getLengthAndHeight(piece.getRotation()).getY(); k++) {
                    int x = piece.getLocation().getY() + j;
                    int y = piece.getLocation().getX() + k;
                    if (piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != null) {
                        if (boardstates[x][y] == NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != NONE) {
                            boardstates[x][y] = piece.getPieceType().colorFromOffSet(j, k, piece.getRotation());
                        } else if (boardstates[x][y] != NONE && piece.getPieceType().colorFromOffSet(j, k, piece.getRotation()) != NONE) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Initialize a board.
     * @return Return a initialized board.
     * @author Yafei Liu(u6605935)
     */
    public static Color[][] initialize() {
        Color[][] board = {
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.B, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.B},
        };
        return board;
    }

    public static Color[][] newBoardWithChallenge(String placement, String challenge) {
        Color[][] boardstates = {
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.B, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.B},
        };
        //Update board states by challenge.
        int k = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                boardstates[i][j] = Color.toColor(challenge.charAt(k));
                k++;
            }
        }
        //Update board states with placement.
        if (placement.length() > 0) {
            if (FocusGame.isPlacementStringValid(boardstates, placement)) {
                FocusGame.updateBoardstates(boardstates, placement);
            }
        }
        return boardstates;
    }

    /**
     *
     * @param challenge A string represents challenge.
     * @return Return a new board which updates challenge.
     * @author Yafei Liu(u6605935)
     */
    public static Color[][] newBoardWithChallenge(String challenge) {
        Color[][] boardstates = {
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.B, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.B},
        };
        //Update board states by challenge.
        int k = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                boardstates[i][j] = Color.toColor(challenge.charAt(k));
                k++;
            }
        }
        return boardstates;
    }

    /**
     * Return a board states that updates placement on a blank board.
     * @param placement A string that represents placement.
     * @return Return a board states that updates placement.
     * @author Yafei Liu(u6605935)
     */
    public static Color[][] updateBoardstates(String placement) {
        Color[][] boardstates = {
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE},
                {Color.B, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.NONE, Color.B},
        };
        if (FocusGame.isPlacementStringValid(placement)) {
            for (int i = 0; i < placement.length(); i += 4) {
                String pieceString = placement.substring(i, i + 4);
                Piece piece = new Piece(pieceString);
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

    /**
     * Return a board states that updates placement.
     * @param boardstates A two dimension arrays represents color states.
     * @param placement A placement string.
     * @return Return a board states that updates placement.
     * @author Yafei Liu(u6605935)
     */
    public static Color[][] updateBoardstates(Color[][] boardstates, String placement) {
        for (int i = 0; i < placement.length(); i += 4) {
            String pieceString = placement.substring(i, i + 4);
            Piece piece = new Piece(pieceString);
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
        return boardstates;
    }

    public static Color[][] addPiece(Color[][] boardstates, String placement) {
            Piece piece = new Piece(placement);
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
        return boardstates;
    }

    /**
     * Return true if the first board is consistent with the second board only in the challenge place.
     * @param withChallenge A board states that only updated challenge on it.
     * @param noChallenge A board states that have some piece placements updated on it.
     * @return Return true if the first board is consistent with the second board only in the challenge place.
     * @author Yafei Liu(u6605935)
     */
    public static boolean placeConsistentWithChallenge(Color[][] withChallenge, Color[][] noChallenge) {
        for (int i = 1; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                if (noChallenge[i][j] != NONE) {
                    if (noChallenge[i][j] != withChallenge[i][j])
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Return true if challenge string is consistent with placement string.
     * @param challenge A challenge string.
     * @param placement A placement string.
     * @return Return true if challenge string is consistent with placement string.
     * @author Yafei Liu(u6605935)
     */
    public static boolean placeConsistentWithChallenge(String challenge, String placement) {
        Color[][] withChallenge = newBoardWithChallenge(challenge);
        Color[][] noChallenge = updateBoardstates(placement);
        for (int i = 1; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                if (noChallenge[i][j] != NONE) {
                    if (noChallenge[i][j] != withChallenge[i][j])
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
     * <p>
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
     * @param col       The cell's column.
     * @param row       The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     * @author Yafei Liu(u6605935)
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
                                        && placeConsistentWithChallenge(challenge, pieceString)
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
     * <p>
     * A given challenge can only solved with a single placement of pieces.
     * <p>
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     * orientation value (0 or 1)
     *
     *
     * @notice This is a overload method for Task6 method, the only difference is thay I delete placement -- fxy2, fxy3, gxy2, gxy3.
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     * @author Yafei Liu(u6605935)
     */

    static Set<String> getViablePiecePlacements1(String placement, String challenge, int col, int row) {
        Set<String> set = new HashSet<>();
        //Get pieces' types which are not in placement string.
        String allPieceType = "ABCDEFGHIJ";
        String notPlacedPieceType = allPieceType;
        for (int i = 0; i < placement.length(); i += 4) {
            String placedPieceType = Character.toString(placement.charAt(i)).toUpperCase();
            notPlacedPieceType = notPlacedPieceType.replace("" + placedPieceType + "", "");
        }
        Color[][] boardstates;
        Color[][] challengeBoardstates;
        Color[][] putPieceString;
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
                                boardstates = updateBoardstates(placement);
                                challengeBoardstates = newBoardWithChallenge(challenge);
                                if (placeConsistentWithChallenge(challengeBoardstates, boardstates)
                                        && isPlacementStringValid(boardstates, pieceString)
                                        && placeConsistentWithChallenge(challenge, pieceString)
                                ) {
                                    putPieceString = updateBoardstates(boardstates, pieceString);
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
        } else { // delete placement fxy2, fxy3, gxy2, gxy3
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    if (set.contains("f" + j + "" + i + "" + 2) && set.contains("f" + j + "" + i + "" + 0)) {
                        set.remove("f" + j + "" + i + "" + 2);
                    }
                    if (set.contains("f" + j + "" + i + "" + 3) && set.contains("f" + j + "" + i + "" + 1)) {
                        set.remove("f" + j + "" + i + "" + 3);
                    }
                    if (set.contains("g" + j + "" + i + "" + 2) && set.contains("g" + j + "" + i + "" + 0)) {
                        set.remove("g" + j + "" + i + "" + 2);
                    }
                    if (set.contains("g" + j + "" + i + "" + 3) && set.contains("g" + j + "" + i + "" + 1)) {
                        set.remove("g" + j + "" + i + "" + 3);
                    }
                }
            }
            return set;
        }
    }

    public static String placement = "";

    public static String getSolution(String challenge) { // FIXME Task 9: determine the solution to the game, given a particular challenge

        boardstates = initialize();
        placement = "";
        String answer = getSolution1(challenge);

        return answer;
    }

    public static String getSolution1(String challenge) {
        if (placement.length() == 40) {
            return placement;
        } else {
//            int a = 0;
//            outterLoop:
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 9; col++) {
                    if (boardstates[row][col] == NONE) {
                        Set<String> set = getViablePiecePlacements1(placement, challenge, col, row);
                        if (set == null) {
                            return placement;
                        }
//                        if (set != null) {
                            for (String newPlace : set) {
                                placement += newPlace;
                                boardstates = addPiece(boardstates,newPlace);
                                getSolution1(challenge);
                                if (placement.length() == 40) {
                                    return placement;
                                }
                                placement = placement.substring(0, placement.length() - 4);
                                boardstates = updateBoardstates(placement);
                            }
                            return placement;
//                        }
//                        a = 1;
                    }
//                    if (a == 1) break;
                }
//                if (a == 1) break;
            }
        }
        return placement;
    }
}
