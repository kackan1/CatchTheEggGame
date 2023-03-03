package CustomControls;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MenuButton extends Button {
    private int size;

    public MenuButton(String s, int size) {
        this.size=size;
        int oversize=(int)(size*1.1);
        DropShadow shadow = new DropShadow();
        setText(s);
        setPrefSize(250,100);
        setStyle("" +
                "-fx-background-color: #89A441;" +
                "-fx-font-size: "+size+"px;" +
                "-fx-background-radius: 20px;");
        addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle("-fx-background-color: #A8CA4C;" +
                        "-fx-font-size: "+oversize+"px;" +
                        "-fx-background-radius: 20px;");
            }
        });
        addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle("" +
                        "-fx-background-color: #89A441;" +
                        "-fx-font-size: "+size+"px;" +
                        "-fx-background-radius: 20px;");
            }
        });

        this.setEffect(shadow);
    }

}
