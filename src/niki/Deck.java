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

/**
 *
 * @author kekse
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList deck;
    private ArrayList<Integer> primes;
    private ArrayList<Integer> colors;

    public long[] keysBoard;
    public long[] handsBoard;

    private static long[] cardValues = {
        0x8000000000000000L, 0x4000000000000000L, 0x2000000000000000L,
        0x1000000000000000L, 0x800000000000000L, 0x400000000000000L,
        0x200000000000000L, 0x100000000000000L, 0x80000000000000L,
        0x40000000000000L, 0x20000000000000L, 0x10000000000000L,
        0x8000000000000L,
        0x800000000000L, 0x400000000000L, 0x200000000000L,
        0x100000000000L, 0x80000000000L, 0x40000000000L,
        0x20000000000L, 0x10000000000L, 0x8000000000L,
        0x4000000000L, 0x2000000000L, 0x1000000000L,
        0x800000000L,
        0x80000000L, 0x40000000L, 0x20000000L,
        0x10000000L, 0x8000000L, 0x4000000L,
        0x2000000L, 0x1000000L, 0x800000L,
        0x400000L, 0x200000L, 0x100000L,
        0x80000L,
        0x8000L, 0x4000L, 0x2000L,
        0x1000L, 0x800L, 0x400L,
        0x200L, 0x100L, 0x80L,
        0x40L, 0x20L, 0x10L,
        0x8L
    };

    Integer[] aPrimes = { 41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
                         41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
                         41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
                         41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2};

    Integer[] iColors = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                          1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                          2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                          3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};

    public Deck() {
        keysBoard = new long[1712304];
        handsBoard = new long[1712304];

        deck = new ArrayList<Long>();
        primes = new ArrayList<Integer>();
        colors = new ArrayList<Integer>();
        
        for(int i=0;i<cardValues.length;i++) {
            deck.add(cardValues[i]);
            primes.add(aPrimes[i]);
            colors.add(iColors[i]);
        }
    }
    
    public static void main(String[] args) {
        Deck d = new Deck();
        d.dealRandom(100,false);
        d.printDeck();
        d.shuffleDeck();
        d.printDeck();
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
            colors.remove(i);
            cards = c^cards;
        }
    }

    public void printDeck() {
        System.out.println(deck);
    }

    public void enumPostFlop() {
        int a, b, c, d, e;
        a=0;b=1;c=2;d=3;e=4;
        int cid = deck.size();
        int i = 0;
        //--
        for(a=0;a<cid-4;a++) {
            for(b=a+1;b<cid-3;b++) {
                for(c=b+1;c<cid-2;c++) {
                    for(d=c+1;d<cid-1;d++) {
                        for(e=d+1;e<cid;e++) {
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
