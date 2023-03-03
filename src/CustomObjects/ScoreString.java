package CustomObjects;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreString {
    private String name;
    private int score;
    private static ArrayList<ScoreString> scoreList = new ArrayList<>();

    public ScoreString(String name, int score) {
        this.name = name;
        this.score = score;
        scoreList.add(this);
        Collections.sort(scoreList, new SortByScore());
    }

    public static ArrayList<ScoreString> getList(){
        return scoreList;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
