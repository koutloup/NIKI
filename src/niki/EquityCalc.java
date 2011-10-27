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

public class EquityCalc {

    public static void main(String[] args) {
        Eva.loadEnumHands();
        Calc("Ad,Ts|Kh,Qh|5c,5d|6c,6d", null, null, 500000);
    }

    public static void Calc(String pHands, String board, String dead, int times) {
        Hand[] hands;
        Deck deck = new Deck();

        String[] sHands = pHands.split("\\|");
        int handCount = sHands.length;
        hands = new Hand[handCount];
        for(int i = 0; i < handCount; i++) {
            hands[i] = new Hand(sHands[i]);
            CPlot.print(hands[i].hCards);
            deck.removeCards(hands[i].hCards);
        }

        if(times > 0)
            deck.enumRandom(5, times);
        else 
            deck.enumPostFlop();

        int[] hValues = new int[handCount];
        int ties = 0;
        int keyBoardSize = deck.keysBoard.length;
        long time = System.currentTimeMillis();
        for(int i = 0; i < keyBoardSize; i++) {
            for(int j = 0; j < handCount; j++) {
                hValues[j] = Eva.getValue(deck.keysBoard[i] * hands[j].hKey,
                                          deck.handsBoard[i] | hands[j].hCards);
            }

            int winningHand = 0;
            int winningValue = 0;
            for(int j = 0; j < handCount; j++) {
                if(hValues[j] >= winningValue) {
                    //check for tie
                    if(winningValue == hValues[j]) {
                        winningHand = -1;
                    } else {
                        winningValue = hValues[j];
                        winningHand = j;
                    }
                }
            }
            if(winningHand >= 0)
                hands[winningHand].wins();
            else
                ties++;
        }
        time = System.currentTimeMillis() - time;
        double gamesPerSecond = (deck.keysBoard.length / time) * 1000;
        System.out.println(deck.handsBoard.length  + " games in "
                           + time + "ms "
                           + " (" + gamesPerSecond + " games/second)");
        System.out.println("\tequity%\twin%\twins");
        
        for(int i = 0; i < handCount; i++) {
            double winPercentage = 0;
            winPercentage = hands[i].wins / (double)(deck.keysBoard.length) * 100;
            winPercentage = (double) Math.round(winPercentage * 10) / 10;

            double equity = 0;
            equity = (hands[i].wins + (double) (ties / handCount)) /
                    (double) deck.keysBoard.length * 100;
            equity = (double)Math.round(equity * 10) / 10;
            
            System.out.println(i + ":\t"
                               + equity + "\t"
                               + winPercentage + "\t"
                               + hands[i].wins);
        }
        
        double tiesPercentage;
        tiesPercentage = (ties / handCount) / (double)(deck.keysBoard.length) * 100;
        tiesPercentage = (double) Math.round(tiesPercentage * 100) / 100;
        System.out.println("ties:\t"
                            + ((double)ties / handCount) + "\t"
                            + tiesPercentage);
    }
}
