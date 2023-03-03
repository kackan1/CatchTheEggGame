package CustomControls;

import javafx.scene.control.Label;

public class GameScore extends Label {
    private int score;

    public GameScore(String s) {
        super("Score: "+ s);
        setStyle("-fx-font-size: 40px;");
    }

    public void addPoint(int points){
        score+=points;
        setText("Score: "+score);
    }

    public int getInt(){
        return score;
    }
}
