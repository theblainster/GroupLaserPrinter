import java.sql.SQLType;
import java.util.Queue;

public class LaserPrinter {
	private boolean 		isOn = false;
	private PaperAssembly   paperTray;
	public DisplayAssembly display;
	private OutputAssembly  outputTray;
	private TonerAssembly   toner;
	private FuserAssembly   fuser;
	private PrintAssembly   printing;
	private PrintQueue		queue;
	
	public LaserPrinter() {
		paperTray  = new PaperAssembly(300);
		display	   = new DisplayAssembly();
		outputTray = new OutputAssembly();
		toner	   = new TonerAssembly(600);
		fuser	   = new FuserAssembly();
		printing   = new PrintAssembly();
		queue	   = new PrintQueue();
	}
	
	// Body of logic

	public boolean printerIsOn(){
		return isOn;
	}

	// Turns on the printer
	public void powerOn() {
		if(isOn) {
			System.out.println("Printer is already on.");
		} else {
			System.out.println("Laser Printer - Starting up.");

			try {
				display   .poweringOn();
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
		if (isOn) {
			paperTray.fillPaper();
		}
		else {
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
	}

	// Adds toner to the toner
	public void fillToner() {
		if (isOn) {
			toner.tonerReload();
		}
		else {
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
	}

	// Replaces the old drum with a new drum
	public void replaceDrum() {
		if (isOn) {
			printing.replaceDrum();
		}
		else {
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
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
		//	display.resetDisplay();
			try {
				queue    .checkForJob();
				paperTray.usePaper(queue.nextDoc().getPageCount());
				toner    .useToner(queue.nextDoc().getPageCount());
				fuser    .heatUp  ();
				printing .powerOn ();
				printing .usePages(queue.nextDoc().getPageCount());
				System.out.println("Successfully printed document: " + queue.nextDoc().getName());
				display  .printing();
				queue    .printJob();
			} catch (Exception e) {
				System.out.println(e.getMessage());

			} finally {
				printing.powerOff();
				fuser   .coolDown();
				if (!checkForErrors()) {
					display.ready();
				}
			}
		}
		else {
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
	
	public boolean lowToner()
	{
		if (toner.tonerIsLow()) {
			//display.tonerWarning();
			return true;
		}
		return false;
	}
	
	public boolean outOfToner()
	{
		if (toner.getValue() == 0){
			return true;
		}
		return false;
	}
	
	public boolean lowDrum()
	{
		if (printing.getValue() < printing.DRUM_WARN_LIFE) {
			display.drumWarning();
			return true;
		}
		return false;
	}
	
	public boolean outOfDrum()
	{
		if (printing.getValue() == 0) {
			display.drumError();
			return true;
		}
		return false;
	}
	
	public boolean lowPaper(){
		if (paperTray.getValue() <= paperTray.LOW_PAPER)
		{
			return true;
		}
		return false;
	}
	public boolean overflowError()
	{
		if (outputTray.outputAssemblyIsFull()){
			return true;
		}
		return false;
	}
	
}
	