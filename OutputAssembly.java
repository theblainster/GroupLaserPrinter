public class OutputAssembly {
    public static final int MAX_PAGES = 75; // Maximum number of pages allowed in the output assembly
    private int currentPagesInOutput;       // Current number of pages in the output assembly

    // Constructor that sets the number of pages in the output assembly to zero
    public OutputAssembly(){
        setCurrentPagesToZero();
    }

    // Gets the current number of pages in the output assembly
    public int getCurrentPagesInOutput(){
        return currentPagesInOutput;
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
}
