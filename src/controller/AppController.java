package controller;
import view.GamePlay;

/**
 *
 */
public class AppController {

   private GamePlay appFrame;
   public void start()
   {
      appFrame = new GamePlay(this);
   }
}
