package com.adrianlesniak.beautifulthailand.models.maps;

/**
 * Created by adrian on 06/02/2017.
 */

public class DistanceMatrixRow {

    public DistanceMatrixElement[] elements;

    public static class DistanceMatrixElement {

        public String status;

        public Duration duration;

        public Distance distance;

        public static class Duration {

            // In seconds
            public int value;

            public String text;
        }

        public static class Distance {

            public int value;

            public String text;

        }

    }

}
