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

public class StopWatch {
    public long startTime = 0L;
    public long time = 0L;
    private static boolean tRunning = false;

    public void start() {
        if(!tRunning)
            startTime = System.currentTimeMillis();
        tRunning = true;
    }

    public void restart() {
        reset();
        start();
    }

    public void stop() {
        if(tRunning)
            time = time + (System.currentTimeMillis() - startTime);
        tRunning = false;
    }

    public void reset() {
        time = 0L;
        tRunning = false;
    }

    @Override
    public String toString() {
        return Long.toString(time);
    }
}
