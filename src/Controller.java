import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class Controller implements ActionListener
{
   private Model theModel;
   private CardGameOutline SuitMatchGame;
   private View  theView;
   private int numPacksPerDeck = 1;
   private int numJokersPerPack = 2;
   private int numUnusedCardsPerPack = 0;
   private Card[] unusedCardsPerPack = null;

   public Controller(Model theModel, View theView)
   {
      
       SuitMatchGame = new CardGameOutline(numPacksPerDeck,
            numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
            2, 7);

      SuitMatchGame.deal();
      this.theModel = theModel;
      this.theView = theView;
      
      initialize();
   }
   public void initialize()
   {
      theView.initializeButtons(this);
      theView.setIconButtons(theView.button1, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(0)));
      theView.setIconButtons(theView.button2, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(1)));
      theView.setIconButtons(theView.button3, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(2)));
      theView.setIconButtons(theView.button4, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(3)));
      theView.setIconButtons(theView.button5, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(4)));
      theView.setIconButtons(theView.button6, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(5)));
      theView.setIconButtons(theView.button7, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(6)));
      theView.initializeComputerCard(theModel.initializeComputerCards());
   }
   public void checkDeck(JButton button, int index)
   {
      if(SuitMatchGame.takeCard(1))
      {
         if(SuitMatchGame.takeCard(1))
         {
            theView.setIconButtons(button, theModel.updateHand(SuitMatchGame.getCardFromDeck()));
         }
      }
      else
      {
         theView.removeButton(button);
      }
      
      if(SuitMatchGame.takeCard(0))
      {
         theView.setComputerHand(theModel.updateHand(SuitMatchGame.getCardFromDeck()), index);
      }
      else
      {
         theView.removeComputerCard(index);
      }
   }
   @Override
   public void actionPerformed(ActionEvent e)
   {
      String actionCommand = e.getActionCommand();

      Random ran = new Random();

      int i = ran.nextInt(theView.computerHand.getComponentCount());

      if (actionCommand.equals("1"))
      {
         
         theView.setPlayArea(theView.button1, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(0),
               SuitMatchGame.getHand(0).inspectCard(i)));   
         checkDeck(theView.button1, i);
      }

      else if (actionCommand.equals("2"))
      {
         theView.setPlayArea(theView.button2, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(1),
               SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button2, i);
      }
      else if (actionCommand.equals("3"))
      {
         theView.setPlayArea(theView.button3, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(2),
               SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button3, i);
      }
      else if (actionCommand.equals("4"))
      {
         theView.setPlayArea(theView.button4, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(3),
               SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button4, i);
      }
      else if (actionCommand.equals("5"))
      {
         theView.setPlayArea(theView.button5, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(4),
               SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button5, i);
      }
      else if (actionCommand.equals("6"))
      {
         theView.setPlayArea(theView.button6, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(5),
               SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button6, i);
      }
      else if (actionCommand.equals("7"))
      {
         theView.setPlayArea(theView.button7, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
         
         theView.displayRoundWinner(theModel.playGame(SuitMatchGame.getHand(1).inspectCard(6),
               SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button7, i);
      }
      
      if(theView.emptyComputerHands()== true)
      {
         theView.displayWinner(theModel.endGame());
      }
      else if(theView.emptyHumanHands() == true)
      {
         theView.displayWinner(theModel.endGame());
      }
      
   }

}
