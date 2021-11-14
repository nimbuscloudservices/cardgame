/**
 * Module 3 Deck of Cards
 *
 * @author Blake, Layla, Saul, Yavik
 * @version 11-13-2021
 */

public class DeckOfCards
{
   public static void main(String[] args)
   {
      //Phase 1 Test
      Card cardOne = new Card('T', Card.Suit.clubs);
      //illegal card
      Card cardTwo = new Card('d', Card.Suit.hearts);
      Card cardThree = new Card('4', Card.Suit.diamonds);
      Card cardFour = new Card();
      Card cardFive = new Card(cardThree);

      System.out.println(cardOne);
      System.out.println(cardTwo);
      System.out.println(cardThree);
      System.out.println(cardFour);
      System.out.println(cardFive);

      //Good gone bad card
      cardOne.set('U', Card.Suit.clubs);
      //illegal card turned good
      cardTwo.set('K', Card.Suit.hearts);
      System.out.println(cardOne);
      System.out.println(cardTwo);
      System.out.println(cardThree);
      System.out.println(cardFour);
      System.out.println(cardFive);

      //Phase 2 Test
      Hand hand = new Hand();
      hand.takeCard(cardTwo);
      hand.takeCard(cardThree);
      System.out.println(hand);
      hand.resetHand();
      System.out.println(hand);
   }
}

/**
 * A class to model playing cards
 */
class Card
{
   // enum of suits of cards
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }
   //Member Data
   private char value;
   private Suit suit;
   private boolean errorFlag;

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
    * a copy constructor that will create a duplicate of the original
    * which is not a reference
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
    * gets the suit of this card
    *
    * @return suit of this card
    */
   public Suit getSuit()
   {
      return this.suit;
   }

   /**
    * gets the value of this card
    *
    * @return value of this card
    */
   public char getValue()
   {
      return this.value;
   }

   /**
    * gets the state of the errorFlag
    *
    * @return state of errorFlag
    */
   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   /**
    * compares this card to another card and checks if cards are identical
    *
    * @param card that is being compared to.
    * @return returns true if all the fields are identical to parameter
    * object card, or it returns false if they are not identical.
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
}

/**
 * A class to model a hand of playing cards
 */
class Hand
{
   public final int MAX_CARDS = 50;
   Card[] myCards;
   int numCards;

   /**
    * Constructor for hand object
    */
   public Hand()
   {
      resetHand();
   }

   /**
    * Clears hand of all card objects
    */
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * Adds a card to next available position in myCards array
    *
    * @param card that is being added to the array
    * @return true if successfully added, false is not
    */
   public boolean takeCard(Card card)
   {
      if (numCards < MAX_CARDS) {
         myCards[numCards] = new Card(card);
         numCards++;
         return true;
      } else {
         return false;
      }
   }

   /**
    * returns and remove the card in the top occupied position of the array
    *
    * @return card that is being removed from top position in the array
    */
   public Card playCard()
   {
      return null;
   }

   /**
    * a stringizer that the client can use to display the entire hand
    *
    * @return the entire hand array as a string
    */
   public String toString()
   {
      String handString = "";
      for (int i = 0; i < numCards; i++) {
         handString = handString.concat(myCards[i].toString());
      }
      return handString;
   }

   /**
    * gets value of num of cards in hand
    *
    * @return number of cards in hand array
    */
   public int getNumCards()
   {
      return this.numCards;
   }

   /**
    * Accessor for an individual card
    *
    * @param k index of a card
    * @return a card with errorFlag = true if k is bad
    */
   public Card inspectCard(int k)
   {
      return null;
   }

}
class Deck
{
   public static final int MAX_CARDS = 312;
   private static Card[] masterPack;
   private Card[] cards;
   int topCard;

   /**
    * constructor for Deck
    * @param numPacks number of packs of playing cards
    */
   public Deck (int numPacks)
   {

   }

   /**
    * Constructor with default of one pack of playing cards
    */
   public Deck()
   {

   }

   /**
    * repopulates cards[] with the standard 52 x numPacks
    * @param numPacks number of playing card packs used
    */
   public void init(int numPacks)
   {

   }

   /**
    * Mixes up the cards with the help of the standard random number generator
    */
   public void shuffle()
   {

   }

   /**
    * returns and removes the card in the top occupied position of cards[]
    * @return card being dealt
    */
   public Card dealCard()
   {
      return null;
   }

   /**
    * gets value of topCard field
    * @return value of topCard field
    */
   public int getTopCard()
   {
      return this.topCard;
   }

   /**
    * Accessor for an individual card.
    * Returns a card with errorFlag = true if k is bad
    * @param k index of card being checked
    * @return returns an object copy of card with errorFlag = true
    */
   public Card inspectCard(int k)
   {
      return null;
   }

   /**
    * Used for initializing masterPack, can not be run more that once.
    */
   private static void allocateMasterPack()
   {

   }


}
