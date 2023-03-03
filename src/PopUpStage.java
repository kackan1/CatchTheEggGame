import CustomControls.GameScore;
import CustomObjects.ScoreString;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PopUpStage extends Stage {

    private ScoreString scoreString;

    public PopUpStage(GameScore score, SceneManager manager) throws IOException {

        VBox box = new VBox(20);

        Scene scene = new Scene(box, 500, 200);

        Label label = new Label("Hej udało Ci się zdobyć " + score.getInt() + " punktów.\n");
        label.setStyle("-fx-font-size: 20px");
        label.setAlignment(Pos.CENTER);
        Label label1 = new Label("Wpisz ponizej jak zapisac Cie do najlepszych wynikow :D");
        label1.setStyle("-fx-font-size: 20px");
        label1.setAlignment(Pos.CENTER);

        box.getChildren().addAll(label, label1);
        box.setAlignment(Pos.CENTER);

        TextField text = new TextField("");
        text.setPrefWidth(100);
        text.setAlignment(Pos.CENTER);
        File file = new File("src/score");
        final FileWriter writer = new FileWriter(file);
        Button button = new Button("Ok");
        button.setOnAction(e -> {
            if (!text.getText().equals("")) {
                scoreString = new ScoreString(text.getText(), score.getInt());
                try {
                    for (ScoreString scoreString : ScoreString.getList()) {
                        writer.write(scoreString.getName() + " " + scoreString.getScore()+"\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                manager.updateHighScores();
                this.close();
                manager.setHighScores();
                manager.resetGameScene();
                manager.initGameScene();
            }
        });
        button.setAlignment(Pos.BOTTOM_CENTER);
        box.getChildren().addAll(text, button);

        this.initModality(Modality.APPLICATION_MODAL);
        setScene(scene);

    }
}

