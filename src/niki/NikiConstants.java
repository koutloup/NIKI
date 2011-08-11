/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
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
public final class NikiConstants {

    //make it impossible to instantiate or subclass the class
    private NikiConstants() {}

    //MASK VALUES
    public static final int vmSF = 0x9000000; // straight flush
    public static final int vm4K = 0x8000000; // 4 of a kind
    public static final int vmFH = 0x7000000; // full house
    public static final int vmF  = 0x6000000; // flush
    public static final int vmS  = 0x5000000; // straight
    public static final int vm3K = 0x4000000; // 3 of a kind
    public static final int vm2P = 0x3000000; // 2 pair
    public static final int vm1P = 0x2000000; // 1 pair
    public static final int vmHC = 0x1000000; // high card

    //each card represented as long
    public static final long[] DECK = {
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

    public static final Integer[] PRIMES = {
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2,
        41, 37, 31, 29, 23, 19, 17, 13, 11, 7, 5, 3, 2
    };
}
