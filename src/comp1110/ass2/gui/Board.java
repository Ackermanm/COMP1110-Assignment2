package comp1110.ass2.gui;

import comp1110.ass2.PieceType;
import comp1110.ass2.Rotation;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int SQUARE_SIZE = 71;


    private static final int ROTATION_THRESHOLD = 50;
    private static final String URI_BASE = "assets/";
    char[] pieceState = new char[6];   //  all off screen to begin with


    class ShowPiece extends ImageView {
        ShowPiece(char piece){

        }

        ShowPiece(char piece, char rotation){

        }
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

    public Rotation charToRotation(char rotation){
        switch (rotation){
            case '0':return Rotation.ZERO;
            case '1':return Rotation.ONE;
            case '2':return Rotation.TWO;
            case '3':return Rotation.THREE;
        }
        return Rotation.ZERO;
    }

        // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places
    /*class DraggablePiece extends ShowPiece{
        int originX,originY;

        double mouseX, mouseY;
        Image[] images = new Image[4];
        int rotation;
        long lastRotationTime = System.currentTimeMillis();
        DraggablePiece(char piece){
            super(piece);
            if (piece < 'a' || piece > 'j') {
                throw new IllegalArgumentException("Invalid piece: " + piece);
            }

            for (int rotation = 0; rotation < 4; rotation++){
                char rot = (char)(rotation+'0');
                int height = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rot)).getX();
                int width = pieceToPieceType(piece).getLengthAndHeight(charToRotation(rot)).getY();
                if (rotation == '1' || rotation == '3'){
                    setFitHeight(width * SQUARE_SIZE);
                    setFitWidth(height * SQUARE_SIZE);
                }
                else {
                    setFitHeight(height * SQUARE_SIZE);
                    setFitWidth(width * SQUARE_SIZE);
                }
                this.setRotate((rotation-'0')*90);
                images[rotation] = new Image(Viewer.class.getResource(URI_BASE + piece + ".png").toString());
            }
            setImage(images[0]);
            rotation = 0;
            pieceState[piece - 'a'] = 0; // start out off board
            originX = MARGIN_X + ;
            setLayoutX(originX);
            originY = MARGIN_Y + ;
            setLayoutY(originY);
            setOnScroll(event -> {            // scroll to change orientation
                if (System.currentTimeMillis() - lastRotationTime > ROTATION_THRESHOLD){
                    lastRotationTime = System.currentTimeMillis();
                    rotate();
                    event.consume();
                    checkCompletion();
                }
            });

            //
        }

        }*/

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // FIXME Task 10: Implement hints

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override
    public void start(Stage primaryStage) {

    }
}
