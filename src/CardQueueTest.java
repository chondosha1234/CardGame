// Some examples of class usage for Assignment 1.
// Note:  Parts of this program (identified below) will not work until you
// have completed the SQueue<T> implementation.  To see the rest of it execute
// simply comment out that section.


public class CardQueueTest {
	public static void compareCards(Card x, Card y)	{
		int result = x.compareTo(y);
		if (result > 0)
			System.out.println(x + " beats " + y);
		else if (result < 0)
			System.out.println(x + " loses to " + y);
		else
			System.out.println(x + " ties " + y);
			
		if (x.equals(y))
			System.out.println(x + " and " + y + " are equal ");
		else
			System.out.println(x + " and " + y + " are not equal ");
		System.out.println();
	}
	
	public static void main(String [] args)	{
		// Demo of Card class
		Card c1 = new Card(Card.Suits.Diamonds, Card.Ranks.Seven);
		Card c2 = new Card(Card.Suits.Hearts, Card.Ranks.Queen);
		Card c3 = new Card(Card.Suits.Spades, Card.Ranks.Queen);
		Card c4 = new Card(Card.Suits.Spades, Card.Ranks.Queen);
		
		System.out.println("Card 1 is " + c1.toString());
		System.out.println("Card 2 is " + c2.toString());
		System.out.println("Card 3 is " + c3.toString());
		System.out.println("Card 4 is " + c4.toString());
		
		compareCards(c1, c2);
		compareCards(c1, c3);
		compareCards(c2, c3);
		compareCards(c4, c1);
		compareCards(c4, c3);
		
		System.out.println("Here are all of the suits:");
		for (Card.Suits s: Card.Suits.values())
			System.out.print(s + " ");
		System.out.println("\n");
		
		System.out.println("Here are all of the ranks:");
		for (Card.Ranks r: Card.Ranks.values())
			System.out.print(r + " ");
		System.out.println("\n");
		
		// ******************************************** 
		// Below this line requires the SQueue<T> class.  
		// Comment this out if you have not yet finished your SQueue<T> class
		
		// First let's make two SQueue of Cards
		SQueue<Card> myCards = new SQueue<Card>(10);
		SQueue<Card> otherCards = new SQueue<Card>(10);
		
		// Now put some cards into one
		try {
			myCards.enqueue(c1);
			myCards.enqueue(c2);
			myCards.enqueue(c3);
		} catch (FullQueueException fe) {
			fe.printStackTrace();
		}
		
		// Print it out
		System.out.println("myCards " + myCards);
		
		// Move two into the other pile
		try {
			otherCards.enqueue(myCards.dequeue());
			otherCards.enqueue(myCards.dequeue());
		} catch (FullQueueException fe) {
			fe.printStackTrace();
		} catch (EmptyQueueException ee) {
			ee.printStackTrace();
		}
		
		System.out.println("myCards " + myCards);
		System.out.println("otherCards " + otherCards);
		
	}
		
	

}
