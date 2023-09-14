public class WarGame {

    private SQueue<Card> deck;
    private SQueue<Card> leftHand;
    private SQueue<Card> rightHand;
    private SQueue<Card> leftDiscard;
    private SQueue<Card> rightDiscard;
    private int rounds;

    public static void main(String[] args) {
        // rounds will be -1 unless changed by cmd line args
        int rounds = -1;
        if (args[0] != null) {
            try {
                rounds = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("You did not enter a whole number for rounds!");
            }
        }

        WarGame game = new WarGame(rounds);
        while (!game.isGameOver()) {
            game.rounds--;
            game.nextTurn();
        }
    }

    public WarGame(int rounds) {

        this.rounds = rounds;
        deck = new SQueue<>(52);
        for (Card.Suits suit : Card.Suits.values()) {
            for (Card.Ranks rank : Card.Ranks.values()) {
                deck.enqueue(new Card(suit, rank));
            }
        }
        deck.shuffle();

        // each player hand and discard can have at most 52 cards
        leftHand = new SQueue<>(52);
        leftDiscard = new SQueue<>(52);
        rightHand = new SQueue<>(52);
        rightDiscard = new SQueue<>(52);

        Card nextCard;
        while (!deck.isEmpty()) {
            nextCard = deck.dequeue();
            leftHand.enqueue(nextCard);
            nextCard = deck.dequeue();
            rightHand.enqueue(nextCard);
        }
    }

    public void nextTurn() {
        //draw cards, if drawCard returns null, it means player lost
        Card leftCard = drawCard(leftHand, leftDiscard);
        Card rightCard = drawCard(rightHand, rightDiscard);

        if (leftCard == null || rightCard == null) {
            gameOver();
        }

        int comparison = leftCard.compareTo(rightCard);
        if (comparison > 0) {
            // left card wins fight
            leftDiscard.enqueue(leftCard);
            leftDiscard.enqueue(rightCard);
        } else if (comparison < 0) {
            // player 2 wins fight
            rightDiscard.enqueue(leftCard);
            rightDiscard.enqueue(rightCard);
        } else {
            //tie
            int winner = resolveWar();
            if (winner == 0) {
                leftDiscard.enqueue(leftCard);
                leftDiscard.enqueue(rightCard);
            } else {
                rightDiscard.enqueue(leftCard);
                rightDiscard.enqueue(rightCard);
            }
        }
    }

    // return will be 0 if left wins and 1 if right wins
    public int resolveWar() {
        Card leftFaceDown = drawCard(leftHand, leftDiscard);
        Card rightFaceDown = drawCard(rightHand, rightDiscard);

        Card leftCard = drawCard(leftHand, leftDiscard);
        Card rightCard = drawCard(rightHand, rightDiscard);

        if (leftCard == null || rightCard == null) {
            gameOver();
        }

        int comparison = leftCard.compareTo(rightCard);
        // if cards tie, recurse first and keep going, once there is a definite end each recursive level
        // will then move on and handle enqueueing cards at that level and return to nextTurn()
        if (comparison == 0) {
            comparison = resolveWar();
        }
        if (comparison > 0) {
            // add 4 cards to winner and return flag to deal with original 2 cards
            leftDiscard.enqueue(leftCard);
            leftDiscard.enqueue(rightCard);
            leftDiscard.enqueue(leftFaceDown);
            leftDiscard.enqueue(rightFaceDown);
            return 0;
        } else {
            rightDiscard.enqueue(leftCard);
            rightDiscard.enqueue(rightCard);
            rightDiscard.enqueue(leftFaceDown);
            rightDiscard.enqueue(rightFaceDown);
            return 1;
        }
    }
    
    public Card drawCard(SQueue<Card> hand, SQueue<Card> discard) {
        Card card;
        try {
            card = hand.dequeue();
        } catch(EmptyQueueException e) {
            // hand and discard empty means player has lost
            if (discard.isEmpty()) {
                return null;
            }
            // take all discard pile into hand
            while (!discard.isEmpty()){
                hand.enqueue(discard.dequeue());
            }
            hand.shuffle();
            card = hand.dequeue();
        }
        return card;
    }

    public boolean isGameOver() {
        if (this.rounds == 0) {
            return true;
        } else if (playerLost(leftHand, leftDiscard)) {
            return true;
        } else if (playerLost(rightHand, rightDiscard)) {
            return true;
        }
        return false;
    }

    public void gameOver() {

    }

    public boolean playerWon(SQueue<Card> hand, SQueue<Card> discard) {
        return (hand.getSize() + discard.getSize() == 52);
    }

    public boolean playerLost(SQueue<Card> hand, SQueue<Card> discard) {
        return hand.isEmpty() && discard.isEmpty();
    }
	
}