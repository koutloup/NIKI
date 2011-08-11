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
public class TestGame {

   private static final boolean COUNT_MASKS = false;

    public static void main(String[] args) {

        Deck deck = new Deck();
        StopWatch stopWatch = new StopWatch();

        //Eva eva = new Eva();

        /**
        //AAo - KKo
        Hand hand1 = new Hand(0x8000000080000000L);
        Hand hand2 = new Hand(0x400000004000L);
        /**/

        //JTs - KKo
        /**/
        Hand hand1 = new Hand(0x18000000L);
        Hand hand2 = new Hand(0x400000004000L);
        /**/

        deck.removeCards(hand1.cards | hand2.cards);
        
        CPlot.print(hand1.cards);
        CPlot.print(hand2.cards);

        System.out.println("enumPostFlop");
        stopWatch.start();
        deck.enumPostFlop();
        stopWatch.stop();
        System.out.println("enumerated "+deck.keysBoard.length+" in "+ stopWatch);

        int handValue1 = 0;
        int handValue2 = 0;
        
        Eva.loadEnumHands();
        stopWatch.restart();
        System.out.println("---");
        int keysBoardSize = deck.keysBoard.length;
        for(int i = 0; i < keysBoardSize; i++) {
            handValue1 = Eva.getValue(deck.keysBoard[i] * hand1.key,
                                      deck.handsBoard[i] | hand1.cards);
            handValue2 = Eva.getValue(deck.keysBoard[i] * hand2.key,
                                      deck.handsBoard[i] | hand2.cards);

            if(handValue1 != handValue2)
                if(handValue1 > handValue2)
                    hand1.wins();
                else hand2.wins();
        }
        stopWatch.stop();

        Long gps = (deck.keysBoard.length/stopWatch.time)*1000;

        double winp0 = hand1.wins / (double)(deck.keysBoard.length) * 100;
        double winp1 = hand2.wins / (double) (deck.keysBoard.length) * 100;
        int ties = deck.keysBoard.length - (hand1.wins+hand2.wins);
        double tp = ties / (double)(deck.keysBoard.length) * 100;
        double ep0 = (hand1.wins + (ties/2)) /
                     (double) deck.keysBoard.length * 100;
        double ep1 = (hand2.wins + (ties/2)) /
                     (double) deck.keysBoard.length * 100;

        //print resulsts
        System.out.println(deck.keysBoard.length + " games in "
                            + stopWatch + "ms ("+gps+"g/s)");
        System.out.println("\t\tequity\t\twin");
        System.out.println("Player 1\t" + ep0 + "\t" + winp0 + "\t" + hand1.wins);
        System.out.println("Player 2\t" + ep1 + "\t" + winp1 + "\t" + hand2.wins);
        System.out.println("Ties\t"+ties+" ("+tp/2+")");
    }
}