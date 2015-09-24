import java.util.*;

/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}

    private final List<Card> cards;

    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public Hand(String c) {
        String[] cardsFromHand = c.split(" ");//separate hand into individual cards
        
        cards = new ArrayList<Card>();
        
        for(String eachCard: cardsFromHand){
        	Card currCard = new Card(eachCard);
        	cards.add(currCard);
        }
        Collections.sort(cards);
    }
    
    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD TC TH 7C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */
    protected boolean hasNKind(int n) {
    	int count = 1;//how many of a kind
    	List<Card> tempList = new ArrayList<Card>(cards);//create comparable hand
    	
    	//iterate through each card in the hand, and compare them one by one
    	for(Card c1: cards){
    		for(Card c2: tempList){
    			if(!(c1.equals(c2))){//if card = comparable hand card, skip
    				if(count==n){//if n =  current count, return true
    					return true;
    				}
    				if(c1.getRank().equals(c2.getRank())){//if card ranks match, add to count
    					count++;
    				}
    			}
    		}
    		tempList.remove(0);
    	}
    	return false;
    }    
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
    	return false;
    }   
    
    /**
     * @returns true if the hand is a straight 
     */
    public boolean isStraight() {
    	
    	//check if card are in ascending  order
    	if(cards.get(2).getRank().compareTo(cards.get(1).getRank())==1
    		&& cards.get(3).getRank().compareTo(cards.get(2).getRank())==1
    		&& cards.get(4).getRank().compareTo(cards.get(3).getRank())==1
    		&& cards.get(5).getRank().compareTo(cards.get(4).getRank())==1){
    		return true;
    	}
    	
    	return false;
        
    }
    
    /**
     * @returns true if the hand is a flush
     */
    public boolean isFlush() {
    	int count = 0;
    	Card tempCard = cards.get(0);
    	//iterate through cards and compare if suits are the same
    	//if even 1 suit if off, return false
    	for(int i = 1; i<cards.size();i++){
    		if(!tempCard.getSuit().equals(cards.get(i).getSuit())){
    			return false;
    		}
    		count++;
    	}
    	if(count==4){
    		return true;
    	}
    	return false;
    }
    
    @Override
    public int compareTo(Hand h) {
        //hint: delegate!
		//and don't worry about breaking ties
    	if(this.kind().compareTo(h.kind())==1){
    		return 1;
    	}
    	else if (this.kind().compareTo(h.kind())==-1){
    		return -1;
    	}    	
    	return 0;
    }
    
    /*
     * Sort by 
     */
    
    
    
    /**
	 * This method is already implemented and could be useful! 
     * @returns the "kind" of the hand: flush, full house, etc.
     */
    public Kind kind() {
        if (isStraight() && isFlush()) return Kind.STRAIGHT_FLUSH;
        else if (hasNKind(4)) return Kind.FOUR_OF_A_KIND; 
        else if (hasNKind(3) && hasNKind(2)) return Kind.FULL_HOUSE;
        else if (isFlush()) return Kind.FLUSH;
        else if (isStraight()) return Kind.STRAIGHT;
        else if (hasNKind(3)) return Kind.THREE_OF_A_KIND;
        else if (isTwoPair()) return Kind.TWO_PAIR;
        else if (hasNKind(2)) return Kind.PAIR; 
        else return Kind.HIGH_CARD;
    }
}