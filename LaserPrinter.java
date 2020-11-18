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
	
	public void reportStatus() {
		if(isOn) {
			System.out.println("--- Printer Status Report ---");
			System.out.println("          Paper Level: " + paperTray.getValue());
			System.out.println("--- Printer Status Report Complete ---");
		} else {
			System.out.println("Printer is off.");
		}
	}
	
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
}