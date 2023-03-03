package CustomObjects;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EggLeft extends ImageView
{
    private static int ID=1;
    private int id=ID++;
    boolean eggCrashed=false;

    public EggLeft(String s, Stage stage) {
        super(s);
        setPreserveRatio(true);
        setFitHeight(80);
        switch (id){
            case 1:
                setX(730);

                break;
            case 2:
                setX(690);
                break;
            case 3:
                setX(650);
                break;
        }
    }

    public void eggCrashed(){
        eggCrashed=true;
    }

    public boolean isEggCrashed(){
        return eggCrashed;
    }

    public int getPos(){
        return id;
    }

}
