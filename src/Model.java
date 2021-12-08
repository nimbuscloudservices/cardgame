import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Model
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;
   
   static int computerWinningsCounter = 0;
   static int humanWinningsCounter = 0;
   
   private int numCardsPerHand = 7;
   private int numPlayers = 2 ;
   
   private int humanCP = 0;
   private int computerCP = 0;
   
   Hand[] winnings = new Hand[numPlayers];
   Hand[] threeCardStack = new Hand[3];
   
   public Model()
   {      
      GUICard.loadCardIcons();
      for (int k = 0; k < 2; k++)
      {
         winnings[k] = new Hand();
      }
      for (int k = 0; k < 3; k++)
      {
         threeCardStack[k] = new Hand();
      }
      
   }
   public Card firstCardInStack(Card card)
   {
      threeCardStack[0].takeCard(card);
      System.out.println(threeCardStack[0].inspectCard(threeCardStack[0].getNumCards()-1));
      
      return card;
   } 
   public Card secondCardInStack(Card card)
   {
      threeCardStack[1].takeCard(card);
      
      System.out.println(threeCardStack[1].inspectCard(threeCardStack[0].getNumCards()-1));
      return card;
   }
   public Card thirdCardInStack(Card card)
   {
      threeCardStack[2].takeCard(card);
      System.out.println(threeCardStack[2].inspectCard(threeCardStack[0].getNumCards()-1));
      return card;
   }
   public int getnumCardsPerHand()
   {
      return this.numCardsPerHand;
   }
   
   public int getnumPlayers()
   {
      return this.numPlayers;
   }
   
   public String playGame(Card card)
   {
      Card firstCard = threeCardStack[0].inspectCard(threeCardStack[0].getNumCards()-1);
      Card secondCard = threeCardStack[1].inspectCard(threeCardStack[0].getNumCards()-1);
      Card thirdCard = threeCardStack[2].inspectCard(threeCardStack[0].getNumCards()-1);
      
      System.out.println(card.getValue());

      if(firstCard.getValue() - card.getValue() == -1 || firstCard.getValue() - card.getValue() == 1)
      {
         return "first";
      }
      else if(secondCard.getValue() - card.getValue() == -1 || secondCard.getValue() - card.getValue() == 1)
      {
         return "second";
      }
      else if(thirdCard.getValue() - card.getValue() == -1 || thirdCard.getValue() - card.getValue() == 1)
      {
         return "third";
      }
      {
         return "Cannot play";
      }
         
   
         
   }
   public Icon getHumanCard(Card humanCard)
   {
      return GUICard.getIcon(humanCard);
   }
   public Icon getComputerCard(Card computerCard)
   {
      return GUICard.getIcon(computerCard);
   }
 
   public Icon initializeHumanCards(Card humanCard)
   {
      return GUICard.getIcon(humanCard);
   }
   public Icon initializeComputerCards()
   {
      return GUICard.getBackCardIcon();  
   }
   public Icon updateHand(Card card)
   {
      return GUICard.getIcon(card);
   }
   public int cannotPlayCounter(int player)
   {
      if(player == 0)
      {
         return computerCP++;
      }
      else
      {
         return humanCP++;
      }
   }
   public String endGame()
   {
      //Determine who collected the most cards
      if (humanCP < computerCP)
      {
         return "Human";
      }
      else
      {
         return "Computer";
      }
   }
}