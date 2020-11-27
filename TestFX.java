import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestFX extends Application {

    public static final String paper = "Paper";
    public static final String toner = "Toner";
    public static final String fuser = "Fuser";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) {

        // HBox for bottom control buttons
        Button btPowerOn  = new Button("Power On");
        Button btPowerOff = new Button("Power Off");
        Button btExit     = new Button("Click to exit");
        HBox hBoxControlButtons = new HBox();
        hBoxControlButtons.setSpacing(12);
        hBoxControlButtons.getChildren().addAll(btPowerOn, btPowerOff, btExit);

        // VBox for Paper, Toner, and Fuser level
        int paperLevelNumber = 100;
        int tonerLevelNumber = 100;
        int fuserLevelNumber = 100;

        CategoryAxis xAxis         = new CategoryAxis();
        NumberAxis   yAxis         = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Paper, Toner, and Fuser Levels");
        yAxis.setLabel("Levels");

        XYChart.Series paperLevel = new XYChart.Series();
        paperLevel.setName("Paper");
        paperLevel.getData().add(new XYChart.Data(paper, paperLevelNumber));

        XYChart.Series tonerLevel = new XYChart.Series();
        tonerLevel.setName("Toner");
        tonerLevel.getData().add(new XYChart.Data(toner, tonerLevelNumber));

        XYChart.Series fuserLevel = new XYChart.Series();
        fuserLevel.setName("Fuser");
        fuserLevel.getData().add(new XYChart.Data(fuser, fuserLevelNumber));

        bc.getData().addAll(paperLevel, tonerLevel, fuserLevel);

        VBox vBoxGraph = new VBox();
        vBoxGraph.getChildren().addAll(bc);

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setMinSize(200, 400);
        grid.setPadding(new Insets(30,30,30,30));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(hBoxControlButtons, 0,1);
        grid.add(vBoxGraph,0,0);

        // Scene and Stage layouts
        Scene scene = new Scene(grid, 50, 50);
        stage.setTitle("My JavaFX Test Program");
        stage.setWidth (800);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();

        // Button events
        var mouseClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse clicked");
            }
        };

        var mouseExit = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                stage.close();
            }
        };

        // Button controls
        btExit.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseExit);
    }
}
