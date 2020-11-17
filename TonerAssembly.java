public class TonerAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int MAX_PRINT = 850; // Maximum number of pages that can be printed
   static final int LOW_TONER = 50;  // The value we warn the consumer to replace his toner at
   static final int OUT_TONER = 15;  // The value where we stsop printing
   
   int sheetsPrinted; // Number of sheets printed
   
   
   
   // Use the toner in the printer, check if its out of toner
   protected int usePaper(int sheetsUsed)
   {
	  warning(0);
	  if(sheetsPrinted > sheetsUsed)
	  {
         return sheetsPrinted = sheetsPrinted - sheetsUsed;
	  }
	  else{
		 warning(1);
	     return sheetsPrinted = OUT_TONER;
	  }
   }
   
   // Reload the toner to its maximum
   protected void tonerReload()
   {
	   sheetsPrinted = MAX_PRINT;
   }
   
   // Gets passed an error number and displays the error which occurred
   protected void warning(int errorNumber)
   {
	  if(errorNumber == 1)
	  {
        System.out.println("Out of toner");
	  }		  

      else if(sheetsPrinted <= LOW_TONER)
	  {
	    System.out.println("Your toner level is low");
	  }
	  
   }
   
   // Sets the current amount of toner
   @Override
   public void setValue(int setToner)
   {
      sheetsPrinted = setToner;
   }
	
   // Gets the current amount of toner
   @Override
   public int getValue(){
      return sheetsPrinted;
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
}