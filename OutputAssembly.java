public class OutputAssembly {
    public static final int MAX_PAGES = 75;

    private int currentPagesInOutput;

    public OutputAssembly(){
        setCurrentPagesToZero();
    }

    public int getCurrentPagesInOutput(){
        return currentPagesInOutput;
    }

    public void setCurrentPagesInOutput(int currentPagesInOutput){
        this.currentPagesInOutput = currentPagesInOutput;
    }

    public void setCurrentPagesToZero(){
        currentPagesInOutput = 0;
    }
}
