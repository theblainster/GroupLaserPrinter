import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javax.print.Doc;


public class TestFX extends Application  {
    LaserPrinter laserPrinter = new LaserPrinter();

    // Maximum, Minimum/Warning, and other levels for bar chart
    public static final int MAX_PAPER_LEVEL  = 300;
    public static final int MAX_TONER_LEVEL  = 850;
    public static final int MAX_DRUM_LEVEL   = 1000;
    public static final int MAX_OUTPUT_LEVEL = 250;

    // Levels (numbers) for bar chart 
    private int paperLevelNumber = laserPrinter.getPaperTrayLevel();
    private int tonerLevelNumber = laserPrinter.getTonerLevel();
    private int drumLevelNumber  = laserPrinter.getDrumLevel();
    private int outputTrayNumber = laserPrinter.getOutputLevel();

	private double errorCode = 0;

    // Levels (percentages) for bar chart
    private int paperLevelPercent = (int) (100 * ((float) paperLevelNumber / (float) MAX_PAPER_LEVEL));
    private int tonerLevelPercent = (int) (100 * ((float) tonerLevelNumber / (float) MAX_TONER_LEVEL));
    private int drumLevelPercent  = (int) (100 * ((float) drumLevelNumber  / (float) MAX_DRUM_LEVEL));
    private int outputTrayPercent = (int) (100 * ((float) outputTrayNumber / (float) MAX_OUTPUT_LEVEL));

    private int jobNumber   = 0;
    private int indexNumber = 0;

    // Current Status
	private Circle tonerLED   = new Circle();
	private Circle drumLED    = new Circle();
	private Circle generalLED = new Circle();
	
