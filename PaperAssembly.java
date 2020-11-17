public class PaperAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int MAX_PAGE  = 300; // Maximum number of pages in the tray
   static final int LOW_PAPER = 25;  // Lowest our paper count should be
   private int      paperTray;       // Current number of paper in the tray



   // Constructor that sets the number of pages in the paper tray to 300
   PaperAssembly() 
   {
      paperTray = MAX_PAGE;
   }
	
   // Constructor that sets the number of pages in the paper tray to a passed in value
   PaperAssembly(int amountOfPaper)
   {
      paperTray = amountOfPaper;
   }
	
   // Sets the current number of paper in the paper tray
   @Override
   public void setValue(int setPaper)
   {
      paperTray = setPaper;
   }
	
   // Gets the current number of paper in the paper tray
   @Override
   public int getValue(){
      return paperTray;
   }	
	
   //Turns on the printer
   @Override
   public void activate(){
      activated = true;
   }
	
   // Turns off the printer
   @Override
   public void deactivate(){	
      activated = false;
   }
	
   // Use the paper in the printer, check if its out of paper
   protected int usePaper(int paperUsed)
   {
	  paperLevel(paperTray);
	  if(paperTray > paperUsed)
	  {
         return paperTray = paperTray - paperUsed;
	  }
	  else
	     System.out.println("Out of paper");
	  return 0;
	  
   }
   
   // Fill the paper in the printer, Check if it's overflowing
   protected int fillPaper(int inputPaper)
   {
	  if(paperTray + inputPaper < MAX_PAGE)
	  {
         return paperTray = paperTray + inputPaper;
	  }
	  else
	  {
	     int remainder = paperTray + inputPaper - MAX_PAGE;
	     System.out.println("Paper Tray is full, you have " +  remainder + " left over");
	     return MAX_PAGE;
      }
   }
   
   // Prints an error if the paper Tray is ever to low
   protected void paperLevel(int totalPaper)
   {
      if(paperTray <= LOW_PAPER)
	  {
	     System.out.println("Your paper tray is low");
	  }
   }
}