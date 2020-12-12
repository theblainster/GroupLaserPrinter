public class OutputAssembly extends AssemblyUnit implements ISimAssembly {
    public static final int MAX_PAGES = 250; // Maximum number of pages allowed in the output assembly
    private int currentPagesInOutput;        // Current number of pages in the output assembly

    // Constructor that sets the number of pages in the output assembly to zero
    public OutputAssembly(){
        setCurrentPagesToZero();
    }

    // Sets the current number of pages in the output assembly to the given number as long as the given number is less
    // than or equal to the maximum number of pages allowed in the output assembly
    public void setCurrentPagesInOutput(int currentPagesInOutput){
        if (currentPagesInOutput <= MAX_PAGES){
            this.currentPagesInOutput = currentPagesInOutput;
        }
    }

    // Sets the number of pages in the output assembly to zero
    public void setCurrentPagesToZero(){
        currentPagesInOutput = 0;
    }

    // Checks if the output assembly is full
    public boolean outputAssemblyIsFull(){
        return currentPagesInOutput >= MAX_PAGES;
    }

    public void addPagesToOutput(int pagesToAdd) throws Exception {
        if (outputAssemblyIsFull()){
            throw new Exception("Output tray is full.");
        }
        else {
            currentPagesInOutput += pagesToAdd;
        }
    }

    @Override
    public void setValue(int setCurrentPages) {
        currentPagesInOutput = setCurrentPages;
    }

    @Override
    public int getValue() {
        return currentPagesInOutput;
    }

    @Override
    public void activate() {
        activated = true;
    }

    @Override
    public void deactivate() {
        activated = false;
    }
}
