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

import static niki.NikiConstants.*;

/**
 *
 * @author Petros Koutloubasis <koutloup@gmail.com>
 */
public class Hand {
    public long cards = 0;
    public long key = 1;
    int wins = 0;

    Hand() {};
    
    Hand(long cards) {
        this.cards = cards;
        for(int i = 0; i < DECK_BIT_MASKS.length; i++) {
            if((cards & DECK_BIT_MASKS[i]) == DECK_BIT_MASKS[i])
                key = key * PRIMES[i];
        }
    }

    Hand(String... cards) {
        for(String card : cards)
            this.addCard(card);
    }

    public void addCard(String card) {
        for(int i = 0; i < DECK_STRINGS.length; i++) {
            if(card.equals(DECK_STRINGS[i])) {
                key = key * PRIMES[i];
                cards = cards + DECK_BIT_MASKS[i];
            }
        }
    }

    public void wins() {
        this.wins++;
    }
}
