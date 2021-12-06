package controller;

import model.Card;
import model.CardGameOutline;
import model.GUICard;
import view.GamePlay;

/**
 * runner objet for the cardgame application
 * @author Blake, Layla, Saul, Yavik
 * @version 12-5-2021
 */
public class AppRunner {
   public static void main(String[] args)
   {
      int NUM_CARDS_PER_HAND = 7;
      int NUM_PLAYERS = 2;

      AppController baseApp = new AppController();
      baseApp.start();

      GUICard.loadCardIcons();

      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardGameOutline SuitMatchGame = new CardGameOutline(numPacksPerDeck,
              numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
              NUM_PLAYERS, NUM_CARDS_PER_HAND);

      SuitMatchGame.deal();
      GamePlay game = new GamePlay("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS,
              SuitMatchGame);

      game.setVisible(true);

   }
}
