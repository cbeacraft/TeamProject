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
        public void shift(int id, String description, int starthour, int startminute, int stophour,
        int stopminute, int interval, int graceperiod, int dock, int lunchstarthour, int lunchstartminute,
        int lunchstophour, int lunchstopminute, int lunchdeduct) {

            this.id = id;
            this.description = description;
            this.start = LocalTime.of(starthour, startminute);
            this.stop = LocalTime.of(stophour, stopminute);
            this.interval = interval; 
            this.graceperiod = graceperiod;
            this.dock = dock;
            this.lunchstart = LocalTime.of(lunchstarthour, lunchstartminute);
            this.lunchstop = LocalTime.of(lunchstarthour, lunchstartminute);
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


