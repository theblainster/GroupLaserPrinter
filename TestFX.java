import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) {
        GridPane  grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setMinSize(200, 400);
        grid.setPadding(new Insets(30,30,30,30));
        grid.setHgap(10);
        grid.setVgap(10);

        Button    btOK = new Button("OK" );
        Button btNotOK = new Button("Not OK");
        Button  btFine = new Button("OK");
        Button  btExit = new Button("Click to exit");

        VBox vBox = new VBox();
        vBox.setSpacing(12);
        vBox.getChildren().addAll(btOK, btNotOK);

        VBox vBox2 = new VBox();
        vBox2.setSpacing(12);
        vBox2.getChildren().addAll(btFine, btExit);

        grid.add(vBox, 0, 0);
        grid.add(vBox2, 0,1);

        var mouseClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (btOK == mouseEvent.getSource()) {
                    if (btOK.getText().equalsIgnoreCase("OK")) {
                        btOK.setText("Not OK");
                    } else {
                        btOK.setText("OK");
                    }
                }

                else if (btNotOK == mouseEvent.getSource()) {
                    if (btNotOK.getText().equalsIgnoreCase("OK")) {
                        btNotOK.setText("Not OK");
                    } else {
                        btNotOK.setText("OK");
                    }
                }

                else if (btFine == mouseEvent.getSource()) {
                    if (btFine.getText().equalsIgnoreCase("OK")) {
                        btFine.setText("Not OK");
                    } else {
                        btFine.setText("OK");
                    }
                }

                if (btOK   .getText().equalsIgnoreCase("Not OK")
                &&  btNotOK.getText().equalsIgnoreCase("Not OK")
                &&  btFine .getText().equalsIgnoreCase("Not OK")) {
                    btOK   .setText("2020!!!");
                    btNotOK.setText("2020!!!");
                    btFine .setText("2020!!!");
                }
            }
        };

        var mouseExit = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                stage.close();
            }
        };

        btOK.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);
        btNotOK.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);
        btFine.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);
        btExit.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseExit);

        Scene scene = new Scene(grid, 50, 50);
        stage.setTitle("My JavaFX Test Program");
        stage.setWidth (400);
        stage.setHeight(400);
        stage.setScene(scene);
        stage.show();
    }
}
