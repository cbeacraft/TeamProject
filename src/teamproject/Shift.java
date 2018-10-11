package teamproject;

public class Shift {

        //Instance Fields
        private int id;
        private String description; 
        private int start;
        private int stop;
        private int interval;
        private int graceperiod;
        private int dock;
        private int lunchstart;
        private int lunchstop;
        private int lunchdeduct;

        //Constructor 
        public void shift(int id, String description, int start, int stop, 
        int interval, int graceperiod, int dock, int lunchstart, int lunchstop, int lunchdeduct) {

            this.id = id;
            this.description = description;
            this.start = start;
            this.stop = stop;
            this.interval = interval; 
            this.graceperiod = graceperiod;
            this.dock = dock;
            this.lunchstart = lunchstart;
            this.lunchstop = lunchstop;
            this.lunchdeduct = lunchdeduct;

        }

        //Getter methods
        public int getId() {
                return id; 
        }
        public String getDescription() {
                return description;
        }
        public int getStart() {
                return start;
        }
        public int getStop() {
                return stop;
        }
        public int getInterval() {
                return interval;
        }
        public int getGraceperiod() {
                return graceperiod;
        }
        public int getDock() {
                return dock;
        }
        public int getLunchstart() {
                return lunchstart;
        }
        public int getLunchstop() {
                return lunchstop;
        }
        public int getLunchdeduct() {
                return lunchdeduct;
        }
        @Override
        public String toString() {
                return null;
        }
}


