package comp1110.ass2.gui;
import comp1110.ass2.FocusGame;
import comp1110.ass2.PieceType;
import comp1110.ass2.Rotation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

public class Board extends Application {

    private static final int SQUARE_SIZE = 71;
    private static final int BOARD_WIDTH = 720;
    private static final int BOARD_HEIGHT = 467;

    private static final String URI_BASE = "assets/";
    private static final String BOARD_URI = Viewer.class.getResource(URI_BASE + "board.png").toString();

    private static final int MARGIN_X = 30;
    private static final int MARGIN_Y = 30;

    private static final int BOARD_SIDE_MARGIN = 40;
    private static final int BOARD_TOP_MARGIN = 88;
    private static final int BOARD_Y = MARGIN_Y;
    private static final int BOARD_X = MARGIN_X  + (4 * SQUARE_SIZE) + SQUARE_SIZE + (4 * SQUARE_SIZE) + SQUARE_SIZE + MARGIN_X;
    private static final int PLAY_AREA_X = BOARD_X + BOARD_SIDE_MARGIN;
    private static final int PLAY_AREA_Y = BOARD_Y + BOARD_TOP_MARGIN ;
    private static final int VIEWER_WIDTH = BOARD_X + BOARD_WIDTH + MARGIN_X;
    private static final int VIEWER_HEIGHT = MARGIN_Y + (2 * MARGIN_Y) + (11 * SQUARE_SIZE) + MARGIN_Y;

    private static DropShadow dropShadow;

    private final Group root = new Group();
    private final Group board = new Group(); // Group that set board.
    private final Group pieces = new Group(); // Group that put pieces.
    private final Group challenge = new Group(); // Group that create new challenges.
    private final Group hints = new Group(); //Group that provide next hints.
    private final Group controls = new Group();
    private final Slider level = new Slider(); // Bottom bar to control difficults.

    private static final int ROTATION_THRESHOLD = 50;

    String[] placement= new String[10]; // Record placements that have been put on the board.
    int[] pieceState = new int[10]; // Record placements that have been put on the board in number.

    public Board() {
        for (int i = 0; i < placement.length; i++){
            placement[i] = "";
            pieceState[i] = 0;
        }
    }
    public Board(int level){
        level = 1;
    }

    /**
     * Return rotation given a character.
     * @param rotation The character that represent rotation type.
     * @return Return rotation given a character.
     * @author Yafei Liu(u6605935)
     */
    public Rotation charToRotation(char rotation){
        switch (rotation){
            case '0':return Rotation.ZERO;
            case '1':return Rotation.ONE;
            case '2':return Rotation.TWO;
            case '3':return Rotation.THREE;
        }
        return Rotation.ZERO;
    }

    /**
     * Return piece type given a character.
     * @param piece The character that reprente piece type.
     * @return Return piece type given a character.
     * @author Yafei Liu(u6605935)
     */
    public PieceType pieceToPieceType(char piece) {
        switch (piece){
            case 'a':return PieceType.A;
            case 'b':return PieceType.B;
            case 'c':return PieceType.C;
            case 'd':return PieceType.D;
            case 'e':return PieceType.E;
            case 'f':return PieceType.F;
            case 'g':return PieceType.G;
            case 'h':return PieceType.H;
            case 'i':return PieceType.I;
            case 'j':return PieceType.J;
        }
        return PieceType.A;
    }

