/*
 * Copyright (C) 2013 Spencer Alderman
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
package com.rogue.reginald.message;

import java.util.concurrent.TimeUnit;

/**
 * Class definition representing a single message
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class Message {

    private final String sender;
    private final String channel;
    private final String message;
    private final String target;
    private final long timestamp;

    Message(String sender, String target, String message, String channel) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
        this.target = target;
        this.timestamp = System.nanoTime();
    }

    public String getSender() {
        return this.sender;
    }

    public String getTarget() {
        return this.target;
    }

    public String getChannel() {
        return this.channel == null ? "Query" : this.channel;
    }

    private String getTime() {
        long diff = System.nanoTime() - this.timestamp;
        TimePoint point = this.getTimePoint(diff);
        if (point.getNext() != null) {
            return String.format("%d %s, %d %s", point.getTime(), this.getProperUnitName(point.getUnit(), point.getTime()),
                    point.getNext().getTime(), this.getProperUnitName(point.getNext().getUnit(), point.getNext().getTime()));
        } else {
            return String.format("%d %s", point.getTime(), this.getProperUnitName(point.getUnit(), point.getTime()));
        }
    }

    private static class TimePoint {

        private final long time;
        private final TimeUnit unit;
        private TimePoint next;

        public TimePoint(long time, TimeUnit unit, TimePoint next) {
            this.time = time;
            this.unit = unit;
            this.next = next;
        }

        public long getTime() {
            return this.time;
        }

        public TimeUnit getUnit() {
            return this.unit;
        }

        public TimePoint getNext() {
            return this.next;
        }

        public TimePoint setNext(TimePoint next) {
            this.next = next;
            return this;
        }
    }

    /**
     * Absolutely disgusting method of retrieving the largest non-zero times
     * difference in a nanosecond period. Forgive me for my sins
     *
     * @version 1.0.0
     * @since 1.0.0
     *
     * @param diff The difference in nanoseconds between two points
     * @return A new {@link TimePoint} representing the largest represented time
     */
    private TimePoint getTimePoint(long diff) {
        if (diff < 0) {
            return null;
        }
        long temp;
        TimeUnit u = TimeUnit.NANOSECONDS;
        TimePoint root = null;
        if ((temp = u.toDays(diff)) > 0) {
            root = new TimePoint(temp, TimeUnit.DAYS, null);
            diff -= root.getUnit().toNanos(root.getTime());
        }
        if ((temp = u.toHours(diff)) > 0 || (root != null && temp >= 0)) {
            TimePoint p = new TimePoint(temp, TimeUnit.HOURS, null);
            root = this.allocateNodes(root, p);
            diff -= p.getUnit().toNanos(p.getTime());
        }
        if ((temp = u.toMinutes(diff)) > 0 || (root != null && temp >= 0)) {
            TimePoint p = new TimePoint(temp, TimeUnit.MINUTES, null);
            root = this.allocateNodes(root, p);
            diff -= p.getUnit().toNanos(p.getTime());
        }
        if ((temp = u.toSeconds(diff)) > 0 || (root != null && temp >= 0)) {
            TimePoint p = new TimePoint(temp, TimeUnit.SECONDS, null);
            root = this.allocateNodes(root, p);
            diff -= p.getUnit().toNanos(p.getTime());
        }
        if ((temp = u.toMillis(diff)) > 0 || (root != null && temp >= 0)) {
            TimePoint p = new TimePoint(temp, TimeUnit.MILLISECONDS, null);
            root = this.allocateNodes(root, p);
            diff -= p.getUnit().toNanos(p.getTime());
        }
        if ((temp = u.toMicros(diff)) > 0 || (root != null && temp >= 0)) {
            TimePoint p = new TimePoint(temp, TimeUnit.MICROSECONDS, null);
            root = this.allocateNodes(root, p);
            diff -= p.getUnit().toNanos(p.getTime());
        }
        if (diff >= 0 || (root != null && temp >= 0)) {
            TimePoint p = new TimePoint(temp, TimeUnit.NANOSECONDS, null);
            root = this.allocateNodes(root, p);
            diff -= p.getUnit().toNanos(p.getTime());
        }
        return root;
    }

    private TimePoint allocateNodes(TimePoint root, TimePoint allocate) {
        if (root == null) {
            return allocate;
        } else if (root.getNext() != null) {
            return root.setNext(this.allocateNodes(root.getNext(), allocate));
        } else {
            return root.setNext(allocate);
        }
    }

    private String getProperUnitName(TimeUnit unit, long amount) {
        String proper = unit.toString().substring(0, 1) + unit.toString().substring(1, unit.toString().length()).toLowerCase();
        return amount != 1 ? proper : proper.substring(0, proper.length() - 1);
    }

    @Override
    public String toString() {
        return String.format("(%s ago) [%s] %s => %s", this.getTime(), this.getChannel(), this.getSender(), this.message);
    }

}
