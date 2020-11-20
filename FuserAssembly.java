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
   
   // Heat the fuser up
   protected void heatUp()
   {
	  while(currentTemp < MAX_TEMP)
	  {
		  currentTemp += MAX_INCREASE;
		  if(currentTemp > MAX_TEMP)
		  {
			  currentTemp = MAX_TEMP;
		  }
	  }
   }
   
   // Cool the fuser down
   protected void coolDown()
   {
      currentTemp = MIN_TEMP;
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