	// Starting Fuser Temperature
    private int  fuserTemp = laserPrinter.getDefaultTemp();
	private Text fuserText = new Text(fuserTemp + " Celsius");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) {
        // Creates bar chart object
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis   yAxis = new NumberAxis();
        BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        barChart.setTitle("Paper, Toner, Drum, and Output Tray Levels");
        yAxis.setLabel("Levels");

        // Data for paper level
        XYChart.Series paperLevel = new XYChart.Series();
        paperLevel.setName("Paper");
        paperLevel.getData().add(new XYChart.Data("Paper", paperLevelPercent));

        // Data for toner level
        XYChart.Series tonerLevel = new XYChart.Series();
        tonerLevel.setName("Toner");
        tonerLevel.getData().add(new XYChart.Data("Toner", tonerLevelPercent));

        // Data for drum level
        XYChart.Series drumLevel = new XYChart.Series();
        drumLevel.setName("Fuser");
        drumLevel.getData().add(new XYChart.Data("Drum", drumLevelPercent));

        // Data for drum level
        XYChart.Series outputLevel = new XYChart.Series();
        outputLevel.setName("Output");
        outputLevel.getData().add(new XYChart.Data("Output Tray", outputTrayPercent));

        // Adds all data levels to bar chart object
        barChart.getData().addAll(paperLevel, tonerLevel, drumLevel, outputLevel);

        // Buttons for adding more to paper, toner, and/or drum levels
        Button btAddPaper     = new Button("Add Paper");
        Button btReplaceToner = new Button("Replace Toner");
        Button btReplaceDrum  = new Button("Replace Fuser");
        Button btRemoveOutput = new Button("Remove Output");

        // HBox for bottom control buttons
        HBox hBoxAddLevels = new HBox();
        hBoxAddLevels.setSpacing(12);
        hBoxAddLevels.setAlignment(Pos.CENTER);
        hBoxAddLevels.getChildren().addAll(btAddPaper, btReplaceToner, btReplaceDrum, btRemoveOutput);

        // VBox for Paper, Toner, and Drum levels; and buttons to add more to these levels
        VBox vBoxGraphLayout = new VBox();
        vBoxGraphLayout.getChildren().addAll(barChart, hBoxAddLevels);

        // Button events for graph
        // Add paper button event
        var addPaperClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Adds paper to the paper tray
                if (paperLevelPercent < 100) {
                    laserPrinter.fillPaper();
                    paperLevelNumber  = laserPrinter.getPaperTrayLevel();
                    paperLevelPercent = (int) (100 * ((float) paperLevelNumber / (float) MAX_PAPER_LEVEL));
                    paperLevel.getData().removeAll();
                    paperLevel.getData().add(new XYChart.Data("Paper", paperLevelPercent));
                    LEDRefresh();
                }
			}
        };

        // Replace toner button event
        var replaceTonerClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                // Replaces the toner
                if (tonerLevelPercent < 100) {
                    laserPrinter.fillToner();
                    tonerLevelNumber  = laserPrinter.getTonerLevel();
                    tonerLevelPercent = (int) (100 * ((float) tonerLevelNumber / (float) MAX_TONER_LEVEL));
                    tonerLevel.getData().removeAll();
                    tonerLevel.getData().add(new XYChart.Data("Toner", tonerLevelPercent));
                    LEDRefresh();
                }
			}
        };

        // Replace drum button event
        var replaceDrumClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                // Replaces the drum
                if (drumLevelPercent < 100) {
                    laserPrinter.replaceDrum();
                    drumLevelNumber  = laserPrinter.getDrumLevel();
                    drumLevelPercent = (int) (100 * ((float) drumLevelNumber / (float) MAX_DRUM_LEVEL));
                    drumLevel.getData().removeAll();
                    drumLevel.getData().add(new XYChart.Data("Drum", drumLevelPercent));
                    LEDRefresh();
                }
			}
        };

        // Remove Output button event
        var removeOutputClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (outputTrayPercent > 0) {
                    laserPrinter.removeOutput();
                    outputTrayNumber = laserPrinter.getOutputLevel();
                    outputTrayPercent = (int) (100 * ((float) outputTrayNumber / (float) MAX_OUTPUT_LEVEL));
                    outputLevel.getData().removeAll();
                    outputLevel.getData().add(new XYChart.Data("Output Tray", outputTrayPercent));
                    LEDRefresh();
                }
            }
        };

        // Button controls for graph
        btAddPaper    .addEventFilter(MouseEvent.MOUSE_CLICKED, addPaperClicked);
        btReplaceToner.addEventFilter(MouseEvent.MOUSE_CLICKED, replaceTonerClicked);
        btReplaceDrum .addEventFilter(MouseEvent.MOUSE_CLICKED, replaceDrumClicked);
        btRemoveOutput.addEventFilter(MouseEvent.MOUSE_CLICKED, removeOutputClicked);

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
            setFuserTemp(laserPrinter.getThickTemp());
            btNormalPaper.setDisable(false);
            btThickPaper .setDisable(true);
            }
        };

        // Normal paper button
        var normalPaperClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
            setFuserTemp(laserPrinter.getNormalTemp());
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
        VBox vBoxFuserLayout = new VBox();
        vBoxFuserLayout.getChildren().addAll(fuserLabel, fuserText, hBoxFuserButtons);

        // Fuser button controls
        btNormalPaper.addEventFilter(MouseEvent.MOUSE_CLICKED, normalPaperClicked);
        btThickPaper .addEventFilter(MouseEvent.MOUSE_CLICKED, thickPaperClicked);


		// STATUS
		// LED Indicators
		Label statusLabel     = new Label("Status");
		Label tonerLEDLabel   = new Label("Toner");
		Label drumLEDLabel    = new Label("Drum");
		Label generalLEDLabel = new Label("Ready");
		
		// Toner LED
		tonerLED.setRadius(5);
		HBox tonerLocation = new HBox();
		tonerLocation.setSpacing(10);
		tonerLocation.setAlignment(Pos.CENTER);
		tonerLocation.getChildren().addAll(tonerLEDLabel, tonerLED);
		tonerLED.setStroke(Color.GRAY);
		tonerLED.setFill(Color.GRAY);
		
		// Drum LED
		drumLED.setRadius(5);
		HBox drumLocation = new HBox();
		drumLocation.setSpacing(10);
		drumLocation.setAlignment(Pos.CENTER);
		drumLocation.getChildren().addAll(drumLEDLabel, drumLED);
		drumLED.setStroke(Color.GRAY);
		drumLED.setFill(Color.GRAY);
		
		// General LED 
		generalLED.setRadius(5);
		HBox generalLocation = new HBox();
		generalLocation.setSpacing(10);
		generalLocation.setAlignment(Pos.CENTER);
		generalLocation.getChildren().addAll(generalLEDLabel, generalLED);
		generalLED.setStroke(Color.GRAY);
		generalLED.setFill(Color.GRAY);
		
		// Vertical to display the status of the toner, drum, and general functions of the printer
		VBox vBoxStatusLayout = new VBox(10);
		vBoxStatusLayout.getChildren().addAll(statusLabel, tonerLocation, drumLocation, generalLocation);

		// PRINT QUEUE
        // Print queue buttons
        Button    btPrintJob  = new Button("Print");
        Button    btCancelJob = new Button("Cancel");
        Button    btAddJob    = new Button("Add");
        Button    btClearJobs = new Button("Clear");
        btPrintJob. setDisable(true);
        btClearJobs.setDisable(true);
        btCancelJob.setDisable(true);

        // Layout for print queue
        VBox vBoxQueueLayout = new VBox();
        HBox queueButtons    = new HBox();
        vBoxQueueLayout.setSpacing(12);
        queueButtons   .setAlignment(Pos.CENTER);
        queueButtons   .setSpacing(12);
        queueButtons   .getChildren().addAll(btPrintJob, btCancelJob, btAddJob, btClearJobs);

        // Table list for jobs in print queue
        TableView printQueueTable  = new TableView();
        ObservableList<Document> tableData   = FXCollections.observableArrayList();
        TableColumn jobId    = new TableColumn("Job ID");
        TableColumn jobName  = new TableColumn("Name");
        TableColumn jobPages = new TableColumn("# of Pages");
        jobId.   setCellValueFactory(new PropertyValueFactory("jobId"));
        jobName .setCellValueFactory(new PropertyValueFactory("Name"));
        jobPages.setCellValueFactory(new PropertyValueFactory("pageCount"));
        printQueueTable.getColumns().addAll(jobId, jobName, jobPages);
        printQueueTable.setEditable(true);

        // Button events for print queue
        // Print button event
        var btPrintJobClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                if (paperLevelNumber - laserPrinter.getNextDocument().getPageCount() >= 0
                &&  tonerLevelNumber - laserPrinter.getNextDocument().getPageCount() >= 0
                &&  drumLevelNumber  - laserPrinter.getNextDocument().getPageCount() >= 0
                &&  outputTrayNumber + laserPrinter.getNextDocument().getPageCount() <  MAX_OUTPUT_LEVEL) {
                    tableData.remove(0);
                    laserPrinter.printJob();
                    indexNumber--;

                    // Updates the paper level on the graph
                    paperLevelNumber = laserPrinter.getPaperTrayLevel();
                    paperLevelPercent = (int) (100 * ((float) paperLevelNumber / (float) MAX_PAPER_LEVEL));
                    paperLevel.getData().removeAll();
                    paperLevel.getData().add(new XYChart.Data("Paper", paperLevelPercent));

                    // Updates the toner level on the graph
                    tonerLevelNumber  = laserPrinter.getTonerLevel();
                    tonerLevelPercent = (int) (100 * ((float) tonerLevelNumber / (float) MAX_TONER_LEVEL));
                    tonerLevel.getData().removeAll();
                    tonerLevel.getData().add(new XYChart.Data("Toner", tonerLevelPercent));

                    // Updates the drum level on the graph
                    drumLevelNumber  = laserPrinter.getDrumLevel();
                    drumLevelPercent = (int) (100 * ((float) drumLevelNumber / (float) MAX_DRUM_LEVEL));
                    drumLevel.getData().removeAll();
                    drumLevel.getData().add(new XYChart.Data("Drum", drumLevelPercent));

                    // Updates the output tray level on the graph
                    outputTrayNumber  = laserPrinter.getOutputLevel();
                    outputTrayPercent = (int) (100 * ((float) outputTrayNumber / (float) MAX_OUTPUT_LEVEL));
                    outputLevel.getData().removeAll();
                    outputLevel.getData().add(new XYChart.Data("Output Tray", outputTrayPercent));

                    // Gives the possibility of a paper jam
                    if (laserPrinter.checkForErrors()) {
                        btPrintJob.setDisable(true);
                    }
                    LEDRefresh();
                }
                if (tableData.isEmpty()) {
                    btPrintJob .setDisable(true);
                    btClearJobs.setDisable(true);
                }
            }
        };

        // Cancel button clicked
        var btCancelJobClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                laserPrinter.cancelJob(printQueueTable.getSelectionModel().getSelectedIndex());
                printQueueTable.getItems().remove(printQueueTable.getSelectionModel().getSelectedIndex());
                indexNumber = printQueueTable.getItems().size();
                if (tableData.isEmpty()) {
                    btCancelJob.setDisable(true);
                    btClearJobs.setDisable(true);
                }
            }
        };

        // Add button clicked
        var btAddJobClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                tableData.add(indexNumber, new Document(jobNumber,"Job #" + jobNumber++, (int) (10 +  (Math.random() * 91))));
                laserPrinter.addJob(tableData.get(indexNumber++));
                printQueueTable.setItems(tableData);
                printQueueTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                if (laserPrinter.printerIsOn()) {
                    btPrintJob.setDisable(false);
                }
                btClearJobs.setDisable(false);
            }
        };

        // Clear button clicked
        var btClearJobsClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                tableData.clear();
                printQueueTable.setItems(tableData);
                laserPrinter.clearQueue();
                btClearJobs .setDisable(true);
                btCancelJob .setDisable(true);
                jobNumber   = 0;
                indexNumber = 0;
            }
        };

        // Print queue table selected
        var printQueueTableSelected = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btCancelJob.setDisable(false);
            }
        };

        // Events assigned to buttons and queue table
        btPrintJob .addEventFilter(MouseEvent.MOUSE_CLICKED, btPrintJobClicked);
        btCancelJob.addEventFilter(MouseEvent.MOUSE_CLICKED, btCancelJobClicked);
        btAddJob   .addEventFilter(MouseEvent.MOUSE_CLICKED, btAddJobClicked);
        btClearJobs.addEventFilter(MouseEvent.MOUSE_CLICKED, btClearJobsClicked);
        printQueueTable.addEventFilter(MouseEvent.MOUSE_CLICKED, printQueueTableSelected);

        vBoxQueueLayout.getChildren().addAll(printQueueTable, queueButtons);

        // PRINTER CONTROL BUTTONS
        // Bottom control buttons
        Button btPowerOn      = new Button("Power On");
        Button btPowerOff     = new Button("Power Off");
        Button btClearErrors  = new Button("Clear All Errors");
        Button btExit         = new Button("Click to exit");
        btPowerOff   .setDisable(true);
        btClearErrors.setDisable(true);

        // HBox for bottom control buttons
        HBox hBoxControlButtons = new HBox();
        hBoxControlButtons.setSpacing(12);
        hBoxControlButtons.getChildren().addAll(btPowerOn, btPowerOff, btClearErrors, btExit);
        hBoxControlButtons.setAlignment(Pos.CENTER);

        // Power On button event
        var mousePowerOn = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                btPowerOff        .setDisable(false);
                btPowerOn         .setDisable(true);
                btClearErrors     .setDisable(false);
                if (tableData.isEmpty()) {
                    btPrintJob .setDisable(true);
                    btClearJobs.setDisable(true);
                    btCancelJob.setDisable(true);
                } else {
                    btPrintJob .setDisable(false);
                    btClearJobs.setDisable(false);
                }
                normalPaperClicked.handle(event);
                laserPrinter.powerOn();
                laserPrinter.resetDisplay();
                LEDRefresh();
            }
        };

        // Power Off button event
        var mousePowerOff = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                btPowerOff   .setDisable(true);
                btPowerOn    .setDisable(false);
                btClearErrors.setDisable(true);
                btNormalPaper.setDisable(true);
                btThickPaper .setDisable(true);
                btPrintJob   .setDisable(true);
                setFuserTemp(0);
                laserPrinter.powerOff();
                LEDRefresh();
            }
        };

        // Clear Errors button event
        var mouseClearErrors = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                laserPrinter.resetDisplay();
                LEDRefresh();
                if (!tableData.isEmpty()) {
                    btPrintJob.setDisable(false);
                }
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
        btPowerOn     .addEventFilter(MouseEvent.MOUSE_CLICKED, mousePowerOn);
        btPowerOff    .addEventFilter(MouseEvent.MOUSE_CLICKED, mousePowerOff);
        btClearErrors .addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClearErrors);
        btExit        .addEventFilter(MouseEvent.MOUSE_CLICKED, mouseExit);


        // LAYOUT
        // HBox for all sections of app
        HBox hBoxLayout = new HBox();
        hBoxLayout.setAlignment(Pos.CENTER);
        hBoxLayout.setSpacing(12);

        // Adds
        hBoxLayout.getChildren().addAll(vBoxGraphLayout, vBoxFuserLayout, vBoxQueueLayout, vBoxStatusLayout);

        // VBox of entire application
        VBox vBoxPrinter = new VBox();
        vBoxPrinter.setAlignment(Pos.CENTER);
        vBoxPrinter.setSpacing(30);
        vBoxPrinter.getChildren().addAll(hBoxLayout, hBoxControlButtons);

        // Scene and Stage layouts
        Scene scene = new Scene(vBoxPrinter, 50, 50);
        stage.setTitle("My JavaFX Test Program");
        stage.setWidth (1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
    }

    private void setFuserTemp(int temperature) {
        fuserTemp = temperature;
        fuserText.setText(fuserTemp + " Celsius");
    }
	
	// Check to see if there is a potential problem
    private void LEDRefresh(){
        if (laserPrinter.printerIsOn()) {
            //Check if the toner level is too low
            if (laserPrinter.outOfToner()) {
                tonerLED.setStroke(laserPrinter.getTonerColor());
                tonerLED.setFill  (laserPrinter.getTonerColor());
            } else if (laserPrinter.lowToner()) {
                tonerLED.setStroke(laserPrinter.getTonerColor());
                tonerLED.setFill  (laserPrinter.getTonerColor());
            } else {
                tonerLED.setStroke(laserPrinter.getTonerColor());
                tonerLED.setFill  (laserPrinter.getTonerColor());
            }

            // Check if the paper level is too low
            if (laserPrinter.outOfPaper()) {
                generalLED.setStroke(laserPrinter.getGeneralColor());
                generalLED.setFill  (laserPrinter.getGeneralColor());
            } else if (laserPrinter.lowPaper()) {
                generalLED.setStroke(laserPrinter.getGeneralColor());
                generalLED.setFill  (laserPrinter.getGeneralColor());
            } else {
                generalLED.setStroke(laserPrinter.getGeneralColor());
                generalLED.setFill  (laserPrinter.getGeneralColor());
            }

            // Check if the fuser level is too low
            if (laserPrinter.outOfDrum()) {
                drumLED.setStroke(laserPrinter.getDrumColor());
                drumLED.setFill  (laserPrinter.getDrumColor());
            } else if (laserPrinter.lowDrum()) {
                drumLED.setStroke(laserPrinter.getDrumColor());
                drumLED.setFill  (laserPrinter.getDrumColor());
            } else {
                drumLED.setStroke(laserPrinter.getDrumColor());
                drumLED.setFill  (laserPrinter.getDrumColor());
            }

            if (laserPrinter.overflowError()) {
                generalLED.setStroke(laserPrinter.getGeneralColor());
                generalLED.setFill  (laserPrinter.getGeneralColor());
            }

            // Check for a paper jam
            if (laserPrinter.paperJam()){
                generalLED.setStroke(laserPrinter.getGeneralColor());
                generalLED.setFill  (laserPrinter.getGeneralColor());
            }

        }
        else {
            tonerLED  .setStroke(laserPrinter.getTonerColor());
            tonerLED  .setFill  (laserPrinter.getTonerColor());
            drumLED   .setStroke(laserPrinter.getDrumColor());
            drumLED   .setFill  (laserPrinter.getDrumColor());
            generalLED.setStroke(laserPrinter.getGeneralColor());
            generalLED.setFill  (laserPrinter.getGeneralColor());
        }
    }
}
