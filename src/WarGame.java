public class WarGame {

    private SQueue<Card> deck;
    private SQueue<Card> player1Hand;
    private SQueue<Card> player2Hand;
    private SQueue<Card> player1Discard;
    private SQueue<Card> player2Discard;

    public WarGame() {
        deck = new SQueue<>(52);
        for (Card.Suits suit : Card.Suits.values()) {
            for (Card.Ranks rank : Card.Ranks.values()) {
                deck.enqueue(new Card(suit, rank));
            }
        }
        deck.shuffle();

        // each player hand and discard can have at most 52 cards
        player1Hand = new SQueue<>(52);
        player1Discard = new SQueue<>(52);
        player2Hand = new SQueue<>(52);
        player2Discard = new SQueue<>(52);

        Card nextCard;
        while (!deck.isEmpty()) {
            nextCard = deck.dequeue();
            player1Hand.enqueue(nextCard);
            nextCard = deck.dequeue();
            player2Hand.enqueue(nextCard);
        }
    }
	
}