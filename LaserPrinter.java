import java.sql.SQLType;
import java.util.Queue;
import javafx.scene.paint.Color;


public class LaserPrinter {
	private boolean 		isOn = false;
	private PaperAssembly   paperTray;
	private DisplayAssembly display;
	private OutputAssembly  outputTray;
	private TonerAssembly   toner;
	private FuserAssembly   fuser;
	private PrintAssembly   printing;
	private PrintQueue		queue;
	
	public LaserPrinter() {
		paperTray  = new PaperAssembly(175);
		display	   = new DisplayAssembly();
		outputTray = new OutputAssembly();
		toner	   = new TonerAssembly(470);
		fuser	   = new FuserAssembly();
		printing   = new PrintAssembly(380);
		queue	   = new PrintQueue();
	}
	
	// Getters
	public int getPaperTrayLevel(){
		return paperTray.getValue();
	}

	public int getTonerLevel() {
		return toner.getValue();
	}

	public int getDrumLevel() {
		return printing.getValue();
	}

	public boolean printerIsOn(){
		return isOn;
	}

	// Turns on the printer
	public void powerOn() {
		if(isOn) {
			System.out.println("Printer is already on.");
		} else {
			display.turnOn();
			System.out.println("Laser Printer - Starting up.");

			try {
				display   .activate();
				paperTray .activate();
				display   .activate();
				outputTray.activate();
				toner	  .activate();
				fuser	  .activate();
				printing  .activate();
			} catch (Exception e) {
				System.out.println("Exception was thrown while powering on the printer.  " + e);
			}
		
			if(paperTray .isActive()
			&& display   .isActive()
			&& outputTray.isActive()
			&& toner	 .isActive()
			&& fuser     .isActive()
			&& printing  .isActive()) {
				isOn = true;
				System.out.println("Laser Printer successfully powered up.");
				display.ready();
			}
		}
	}

	// Reports the information of the parts of the printer
	public void reportStatus() {
		if(isOn) {
			System.out.println("\n\n");
			System.out.println("--- Printer Status Report ---");
			System.out.println("          Paper Level: " + paperTray .getValue());
			System.out.println("          Toner Level: " + toner	 .getValue());
			System.out.println("    Fuser Temperature: " + fuser	 .getValue());
			System.out.println("           Drum Level: " + printing  .getValue());
			System.out.println("        Jobs in Queue: " + queue     .getValue());
			System.out.println(" Pages in Output Tray: " + outputTray.getValue());
			System.out.println("--- Printer Status Report Complete ---");
			System.out.println("\n\n");
		} else {
			System.out.println("Printer is off.");
		}
	}

	// Powers of the printers
	public void powerOff() {
		if(isOn) {
			display.resetDisplay();
			System.out.println("Laser Printer - Shutting down.");
		
			try {
				paperTray.deactivate();
			} catch (Exception e) {
				System.out.println("Exception was thrown while powering off the printer.  " + e);
			}

			if(!paperTray.isActive()) {
				isOn = false;
				System.out.println("Laser Printer successfully shutdown.");
			}
		} else {
			System.out.println("Printer is already off.");
		}
	}

	// Adds paper to the paper tray
	public void fillPaper() {
		paperTray.fillPaper();
	}

	// Adds toner to the toner
	public void fillToner() {
		toner.tonerReload();
	}

	// Replaces the old drum with a new drum
	public void replaceDrum() {
		printing.replaceDrum();
	}

	// Adds a document to the print queue
	public void addJob(String nameOfJob, int numberOfPagesToPrint) {
		queue.addJob(nameOfJob, numberOfPagesToPrint);
	}

	// Removes the first document from the queue
	public void cancelJob(){
		try {
			queue.checkForJob();
			System.out.println("Cancelled document: " + queue.nextDoc().getName());
			queue.cancelJob();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Prints the first document in the queue
	public void printJob() {
		if (isOn) {
			try {
				queue    .checkForJob();
				paperTray.usePaper(queue.nextDoc().getPageCount());
				toner    .useToner(queue.nextDoc().getPageCount());
				printing .powerOn ();
				printing .usePages(queue.nextDoc().getPageCount());
				System.out.println("Successfully printed document: " + queue.nextDoc().getName());
				display  .printing();
				queue    .printJob();
			} catch (Exception e) {
				System.out.println(e.getMessage());

			} finally {
				printing.powerOff();
				printing.powerOff();
				if (!checkForErrors()) {
					display.ready();
				}
			}
		}
		else {
			display.resetDisplay();
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
	}

	// Reports the list of the documents in the queue
	public void reportQueue() {
		queue.reportQueue();
	}

	// Checks for errors or warnings
	public boolean checkForErrors(){
		// Checks for low and empty toner
		boolean isErrors = false;
		isErrors = lowToner();
		isErrors = lowToner();
		isErrors = outOfToner(); 

		// Checks for low or empty toner
		isErrors = lowDrum();
		isErrors = outOfDrum();
		

		// Checks for general errors
		isErrors = lowPaper();
		isErrors = overflowError();
		return isErrors;
	}
	
	// Checks for low toner
	public boolean lowToner()
	{
		if (toner.tonerIsLow()) {
			display.tonerWarning();
			return true;
		}
		return false;
	}
	
	// Checks for empty toner
	public boolean outOfToner()
	{
		if (toner.getValue() == 0){
			display.tonerError();
			return true;
		}
		return false;
	}
	
	// Checks for low drum
	public boolean lowDrum()
	{
		if (printing.getValue() < printing.DRUM_WARN_LIFE) {
			display.drumWarning();
			return true;
		}
		return false;
	}
	
	// Checks for empty drum
	public boolean outOfDrum()
	{
		if (printing.getValue() <= 0) {
			display.drumError();
			return true;
		}
		return false;
	}
	
	// Checks for low paper
	public boolean lowPaper(){
		if (paperTray.getValue() <= paperTray.LOW_PAPER)
		{
			display.lowPaper();
			return true;
		}
		return false;
	}
	
	// Checks for empty paper
	public boolean outOfPaper(){
		if(paperTray.getValue() < 0)
		{
			display.generalError();
			return true;
		}
		return false;
	}
	
	// Check if there is a paper jam
	public boolean paperJam(){
	int jamPaper = (int) (Math.random() * 100);
		if(jamPaper <=5)
		{
			display.generalError();
			return true;
		}
		return false;
	}
	
	// Checks if there is to much paper in the output
	public boolean overflowError()
	{
		if (outputTray.outputAssemblyIsFull()){
			display.generalError();
			return true;
		}
		return false;
	}
	
	// Gets the color of the toner LED
	public Color getTonerColor()
	{
		return display.toner.getLightColor();
	}
	
	// Gets the color of the drum LED
	public Color getDrumColor()
	{
		return display.drum.getLightColor();
	}
	
	//Gets the color of the general LED
	public Color getGeneralColor()
	{
		return display.error.getLightColor();
	}
	
	// Gets the temperature used for normal paper
	public int getNormalTemp(){
		return fuser.getNormalTemperature();
	}
	
	// Gets the temperature used for thick paper
	public int getThickTemp(){
		return fuser.getThickTemperature();
	}
	
	// Gets the resting temperature of the printer
	public int getDefaultTemp(){
		return fuser.getDefaultTemperature();
	}
}
	