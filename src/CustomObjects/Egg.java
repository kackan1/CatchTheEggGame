package CustomObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;

public class Egg {
    private int speed;
    private Ellipse ellipse;
    private boolean shouldMove=true;
    private int rotation=0;

    public Egg(Stage stage, int speed) {
        double x = (int)(Math.random() * (stage.getWidth()-100)) + 50;
        ellipse = new Ellipse(20, 25);
        ellipse.setCenterX(x);
        ellipse.setCenterY(40);
        ellipse.setFill(Color.color(Math.random(),0.5,Math.random()));
        ellipse.setStyle("-fx-border-color: black");
        ellipse.setStroke(Color.BLACK);
        this.speed = speed;
    }

    public Ellipse getEgg() {
        return ellipse;
    }

    public void move() {
        if (shouldMove) {
            ellipse.setCenterY(ellipse.getCenterY() + speed);
            rotation+=4;
        }
    }

    public int getRotation(){
        return rotation;
    }
}
