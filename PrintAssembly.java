public class PrintAssembly extends AssemblyUnit implements ISimAssembly
{
   // Constants
   final static int DRUM_MAX_LIFE         = 100;
   final static int DRUM_WARN_LIFE        = DRUM_MAX_LIFE / 10;
   final static int MAX_ROTATION_SPEED    = 300;
   final static int MIRROR_SPIN_DOWN_RATE = 2;
   final static int MIRROR_SPIN_UP_RATE   = 2;

   // Variables
   static boolean lampStatus       = false;
   static boolean mirrorSpinning   = false;
   static boolean wireChargeStatus = false;
   static int     mirrorRotSpeed   = 0;
   static int     sheetsPrinted    = 0;
   

   // Toggle spinning the mirror 
   public void toggleMirrorSpin() 
   {
      if(mirrorSpinning) 
      {
         System.out.println("Mirror spinning down.");
         decreaseMirrorSpin();
         mirrorSpinning = false;
         System.out.println("\nMirror no longer spinning.");
      } 

      else 
      {
         System.out.println("Mirror spinning up.");
         increaseMirrorSpin();
         mirrorSpinning = true;
         System.out.println("\nMirror up to speed.");
         
      }
   }
   
   // Checks if the mirror is spinning 
   public boolean isMirrorSpinning()
   {
      return mirrorSpinning;
   }
   
   // Increases the speed of the spinning mirror
   public void increaseMirrorSpin() 
   {
      while(mirrorRotSpeed < MAX_ROTATION_SPEED) 
      {
         System.out.print("+");
         mirrorRotSpeed += MIRROR_SPIN_UP_RATE;
      }
   }
   
   // Decreases the speed of the spinning mirror
   public void decreaseMirrorSpin() 
   {
      while(mirrorRotSpeed > 0) 
      {
         System.out.print("-");
         mirrorRotSpeed -= MIRROR_SPIN_DOWN_RATE;
      }
      
      if(mirrorRotSpeed < 0) 
      {
         mirrorRotSpeed = 0;
      }
   }
   
   // Toggle wire charge
   public void toggleWireCharge() 
   {
      wireChargeStatus = !wireChargeStatus;
   }
   
   // Gets the status of the wire
   public boolean isWireCharged() 
   {
      return wireChargeStatus;
   }
   
   // Toggle the discharge lamp 
   public void toggleDischargeLamp() 
   {
      lampStatus = !lampStatus;
   }
   
   // Gets the status of the discharge lamp
   public boolean isDischargeLampPowered() 
   {
      return lampStatus;
   }
   
   // Prints pages and displays drum life warnings
   public void usePages(int number) 
   {
      if((sheetsPrinted + number) < DRUM_MAX_LIFE ) 
      {
         sheetsPrinted += number;
         
         if((DRUM_MAX_LIFE - sheetsPrinted) < DRUM_WARN_LIFE) 
	     {
            System.out.println("WARNING: Drum life below warning");
         }
         
         System.out.println("SUCCESSFUL: printed " + number + " sheets.");
         System.out.println("Total printed sheets: " + sheetsPrinted);
      } 

      else 
         {
          System.out.println("ERROR: Could not print, drum life too low.");
         }
     
      System.out.println("Drum life remaining: " + (DRUM_MAX_LIFE - sheetsPrinted));
   }
   
   // Drum replacement 
   public void replaceDrum() 
   {
      sheetsPrinted = 0;
   }
   
   // Powers On/Off of all parts of the print assembly
   public void powerCycle() 
   {
      toggleMirrorSpin();
      toggleWireCharge();
      toggleDischargeLamp();
   }

   @Override
   public void setValue(int newValue) {}

   @Override
   public int getValue() {return DRUM_MAX_LIFE - sheetsPrinted;}

   @Override
   public void activate() {activated = true;}

   @Override
   public void deactivate() {activated = false;}
}
