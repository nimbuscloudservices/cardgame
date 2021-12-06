package model;

import java.util.Random;

/**
 * A class to model a deck of cards
 */
public class Deck {
   public static final int PACK = 52;
   public static final int MAX_NUMBER_PACK = 6;
   public static final int MAX_CARDS = PACK * MAX_NUMBER_PACK;
   static boolean allocated = false;
   private static Card[] masterPack;
   int topCard;
   private int numPacks;
   private Card[] cards;

   /**
    * constructor for Deck
    *
    * @param numPacks number of packs of playing cards
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      cards = new Card[numPacks * PACK];
      init(numPacks);

   }

   /**
    * Constructor with default of one pack of playing cards
    */
   public Deck()
   {
      this(1);
   }

   /**
    * Used for initializing masterPack, can not be run more that once.
    */
   private static void allocateMasterPack()
   {
      int i = 0;
      char[] cardValues = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
              'J', 'Q', 'K'};
      if (!allocated)
      {
         allocated = true;
         masterPack = new Card[PACK];
         while (i < PACK)
         {
            for (int j = 0; j < Card.Suit.values().length; j++)
            {
               for (int k = 0; k < cardValues.length; k++)
               {
                  masterPack[i] = new Card(cardValues[k],
                          Card.Suit.values()[j]);
                  i++;
               }
            }
         }
      }
   }

   /**
    * repopulates cards[] with the standard 52 x numPacks
    *
    * @param numPacks number of playing card packs used
    */
   public void init(int numPacks)
   {
      if(numPacks > 0 && numPacks <=6)
      {
         this.numPacks = numPacks;
         cards = new Card[numPacks * PACK];
         topCard = numPacks * PACK;
         for (int packNum = 0; packNum < numPacks; packNum++)
         {
            for (int card = 0; card < PACK; card++)
            {
               cards[packNum * PACK + card] = new Card(masterPack[card]);
            }
         }
      }
   }

   /**
    * Mixes up the cards with the help of the standard random number generator
    */
   public void shuffle()
   {
      Random shuffle = new Random();

      for (int i = 0; i < cards.length; i++)
      {
         int randomCard = shuffle.nextInt(PACK * numPacks);
         Card assignCard = cards[randomCard];
         cards[randomCard] = cards[i];
         cards[i] = assignCard;
      }
   }

   /**
    * returns and removes the card in the top occupied position of cards[]
    *
    * @return card being dealt
    */
   public Card dealCard()
   {
      Card dealCard;
      if (topCard > 0)
      {
         dealCard = cards[getTopCard() - 1];
         cards[getTopCard() - 1] = null;

         topCard--;
         return new Card(dealCard);
      }
      return new Card(' ', Card.Suit.spades);
   }

   /**
    * gets value of topCard field
    *
    * @return value of topCard field
    */
   public int getTopCard()
   {
      return this.topCard;
   }

   /**
    * Accessor for an individual card. Returns a card with errorFlag = true if k
    * is bad
    *
    * @param k index of card being checked
    * @return returns an object copy of card with errorFlag = true
    */
   public Card inspectCard(int k)
   {
      if (k >= topCard && k >= 0)
      {
         return new Card(' ', Card.Suit.spades);
      }
      return cards[k];
   }

   /**
    * Adds the card to the top of the deck based on the number of packs
    * @param card
    * @return true if the card can be successfully added
    */
   public boolean addCard(Card card)
   {
      if (cards.length == topCard) {
         return false;
      }

      for (int i = 0; i < topCard; i++) {
         if (cards[i].equals((card))) {
            return false;
         }
      }

      for (int i = 0; i < numPacks; i++) {
         cards[topCard] = card;
         topCard++;
      }
      return true;
   }

   /**
    * Remove all the instances of a specific card from the deck
    * @param card
    * @return true if the card is successfully removed
    */
   public boolean removeCard(Card card)
   {
      int i = 0;

      while (i < topCard)
      {
         if (cards[i].equals(card))
         {
            cards[i] = cards[topCard - 1];
            cards[topCard - 1] = null;
            topCard--;
            return false;
         }
         else
         {
            i++;
         }
      }
      return false;
   }

   /**
    *
    * @return the number of cards remaining in the deck
    */
   public int getNumCards()
   {
      return topCard;
   }
}
