import javafx.scene.paint.Color;

public class DisplayAssembly extends AssemblyUnit {
    public static final String GREEN  = "green";
    public static final String YELLOW = "yellow";
    public static final String RED    = "red";
    public static final String BLACK  = "black";

    public indicatorLight toner;
    public indicatorLight drum;
    public indicatorLight error;
    public indicatorLight ready;

    // No-args Constructor that creates 4 indicator lights for the 4 major parts of the printer
    public DisplayAssembly() {
        toner = new indicatorLight();
        drum  = new indicatorLight();
        error = new indicatorLight();
        ready = new indicatorLight();
    }

    @Override
    public void activate() {
        activated = true;
    }

    @Override
    public void deactivate() {
        activated = false;
    }

    public static class indicatorLight {
        private boolean lightIsOn;       // Determines if light is solid
        private boolean lightIsFlashing; // Determines if light is blinking
        private Color   lightColor;      // Determines light color

        // No args constructor: defaults to off, no blinking, and black color
        public indicatorLight() {
            lightIsOn       = false;
            lightIsFlashing = false;
            lightColor      = Color.GREEN;
        }

        // Getters
        public boolean getLightIsOn      (){
            return lightIsOn;
        }
        public boolean getLightIsFlashing(){
            return lightIsFlashing;
        }
        public Color  getLightColor     (){
            return lightColor;
        }

        // Setters
        public void setLightIsOn      (boolean lightIsOn)      {this.lightIsOn       = lightIsOn;}
        public void setLightIsFlashing(boolean lightIsFlashing){this.lightIsFlashing = lightIsFlashing;}
        public void setLightColor     (Color lightColor)       {this.lightColor      = lightColor;}
    }

    // Displays blinking red light for a toner error
    public void tonerError() {
        toner.lightIsOn       = false;
        toner.lightIsFlashing = true;
        toner.lightColor      = Color.RED;
    }

    // Displays solid yellow light for a toner warning
    public void tonerWarning() {
        toner.lightIsOn       = true;
        toner.lightIsFlashing = false;
        toner.lightColor      = Color.YELLOW;
    }

    // Displays blinking red light for a drum error
    public void drumError() {
        drum.lightIsOn       = false;
        drum.lightIsFlashing = true;
        drum.lightColor      = Color.RED;
    }

    // Displays solid yellow light for a drum warning
    public void drumWarning() {
        drum.lightIsOn       = true;
        drum.lightIsFlashing = false;
        drum.lightColor      = Color.YELLOW;
    }

    // Displays blinking red light for a general error
    public void generalError() {
        error.lightIsOn       = false;
        error.lightIsFlashing = true;
        error.lightColor      = Color.RED;
    }
	
	// Displays a solid yellow light for low paper
	public void lowPaper() {
		error.lightIsOn       = true;
		error.lightIsFlashing = false;
		error.lightColor      = Color.YELLOW;
	}

    // Displays blinking green light when powering on the printer
    public void poweringOn() {
        ready.lightIsOn       = false;
        ready.lightIsFlashing = true;
        ready.lightColor      = Color.GREEN;
    }

    // Displays blinking green light when printing a job
    public void printing() {
        ready.lightIsOn       = false;
        ready.lightIsFlashing = true;
        ready.lightColor      = Color.GREEN;
    }

    // Displays solid green light when printer is waiting to print
    public void ready() {
        ready.lightIsOn       = true;
        ready.lightIsFlashing = false;
        ready.lightColor      = Color.GREEN;
    }

    // Resets all error/warning lights
    public void resetDisplay() {
        toner.lightIsOn       = false;
        toner.lightIsFlashing = false;
        toner.lightColor      = Color.GREY;

        drum.lightIsOn        = false;
        drum.lightIsFlashing  = false;
        drum.lightColor       = Color.GREY;

        error.lightIsOn       = false;
        error.lightIsFlashing = false;
        error.lightColor      = Color.GREY;

        ready.lightIsOn       = false;
        ready.lightIsFlashing = false;
        ready.lightColor      = Color.GREY;
    }
	
	public void turnOn()
	{
		toner.lightIsOn       = false;
        toner.lightIsFlashing = false;
        toner.lightColor      = Color.GREEN;

        drum.lightIsOn        = false;
        drum.lightIsFlashing  = false;
        drum.lightColor       = Color.GREEN;

        error.lightIsOn       = false;
        error.lightIsFlashing = false;
        error.lightColor      = Color.GREEN;

        ready.lightIsOn       = false;
        ready.lightIsFlashing = false;
        ready.lightColor      = Color.GREEN;
	}
}
