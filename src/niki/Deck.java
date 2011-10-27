/*
 * Copyright 2011 Petros Koutloubasis <koutloup@gmail.com>
 *
 * NIKI is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * NIKI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with NIKI. If not, see <http://www.gnu.org/licenses/>.
 */

package niki;

import java.util.Random;
import java.util.ArrayList;
import java.math.BigInteger;

import static niki.NikiConstants.*;

public class Deck {
    private ArrayList<Long> deck;
    private ArrayList<Integer> primes;
    public long[] keysBoard;
    public long[] handsBoard;

    public static void main(String[] args) {
        Deck d = new Deck();
    }

    public Deck() {
        deck = new ArrayList<Long>();
        primes = new ArrayList<Integer>();

        for(int i = 0; i < DECK_BIT_MASKS.length; i++) {
            deck.add(DECK_BIT_MASKS[i]);
            primes.add(PRIMES[i]);
        }
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

    public void enumRandom(int size, int count) {
        Long cards = 0L;
        Long key = 0L;
        Random r = new Random();
        keysBoard = new long[count];
        handsBoard = new long[count];

        int x = 0;
        long oldCards = 0L;
        for(int i = 0; i < count; i++) {
            cards = 0L;
            key = 1L;
            while(Long.bitCount(cards) != size) {
                x = r.nextInt(deck.size());
                oldCards = cards;
                cards = cards | (Long) deck.get(x);
                if(cards != oldCards)
                    key = 1L * key * primes.get(x);
            }
            handsBoard[i] = cards;
            keysBoard[i] = key;
        }
    }

    public static BigInteger fac(int n) {
        BigInteger fac = BigInteger.ONE;
        for (int i = 1; i <= n; ++i)
            fac = fac.multiply(BigInteger.valueOf(i));
        return fac;
    }

    public int getPossibleDraws(int n, int r) {
        BigInteger result;
        BigInteger a;
        BigInteger b;
        BigInteger c;
        a = fac(n);
        b = fac(r);
        c = fac(n -r);

        result = a.divide(b.multiply(c));

        return result.intValue();
    }

    public void enumPostFlop() {
        int pDraws = getPossibleDraws(deck.size(), 5);
        keysBoard = new long[pDraws];
        handsBoard = new long[pDraws];
        
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
                            handsBoard[i] = deck.get(a)|deck.get(b)|deck.get(c)|deck.get(d)|deck.get(e);
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
