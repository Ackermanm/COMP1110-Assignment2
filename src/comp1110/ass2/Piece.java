package comp1110.ass2;

public class Piece {
    private PieceType pieceType;
    private Location location;
    private Rotation rotation;

    public Piece(String placement) {
        this.pieceType = PieceType.valueOf(Character.toString((placement.charAt(0) - 32)));
        this.rotation = placementToRotation(placement);
        this.location = placementToLocation(placement);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Location getLocation() {
        return location;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Rotation placementToRotation(String placement){
        for (int i=3;i<40;i=i+4){
            if (placement.charAt(i) == '0') {return Rotation.ZERO; }
            if (placement.charAt(i) == 'S') {return Rotation.ONE; }
            if (placement.charAt(i) == 'E') {return Rotation.TWO; }
            if (placement.charAt(i) == 'W') {return Rotation.THREE; }
        }
        return Rotation.ZERO;
    }

    public Location placementToLocation(String placement) {

        return null;
    }
}
