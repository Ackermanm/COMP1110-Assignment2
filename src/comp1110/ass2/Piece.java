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
        for (int i=0;i<placement.length();i+=4){
            if (placement.charAt(i+3) == '0') {return Rotation.ZERO; }
            if (placement.charAt(i+3) == '1') {return Rotation.ONE; }
            if (placement.charAt(i+3) == '2') {return Rotation.TWO; }
            if (placement.charAt(i+3) == '3') {return Rotation.THREE; }
        }
        return Rotation.ZERO;
    }

    public Location placementToLocation(String placement) {
        int x =  Integer.parseInt(Character.toString(placement.charAt(1)));
        int y =  Integer.parseInt(Character.toString(placement.charAt(2)));
        return new Location(x,y);
    }
}
