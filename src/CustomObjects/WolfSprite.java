package CustomObjects;

import CustomControls.GameScore;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.text.Element;

public class WolfSprite extends ImageView {
    private double velX;
    private final double speed=7;

    private SimpleDoubleProperty x;
    private SimpleDoubleProperty y;


    public WolfSprite(String s) {
        super(s);
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();
        y.set(600);
        xProperty().bind(x);
        yProperty().bind(y);
        setPreserveRatio(true);
        setFitHeight(200);
    }

    public void tick(){
        x.set(x.getValue()+velX);
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void moveLeft() {
        setScaleX(-1);
        setVelX(-speed);
    }

    public void moveRight() {
        setScaleX(1);
        setVelX(speed);

    }

    public double getVelX(){
        return velX;
    }

    public boolean checkCollision(WolfSprite wolf, Egg egg, GameScore score, Group parent) {
        boolean collisionDetect = false;
        if (wolf.getBoundsInParent().intersects(egg.getEgg().getBoundsInParent())) {
            collisionDetect = true;
        }
        return collisionDetect;
    }

}
