package model;

/**
 * A class to model playing cards
 */
public class Card {
   //Member Data
   private char value;
   private Suit suit;
   private boolean errorFlag;
   public static char[] valueRanks = {'A', '2', '3', '4', '5', '6', '7',
           '8', '9', 'T', 'J', 'Q', 'K'};
   public static Suit[] suitRanks = {Suit.clubs, Suit.diamonds, Suit.hearts,
           Suit.spades};

   /**
    * constructor for Card object
    *
    * @param value of the card
    * @param suit  of the card
    */
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   /**
    * constructor with default values
    */
   public Card()
   {
      set('A', Suit.spades);
   }

   /**
    * a copy constructor that will create a duplicate of the original which is
    * not a reference
    *
    * @param origCard original card object
    */
   public Card(Card origCard)
   {
      set(origCard.getValue(), origCard.getSuit());
      errorFlag = origCard.getErrorFlag();
   }

   /**
    * changes value and suit to user specified values
    *
    * @param value of this card
    * @param suit  of this card
    * @return true if successfully changes without error, false if error
    */
   public boolean set(char value, Suit suit)
   {

      if (isValid(value, suit))
      {
         this.value = value;
         errorFlag = false;
         this.suit = suit;
         return true;
      }
      else
      {
         errorFlag = true;
         return false;
      }
   }

   /**
    * helper method that determines if passed values are valid
    *
    * @param value of card
    * @param suit  of card
    * @return true if passed values are valid, false if invalid
    */
   private boolean isValid(char value, Suit suit)
   {
      boolean validValue = false;
      boolean validSuit = false;
      char[] cardValues = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
              'J', 'Q', 'K' };
      for (char i : cardValues)
      {
         if (i == value)
         {
            validValue = true;
            break;
         }
      }
      for (Suit value1 : Suit.values())
      {
         if (value1 == suit)
         {
            validSuit = true;
            break;
         }

      }
      return validValue && validSuit;

   }

   /**
    * gets this card's suit
    *
    * @return suit of this card
    */
   public Suit getSuit()
   {
      return this.suit;
   }

   /**
    * gets value of this card
    *
    * @return value of this card
    */
   public char getValue()
   {
      return this.value;
   }

   /**
    * gets errorFlag value
    *
    * @return value of errorFlag of this card
    */
   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   /**
    * Compares this card object to a user specified card object to confirm if
    * they are equal
    *
    * @param card that is being compared to this card
    * @return true if cards are the same, else false if it does not match
    */
   public boolean equals(Card card)
   {
      if (this.suit == card.suit & this.value == card.value)
      {
         return this.errorFlag == card.errorFlag;
      }
      else
      {
         return false;
      }
   }

   /**
    * returns a string stating the full card name
    *
    * @return "Invalid" if errorFlag is true or full card name
    */
   public String toString()
   {
      if (errorFlag)
         return "Invalid";
      return value + " of " + suit;
   }

   // enum of suits of cards
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }

   /**
    * Puts the order of the card values in here with the smallest first
    * @param card
    * @return card value
    */
   static int cardValue(Card card)
   {
      for (int i = 0; i < valueRanks.length; i++)
      {
         if (card.getValue() == valueRanks[i])
         {
            return i;
         }
      }
      return - 1;
   }

   /**
    * Sorts the incoming array of cards using a bubble sort routine
    * @param cardArray
    * @param arraySize
    */
   public static void arraySort(Card[] cardArray, int arraySize)
   {
      Card temp;

      for(int card = 0; card < arraySize; card++)
      {
         for(int nextCard = 1; nextCard < (arraySize - card); nextCard++)
         {
            int prevCard = cardValue(cardArray[nextCard - 1]);
            int curCard = cardValue(cardArray[nextCard]);

            if(prevCard  < curCard)
            {
               temp = cardArray[nextCard -1];
               cardArray[nextCard - 1] = cardArray[nextCard];
               cardArray[arraySize] = temp;
            }
         }
      }
   }
}
