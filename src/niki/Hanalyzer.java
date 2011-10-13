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

import static niki.NikiConstants.*;

public class Hanalyzer {

    //masks for hand values
    
    public static final long pMask = 0x8000800080008000L;

    /*
    public static final int vmSF = 0x9000000; // straight flush
    public static final int vm4K = 0x8000000; // 4 of a kind
    public static final int vmFH = 0x7000000; // full house
    public static final int vmF  = 0x6000000; // flush
    public static final int vmS  = 0x5000000; // straight
    public static final int vm3K = 0x4000000; // 3 of a kind
    public static final int vm2P = 0x3000000; // 2 pair
    public static final int vm1P = 0x2000000; // 1 pair
    public static final int vmHC = 0x1000000; // high card
    */
    
    public static final int maskedPart = 0xF000000;

    public static int getHandValue(long h) {
        long pKickers = 0L; //possible kickers
        long mask, masked;

        int i = 0;
        int bCount = 0;
        int straightCount = 0;

        int vMask = vmHC;

        int vMp0 = 0;
        int vMp1 = 0;

        mask = pMask;
        for(i=14;i>1;i--) {
            masked = h & mask;
            if(masked != 0) {
                bCount = Long.bitCount(masked);
                straightCount++;
                if(straightCount == 5) {
                    vMask = vmS;
                    vMp0 = i+4;
                    vMp1 = 0;
                    //no fullhouse and no 4 of a kind possible
                    //break no other checks needed here
                    break;
                }
                if(bCount == 1) {
                    pKickers = pKickers | ((masked % 0xFFFFL) & 0xFFFFL);
                } else if(bCount == 2) {
                    //first pair
                    if(vMask == vmHC) {
                        vMask = vm1P;
                        vMp0 = i;
                    //3. pair, possible kicker!
                    } else if(vMask == vm2P) {
                        pKickers = pKickers | (0x2L << i);
                    } else if(vMask == vm1P) {
                        vMask = vm2P;
                        vMp1 = i;
                    //full house
                    } else if(vMask == vm3K) {
                        vMask = vmFH;
                        vMp1 = i;
                        //no better hand possible
                        return cHV(vMask, vMp0, vMp1);
                    }
                } else if(bCount == 3) {
                    //we made a full house
                    if(vMask == vm1P || vMask == vm2P) {
                        vMask = vmFH;
                        vMp1 = vMp0;
                        vMp0 = i;
                        //no better hand possible
                        return cHV(vMask, vMp0, vMp1);
                    } else if(vMask == vm3K) {
                        vMask = vmFH;
                        vMp1 = i;
                        //no better hand possible
                        return cHV(vMask, vMp0, vMp1);
                    } else {
                        vMask = vm3K;
                        vMp0 = i;
                        //vMp1 = 0;
                    }
                } else if(bCount == 4) {
                    vMask = vm4K;
                    vMp0 = i;
                    //get kicker
                    vMp1 = getHighCard(h^mask);
                    //no better hand possible
                    return cHV(vMask, vMp0, vMp1);
                }
            } else
                straightCount = 0;

            mask = mask >>> 1;
        }

        //check for the wheel
        if(straightCount == 4) {
            if((h & 0x8000800080008000L) != 0) {
                vMp0 = 5;
                vMp1 = 0;
                vMask = vmS;
                straightCount = 5;
            }
        }

        //check for flush
        Long fMask = 0L;
        int sameColor = 0;
        while (true) {
            fMask = h & 0xFFFF000000000000L;
            sameColor = Long.bitCount(fMask);
            if(sameColor >= 5) {
                vMask = vmF;
                break;
            }
            fMask = h & 0x0000FFFF00000000L;
            sameColor = Long.bitCount(fMask);
            if(sameColor >= 5) {
                vMask = vmF;
                break;
            }
            fMask = h & 0x00000000FFFF0000L;
            sameColor = Long.bitCount(fMask);
            if(sameColor >= 5) {
                vMask = vmF;
                break;
            }
            fMask = h & 0x000000000000FFFFL;
            sameColor = Long.bitCount(fMask);
            if(sameColor >= 5) {
                vMask = vmF;
                break;
            }
            break;
        }

        //check for sf
        if(vMask == vmF && (straightCount >= 5)) {
            //vMp0 is flush high
            fMask = (fMask % 0xFFFFL) & 0xFFFFL;

            if(Long.bitCount(fMask & 0x8078L) == 5) {
                vMask = vmSF;
                vMp0 = 5;
                vMp1 = 0;
            }

            long sfMask = 0xF800;
            for(i=0;i<9;i++) {
                if((sfMask & fMask) == sfMask) {
                    vMask = vmSF;
                    vMp0 = 14-i;
                    vMp1 = 0;
                    i=10;
                }
                sfMask = sfMask >>> 1;
            }
        }

        //kickers
        int nKickers=0;
        if(vMask == vmHC) {
            nKickers = 5;
        } else if(vMask == vm1P) {
            nKickers = 3;
        } else if(vMask == vm3K)  {
            nKickers = 2;
        } else if(vMask == vm2P) {
            nKickers = 1; 
        } else if(vMask == vmF) {
            nKickers = 5;
            pKickers = (fMask % 0xFFFFL) & 0xFFFFL;
            vMp0 = 0;
            vMp1 = 0;
        } else {
            //straight or fullhouse
            //no kicker(s) needed
            return cHV(vMask, vMp0, vMp1);
        }

        long kickers = 0L;
        long kicker;
        while(nKickers > 0) {
            kicker = Long.highestOneBit(pKickers);
            kickers = kickers | kicker;
            pKickers = pKickers ^ kicker;
            nKickers--;
        }

        return cHV(vMask, vMp0, vMp1, kickers);
    }

    
    public static int cHV(int vMask, int vMp0, int vMp1) {
        return vMask | (vMp0 << 20) | (vMp1 << 16);
    }

    
    public static int cHV(int vMask, int vMp0, int vMp1, long kickers) {
        return  vMask | (vMp0 << 20) | (vMp1 << 16) | (int)kickers;
    }


