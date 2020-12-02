import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Label;


public class TestFX extends Application {
    LaserPrinter laserPrinter = new LaserPrinter();

    // Maximum, Minimum/Warning, and other levels for bar chart
    public static final int MAX_PAPER_LEVEL    = 400;
    public static final int MAX_TONER_LEVEL    = 200;
    public static final int MAX_FUSER_LEVEL    = 500;
    public static final int DEFAULT_FUSER_TEMP = 0;
    public static final int PAPER_TO_ADD       = 50;

    // Levels (numbers) for bar chart
    private int paperLevelNumber = 100;
    private int tonerLevelNumber = 100;
    private int fuserLevelNumber = 100;

    // Levels (percentages) for bar chart
    private int paperLevelPercent = (int) (100 * ((float) paperLevelNumber / (float) MAX_PAPER_LEVEL));
    private int tonerLevelPercent = (int) (100 * ((float) tonerLevelNumber / (float) MAX_TONER_LEVEL));
    private int fuserLevelPercent = (int) (100 * ((float) fuserLevelNumber / (float) MAX_FUSER_LEVEL));
	
	// Starting Fuser Temperature
	private int  fuserTemp = DEFAULT_FUSER_TEMP;
	private Text fuserText = new Text(fuserTemp + " Celsius");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) {
        // Creates bar chart object
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis   yAxis = new NumberAxis();
        BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
        barChart.setTitle("Paper, Toner, and Fuser Levels");
        yAxis.setLabel("Levels");

        // Data for paper level
        XYChart.Series paperLevel = new XYChart.Series();
        paperLevel.setName("Paper");
        paperLevel.getData().add(new XYChart.Data("Paper", paperLevelPercent));

        // Data for toner level
        XYChart.Series tonerLevel = new XYChart.Series();
        tonerLevel.setName("Toner");
        tonerLevel.getData().add(new XYChart.Data("Toner", tonerLevelPercent));

        // Data for fuser level
        XYChart.Series fuserLevel = new XYChart.Series();
        fuserLevel.setName("Fuser");
        fuserLevel.getData().add(new XYChart.Data("Fuser", fuserLevelPercent));

        // Adds all data levels to bar chart object
        barChart.getData().addAll(paperLevel, tonerLevel, fuserLevel);


        // Buttons for adding more to paper, toner, and/or fuser levels
        Button    btAddPaper     = new Button   ("Add Paper");
        Button    btReplaceToner = new Button   ("Replace Toner");
        Button    btReplaceFuser = new Button   ("Replace Fuser");

        // HBox for bottom control buttons
        HBox hBoxAddLevels = new HBox();
        hBoxAddLevels.setSpacing(12);
        hBoxAddLevels.getChildren().addAll(btAddPaper, btReplaceToner, btReplaceFuser);

        // VBox for Paper, Toner, and Fuser levels; and buttons to add more to these levels
        VBox vBoxGraph = new VBox();
        vBoxGraph.getChildren().addAll(barChart, hBoxAddLevels);

        // Button events for graph
        // Add paper button event
        var addPaperClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Adds paper unless the amount added will put the paper level above maximum level
                if ((paperLevelNumber + PAPER_TO_ADD) < MAX_PAPER_LEVEL) {
                    paperLevelNumber += PAPER_TO_ADD;
                }
                else {
                    paperLevelNumber = MAX_PAPER_LEVEL;
                }
                paperLevelPercent = (int) (100 * ((float) paperLevelNumber / (float) MAX_PAPER_LEVEL));
                paperLevel.getData().removeAll();
                paperLevel.getData().add(new XYChart.Data("Paper", paperLevelPercent));
            }
        };

        // Replace toner button event
        var replaceTonerClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                tonerLevelNumber  = MAX_TONER_LEVEL;
                tonerLevelPercent = (int) (100 * ((float) tonerLevelNumber / (float) MAX_TONER_LEVEL));
                tonerLevel.getData().removeAll();
                tonerLevel.getData().add(new XYChart.Data("Toner", tonerLevelPercent));
            }
        };

        // Replace fuser button event
        var replaceFuserClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                fuserLevelNumber  = MAX_FUSER_LEVEL;
                fuserLevelPercent = (int) (100 * ((float) fuserLevelNumber / (float) MAX_FUSER_LEVEL));
                fuserLevel.getData().removeAll();
                fuserLevel.getData().add(new XYChart.Data("Fuser", fuserLevelPercent));
            }
        };

        // Button controls for graph
        btAddPaper    .addEventFilter(MouseEvent.MOUSE_CLICKED, addPaperClicked);
        btReplaceToner.addEventFilter(MouseEvent.MOUSE_CLICKED, replaceTonerClicked);
        btReplaceFuser.addEventFilter(MouseEvent.MOUSE_CLICKED, replaceFuserClicked);


        // FUSER CONTROLS
        // Fuser Buttons
        Button btNormalPaper = new Button("Normal Paper");
        Button btThickPaper  = new Button("Thick Paper");
        btNormalPaper.setDisable(true);
        btThickPaper .setDisable(true);

        // Fuser Label
        Label fuserLabel = new Label("Fuser Information");
        fuserText .setFont(Font.font("New Times Roman", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));
        fuserLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));

        // Thick paper button
        var thickPaperClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
            setFuserTemp(190);
            btNormalPaper.setDisable(false);
            btThickPaper .setDisable(true);
            }
        };

        // Normal paper button
        var normalPaperClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
            setFuserTemp(175);
            btNormalPaper.setDisable(true);
            btThickPaper .setDisable(false);
            }
        };

        // The HBox that holds the buttons
        HBox hBoxFuserButtons = new HBox();
        hBoxFuserButtons.setSpacing (50);
        hBoxFuserButtons.setAlignment(Pos.CENTER);
        hBoxFuserButtons.getChildren().addAll(btNormalPaper, btThickPaper);

        // The vertical box that displays the label, text, and buttons
        VBox vBoxFuserLayout = new VBox(10);
        vBoxFuserLayout.getChildren().addAll(fuserLabel, fuserText, hBoxFuserButtons);

        // Fuser button controls
        btNormalPaper.addEventFilter(MouseEvent.MOUSE_CLICKED, normalPaperClicked);
        btThickPaper .addEventFilter(MouseEvent.MOUSE_CLICKED, thickPaperClicked);


        // PRINTER CONTROL BUTTONS

        // Bottom control buttons
        Button btPowerOn          = new Button("Power On");
        Button btPowerOff         = new Button("Power Off");
        Button btExit             = new Button("Click to exit");
        btPowerOff.setDisable(true);

        // HBox for bottom control buttons
        HBox hBoxControlButtons = new HBox();
        hBoxControlButtons.setSpacing(12);
        hBoxControlButtons.getChildren().addAll(btPowerOn, btPowerOff, btExit);
        hBoxControlButtons.setAlignment(Pos.CENTER);

        // Power On button event
        var mousePowerOn = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                btPowerOff   .setDisable(false);
                btPowerOn    .setDisable(true);
                btNormalPaper.setDisable(false);
                btThickPaper .setDisable(true);
                laserPrinter .powerOn();
                normalPaperClicked.handle(event);
            }
        };

        // Power Off button event
        var mousePowerOff = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                btPowerOff   .setDisable(true);
                btPowerOn    .setDisable(false);
                btNormalPaper.setDisable(true);
                btThickPaper .setDisable(true);
                setFuserTemp(0);
                laserPrinter.powerOff();

            }
        };

        // Exit button event
        var mouseExit = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                stage.close();
            }
        };

        // Assigns the control button events to the buttons
        btPowerOn .addEventFilter(MouseEvent.MOUSE_CLICKED, mousePowerOn);
        btPowerOff.addEventFilter(MouseEvent.MOUSE_CLICKED, mousePowerOff);
        btExit    .addEventFilter(MouseEvent.MOUSE_CLICKED, mouseExit);

        // LAYOUT
        // HBox for all sections of app
        HBox hBoxLayout = new HBox();
        hBoxLayout.setAlignment(Pos.CENTER);

        // Adds
        hBoxLayout.getChildren().addAll(vBoxGraph, vBoxFuserLayout);

        // VBox of entire application
        VBox vBoxPrinter = new VBox();
        vBoxPrinter.setAlignment(Pos.CENTER);
        vBoxPrinter.setSpacing(12);
        vBoxPrinter.getChildren().addAll(hBoxLayout, hBoxControlButtons);

        // Scene and Stage layouts
        Scene scene = new Scene(vBoxPrinter, 50, 50);
        stage.setTitle("My JavaFX Test Program");
        stage.setWidth (1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
    }

    public void setFuserTemp(int temperature) {
        fuserTemp = temperature;
        fuserText.setText(fuserTemp + " Celsius");
    }
}
