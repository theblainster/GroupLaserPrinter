public class PaperAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int MAX_PAGE = 300; // Maximum number of pages in the tray
   private int paperTray;           // Current number of paper in the tray

   PaperAssembly() 
   {
      paperTray = MAX_PAGE;
   }
	
	
   PaperAssembly(int amountOfPaper)
   {
      paperTray = amountOfPaper;
   }
	
   @Override
   public void setValue(int setPaper)
   {
      paperTray = setPaper;
   }
	
   @Override
   public int getValue(){
      return paperTray;
   }	
	
   @Override
   public void activate(){
      activated = true;
   }
	
   @Override
   public void deactivate(){	
      activated = false;
   }
	
   public int usePaper(int paperUsed)
   {
      return paperTray = paperTray - paperUsed;
   }
}