    //fast
    public static int getHighCard(long cards) {
        if(cards == 0) return 0;
        long mask = 0L;
        if((cards & 0xFC00FC00FC00FC00L) != 0) {
            if((cards & 0xE000E000E000E000L) != 0) {
                if((cards & 0x8000800080008000L) != 0) return 14;
                else if((cards & 0x4000400040004000L) != 0) return 13;
                else return 12;
            } else {
                if((cards & 0x1000100010001000L) != 0) return 11;
                else if((cards & 0x800080008000800L) != 0) return 10;
                else return 9;
            }
        } else if((cards & 0x1F801F801F801F8L) != 0) {
                if((cards & 0x1C001C001C001C0L) != 0) {
                    if((cards & 0x100010001000100L) != 0) return 7;
                    else if((cards & 0x80008000800080L) != 0) return 6;
                    else return 5;
                } else {
                    if((cards & 0x20002000200020L) != 0) return 4;
                    else if((cards & 0x10001000100010L) != 0) return 3;
                    else return 2;
                }
        } else return 8;
    }


    public static void printMask(Long mask) {
        int i = Long.numberOfLeadingZeros(mask);
        while(i>0) {
            System.out.print("0");
            i--;
        }
        System.out.println(Long.toBinaryString(mask));
    }

    
    private static void printMask(int mask) {
        int i = Integer.numberOfLeadingZeros(mask);
        while(i>0) {
            System.out.print("0");
            i--;
        }
        System.out.println(Integer.toBinaryString(mask));
    }

    
    public static void printHandValue(int v) {
        int masked = 0;
        int v0 = (0xF00000 & v) >> 20;
        int v1 = (0xF0000 & v) >> 16;
        int kicker = 0;

        masked = 0xF000000 & v;
        if(masked == vmHC) {
            v0 = 30-Integer.numberOfLeadingZeros(v&0xFFFF);
            System.out.println(v0+" high");
        } else if(masked == vm1P) {
            System.out.println("Pair of "+v0);
        } else if(masked == vm2P) {
            System.out.println("Two Pairs "+v0+" and "+v1);
        } else if(masked == vm3K) {
            System.out.println("Three of a kind "+v0);
        } else if(masked == vmS) {
            System.out.println("Straight "+v0+" high");
        }  else if(masked == vmF) {
            v0 = 30-Integer.numberOfLeadingZeros(v&0xFFFF);
            System.out.println("Flush "+v0+" high");
        }  else if(masked == vmFH) {
            System.out.println("Full House with "+v0+" full of "+v1);
        }  else if(masked == vm4K) {
            System.out.println("4 of a kind "+v0+" with "+v1);
        }  else if(masked == vmSF) {
            System.out.println("SF! "+v0);
        }

    }
}