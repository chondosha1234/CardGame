public class WarGame {

    private SQueue<Card> deck;
    private SQueue<Card> leftHand;
    private SQueue<Card> rightHand;
    private SQueue<Card> leftDiscard;
    private SQueue<Card> rightDiscard;
    private int rounds;
    private int roundCounter;

    public static void main(String[] args) {
        // rounds will be -1 unless changed by cmd line args
        int rounds = -1;
        if (args.length != 0 && args[0] != null) {
            try {
                rounds = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("You did not enter a whole number for rounds!");
            }
        }

        System.out.println("Welcome to the Game of War!");
        WarGame game = new WarGame(rounds);

        System.out.println("Starting the Game of War!");
        if (rounds >= 0) {
            System.out.println("Max number of rounds: " + rounds);
        } else {
            System.out.println("Unlimited rounds!");
        }

        while (!game.isGameOver()) {
            game.rounds--;
            game.nextTurn();
            game.roundCounter++;
        }
        game.gameOver();
    }

    public WarGame(int rounds) {
        this.rounds = rounds;
        this.roundCounter = 0;
        dealDeck();
    }

    public void dealDeck() {
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

        System.out.println("Now dealing cards to players");
        Card nextCard;
        while (!deck.isEmpty()) {
            nextCard = deck.dequeue();
            leftHand.enqueue(nextCard);
            nextCard = deck.dequeue();
            rightHand.enqueue(nextCard);
        }
        System.out.println("leftHand size: " + leftHand.getSize());

        System.out.println("Player 1's deck: ");
        System.out.println(leftHand.toString());

        System.out.println("Player 2's deck: ");
        System.out.println(rightHand.toString());
    }

    public void nextTurn() {
        int leftDeckSize = leftHand.getSize() + leftDiscard.getSize();
        int rightDeckSize = rightHand.getSize() + rightDiscard.getSize();
        System.out.println("left deck size: " + leftDeckSize);
        System.out.println("right deck size: " + rightDeckSize);
        //draw cards, if drawCard returns null, it means player lost
        Card leftCard = drawCard(leftHand, leftDiscard);
        Card rightCard = drawCard(rightHand, rightDiscard);

        if (leftCard == null || rightCard == null) {
            gameOver();
        }

        int comparison = leftCard.compareTo(rightCard);
        if (comparison > 0) {
            // left card wins fight
            System.out.println("Round " + roundCounter + " Player 1 wins: " + leftCard + " beats " + rightCard);
            leftDiscard.enqueue(leftCard);
            leftDiscard.enqueue(rightCard);
        } else if (comparison < 0) {
            // player 2 wins fight
            System.out.println("Round " + roundCounter + " Player 2 wins: " + rightCard + " beats " + leftCard);
            rightDiscard.enqueue(leftCard);
            rightDiscard.enqueue(rightCard);
        } else {
            //tie
            System.out.println("Round " + roundCounter + " WAR: " + leftCard + " ties " + rightCard);
            int winner = resolveWar();
            // winner will return 0 for left (player 1) or 1 for right (player 2)
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

        System.out.println("Face Down");
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
        // figure out player drawing for use in print statements
        int player;
        if (hand.equals(leftHand)) {
            player = 1;
        } else {
            player = 2;
        }

        Card card;
        try {
            card = hand.dequeue();
        } catch(EmptyQueueException e) {
            // hand and discard empty means player has lost
            if (discard.isEmpty()) {
                return null;
            }
            // take all discard pile into hand
            System.out.println("Getting and shuffling the pile for Player " + player);
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

        int leftDeckSize = leftHand.getSize() + leftDiscard.getSize();
        int rightDeckSize = rightHand.getSize() + rightDiscard.getSize();
        // if rounds hits 0, it means there was a set number, and it hit 0
        // else rounds are set to negative, and it was unlimited rounds
        if (rounds == 0) {
            System.out.println("After " + roundCounter + " rounds here are the results:");
            System.out.println("Player 1: " + leftDeckSize + " cards.");
            System.out.println("Player 2: " + rightDeckSize + " cards.");

            if (leftDeckSize > rightDeckSize) {
                System.out.println("Player 1 wins!");
            } else if (leftDeckSize < rightDeckSize) {
                System.out.println("Player 2 wins!");
            } else {
                System.out.println("It's a tie!");
            }
        } else {
            if (leftDeckSize == 0) {
                System.out.println("Player 1 is out of cards!");
                System.out.println("Player 2 wins!!");
            } else {
                System.out.println("Player 2 is out of cards!");
                System.out.println("Player 1 wins!!");
            }
        }
        System.exit(0);
    }

    public boolean playerLost(SQueue<Card> hand, SQueue<Card> discard) {
        return hand.isEmpty() && discard.isEmpty();
    }
	
}