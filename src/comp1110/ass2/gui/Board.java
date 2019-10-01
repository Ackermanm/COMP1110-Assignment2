package comp1110.ass2.gui;

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



    private static final int ROTATION_THRESHOLD = 50;
    char[] pieceState = new char[6];   //  all off screen to begin with




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
        Image[] images = new Image[4];
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
//            if (getLayoutX() < PLAY_AREA_X || getLayoutY() < PLAY_AREA_Y ||
//                    getLayoutX() > PLAY_AREA_X + (9*SQUARE_SIZE)|| getLayoutY()>PLAY_AREA_Y + (5*SQUARE_SIZE)){
//                snapToOrigin();
//            }
//            else {
                double[] distance = new double[45];
                double min = 100;
                int k = 0;
                int xy = 0;
                int xLocation;
                int yLocation;

                double x = (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;
                double y = (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 9; j++) {
                        distance[k] = (((x - j) * (x - j)) + ((y - i) * (y - i)));
                        min = Math.min(distance[k], min);
                        k++;
                    }
                }
                for (int i = 0; i < 45; i++) {
                    if (min == distance[i]) {
                        xy = i;
                        break;
                    }
                }
                xLocation = xy % 9;
                yLocation = xy / 9;

                setLayoutX(PLAY_AREA_X + xLocation * SQUARE_SIZE);
                setLayoutY(PLAY_AREA_Y + yLocation * SQUARE_SIZE);
//            }
        }
        public void snapToOrigin(){
            setLayoutX(originX);
            setLayoutY(originY);
            }
        private void rotate() {
            rotation = (rotation + 1) % 4;
            setRotate(rotation * 90);
            char rot = (char)(rotation+'0');
            int height = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rot)).getX();
            int width = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rot)).getY();
            if (rotation == 0 || rotation == 2){
                setFitHeight(height * SQUARE_SIZE);
                setFitWidth(width * SQUARE_SIZE);
            }
            if (rotation == 1 || rotation == 3) {
                setFitHeight(width * SQUARE_SIZE);
                setFitWidth(height * SQUARE_SIZE);
            }
            setImage(new Image(Viewer.class.getResource(URI_BASE + piece + ".png").toString()));
            toFront();
//            setPosition();
        }
    }

    private void makePieces() {
        pieces.getChildren().clear();
        for (char i = 'a'; i <= 'j'; i++) {
            pieces.getChildren().add(new DraggablePiece(i));
        }
    }


    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // FIXME Task 10: Implement hints

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(pieces);
        root.getChildren().add(board);

        makePieces();
        makeBoard();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
