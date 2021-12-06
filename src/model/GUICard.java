package model;

import javax.swing.*;

public class GUICard {
   // 14 = A thru K + joker
   private static Icon[][] iconCards = new ImageIcon[14][4];
   static final int NUM_CARD_IMAGES = 57;
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   /**
    * creates a string for the file name of each card
    *
    * @return Icons in a 2-D array
    */
   public static void loadCardIcons()
   {
      String dirPrefix = "images/";
      String ext = ".gif";
      for (int val = 0; val < 14; val++)
      {
         for (int suit = 0; suit < 4; suit++)
         {
            String cardVal = intToCardValue(val);
            String cardSuit = intToCardSuit(suit);
            String fileName = dirPrefix + cardVal + cardSuit + ext;
            iconCards[val][suit] = new ImageIcon(fileName);

         }
      }
      iconBack = new ImageIcon("images/BK.gif");
      iconsLoaded = true;
   }

   /**
    * helper method that returns corresponding card value
    *
    * @param k index of card value in array
    * @return card value
    */
   static String intToCardValue(int k)
   {
      char[] cardVal = { 'A', '2', '3', '4', '5', '6', '7', '8',
              '9', 'T', 'J', 'Q', 'K'};

      if (k < 0 || k > cardVal.length - 1)
      {
         return "invalid value";
      }
      else
      {
         return Character.toString(cardVal[k]);
      }

   }

   /**
    * helper method that returns corresponding card suit
    *
    * @param k index of suit in array
    * @return suit value
    */
   static String intToCardSuit(int k)
   {
      char[] suitVal = { 'C', 'D', 'H', 'S' };

      if (k < 0 || k > suitVal.length)
      {
         return "invalid value";
      }
      else
      {
         return Character.toString(suitVal[k]);
      }
   }

   /**
    * helper method to convert value to int
    *
    * @param card that is being used
    * @return value of this card as int
    */
   static int valueAsInt(Card card)
   {
      char cardsValue = card.getValue();

      if(cardsValue == 'A')
         return 0;
      if(cardsValue == 'X')
         return 13;
      for(int k = 0; k <= 11; k++)
      {
         if(Card.valueRanks[k] == cardsValue)
            return k + 1;
      }
      return 0;
   }
   /**
    * helper method to convert suit to int
    *
    * @param card that is being used
    * @return suit of this card as int
    */
   public static int suitAsInt(Card card)
   {
      Card.Suit suit = card.getSuit();
      return switch (suit)
              {
                 case clubs -> 0;
                 case diamonds -> 1;
                 case hearts -> 2;
                 case spades -> 3;
              };
   }

   /**
    * gets a 2D array of card value and suit as an int
    *
    * @return value and suit
    */
   public static Icon getIcon(Card card)
   {
      if(!iconsLoaded)
      {
         loadCardIcons();
      }
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   /**
    * @return back of card Icon
    */
   public static Icon getBackCardIcon()
   {
      return iconBack;
   }
}
