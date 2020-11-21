public class PrintAssembly extends AssemblyUnit implements ISimAssembly
{
   // Constants
   final static int DRUM_MAX_LIFE         = 10000;
   final static int DRUM_WARN_LIFE        = 150;
   final static int MAX_ROTATION_SPEED    = 300;
   final static int MIRROR_SPIN_DOWN_RATE = 2;
   final static int MIRROR_SPIN_UP_RATE   = 2;

   // Variables
   private static boolean lampStatus;
   private static boolean mirrorSpinning;
   private static boolean wireChargeStatus;
   private static int     mirrorRotSpeed;
   private static int     sheetsPrinted;
   
   public PrintAssembly() {
      lampStatus       = false;
      mirrorSpinning   = false;
      wireChargeStatus = false;
      mirrorRotSpeed   = 0;
      sheetsPrinted    = 0;
   }

   // Stops the mirror spinning
   public void mirrorSpinDown()
   {
      if (mirrorSpinning)
      {
         decreaseMirrorSpin();
         mirrorSpinning = false;
      }
   }

   // Starts the mirror spinning
   public void mirrorSpinUp()
   {
      if (!mirrorSpinning)
      {
         increaseMirrorSpin();
         mirrorSpinning = true;
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
         mirrorRotSpeed += MIRROR_SPIN_UP_RATE;
      }
   }
   
   // Decreases the speed of the spinning mirror
   public void decreaseMirrorSpin() 
   {
      while(mirrorRotSpeed > 0) 
      {
         mirrorRotSpeed -= MIRROR_SPIN_DOWN_RATE;
      }
      
      if(mirrorRotSpeed < 0) 
      {
         mirrorRotSpeed = 0;
      }
   }
   
   // Turns wire charge on
   public void wireChargeOn()
   {
      wireChargeStatus = true;
   }

   // Turns wire charge off
   public void wireChargeOff()
   {
      wireChargeStatus = false;
   }
   
   // Gets the status of the wire
   public boolean isWireCharged() 
   {
      return wireChargeStatus;
   }
   
   // Turns discharge lamp on
   public void dischargeLampOn()
   {
      lampStatus = true;
   }

   // Turns discharge lamp of
   public void dischargeLampOff()
   {
      lampStatus = false;
   }
   
   // Gets the status of the discharge lamp
   public boolean isDischargeLampPowered() 
   {
      return lampStatus;
   }
   
   // Prints pages and displays drum life warnings
   public void usePages(int number) throws Exception {
      if((sheetsPrinted + number) < DRUM_MAX_LIFE ) 
      {
         sheetsPrinted += number;
         
         if((DRUM_MAX_LIFE - sheetsPrinted) < DRUM_WARN_LIFE) 
	     {
            System.out.println("WARNING: Drum life below warning");
         }
      } 

      else 
      {
          throw new Exception("Could not print, drum life too low.");
      }
   }
   
   // Drum replacement 
   public void replaceDrum() 
   {
      sheetsPrinted = 0;
   }
   
   // Powers on all parts of the print assembly
   public void powerOn()
   {
      mirrorSpinUp();
      wireChargeOn();
      dischargeLampOn();
   }

   // Powers off all parts of the print assembly
   public void powerOff()
   {
      mirrorSpinDown();
      wireChargeOff();
      dischargeLampOff();
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
