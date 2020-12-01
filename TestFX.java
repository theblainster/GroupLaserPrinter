import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.control.Label;


public class TestFX extends Application {

    public static final String PAPER    = "Paper";
    public static final String TONER    = "Toner";
    public static final String FUSER    = "Fuser";



    // Levels for bar chart
    private int paperLevelNumber = 100;
    private int tonerLevelNumber = 100;
    private int fuserLevelNumber = 100;
	
	// Fuser Temperature when printer is off
	private int fuserTemp        = 0;
	private Text fuserText = new Text(fuserTemp + " Celcius");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) {
        // HBox for bottom control buttons
        Button btPowerOn          = new Button("Power On");
        Button btPowerOff         = new Button("Power Off");
        Button btExit             = new Button("Click to exit");
        HBox   hBoxControlButtons = new HBox();
        hBoxControlButtons.setSpacing(12);
        hBoxControlButtons.getChildren().addAll(btPowerOn, btPowerOff, btExit);

        // Creates bar chart object
        CategoryAxis xAxis         = new CategoryAxis();
        NumberAxis   yAxis         = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Paper, Toner, and Fuser Levels");
        yAxis.setLabel("Levels");

        // Data for paper level
        XYChart.Series paperLevel = new XYChart.Series();
        paperLevel.setName("Paper");
        paperLevel.getData().add(new XYChart.Data(PAPER, paperLevelNumber));

        // Data for toner level
        XYChart.Series tonerLevel = new XYChart.Series();
        tonerLevel.setName("Toner");
        tonerLevel.getData().add(new XYChart.Data(TONER, tonerLevelNumber));

        // Data for fuser level
        XYChart.Series fuserLevel = new XYChart.Series();
        fuserLevel.setName("Fuser");
        fuserLevel.getData().add(new XYChart.Data(FUSER, fuserLevelNumber));

        // Adds all data levels to bar chart object
        bc.getData().addAll(paperLevel, tonerLevel, fuserLevel);


        // Buttons for adding more to paper, toner, and/or fuser levels
        Button    btAddPaper     = new Button   ("Add Paper");
        Button    btAddToner     = new Button   ("Add Toner");
        Button    btReplaceFuser = new Button   ("Replace Fuser");
        TextField txNumberToAdd  = new TextField();

        // HBox for bottom control buttons
        HBox hBoxAddLevels = new HBox();
        hBoxAddLevels.setSpacing(12);
        hBoxAddLevels.getChildren().addAll(btAddPaper, btAddToner, btReplaceFuser, txNumberToAdd);

        // VBox for Paper, Toner, and Fuser levels; and buttons to add more to these levels
        VBox vBoxGraph = new VBox();
        vBoxGraph.getChildren().addAll(bc, hBoxAddLevels);

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

        // BUTTON EVENTS
        // Exit button
        var mouseExit = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                stage.close();
            }
        };

        // Add paper button
        var addPaperClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                paperLevelNumber += Integer.parseInt(txNumberToAdd.getText());
                paperLevel.getData().removeAll();
                paperLevel.getData().add(new XYChart.Data(PAPER, paperLevelNumber));
            }
        };

        // Add toner button
        var addTonerClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                tonerLevelNumber += Integer.parseInt(txNumberToAdd.getText());
                tonerLevel.getData().removeAll();
                tonerLevel.getData().add(new XYChart.Data(TONER, tonerLevelNumber));
            }
        };

        var replaceFuserClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                fuserLevelNumber = 200;
                fuserLevel.getData().removeAll();
                fuserLevel.getData().add(new XYChart.Data(FUSER, fuserLevelNumber));
            }
        };

        // Button controls
        btExit        .addEventFilter(MouseEvent.MOUSE_CLICKED, mouseExit);
        btAddPaper    .addEventFilter(MouseEvent.MOUSE_CLICKED, addPaperClicked);
        btAddToner    .addEventFilter(MouseEvent.MOUSE_CLICKED, addTonerClicked);
        btReplaceFuser.addEventFilter(MouseEvent.MOUSE_CLICKED, replaceFuserClicked);
		
		

	//FUSER CONTROLS

	// Fuser Temperature
	Button btThickPaper = new Button("Thick Paper");
	Button btNormalPaper = new Button("Normal Paper");

	// Fuser Label
	Label fuserLabel = new Label("Fuser Information");
	fuserText.setFont(Font.font("New Times Roman", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));


	// Thick paper button
	var thickPaperClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle (MouseEvent event) {
		fuserTemp = 190;
		fuserText.setText(fuserTemp + " Celcius");
		btNormalPaper.setDisable(false);
		btThickPaper.setDisable(true);
		}

	};


	// Normal paper button
	var normalPaperClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle (MouseEvent event) {
		fuserTemp = 175;
		fuserText.setText(fuserTemp + " Celcius");
		btNormalPaper.setDisable(true);
		btThickPaper.setDisable(false);
		}
	};

	// The horizontal box that holds the buttons
	HBox fuserButtons = new HBox();
	fuserButtons.setSpacing (50);
	fuserButtons.setAlignment(Pos.CENTER);
	fuserButtons.getChildren().addAll(btThickPaper, btNormalPaper);


	// The vertical box that displays the label, text, and buttons
	VBox fuserLayout = new VBox(10);
	fuserLayout.getChildren().addAll(fuserLabel, fuserText, fuserButtons);


	// Add Fuser to the Grid.
	grid.add(fuserLayout,10,0);


	// Fuser button controls
	btNormalPaper.addEventFilter(MouseEvent.MOUSE_CLICKED, normalPaperClicked);
	btThickPaper .addEventFilter(MouseEvent.MOUSE_CLICKED, thickPaperClicked);
	
    }
}
