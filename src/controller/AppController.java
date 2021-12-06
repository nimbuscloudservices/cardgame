package controller;

import view.CardTable;

/**
 *
 */
public class AppController {

   private CardTable appFrame;
   public void start()
   {
      appFrame = new CardTable(this);
   }
}
