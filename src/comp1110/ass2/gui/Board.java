package comp1110.ass2.gui;
import static comp1110.ass2.gui.Level.*;
import comp1110.ass2.FocusGame;
import comp1110.ass2.PieceType;
import comp1110.ass2.Rotation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    private final Group board = new Group();
    private final Group pieces = new Group();
    private final Group challenge = new Group();


    private static final int ROTATION_THRESHOLD = 50;

    String[] placement= new String[10];
    int[] pieceState = new int[10];

    String solution = "a000b013c113d302e323f400g420h522i613j701";
    String challengeString = "RRRBWBBRB";
    Level lev = EXPERT;

    public Board() {
        for (int i = 0; i < placement.length; i++){
            placement[i] = "";
            pieceState[i] = 0;
        }
    }

    public Rotation charToRotation(char rotation){
        switch (rotation){
            case '0':return Rotation.ZERO;
            case '1':return Rotation.ONE;
            case '2':return Rotation.TWO;
            case '3':return Rotation.THREE;
        }
        return Rotation.ZERO;
    }

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

    class ShowPiece extends ImageView {
        ShowPiece(char piece){

        }
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
    class DraggablePiece extends ShowPiece{
        char piece;
        int originX,originY;
        double mouseX, mouseY;
        int rotation;
        long lastRotationTime = System.currentTimeMillis();


        DraggablePiece(char piece){
            super(piece);
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
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
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
            }
            else {
                snapToOrigin();
            }
            checkCompletion();
        }
        public void initialization(String placement){
            int[] layout = new int[2];
            layout[0] = placement.charAt(1) - '0';
            layout[1] = placement.charAt(2) - '0';
            int xLocation = layout[0];
            int yLocation = layout[1];
            rotation = placement.charAt(3) - '0';
                if (boardIsPlacementValid(layout)) {
                    if (rotation == 1 || rotation == 3) {
                        if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE - 0.5 * SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE + 0.5 * SQUARE_SIZE);
                        }
                        if (piece == 'b' || piece == 'c' || piece == 'j' || piece == 'f') {
                            setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE - SQUARE_SIZE);
                            setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE + SQUARE_SIZE);
                            double a = getLayoutX();
                            double b = getLayoutY();
                        }
                    } else {
                        setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE);
                        setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE);
                    }
                }
                else snapToOrigin();
            setRotate(rotation * 90);
        }
        public int[] transformLayout(){
            double[][] distance = new double[5][9];
            int[] layout = new int[2];
            double min = 100000;
            int rowLocation = -1;
            int columnLocation = -1;
            double x = 0;
            double y = 0;
            if (rotation == 1 || rotation == 3) {
                if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                    x = (getLayoutX() + 0.5 * SQUARE_SIZE - PLAY_AREA_X) / SQUARE_SIZE;
                    y = (getLayoutY() - 0.5 * SQUARE_SIZE - PLAY_AREA_Y) / SQUARE_SIZE;
                }

                if (piece == 'b' || piece == 'c' || piece == 'j' || piece == 'f') {
                    x = (getLayoutX() + SQUARE_SIZE - PLAY_AREA_X) / SQUARE_SIZE;
                    y = (getLayoutY() - SQUARE_SIZE - PLAY_AREA_Y) / SQUARE_SIZE;
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
        private void snapToOrigin(){
            rotation = -1;
            rotate();
            setLayoutX(originX);
            setLayoutY(originY);
            pieceState[piece-'a'] = 0;
            placement[piece-'a'] = "";
            }
        private void rotate() {
            rotation = (rotation + 1) % 4;
            setRotate(rotation * 90);
            if (rotation == 1 || rotation == 3) {
                if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                    setLayoutX(getLayoutX() - 0.5 * SQUARE_SIZE);
                    setLayoutY(getLayoutY() + 0.5 * SQUARE_SIZE);
                }

                if (piece == 'b' || piece == 'c' || piece == 'j' ||  piece == 'f') {
                    setLayoutX(getLayoutX() - SQUARE_SIZE);
                    setLayoutY(getLayoutY() + SQUARE_SIZE);
                }
            }
            else if (rotation == 0 || rotation == 2){
                if (piece == 'a' || piece == 'd' || piece == 'e' || piece == 'g') {
                    setLayoutX(getLayoutX() + 0.5 * SQUARE_SIZE);
                    setLayoutY(getLayoutY() - 0.5 * SQUARE_SIZE);
                }

                if (piece == 'b' || piece == 'c' || piece == 'j' ||  piece == 'f') {
                    setLayoutX(getLayoutX() + SQUARE_SIZE);
                    setLayoutY(getLayoutY() - SQUARE_SIZE);
                }
            }
            toFront();
//            int[] layout = transformLayout();  //  xuanzhuan bu tongguo
//            if (!boardIsPlacementValid(layout)) snapToOrigin();
        }

        public boolean onBoard(){
            if (getLayoutX() < PLAY_AREA_X - SQUARE_SIZE) return false;
            if (getLayoutY() < PLAY_AREA_Y - SQUARE_SIZE) return false;
            if (getLayoutX() > BOARD_X + BOARD_WIDTH + SQUARE_SIZE) return false;
            if (getLayoutY() > BOARD_Y + BOARD_HEIGHT + SQUARE_SIZE) return false;
            return true;
        }
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
        public boolean checkCompletion(){
            for (int i = 0; i < pieceState.length; i++){
                if (pieceState[i] != 1)return false;
            }
            return true;
        }
    }
//    private void makePieces() {
//        pieces.getChildren().clear();
//        for (char i = 'a'; i <= 'j'; i++) {
//            pieces.getChildren().add(new DraggablePiece(i));
//        }
//    }


    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    public void challenges(String solution, String challenge, Level level){
        this.challenge.getChildren().clear();
        this.pieces.getChildren().clear();
        this.challenge.getChildren().clear();
        ShowPiece[] show = new ShowPiece[challenge.length()];
        DraggablePiece[] draggablePieces = new DraggablePiece[10];
        for (int i = 0; i < challenge.length(); i++){
            show[i] = new ShowPiece(challenge.substring(i,i+1));
        }
        for (int i = 0; i < challenge.length(); i++){
            int x = i % 3;
            int y = i / 3;
            show[i].setLayoutX(PLAY_AREA_X + 3 * SQUARE_SIZE + x * SQUARE_SIZE);
            show[i].setLayoutY(BOARD_Y + BOARD_HEIGHT + SQUARE_SIZE + y * SQUARE_SIZE);
        }
        for (int i = 0; i < challenge.length(); i++) {
            this.challenge.getChildren().add(show[i]);
        }
        for (char i = 'a'; i <= 'j'; i++) {
            draggablePieces[i-'a'] = new DraggablePiece(i);
            this.pieces.getChildren().add(draggablePieces[i-'a']);
        }
        for (int i = 0; i < 3-level.levelToInt(level); i++){
            String placement = solution.substring(i*4,i*4+4);
            draggablePieces[i].initialization(placement);
        }

    }
    // FIXME Task 10: Implement hints

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(pieces);
        root.getChildren().add(board);
        root.getChildren().add(challenge);
        challenges(solution, challengeString, lev);
//        makePieces();
        makeBoard();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
