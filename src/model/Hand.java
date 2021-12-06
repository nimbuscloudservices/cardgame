package model;

/**
 * A class to model a hand of playing cards
 */
public class Hand {
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
      if (numCards < MAX_CARDS)
      {
         myCards[numCards] = new Card(card);
         numCards++;
         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * returns and removes the card in the top occupied position of the array.
    *
    * @return card at the top of the myCards array
    */
   public Card playCard(int cardIndex)
   {

      if (numCards == 0) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.spades);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];

      numCards--;
      for (int i = cardIndex; i < numCards; i++) {
         myCards[i] = myCards[i + 1];
      }

      myCards[numCards] = null;

      return card;
   }
   /*
    * playCard plays card on top of the deck
    * and returns that card to the caller
    * */
   public Card playCard()
   {

      if(numCards == 0)
         return null;

      Card card = new Card(myCards[numCards -1].getValue(),
              myCards[numCards -1].getSuit());
      myCards[numCards -1] = null;
      numCards--;

      return card;

   }

   /**
    * converts myCards array to string form
    *
    * @return myCards array as a string
    */
   public String toString()
   {
      String displayHand = "";

      displayHand += myCards[0];

      for (int i = 1; i < numCards; i++)
      {
         displayHand += " , ";
         displayHand += myCards[i];
      }
      return displayHand;
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
      if (k < numCards && k >= 0)
      {
         return new Card(myCards[k]);
      }

      return new Card(' ', Card.Suit.spades);
   }

   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }
}
