/**
 * Module 3 Deck of Cards
 *
 * @author
 * @version 11-13-2021
 */

public class DeckOfCards
{
   public static void main(String[] args)
   {
      //Phase 1 Test
      Card cardOne = new Card('9', Card.Suit.Clubs);
      //illegal card
      Card cardTwo = new Card('p', Card.Suit.Hearts);
      Card cardThree = new Card('4', Card.Suit.Spades);
      System.out.println(cardOne.toString());
      System.out.println(cardTwo.toString());
      System.out.println(cardThree.toString());
      //Good gone bad card
      cardOne.set('U', Card.Suit.Clubs);
      //illegal card turned good
      cardTwo.set('K', Card.Suit.Hearts);
      System.out.println(cardOne.toString());
      System.out.println(cardTwo.toString());
      System.out.println(cardThree.toString());
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
      Clubs, Diamonds, Hearts, Spades
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
      this.value = value;
      this.suit = suit;
   }

   /**
    * constructor with default values
    */
   public Card()
   {
      this.value = 'A';
      this.suit = Suit.Spades;
   }

   /**
    * a copy constructor that will create a duplicate of the original
    * which is not a reference
    *
    * @param origCard original card object
    */
   public Card(Card origCard)
   {
      this.value = origCard.getValue();
      this.suit = origCard.getSuit();
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
   public boolean geterrorFlag()
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
         if (this.errorFlag == card.errorFlag)
         {
            return true;
         }
         else
         {
            return false;
         }
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

