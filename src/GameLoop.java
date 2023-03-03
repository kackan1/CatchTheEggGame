import CustomControls.GameScore;
import CustomObjects.Egg;
import CustomObjects.EggLeft;
import CustomObjects.WolfSprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GameLoop {
    final Duration frameT = new Duration(16);
    private Stage stage;
    private Group parent;
    private Timeline timeline;
    private WolfSprite wolf;
    private ArrayList<Egg> eggList;
    private ArrayList<Egg> eggToRemove;
    private GameScore score;
    private int eggAmount = 1;
    private int eggCrashed;
    private ImageView background;
    private SceneManager manager;
    private int eggMinSpeed=4;
    private int eggMaxSpeed=6;
    private ArrayList<EggLeft> eggLefts;
    private int pointsToAdd=1;

    public GameLoop(Stage stage, Group parent, ImageView background, SceneManager manager, WolfSprite wolf, ArrayList<EggLeft> eggLefts) {
        this.manager=manager;
        this.background=background;
        this.stage = stage;
        this.parent = parent;
        this.eggLefts=eggLefts;
        eggCrashed=0;

        score = new GameScore("0");
        score.setAlignment(Pos.TOP_RIGHT);
        parent.getChildren().add(score);

        eggToRemove = new ArrayList<>();
        eggList = new ArrayList<>();
        addEgg();

        this.wolf = wolf;
        parent.getChildren().add(wolf);

        KeyFrame frame = new KeyFrame(frameT, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                calculateEggAmount();
                makeEggs();
                render();
                gameEndedCheck();
                wolf.tick();
            }
        });
        timeline = new Timeline(60, frame);
        timeline.setCycleCount(-1);
    }

    public void gameEndedCheck(){
        if (eggCrashed>=3){
            timeline.stop();
            PopUpStage popup = null;
            try {
                popup = new PopUpStage(score, manager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            popup.show();
        }
    }

    public void render(){
        parent.getChildren().clear();
        parent.getChildren().add(background);
        parent.getChildren().add(score);
        parent.getChildren().add(wolf);
        for (int i=0;i<=2;i++) {
            if(!eggLefts.get(i).isEggCrashed()){
                parent.getChildren().add(eggLefts.get(i));
            }
        }
        for (Egg egg : eggList){
            parent.getChildren().add(egg.getEgg());
        }
    }

    public void makeEggs() throws ConcurrentModificationException {
        if (eggList.size() < eggAmount) {
            addEgg();
        }
        for (Egg egg : eggList) {
            if (egg.getEgg().getCenterY() <= stage.getHeight() ) {
                egg.move();
                egg.getEgg().setRotate(egg.getRotation());
            } else {
                eggToRemove.add(egg);
                eggLefts.get(eggCrashed).eggCrashed();
                eggCrashed++;
            }
        }
        for (Egg egg : eggList) {
            checkCollisions(egg);
        }
        for (Egg egg : eggToRemove){
            parent.getChildren().remove(egg.getEgg());
            eggList.remove(egg);
        }
    }

    public void checkCollisions(Egg egg) {
        if (wolf.checkCollision(wolf, egg, score, parent)) {
            score.addPoint(pointsToAdd);
            eggToRemove.add(egg);
        }
    }

    public void calculateEggAmount() {
        if (score.getInt() >= 8) {
            eggMaxSpeed=7;
            eggMinSpeed=4;
            pointsToAdd=1;
            if (score.getInt() >= 36) {
                eggAmount = 2;
                eggMinSpeed=2;
                eggMaxSpeed=7;
                pointsToAdd=2;
                if (score.getInt() >= 60) {
                    eggAmount = 3;
                    eggMinSpeed=2;
                    eggMaxSpeed=6;
                    pointsToAdd=3;
                    if (score.getInt() >= 120){
                        eggMinSpeed=3;
                        eggMaxSpeed=7;
                        pointsToAdd=3;
                        if (score.getInt()>=150){
                            eggAmount=4;
                            eggMinSpeed=2;
                            eggMaxSpeed=7;
                            pointsToAdd=4;
                        }
                    }
                }
            }
        }
    }

    public void removeEgg(Egg egg) {
        eggList.remove(egg);
    }

    public void addEgg() {
        int speed = (int) ((Math.random() * (eggMaxSpeed-eggMinSpeed)) + eggMinSpeed);
        Egg egg = new Egg(stage, speed);
        eggList.add(egg);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
