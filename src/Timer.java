
public class Timer extends Thread
{
   //run() contains all of the needed timer code. 
   @Override
   public void run()
   {
      
      try {
         Thread.sleep(1000);
      }
      catch(InterruptedException e){
      }

   }
}
