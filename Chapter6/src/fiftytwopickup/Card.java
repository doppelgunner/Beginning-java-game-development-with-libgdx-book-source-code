package fiftytwopickup;

import base_B.BaseActor;

/**
 * Created by robertoguazon on 05/08/2016.
 */
public class Card extends BaseActor {

    private String rank;
    private String suit;
    public float offsetX;
    public float offsetY;
    public float originalX;
    public float originalY;
    public boolean draggable;

    public static final String[] rankNames = {
        "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    };

    public static final String[] suitNames = {
            "Clubs", "Hearts", "Spades", "Diamonds"
    };

    public Card(String rank, String suit) {
        super();
        this.rank = rank;
        this.suit = suit;
        draggable = true;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }

    public int getRankIndex() {

        for (int i = 0; i < rankNames.length; i++) {
            if (rank.equals(rankNames[i])) {
                return i;
            }
        }

        return -1;
    }
}


