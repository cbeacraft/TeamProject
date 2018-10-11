package teamproject;
import java.time.LocalTime;

public class Shift {

        //Instance Fields
        private int id;
        private String description; 
        private LocalTime start;
        private LocalTime stop;
        private int interval;
        private int graceperiod;
        private int dock;
        private LocalTime lunchstart;
        private LocalTime lunchstop;
        private int lunchdeduct;

        //Constructor 
        public void shift(int id, String description, LocalTime start, LocalTime stop, int interval, 
        int graceperiod, int dock, LocalTime lunchstart, LocalTime lunchstop, int lunchdeduct) {

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
        public LocalTime getStart() {
                return start;
        }
        public LocalTime getStop() {
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
        public LocalTime getLunchstart() {
                return lunchstart;
        }
        public LocalTime getLunchstop() {
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


