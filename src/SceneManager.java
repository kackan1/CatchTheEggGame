import CustomControls.MenuButton;
import CustomObjects.EggLeft;
import CustomObjects.ScoreString;
import CustomObjects.WolfSprite;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class SceneManager {
    private Scene menuScene;
    private Scene gameScene;
    private Scene highScores;
    private Stage stage;
    private final Button shortCutMenu = new Button();
    private BufferedReader reader;
    private File scores;
    private ScoreString scoreString;
    private VBox highScoresL;
    private Group gameL;
    private Label record;
    private GameLoop gameLoop;
    private ArrayList<EggLeft> eggLefts;

    public SceneManager(Stage stage) {
        this.stage = stage;
        init();
    }

    public void init() {
        initMenuScene();
        initGameScene();
        initHighScoresScene();
        stage.setScene(menuScene);
        setUpMnemonic();
    }

    public void resetGameScene(){
        gameLoop.stop();
        gameL.getChildren().clear();
    }

    public void initGameScene() {

        eggLefts=new ArrayList<>();
        EggLeft eg1 = new EggLeft("Images" + "/Jajka/JajkaPozostale.png", stage);
        EggLeft eg2 = new EggLeft("Images/Jajka/JajkaPozostale.png", stage);
        EggLeft eg3 = new EggLeft("Images/Jajka/JajkaPozostale.png", stage);
        eggLefts.add(eg1);
        eggLefts.add(eg2);
        eggLefts.add(eg3);

        gameL = new Group();
        gameScene = new Scene(gameL, 800, 800);
        ImageView background = new ImageView("Images/Background.jpg");
        background.fitWidthProperty().bind(stage.widthProperty());
        background.fitHeightProperty().bind(stage.heightProperty());
        gameL.getChildren().add(background);

        WolfSprite wolf = new WolfSprite("Images/Wolf.png");
        gameLoop = new GameLoop(stage, gameL, background, this, wolf, eggLefts);
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT:
                        wolf.moveLeft();
                        break;
                    case RIGHT:
                        wolf.moveRight();
                        break;
                }
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        wolf.setVelX(0);
                        break;
                    case RIGHT:
                        wolf.setVelX(0);
                        break;
                }
            }
        });
    }

    public void initHighScoresScene() {
        highScoresL = new VBox(20);
        highScoresL.setAlignment(Pos.CENTER);

        highScoresL.setStyle("-fx-background-color: rgb(85,107,47)");
        highScores = new Scene(highScoresL, 800, 800);


        StringBuffer sb = new StringBuffer();
        File file = new File("src/score");
        try {
            if (file.createNewFile()) {
            } else {
                reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String tab[] = line.split(" ");
                    scoreString = new ScoreString(tab[0], Integer.parseInt(tab[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateHighScores();
    }

    public void updateHighScores() {
        highScoresL.getChildren().clear();

        for (int i = 0; i < ScoreString.getList().size(); i++) {

            String name = ScoreString.getList().get(i).getName();
            String score = String.valueOf(ScoreString.getList().get(i).getScore());
            record = new Label("Place nr " + (i + 1) + ":   " + name + "   " + score);
            record.setStyle("-fx-font-size: 24px;" +
                    "-fx-background-color: rgb(137,163,64);" +
                    "-fx-alignment: center;");
            record.setPrefSize(600, 40);
            highScoresL.getChildren().add(record);
        }
        MenuButton button = new MenuButton("Back", 16);
        button.setPrefSize(120, 40);
        button.setOnAction(e -> {
            setMenuScene();
        });
        highScoresL.getChildren().add(button);
    }

    public void initMenuScene() {
        VBox menuL = new VBox(100);
        menuL.setStyle("-fx-background-color: darkolivegreen");
        menuL.setAlignment(Pos.CENTER);
        menuScene = new Scene(menuL, 800, 800);
        MenuButton newGame = new MenuButton("New Game", 30);
        newGame.setOnAction(e -> {
            setGameScene();
            gameLoop.start();
        });
        MenuButton highScores = new MenuButton("High Scores", 30);
        highScores.setOnAction(e -> {
            setHighScores();
        });
        MenuButton exit = new MenuButton("Exit", 30);
        exit.setOnAction(e -> {
            System.exit(0);
        });
        menuL.getChildren().addAll(newGame, highScores, exit);
    }

    public void setUpMnemonic() {
        shortCutMenu.setOnAction(e -> {
            init();
        });
        KeyCombination kc = new KeyCharacterCombination("Q", KeyCombination.ALT_DOWN);
        Mnemonic mnemonic = new Mnemonic(shortCutMenu, kc);
        gameScene.addMnemonic(mnemonic);
        highScores.addMnemonic(mnemonic);
    }

    public void setMenuScene() {
        stage.setScene(menuScene);
    }

    public void setGameScene() {
        stage.setScene(gameScene);
    }

    public void setHighScores() {
        stage.setScene(highScores);
    }
}
