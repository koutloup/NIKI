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
    public long cards;
    public long key;
    int wins = 0;
    
    Hand(long cards) {
        this.cards = cards;
        this.key = 1;
        this.wins=0;
        for(int i = 0; i < DECK.length; i++) {
            if((cards & DECK[i]) == DECK[i])
                key = key * PRIMES[i];
        }
    }

    public void wins() {
        this.wins++;
    }
}
