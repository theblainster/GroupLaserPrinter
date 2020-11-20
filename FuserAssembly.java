public class FuserAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int MAX_TEMP     = 200;  // Maximum temperature the fuser can be
   static final int MIN_TEMP     =  75;  // The minimum temperature the fuser can be
   static final int MAX_INCREASE =   3;  // The maximum temperature the fuser can increase per second
   
   int currentTemp; // Number of sheets printed
   
   // Constructor that sets the temperature
   FuserAssembly()
   {
	   currentTemp = MIN_TEMP;
   }
   
   // Constructor that sets the temperature to a passed in value
   FuserAssembly(int changeTemp)
   {
      currentTemp = changeTemp;
   }
   
   // Get the amount of time it takes to heat up the toner
   protected void heatUp(int increaseTemp)
   {
	  int heatTime; //The amount of time it takes for the fuser to heat up
	  
	  if(currentTemp + increaseTemp < MAX_TEMP)
	  {
		  while(increaseTemp != 0)
		  {
			  currentTemp += MAX_INCREASE;
			  System.out.println("Temperature" + currentTemp);
			  increaseTemp--;
		  }
	  }
   }
   
   
   // Sets the current amount of toner
   @Override
   public void setValue(int setTemp)
   {
      currentTemp = setTemp;
   }
	
   // Gets the current amount of toner
   @Override
   public int getValue(){
      return currentTemp;
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