public class DisplayAssembly extends AssemblyUnit {
    public static final String GREEN  = "green";
    public static final String YELLOW = "yellow";
    public static final String RED    = "red";
    public static final String BLACK  = "black";

    @Override
    public void activate() {
        activated = true;
    }

    @Override
    public void deactivate() {
        activated = false;
    }

    public static class indicatorLight {
        private boolean lightIsOn;
        private boolean lightIsFlashing;
        private String  lightColor;

        // No args constructor
        public indicatorLight() {
            lightIsOn       = false;
            lightIsFlashing = false;
            lightColor      = BLACK;
        }

        // Getters
        public boolean getLightIsOn      (){
            return lightIsOn;
        }
        public boolean getLightIsFlashing(){
            return lightIsFlashing;
        }
        public String  getLightColor     (){
            return lightColor;
        }

        // Setters
        public void setLightIsOn      (boolean lightIsOn)      {this.lightIsOn       = lightIsOn;}
        public void setLightIsFlashing(boolean lightIsFlashing){this.lightIsFlashing = lightIsFlashing;}
        public void setLightColor     (String lightColor)      {this.lightColor      = lightColor;}
    }

    public indicatorLight toner = new indicatorLight();
    public indicatorLight drum  = new indicatorLight();
    public indicatorLight error = new indicatorLight();
    public indicatorLight ready = new indicatorLight();

    public void tonerError() {
        toner.lightIsOn       = false;
        toner.lightIsFlashing = true;
        toner.lightColor      = RED;
    }

    public void tonerWarning() {
        toner.lightIsOn       = true;
        toner.lightIsFlashing = false;
        toner.lightColor      = YELLOW;
    }

    public void drumError() {
        drum.lightIsOn       = false;
        drum.lightIsFlashing = true;
        drum.lightColor      = RED;
    }

    public void drumWarning() {
        drum.lightIsOn       = true;
        drum.lightIsFlashing = false;
        drum.lightColor      = YELLOW;
    }

    public void generalError() {
        error.lightIsOn       = false;
        error.lightIsFlashing = true;
        error.lightColor      = RED;
    }

    public void poweringOn() {
        ready.lightIsOn       = false;
        ready.lightIsFlashing = true;
        ready.lightColor      = GREEN;
    }

    public void printing() {
        ready.lightIsOn       = false;
        ready.lightIsFlashing = true;
        ready.lightColor      = GREEN;
    }

    public void ready() {
        ready.lightIsOn       = true;
        ready.lightIsFlashing = false;
        ready.lightColor      = GREEN;
    }

    public void resetDisplay() {
        toner.lightIsOn       = false;
        toner.lightIsFlashing = false;
        toner.lightColor      = BLACK;

        drum.lightIsOn        = false;
        drum.lightIsFlashing  = false;
        drum.lightColor       = BLACK;

        error.lightIsOn       = false;
        error.lightIsFlashing = false;
        error.lightColor      = BLACK;

        ready.lightIsOn       = false;
        ready.lightIsFlashing = false;
        ready.lightColor      = BLACK;
    }
}
