package com.adrianlesniak.beautifulthailand.models;

import com.google.gson.annotations.SerializedName;

import org.joda.time.LocalTime;

/**
 * Created by adrian on 04/02/2017.
 */

public class OpeningHours {

    @SerializedName("open_now")
    public boolean openNow;

    static public class Period {
        static public class OpenClose {
            public enum DayOfWeek {
                SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, UNKNOWN
            }

            public DayOfWeek day;

            /**
             * Time that this Open or Close happens at.
             */
            public LocalTime time;
        }

        public OpenClose open;

        public OpenClose close;
    }

    public Period[] periods;

    @SerializedName("weekday_text")
    public String[] weekdayText;

    @SerializedName("permanently_closed")
    public Boolean permanentlyClosed;
}
