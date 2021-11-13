
//CST338 - Module 3 Deck of Cards
//11/13/2021

public class deckOfCards
{
   public static void main(String[] args)
   {

   }
}

class Card
{
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }
   //Member Data
   private char value;
   private Suit suit;
   private boolean errorFlag;


   //stringizer
   public String toString()
   {

      if (errorFlag) return "Invalid";
      return value + " of " + suit;
   }

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

   //Accessor getSuit returns suit.
   public Suit getSuit()
   {
      return this.suit;
   }

   //Accessor getValue returns value.
   public char getValue()
   {
      return this.value;
   }

   //Accessor geterrorFlag returns a boolean value stored in errorFlag.
   public boolean geterrorFlag()
   {
      return this.errorFlag;
   }

   //equals returns true if all the fields are identical to parameter object card, or it returns
   //false if they are not identical.
   public boolean equals(Card card)
   {
      if(this.suit == card.suit & this.value == card.value)
      {
         if(this.errorFlag == card.errorFlag)
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
}
