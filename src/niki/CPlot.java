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
public class CPlot {

    public static void print(Long h) {
        System.out.println(getStr(h));
    }
    
    public static String getStr(Long h) {
        Long mask = 0x8000000000000000L;
        Long masked = 0L;
        int c = 0;
        String r = "";
        for(int i=64;i >0;i--) {
            /*
            printMask(h);
            printMask(mask);
            printMask(masked);
            //*/
            masked = mask & h;
            if(masked != 0) {
                c = ((i+14)%16);
                if(c == 14) r = r + "A";
                else if(c == 13) r = r + "K";
                else if(c == 12) r = r + "Q";
                else if(c == 11) r = r + "J";
                else if(c == 10) r = r + "T";
                else if(c <10) r = r + c;

                if(i>48) r = r + "♣";
                else if(i>32) r = r + "♦";
                else if(i>16) r = r + "♥";
                else r = r + "♠";
            }
            mask = mask >>> 1;
        }

        return r;
    }

    private static void printMask(Long mask) {
        int i = Long.numberOfLeadingZeros(mask);
        if(mask==0) System.out.println(Long.toBinaryString(mask));
        else {
            while(i>0) {
                System.out.print("0");
                i--;
            }
            System.out.println(Long.toBinaryString(mask));
        }
    }

    public static String print(int v, int s) {
        String out = "";
        if(v<10) 
            out = "v";
        else if(v==11) 
            out = "J";
        else if(v==12) 
            out = "Q";
        else if(v==13) 
            out = "K";
        else if(v==14)
            out = "A";

        if(s==1)
            out += "♠";
        else if(s==2)
            out += "♥";
        else if(s==3)
            out += "♦";
        else if(s==4)
            out += "♣";

        return out;        
    }
}
