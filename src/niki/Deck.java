/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package niki;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

import static niki.NikiConstants.*;

/**
 *
 * @author kekse
 */
public class Deck {
    private ArrayList<Long> deck;
    private ArrayList<Integer> primes;
    public long[] keysBoard;
    public long[] handsBoard;

    public Deck() {
        keysBoard = new long[1712304];
        handsBoard = new long[1712304];

        deck = new ArrayList<Long>();
        primes = new ArrayList<Integer>();

        for(int i = 0; i < DECK.length; i++) {
            deck.add(DECK[i]);
            primes.add(PRIMES[i]);
        }
    }
    
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public long dealRandom(int i) {
        return dealRandom(i, true);
    }
    
    private long dealRandom(int i, boolean remove) {
        Long cards = 0L;
        Random r = new Random();
        int x;
        while(Long.bitCount(cards) != i) {
            x = r.nextInt(deck.size());
            cards = cards | (Long) deck.get(x);
            if(remove) {
                deck.remove(x);
                primes.remove(x);
            }
        }
        return cards;
    }

    public void removeCards(long cards) {
        Long c;
        int i;
        while(cards != 0) {
            c = Long.highestOneBit(cards);
            i = deck.indexOf(c);
            deck.remove(c);
            primes.remove(i);
            cards = c^cards;
        }
    }

    public void printDeck() {
        System.out.println(deck);
    }

    public void enumPostFlop() {
        int i = 0;
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        int e = 4;
        int deckSize = deck.size();
        //--
        for(a=0;a<deckSize-4;a++) {
            for(b=a+1;b<deckSize-3;b++) {
                for(c=b+1;c<deckSize-2;c++) {
                    for(d=c+1;d<deckSize-1;d++) {
                        for(e=d+1;e<deckSize;e++) {
                            handsBoard[i] = (Long)deck.get(a)|(Long)deck.get(b)|(Long)deck.get(c)|(Long)deck.get(d)|(Long)deck.get(e);
                            keysBoard[i] = 1L *primes.get(a) * primes.get(b) * primes.get(c) * primes.get(d) * primes.get(e);
                            i++;
                        }
                    }
                }
            }
        }
        //--
    }
}
