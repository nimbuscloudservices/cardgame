import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class to model playing cards
 */
public class Card {
   private char value;

   ;
   private Suit suit;
   private boolean errorFlag;
   /**
    * constructor for Card object
    *
    * @param value of the card
    * @param suit  of the card
    */
   public Card(char value, Suit suit) {
      this.value = value;
      this.suit = suit;
   }

   /**
    * constructor with default values
    */
   public Card() {
      this.value = 'A';
      this.suit = Suit.Spades;
   }

   /**
    * a copy constructor that will create a duplicate of the original
    * which is not a reference
    *
    * @param origCard original card object
    */
   public Card(Card origCard) {
      this.value = origCard.getValue();
      this.suit = origCard.getSuit();
   }

   public boolean set(char value, Suit suit) {

      if(isValid(value, suit))
      {

      }
   }

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
    * gets value of this card
    *
    * @return value of this card
    */
   public char getValue() {
      return value;
   }

   public Suit getSuit() {
      return suit;
   }


   public enum Suit {Clubs, Diamonds, Hearts, Spades}


}
