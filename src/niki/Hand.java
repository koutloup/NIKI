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
 * @author Petros Koutloubasis <koutloup@gmail.com>
 */
public class Hand {
    public long cards;
    public long key;
    int wins = 0;

    private Integer[] aPrimes = {
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2
    };

    long deck[] = {
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
    
    
    Hand(long cards) {
        this.cards = cards;
        this.key = 1;
        this.wins=0;

        for(int i = 0; i < deck.length; i++) {
            if((cards & deck[i]) == deck[i])
                key = key * aPrimes[i];
        }
    }

    public void wins() {
        this.wins++;
    }
}
