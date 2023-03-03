package CustomObjects;

import java.util.Comparator;

public class SortByScore implements Comparator<ScoreString> {
    @Override
    public int compare(ScoreString o1, ScoreString o2) {
        return o2.getScore()-o1.getScore();
    }
}
