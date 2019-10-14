package comp1110.ass2.gui;

public enum Level {
    STARTER,JUNIOR,MASTER,EXPERT;
    public int levelToInt(Level l){
        switch (l){
            case STARTER: return 0;
            case JUNIOR: return 1;
            case MASTER: return 2;
            case EXPERT: return 3;
        }
        return 0;
    }
}
