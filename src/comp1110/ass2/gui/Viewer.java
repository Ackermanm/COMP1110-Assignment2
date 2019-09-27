package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import comp1110.ass2.Piece;
import comp1110.ass2.PieceType;
import comp1110.ass2.Rotation;
import gittest.C;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 71;

    private static final int BOARD_WIDTH = 720;
    private static final int BOARD_HEIGHT = 467;
    private static final int BOARD_SIDE_MARGIN = 40;
    private static final int BOARD_TOP_MARGIN = 88;
    private static final int BOARD_BOTTOM_MARGIN = 24;
    private static final int OBJECTIVE_SIZE = SQUARE_SIZE * 3;

    private static final int MARGIN_X = 30;
    private static final int MARGIN_Y = 30;
    private static final int BOARD_Y = MARGIN_Y;
    private static final int BOARD_X = MARGIN_X  + (4 * SQUARE_SIZE) + SQUARE_SIZE + (4 * SQUARE_SIZE) + SQUARE_SIZE + MARGIN_X;

    private static final int PLAY_AREA_X = BOARD_X + BOARD_SIDE_MARGIN;
    private static final int PLAY_AREA_Y = BOARD_Y + BOARD_TOP_MARGIN ;

    private static final int VIEWER_WIDTH = BOARD_X + BOARD_WIDTH + MARGIN_X;
    private static final int VIEWER_HEIGHT = MARGIN_Y + (2 * MARGIN_Y) + (11 * SQUARE_SIZE) + MARGIN_Y;

    private static final String URI_BASE = "assets/";
    private static final String BOARD_URI = Viewer.class.getResource(URI_BASE + "board.png").toString();

    private final Group root = new Group();
    private final Group board = new Group();
    private final Group placement = new Group();


    private final Group controls = new Group();
    private TextField textField;

    private static DropShadow dropShadow;

    /* Graphical representations of pieces */
    class ShowPiece extends ImageView {
        char pieceID;
        ShowPiece(char piece, char rotation) {
            if (piece < 'a' || piece > 'j') {
                throw new IllegalArgumentException("Invalid piece: " + piece);
            }
            this.pieceID = piece;
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

            this.setImage(new Image(Viewer.class.getResource(URI_BASE + pieceID + ".png").toString()));

            this.setEffect(dropShadow);
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

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {//Check valid placement, set rotation function.
        // FIXME Task 4: implement the simple placement viewer
        this.placement.getChildren().clear();
        if (!FocusGame.isPlacementStringWellFormed(placement) || !FocusGame.isPlacementStringValid(placement)){
            System.out.println("Bad placement!");
            return;
        }
        if (placement.length() == 0) {
            return;
        }
        for (int i = 0; i < placement.length(); i+=4) {
            ShowPiece showPiece = new ShowPiece(placement.charAt(i),placement.charAt(i+3));
            int x = placement.charAt(i+1) - '0';
            int y = placement.charAt(i+2) - '0';

            showPiece.setLayoutX(PLAY_AREA_X + (x * SQUARE_SIZE));
            showPiece.setLayoutY(PLAY_AREA_Y + (y * SQUARE_SIZE));
            if (placement.charAt(i+3) == '1' || placement.charAt(i+3) == '3') {
                if (placement.charAt(i) == 'a' || placement.charAt(i) == 'd' || placement.charAt(i) == 'e' || placement.charAt(i) == 'g') {
                    showPiece.setLayoutX(PLAY_AREA_X + x * SQUARE_SIZE - 0.5 * SQUARE_SIZE);
                    showPiece.setLayoutY(PLAY_AREA_Y + y * SQUARE_SIZE + 0.5 * SQUARE_SIZE);
                }
                if (placement.charAt(i) == 'b' || placement.charAt(i) == 'c' || placement.charAt(i) == 'j' ||  placement.charAt(i) == 'f') {
                    showPiece.setLayoutX(PLAY_AREA_X + x * SQUARE_SIZE - SQUARE_SIZE);
                    showPiece.setLayoutY(PLAY_AREA_Y + y * SQUARE_SIZE + SQUARE_SIZE);
                }
            }
            this.placement.getChildren().add(showPiece);
        }

//        this.placement.setOpacity(0);

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(550);
        hb.setLayoutY(VIEWER_HEIGHT - 100);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        root.getChildren().add(board);
        root.getChildren().add(placement);

        makeControls();
        makeBoard();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
