public class TonerAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int MAX_PRINT = 850; // Maximum number of pages that can be printed
   static final int LOW_TONER = 50;  // The value we warn the consumer to replace his toner at
   static final int OUT_TONER = 15;  // The value where we stop printing
   
   private int sheetsRemaining; // Number of sheets able to be printed
   
   // Constructor that sets the toner to its max printable.
   TonerAssembly()
   {
	   sheetsRemaining = MAX_PRINT;
   }
   
   // Constructor that sets the toner level to a passed in value
   TonerAssembly(int amountOfToner)
   {
      sheetsRemaining = amountOfToner;
   }
   
   // Use the toner in the printer, check if its out of toner
   protected void useToner(int sheetsUsed) throws Exception {
	  if(sheetsRemaining > sheetsUsed)
	  {
         sheetsRemaining = sheetsRemaining - sheetsUsed;
	  }
	  else {
		 throw new Exception("Not enough toner.");
	  }

	  // Checks if toner is low
      if(tonerIsLow())
      {
          System.out.println("Your toner level is low");
      }
   }
   
   // Reload the toner to its maximum
   protected void tonerReload()
   {
	   sheetsRemaining = MAX_PRINT;
   }

   public boolean tonerIsLow(){
       return sheetsRemaining <= LOW_TONER;
   }

   // Sets the current amount of toner
   @Override
   public void setValue(int setToner)
   {
      sheetsRemaining = setToner;
   }
	
   // Gets the current amount of toner
   @Override
   public int getValue(){
      return sheetsRemaining;
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