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
		paperTray  = new PaperAssembly(300);
		display	   = new DisplayAssembly();
		outputTray = new OutputAssembly();
		toner	   = new TonerAssembly(600);
		fuser	   = new FuserInterface();
		printing   = new PrintAssembly();
		queue	   = new PrintQueue();
	}
	
	// Body of logic

	// TUrns on the printer
	public void powerOn() {
		if(isOn) {
			System.out.println("Printer is already on.");
		} else {
			System.out.println("Laser Printer - Starting up.");

			try {
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
			}
		}
	}

	// Reports the information of the parts of the printer
	public void reportStatus() {
		if(isOn) {
			System.out.println("--- Printer Status Report ---");
			System.out.println("          Paper Level: " + paperTray.getValue());
			System.out.println("		  Drum  Level: " + printing .);
			System.out.println("--- Printer Status Report Complete ---");
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
		if (isOn) {
			queue.addJob(nameOfJob, numberOfPagesToPrint);
		}
		else {
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
	}

	// Removes a document from the print queue, given a specific document ID
	public void cancelJob(int jobToRemove){
		if (isOn) {
			queue.cancelJob(jobToRemove);
		}
		else {
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
	}

	// Prints the first document in the queue
	public void printJob() {
		if (isOn) {
			queue.printJob();
		}
		else {
			System.out.println("Printer is not powered on. Please turn on the printer first.");
		}
	}
}