    /**
     * The class that make up different piece.
     * @piece Character that represents piece.
     * @rotation character that represents rotation.
     * @author Yafei Liu(u6605935)
     */
    class ShowPiece extends ImageView {
        ShowPiece(char piece, char rotation) {
            if (piece < 'a' || piece > 'j') {
                throw new IllegalArgumentException("Invalid piece: " + piece);
            }
            int height = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rotation)).getX();
            int width = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rotation)).getY();
            if (rotation == '0' || rotation == '2'){
                setFitHeight(height * SQUARE_SIZE);
                setFitWidth(width * SQUARE_SIZE);
            }
            if (rotation == '1' || rotation == '3') {
                setFitHeight(width * SQUARE_SIZE);
                setFitWidth(height * SQUARE_SIZE);
            }
            this.setRotate((rotation-'0')*90);

            this.setImage(new Image(Viewer.class.getResource(URI_BASE + piece + ".png").toString()));

            this.setEffect(dropShadow);
        }
        ShowPiece(String color) {
            setFitHeight(SQUARE_SIZE);
            setFitWidth(SQUARE_SIZE);
            this.setImage(new Image(Viewer.class.getResource(URI_BASE + "sq-" + color.toLowerCase() + ".png").toString()));
            setEffect(dropShadow);
        }
    }

    /**
     * Create base board.
     * @author Yafei Liu(u6605935)
     */
    void makeBoard() {
        board.getChildren().clear();
        ImageView baseBoard = new ImageView();
        baseBoard.setImage(new Image(BOARD_URI));
        baseBoard.setFitWidth(BOARD_WIDTH);
        baseBoard.setFitHeight(BOARD_HEIGHT);
        baseBoard.setLayoutX(BOARD_X);
        baseBoard.setLayoutY(BOARD_Y);
        board.getChildren().add(baseBoard);

        board.toBack();
    }

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    /**
     * DraggablePiece class is to create pieces that can be dragged.
     * @piece Character that represents piece.
     * @originX,originY The origin coordinates that each piece is put.
     * @mouseX,mouseY The coordinates of mouse.
     * @rotation A scalar that represents a rotation.
     * @author Yafei Liu(u6605935)
     */
    class DraggablePiece extends ImageView{
        char piece;
        int originX,originY;
        double mouseX, mouseY;
        int rotation;
        long lastRotationTime = System.currentTimeMillis();


        DraggablePiece(char piece){
            this.piece = piece;
            Image image = new Image(Viewer.class.getResource(URI_BASE + piece + ".png").toString());
            setImage(image);
            rotation = 0;
            char rot = (char)(rotation+'0');
            int height = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rot)).getX();
            int width = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rot)).getY();

            setFitHeight(height * SQUARE_SIZE);
            setFitWidth(width * SQUARE_SIZE);

            originX = MARGIN_X + 5 * SQUARE_SIZE * ((piece - 'a') % 2);
            setLayoutX(originX);
            if (piece == 'i' || piece == 'j') {
                originY = 15 + ((piece - 'a') / 2) * (2 * SQUARE_SIZE + 10) + SQUARE_SIZE; //15 is 1/2 MARGIN
            } else {
                originY = 15 + ((piece - 'a') / 2) * (2 * SQUARE_SIZE + 10);//15 is 1/2 MARGIN
            }
            setLayoutY(originY);
            setOnScroll(event -> {            // scroll to change rotation
                if (System.currentTimeMillis() - lastRotationTime > ROTATION_THRESHOLD){
                    lastRotationTime = System.currentTimeMillis();
                    rotate();
                    event.consume();
                }
            });
            setOnMousePressed(event -> {      // mouse is pressing
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged
                toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            setOnMouseReleased(event -> {     // drag is complete
                snapToGrid();
            });
        }

        /**
         * This method is to snap a piece to grid. When a piece is dragged on the board and the user release mouse,
         * the piece will be put on one grid which is the closest grid it is.
         *
         * This method is realized by computing distances of piece itself and every grid the board have, and snap it
         * on the grid which the distance is the smallest.
         * @author Yafei Liu(u6605935)
         */
        public void snapToGrid() {
            if (onBoard()) {
                int[] layout = transformLayout();
                int xLocation = layout[0];
                int yLocation = layout[1];
                if (boardIsPlacementValid(layout)) {
                    if (rotation == 1 || rotation == 3) {
                        if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE - 0.5 * SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE + 0.5 * SQUARE_SIZE);
                        }
                        else if (piece == 'b' || piece == 'c' || piece == 'j' || piece == 'f') {
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE - SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE + SQUARE_SIZE);
                        }
                        else if (piece == 'h' || piece == 'i'){
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE);
                        }
                    } else {
                        setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE);
                        setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE);
                    }
                }
                else {
                    snapToOrigin();
                }
            }
            else {
                snapToOrigin();
            }
            toFront();
            hints();
            checkCompletion();
        }

        /**
         * This method is to initialize some pieces give a placement string. This can be used to create different
         * difficulties games by initialize different numbers pieces on it.
         * @param placementString A string that represents placement which need to be initialized.
         * @author Yafei Liu(u6605935)
         */
        public void initialization(String placementString){
            int[] layout = new int[2];
            layout[0] = placementString.charAt(1) - '0';
            layout[1] = placementString.charAt(2) - '0';
            int xLocation = layout[0];
            int yLocation = layout[1];
            rotation = placementString.charAt(3) - '0';
                if (boardIsPlacementValid(layout)) {
                    if (rotation == 1 || rotation == 3) {
                        if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE - 0.5 * SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE + 0.5 * SQUARE_SIZE);
                        }
                        if (piece == 'b' || piece == 'c' || piece == 'j' || piece == 'f') {
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE - SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE + SQUARE_SIZE);
                        }
                    } else {
                        setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE);
                        setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE);
                    }
                }
                else snapToOrigin();
            setRotate(rotation * 90);
            toFront();
        }

        /**
         * Return the coordinates where a piece is by calculating the layout of a piece on board.
         * @return Return the coordinates where a piece is. The first integer of the array is X coordinate, and
         * the second integer in the array is Y coordinate.
         * @author Yafei Liu(u6605935)
         */
        public int[] transformLayout(){
            double[][] distance = new double[5][9];
            int[] layout = new int[2];
            double min = 100000;
            int rowLocation = -1;
            int columnLocation = -1;
            double x = 0;
            double y = 0;
            if (rotation == 1 || rotation == 3) { /* When rotation is 1 and 3, the piece itself is not on the coordinates
             it should be due to rotate deviation. So we move back the coordinate it should be.*/
                if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                    x = (getLayoutX() + 0.5 * SQUARE_SIZE - PLAY_AREA_X) / SQUARE_SIZE;
                    y = (getLayoutY() - 0.5 * SQUARE_SIZE - PLAY_AREA_Y) / SQUARE_SIZE;
                }

                else if (piece == 'b' || piece == 'c' || piece == 'j' || piece == 'f') {
                    x = (getLayoutX() + SQUARE_SIZE - PLAY_AREA_X) / SQUARE_SIZE;
                    y = (getLayoutY() - SQUARE_SIZE - PLAY_AREA_Y) / SQUARE_SIZE;
                }
                else {
                    x = (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;
                    y = (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;
                }
            } else {
                x = (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;
                y = (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    distance[i][j] = (((x - j) * (x - j)) + ((y - i) * (y - i)));
                    min = Math.min(distance[i][j], min);
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    if (min == distance[i][j]) {
                        columnLocation = j;
                        rowLocation = i;
                        break;
                    }
                }
            }
            layout[0] = columnLocation;
            layout[1] = rowLocation;
            return layout;
        }

        /**
         * The method is used to snap a piece to its origin place if it is not well put, Such as overlap other piece of
         * put out of board.
         * @author Yafei Liu(u6605935)
         */
        private void snapToOrigin(){
            rotation = -1;
            rotate();
            setLayoutX(originX);
            setLayoutY(originY);
            pieceState[piece-'a'] = 0;
            placement[piece-'a'] = "";
            }

        /**
         * Rotate a piece. when rotation a not symmetric graph, the graph is rotate by its central, which cause the top left
         * of the piece is not at its origin top left coordinate. So we need to give it some offset when rotate.
         *
         * Rotate 180 and 360 will come back to its origin top left.
         * @author Yafei Liu(u6605935)
         */
        private void rotate() {
            rotation = (rotation + 1) % 4;
            int[] layout = transformLayout();
            boardIsPlacementValid(layout);
            setRotate(rotation * 90);
            if (rotation == 1 || rotation == 3) { /*Piece a d e g have 0.5 size deviation when rotate*/
                if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                    setLayoutX(getLayoutX() - 0.5 * SQUARE_SIZE);
                    setLayoutY(getLayoutY() + 0.5 * SQUARE_SIZE);
                }

                else if (piece == 'b' || piece == 'c' || piece == 'j' ||  piece == 'f') {  /*Piece b c j f have 0.5 size deviation when rotate*/
                    setLayoutX(getLayoutX() - SQUARE_SIZE);
                    setLayoutY(getLayoutY() + SQUARE_SIZE);
                }
                else {
                    setLayoutX(getLayoutX());
                    setLayoutY(getLayoutY());
                }
            }
            else if (rotation == 0 || rotation == 2){
                if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                    setLayoutX(getLayoutX() + 0.5 * SQUARE_SIZE);
                    setLayoutY(getLayoutY() - 0.5 * SQUARE_SIZE);
                }

                else if (piece == 'b' || piece == 'c' || piece == 'j' ||  piece == 'f') {
                    setLayoutX(getLayoutX() + SQUARE_SIZE);
                    setLayoutY(getLayoutY() - SQUARE_SIZE);
                }
                else {
                    setLayoutX(getLayoutX());
                    setLayoutY(getLayoutY());
                }
            }
            toFront();
        }

        /**
         * Return true if the piece is put on board, false if it is not.
         * @return Return true if the piece is put on board, false if it is not.
         * @author Yafei Liu(u6605935)
         */
        public boolean onBoard(){
            if (getLayoutX() < PLAY_AREA_X - SQUARE_SIZE) return false;
            if (getLayoutY() < PLAY_AREA_Y - SQUARE_SIZE) return false;
            if (getLayoutX() > BOARD_X + BOARD_WIDTH + SQUARE_SIZE) return false;
            if (getLayoutY() > BOARD_Y + BOARD_HEIGHT + SQUARE_SIZE) return false;
            return true;
        }

        /**
         *
         * @param layout The coordinates of a piece on board, layout[0] is X coordinate, layout[1] is Y coordinate.
         * @return True if a piece if valid, which contains not overlap other piece already on board and not out of board.
         * @author Yafei Liu(u6605935)
         */
        public boolean boardIsPlacementValid(int[] layout){
            if (pieceState[piece-'a'] == 1){
                pieceState[piece-'a'] = 0;
                placement[piece-'a'] = "";
            }
            String allPlacement = "";
            String pieceString = piece + "" + layout[0] + "" + layout[1] + "" + rotation;

            for (int i = 0; i < placement.length; i++){
                allPlacement += placement[i];
            }
            allPlacement += pieceString;
            if (FocusGame.isPlacementStringValid(allPlacement)){
                pieceState[piece-'a'] = 1;
                placement[piece-'a'] = pieceString;
                return true;
            }
            else return false;
        }

        /**
         * True if all the pieces are put on the board and is consistent with challenge.
         * @return True if all the pieces are put on the board and is consistent with challenge.
         * @author Yafei Liu(u6605935)
         */
        public boolean checkCompletion(){
            for (int i = 0; i < pieceState.length; i++){
                if (pieceState[i] != 1) {
                    return false;
                }
            }
            String allPlacement = "";
            for (int i = 0; i < placement.length; i++){
                allPlacement += placement[i];
            }
            if (allPlacement.equals(SOLUTIONS[a].placement)){
                Stage stage = new Stage();
                stage.setTitle("Square");
                Group root = new Group();
                Scene scene = new Scene(root, 300, 300);
                stage.setScene(scene);
                Text hi = new Text("Good job!");
                hi.setLayoutX(60);
                hi.setLayoutY(150);
                hi.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
                hi.setFill(Color.RED);
                root.getChildren().add(hi);
                stage.show();

                return true;
            }
            return false;
        }
    }

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    /**
     * Create a specific challenge given solution string and challenge string, different level prove some initialized pieces.
     * @param solution A string that represents challenge solution.
     * @param challenge A string that represents challenge.
     * @param level A integer that represents difficulty. Level 0 will initialize three pieces on board, level 1 initialize 2 pieces
     *              and level 2 initialize 1 piece while level 3 do not initialize any pieces.
     * @author Yafei Liu(u6605935)
     */
    public void challenges(String solution, String challenge, int level){
        this.challenge.getChildren().clear();
        this.pieces.getChildren().clear();
        ShowPiece[] show = new ShowPiece[challenge.length()];
        DraggablePiece[] draggablePieces = new DraggablePiece[10];
        for (int i = 0; i < challenge.length(); i++){
            show[i] = new ShowPiece(challenge.substring(i,i+1));
        }
        for (int i = 0; i < challenge.length(); i++){
            int x = i % 3;
            int y = i / 3;
            show[i].setLayoutX(PLAY_AREA_X + 3 * SQUARE_SIZE + x * SQUARE_SIZE);
            show[i].setLayoutY(BOARD_Y + BOARD_HEIGHT  + y * SQUARE_SIZE + 0.5 * SQUARE_SIZE);
        }
        for (int i = 0; i < challenge.length(); i++) {
            this.challenge.getChildren().add(show[i]);
        }
        for (char i = 'a'; i <= 'j'; i++) {
            draggablePieces[i-'a'] = new DraggablePiece(i);
            this.pieces.getChildren().add(draggablePieces[i-'a']);
            this.pieces.toFront();
        }
        for (int i = 0; i < 3-level; i++){
            String placement = solution.substring(i*4,i*4+4);
            draggablePieces[i].initialization(placement);
        }
        hints();
    }

    /**
     * Make a difficulty control bar at the bottom of the game. Minimum difficulty is 1 and maximum difficulty is 4.
     * @author Yafei Liu(u6605935)
     */
    public void makeControls(){
        Button button = new Button("Restart"); // Press restart button can restart a game by different difficulties.
        button.setLayoutX(PLAY_AREA_X + 6*SQUARE_SIZE);
        button.setLayoutY(VIEWER_HEIGHT - 100);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newGame();
            }
        });
        controls.getChildren().add(button);

        level.setMin(1);
        level.setMax(4);
        level.setValue(0);
        level.setShowTickLabels(true);
        level.setShowTickMarks(true);
        level.setMajorTickUnit(1);
        level.setMinorTickCount(0);
        level.setSnapToTicks(true);
        level.setLayoutX(PLAY_AREA_X + 3.5 * SQUARE_SIZE); // Set slider's layout.
        level.setLayoutY(VIEWER_HEIGHT - 1.5 * SQUARE_SIZE);
        controls.getChildren().add(level);

        final Label levelCaption = new Label("Level:");
        levelCaption.setTextFill(Color.GREY);
        levelCaption.setLayoutX(PLAY_AREA_X + 2.5 * SQUARE_SIZE);
        levelCaption.setLayoutY(VIEWER_HEIGHT - 100);
        controls.getChildren().add(levelCaption);
    }

    /**
     * Start a new game with a difficulty.
     * @author Yafei Liu(u6605935)
     */
    public static int a = 20;
    public void newGame(){
        hideHint();
        this.challenge.getChildren().clear();
        this.pieces.getChildren().clear();
        int lev = (int)level.getValue() - 1;
        for (int i = 0; i < placement.length; i++){
            pieceState[i] = 0;
            placement[i] = "";
        }
        Random random = new Random();
        a = random.nextInt(119);
        challenges(SOLUTIONS[a].placement,SOLUTIONS[a].objective,lev);
    }

    // FIXME Task 10: Implement hints

    /**
     * Provide hints for a challenge, give the next piece in solution that you have not placed. If you already put a piece
     * on board, I will not correct your put, I only give you the next piece in solution.
     * @author Yafei Liu(u6605935)
     */
    private void hints() {
        this.hints.getChildren().clear();
        int hintPiece = -1;
        String hintplacement = "";
        for (int i = 0; i < placement.length; i++){
            if (placement[i].equals("")){
                hintPiece = i;
                break;
            }
        }
        if (checkFinish()){ /* If you finish all the pieces, then I do not provide you any hints cause there is no hint any more.
         I show a good job text at draggable class if you complete the game.*/
        }
        else {
            hintplacement = SOLUTIONS[a].placement.substring(hintPiece * 4, hintPiece * 4 + 4);
            ShowPiece showPiece = new ShowPiece(hintplacement.charAt(0), hintplacement.charAt(3));
            int x = hintplacement.charAt(1) - '0';
            int y = hintplacement.charAt(2) - '0';
            showPiece.setLayoutX(PLAY_AREA_X + (x * SQUARE_SIZE));
            showPiece.setLayoutY(PLAY_AREA_Y + (y * SQUARE_SIZE));
            showPiece.setRotate((hintplacement.charAt(3)) * 90);
            if (hintplacement.charAt(3) == '1' || hintplacement.charAt(3) == '3') {
                if (hintplacement.charAt(0) == 'a' || hintplacement.charAt(0) == 'd' || hintplacement.charAt(0) == 'e' || hintplacement.charAt(0) == 'g') {
                    showPiece.setLayoutX(showPiece.getLayoutX() - 0.5 * SQUARE_SIZE);
                    showPiece.setLayoutY(showPiece.getLayoutY() + 0.5 * SQUARE_SIZE);
                }
                if (hintplacement.charAt(0) == 'b' || hintplacement.charAt(0) == 'c' || hintplacement.charAt(0) == 'j' || hintplacement.charAt(0) == 'f') {
                    showPiece.setLayoutX(showPiece.getLayoutX() - SQUARE_SIZE);
                    showPiece.setLayoutY(showPiece.getLayoutY() + SQUARE_SIZE);
                }
            }
            this.hints.getChildren().add(showPiece);
            hideHint();
        }
    }

    /**
     *
     * @return True if the game is completed.
     * @author Yafei Liu(u6605935)
     */
    public boolean checkFinish(){
        for (int i = 0; i < placement.length; i++){
            if (placement[i] == ""){
                return false;
            }
        }
        return true;
    }

    /**
     * Hide hint by set its opacity in 0 and let it to back.
     * @author Yafei Liu(u6605935)
     */
    public void hideHint(){
        this.hints.setOpacity(0);
        this.hints.toBack();
    }
    /**
     * Show hint by set its opacity in 1 and let it to front.
     * @author Yafei Liu(u6605935)
     */

    public void showHint(){
        this.hints.setOpacity(1);
        this.hints.toFront();
    }

    /**
     * Show hints by press key SLASH, hide hints by release SLASH.
     * @param scene The scene that show hints.
     * @author Yafei Liu(u6605935)
     */
    private void showHints(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                showHint();
                pieces.setOpacity(0);
                event.consume();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                hideHint();
                pieces.setOpacity(1);
                pieces.toFront();
                event.consume();
            }
        });
    }
    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    public void interestingChallenges(){
        Random random = new Random();
        String challenge = "";
        int ran = 0;
        String color = "RWBG";
        for (int i = 0; i < 9; i++){
            ran = random.nextInt(4);
            challenge += color.substring(ran,ran+1);
        }
        String solution = "";
        solution = FocusGame.getSolution(challenge);
        if (solution.length() == 40){
            challenges(solution, challenge, 3);
        }
    }

    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(pieces);
        root.getChildren().add(board);
        root.getChildren().add(challenge);
        root.getChildren().add(hints);
        root.getChildren().add(controls);
        challenges(SOLUTIONS[a].placement, SOLUTIONS[a].objective, 3);
        showHints(scene);;
        makeBoard();
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * These are solutions and corresponding challenges that I copy from TestUtility to complete task 8(give a new challenge).
     * @author Yafei Liu(u6605935)
     */
     static final Solution[] SOLUTIONS = {
            new Solution("RRRBWBBRB",
                    "a000b013c113d302e323f400g420h522i613j701"),
            new Solution("RWWRRRWWW",
                    "a701b400c410d303e111f330g030h000i733j332"),
            new Solution("BGGWGGRWB",
                    "a100b210c220d400e003f801g030h502i733j332"),
            new Solution("WRRWRRGWW",
                    "a123b232c222d613e400f011g000h522i200j701"),
            new Solution("GWRGWWGGG",
                    "a513b130c502d002e020f401g721h101i713j332"),
            new Solution("GRWGRWWWW",
                    "a701b132c430d403e111f511g620h101i603j003"),
            new Solution("RGGRGGRRB",
                    "a332b513c613d211e103f001g030h201i500j701"),
            new Solution("GGGRGRBBB",
                    "a723b013c202d120e422f440g000h601i232j410"),
            new Solution("RGGGGRBGG",
                    "a200b230c430d711e600f011g000h503i133j112"),
            new Solution("BBBWRWGGG",
                    "a630b130c330d002e020f100g301h601i502j412"),
            new Solution("WBWWWWRWG",
                    "a500b012c703d232e000f320g300h522i030j322"),
            new Solution("BBGRWBRRB",
                    "a701b532c402d230e210f140g420h100i612j003"),
            new Solution("WWRGWWGGW",
                    "a000b013c113d623e500f401g721h322i701j303"),
            new Solution("WRRRRRWWW",
                    "a210b232c703d420e500f001g120h522i030j100"),
            new Solution("WWWWRWBBB",
                    "a000b013c113d600e330f410g721h300i531j412"),
            new Solution("GBBRRRBBG",
                    "a012b202c330d512e222f600g030h611i630j000"),
            new Solution("BRRBWRBBB",
                    "a532b111c400d520e330f300g210h000i030j701"),
            new Solution("GGGWGWGGB",
                    "a622b322c202d232e021f540g110h601i003j410"),
            new Solution("GWRGGWGGG",
                    "a403b130c330d002e020f520g500h101i630j701"),
            new Solution("WWRGWRWWR",
                    "a221b300c613d513e100f421g030h003i531j701"),
            new Solution("WWRGGWGGW",
                    "a403b332c703d621e111f500g030h000i511j303"),
            new Solution("RBGWBBGWR",
                    "a021b503c703d621e301f340g110h000i603j230"),
            new Solution("BGWBWRBBB",
                    "a532b111c302d520e330f510g210h000i030j701"),
            new Solution("BGRWWWWWW",
                    "a000b013c302d510e623f521g120h222i210j701"),
            new Solution("WBWWBWGGG",
                    "a723b130c330d613e100f120g310h601i400j003"),
            new Solution("WGBWGBWGB",
                    "a111b013c230d621e513f311g500h201i000j701"),
            new Solution("BBBGGGWWW",
                    "a021b512c502d003e102f330g721h220i402j332"),
            new Solution("GBBGWWGBB",
                    "a723b202c002d012e701f140g501h422i422j022"),
            new Solution("BWGGWGGWB",
                    "a000b513c613d400e013f411g201h323i131j701"),
            new Solution("WBWGGBWGW",
                    "a221b301c430d600e713f011g511h001i400j113"),
            new Solution("GWGGWBGGG",
                    "a610b130c330d002e020f401g721h101i520j500"),
            new Solution("BGGWGGGWB",
                    "a621b210c130d022e001f811g430h402i703j200"),
            new Solution("GWWGWBGGG",
                    "a621b130c330d002e020f401g500h101i512j701"),
            new Solution("GBGWWBWWG",
                    "a723b310c330d103e701f001g030h213i520j300"),
            new Solution("GWWBWWGBG",
                    "a723b222c330d103e701f001g030h411i212j300"),
            new Solution("BGBWWWWBW",
                    "a532b410c002d213e013f801g620h122i433j400"),
            new Solution("GGWWGGBWG",
                    "a003b501c102d600e713f120g030h523i230j311"),
            new Solution("WBWWWWGGG",
                    "a723b130c330d011e701f320g300h000i213j501"),
            new Solution("BBBBGGBGB",
                    "a622b412c002d532e302f011g111h601i221j132"),
            new Solution("BBBWWWBGB",
                    "a600b122c001d532e101f520g400h612i302j132"),
            new Solution("WGGBGGGBW",
                    "a010b413c130d600e713f000g211h301i022j613"),
            new Solution("GBGWWWBGB",
                    "a600b310c202d532e013f520g000h612i222j132"),
            new Solution("BWGWWWGWB",
                    "a022b510c130d532e622f411g110h000i303j500"),
            new Solution("BGBBGGWBB",
                    "a000b220c703d621e430f011g201h123i510j400"),
            new Solution("BGBBWBBGB",
                    "a601b532c302d011e513f801g210h000i222j132"),
            new Solution("WWGBBWWBW",
                    "a000b200c711d121e013f521g311h601i331j511"),
            new Solution("WBWGGBBGW",
                    "a112b301c711d013e130f000g511h601i400j332"),
            new Solution("BWWBWGBWW",
                    "a503b232c711d521e211f401g011h601i133j000"),
            new Solution("RRRRRRRRR",
                    "a300b532c122d513e232f000g611h601i030j010"),
            new Solution("RRRRRWRWW",
                    "a412b601c111d221e300f011g530h001i432j701"),
            new Solution("RWWWRWWWR",
                    "a301b611c703d132e432f230g110h500i023j000"),
            new Solution("BWBBWBBBB",
                    "a132b601c430d011e211f300g410h000i422j701"),
            new Solution("BWWBBBBWB",
                    "a021b501c711d232e211f001g430h601i412j100"),
            new Solution("WWBWWBWWB",
                    "a003b601c430d300e022f411g100h122i523j701"),
            new Solution("BWBBBBWWW",
                    "a021b232c711d213e503f001g311h601i531j100"),
            new Solution("BBBBWBBWB",
                    "a021b102c613d223e402f440g420h000i333j701"),
            new Solution("BBBBWBBBB",
                    "a021b102c430d223e402f711g321h000i523j701"),
            new Solution("BBBWWBWBB",
                    "a013b102c613d432e402f000g420h122i121j701"),
            new Solution("WBBWBWWBW",
                    "a021b532c601d300e701f321g310h000i433j111"),
            new Solution("BBBWWBBBB",
                    "a030b102c613d432e402f120g420h000i231j701"),
            new Solution("WGGWWGWWW",
                    "a111b013c002d400e701f311g620h222i632j411"),
            new Solution("GGGGWGWWG",
                    "a711b502c302d020e000f421g030h523i622j211"),
            new Solution("GGWWGGWWG",
                    "a003b501c102d600e713f321g030h523i120j311"),
            new Solution("WBBWWBWWW",
                    "a022b420c430d711e600f001g401h122i213j100"),
            new Solution("BBBBWWBBB",
                    "a021b102c613d223e402f440g321h000i522j701"),
            new Solution("WBBBWWBWW",
                    "a021b410c100d232e621f400g220h000i532j701"),
            new Solution("WWRWRWWWR",
                    "a423b300c001d510e100f540g520h122i121j701"),
            new Solution("RRWWRWWRR",
                    "a230b100c300d003e432f120g030h601i733j412"),
            new Solution("WWWRRWWRR",
                    "a230b300c002d212e432f510g030h521i012j701"),
            new Solution("WGGGGGGGW",
                    "a000b502c302d711e123f011g530h323i213j420"),
            new Solution("WGGGGGWWW",
                    "a003b532c601d232e701f330g100h301i030j112"),
            new Solution("GGGGGGGGG",
                    "a520b130c330d002e020f400g721h101i700j410"),
            new Solution("GGRBGRBBR",
                    "a613b010c703d411e221f500g030h201i003j432"),
            new Solution("BGBGBRGRR",
                    "a013b201c302d422e510f240g121h522i000j701"),
            new Solution("WBWWRWWGW",
                    "a030b500c230d711e100f311g530h003i403j412"),
            new Solution("GGBRRGBRG",
                    "a622b302c102d321e130f801g601h523i002j020"),
            new Solution("GWWWRWWWB",
                    "a022b132c202d003e630f410g110h601i431j412"),
            new Solution("BBRWBBGWB",
                    "a630b103c130d200e412f801g430h501i210j003"),
            new Solution("BGWWBGWWW",
                    "a000b320c302d121e013f340g530h511i210j701"),
            new Solution("GWBGGWWGG",
                    "a600b213c430d711e003f520g030h101i502j403"),
            new Solution("BGGBGBBGG",
                    "a123b230c430d711e512f011g000h201i211j500"),
            new Solution("GGRGGRRRR",
                    "a030b532c202d513e232f001g611h601i102j112"),
            new Solution("GWGGWRGGG",
                    "a511b130c330d002e020f401g721h101i713j500"),
            new Solution("RRWGRRGGR",
                    "a030b500c230d403e521f020g721h111i713j000"),
            new Solution("GRRGGGRRG",
                    "a621b412c330d222e400f001g030h101i113j701"),
            new Solution("WRWWRWGWG",
                    "a013b500c411d132e711f540g520h300i000j203"),
            new Solution("RGGRWGGRR",
                    "a311b103c130d620e200f540g500h513i700j003"),
            new Solution("GGGGGGRWR",
                    "a003b020c102d620e132f540g500h513i700j311"),
            new Solution("GGGRRRWRW",
                    "a701b402c202d212e630f240g011h520i003j130"),
            new Solution("GRGWRRRWG",
                    "a532b502c411d520e132f811g110h000i021j300"),
            new Solution("RBRRBBRRB",
                    "a332b103c711d313e412f001g030h601i530j200"),
            new Solution("RRBWBRWWR",
                    "a600b132c002d210e432f120g400h013i733j512"),
            new Solution("WBWWRWWBW",
                    "a532b012c502d711e000f030g300h122i433j412"),
            new Solution("WWWRBRRBR",
                    "a132b300c002d523e321f011g111h511i632j701"),
            new Solution("RWWRWBWWB",
                    "a000b400c703d303e013f140g420h213i612j432"),
            new Solution("RBRRBRRBR",
                    "a012b532c032d513e311f240g611h601i003j200"),
            new Solution("WWWWWWWWW",
                    "a022b132c430d003e611f511g110h400i200j701"),
            new Solution("RRBBBBGGB",
                    "a100b130c711d011e212f001g400h601i523j332"),
            new Solution("GGGBBGRBG",
                    "a630b402c202d432e220f011g000h123i701j520"),
            new Solution("GBGRRRBRB",
                    "a332b202c711d320e003f100g030h601i222j511"),
            new Solution("GBRGBRGGG",
                    "a500b422c703d020e403f000g030h323i631j002"),
            new Solution("GBWRBBRRG",
                    "a120b013c202d501e232f540g000h512i413j701"),
            new Solution("WWBWGWWWR",
                    "a420b132c430d600e713f210g400h100i223j003"),
            new Solution("BGGRWBBGG",
                    "a030b230c210d003e621f440g101h611i303j500"),
            new Solution("BGRBGRGGG",
                    "a630b130c330d502e013f600g000h201i211j512"),
            new Solution("BRWRRRWRB",
                    "a402b532c001d320e101f240g611h601i303j130"),
            new Solution("BBBRRBWRB",
                    "a230b601c430d212e302f001g030h100i523j701"),
            new Solution("BWRBBWWBB",
                    "a621b102c501d432e022f140g311h000i602j701"),
            new Solution("RBBRBBRRR",
                    "a100b111c711d313e420f001g400h601i030j332"),
            new Solution("RWWBWWBBR",
                    "a021b611c703d223e432f000g321h500i300j010"),
            new Solution("RBBRWBRRB",
                    "a532b601c111d303e232f011g401h001i523j701"),
            new Solution("BBRRRRBBR",
                    "a000b013c122d411e202f721g500h422i231j701"),
            new Solution("RBBBWWBWR",
                    "a132b410c332d110e020f540g620h601i403j000"),
            new Solution("BWRBWRBWR",
                    "a611b103c430d513e201f001g030h400i333j701"),
            new Solution("BBBWRWRRR",
                    "a111b013c002d330e630f240g301h601i502j412"),
            new Solution("BBBRWBWRB",
                    "a323b102c613d121e402f440g420h000i021j701"),
            new Solution("RWBWWWBWR",
                    "a132b532c332d600e200f420g610h103i401j003"),
            new Solution("WRBWRBWRB",
                    "a003b601c430d413e022f300g100h122i523j701"),
            new Solution("BWRBRWBWR",
                    "a412b100c332d003e022f140g500h522i313j701"),
            new Solution("BRBBRBBWB",
                    "a021b102c502d223e411f811g611h000i333j432")
    };
}
