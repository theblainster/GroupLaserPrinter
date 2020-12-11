public class FuserAssembly extends AssemblyUnit implements ISimAssembly
{
   static final int THICK_PAPER_TEMP   = 190; // The temperature for thick paper
   static final int NORMAL_PAPER_TEMP  = 175;  // The temperature for normal paper
   static final int DEFAULT_PAPER_TEMP = 0;   // The resting fuser temperature
   
   int currentTemp; // Number of sheets printed
   
   // Constructor that sets the temperature
   FuserAssembly()
   {
	   currentTemp = DEFAULT_PAPER_TEMP;
   }
   
   // Constructor that sets the temperature to a passed in value
   FuserAssembly(int changeTemp)
   {
      currentTemp = changeTemp;
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
   
   // 
   public int getDefaultTemperature(){
	   return DEFAULT_PAPER_TEMP;
   }
   
   public int getNormalTemperature(){
      return NORMAL_PAPER_TEMP;
   }
   
   public int getThickTemperature(){
	   return THICK_PAPER_TEMP;
   }
}