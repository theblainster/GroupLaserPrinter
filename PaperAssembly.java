public class PaperAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int MAX_PAGE    = 300; // Maximum number of pages in the tray
   static final int INPUT_PAPER = 50;  // The amount of paper the user will add to the tray
   static final int LOW_PAPER   = 25;  // Lowest our paper count should be
   private int      paperTray;         // Current number of paper in the tray



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
   protected void usePaper(int PaperToUse) throws Exception {
	  if(paperTray > PaperToUse)
	  {
         paperTray = paperTray - PaperToUse;
	  }
	  else {
          throw new Exception("Not enough paper in the paper tray.");
      }
   }
   
   // Fill the paper in the printer, Check if it's overflowing
   protected void fillPaper()
   {
	  if(paperTray + INPUT_PAPER < MAX_PAGE)
	  {
         paperTray += INPUT_PAPER;
	  }
	  else
	  {
	     paperTray = MAX_PAGE;
      }
   }
}