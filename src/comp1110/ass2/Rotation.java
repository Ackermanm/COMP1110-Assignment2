package comp1110.ass2;

public enum Rotation {
    ZERO, ONE, TWO, THREE;
    public String rotationToNumber(){
        switch (this){
            case ZERO:return "0";
            case ONE:return "1";
            case TWO:return "2";
            case THREE:return "3";
        }
        return "0";
    }
}
