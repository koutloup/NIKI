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

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import gnu.trove.map.hash.TLongIntHashMap;

/**
 *
 * @author Petros Koutloubasis <koutloup@gmail.com>
 */
public class Eva {
    
    private static final boolean DEBUG = true;
    static private TLongIntHashMap hmNonFlush;

    private static final int[] sfMasks = {
        0, 0, 0, 0,
        0, 0, 0, 0xF8,
        0x1F0, 0x3E0,  0x7C0,  0xF80,
        0x1F00, 0x3E00, 0x7C00, 0xF800
    };

    public static void loadEnumHands() {
        try {
            FileInputStream fis = new FileInputStream("nonflush.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            hmNonFlush = (TLongIntHashMap) ois.readObject();
            System.out.println("loaded "+hmNonFlush.size()+" enumareted hands");
            ois.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getValue(long keyHand, long cardsHand) {
        int handValue;
        handValue = Eva.getFlushValue(cardsHand);
        if (handValue != 0)
            return handValue;
        handValue = Eva.getNoneFlushValue(keyHand);
        return handValue;
    }

    public static int getNoneFlushValue(long keyHand) {
        return hmNonFlush.get(keyHand);
    }

    public static int getFlushValue(long cardsHand) {
        long flashMask;
        int bitCount;
        int potentialKickers = 0;
        int vmF = 0x6000000;
        int vmSF = 0x9000000;

        while (true) {
            flashMask = cardsHand & 0xFFFF000000000000L;
            bitCount = Long.bitCount(flashMask);
            if (bitCount >= 5)
                break;
            
            flashMask = cardsHand & 0xFFFF00000000L;
            bitCount = Long.bitCount(flashMask);
            if (bitCount >= 5)
                break;

            flashMask = cardsHand & 0xFFFF0000L;
            bitCount = Long.bitCount(flashMask);
            if (bitCount >= 5)
                break;

            flashMask = cardsHand & 0xFFFFL;
            bitCount = Long.bitCount(flashMask);
            if (bitCount >= 5)
                break;

            //no flush
            return 0;
        }
        
        flashMask = (flashMask % 0xFFFFL) & 0xFFFFL;
        potentialKickers = (int) flashMask;

        int trailingZeros = 0;
        int kicker = 0;
        int kickers = 0;
        potentialKickers = potentialKickers ^ kickers;
        for(int i = 0; i < 5; i++) {
            kicker = Integer.highestOneBit(potentialKickers);
            kickers = kickers | kicker;
            potentialKickers = potentialKickers ^ kicker;
            //straightflush check
            if (i < 3) {
                trailingZeros = Integer.numberOfTrailingZeros(kicker);
                if((trailingZeros > 6) && ((sfMasks[trailingZeros] & flashMask) == sfMasks[trailingZeros])) {
                    return (vmSF | (trailingZeros-1 << 20));
                }
            }
        }

        //wheel check
        if((flashMask & 0x8078) == 0x8078)
            return (vmSF | (5 << 20));

        return vmF | kickers;
    }
}
