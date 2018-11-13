package teamproject;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

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
        private int starthour;
        private int startminute;
        private int stophour;
        private int stopminute;
        private int lunchstarthour;
        private int lunchstartminute;
        private int lunchstophour;
        private int lunchstopminute;

        //Constructor 
        public Shift(int id, String description, int starthour, int startminute, int stophour,
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
            this.lunchstop = LocalTime.of(lunchstophour, lunchstopminute);
            this.lunchdeduct = lunchdeduct;
            this.starthour = starthour;

        }
    //Setter Methods

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }

    public void setStartminute(int startminute) {
        this.startminute = startminute;
    }

    public void setStophour(int stophour) {
        this.stophour = stophour;
    }

    public void setStopminute(int stopminute) {
        this.stopminute = stopminute;
    }

    public void setLunchstarthour(int lunchstarthour) {
        this.lunchstarthour = lunchstarthour;
    }

    public void setLunchstartminute(int lunchstartminute) {
        this.lunchstartminute = lunchstartminute;
    }

    public void setLunchstophour(int lunchstophour) {
        this.lunchstophour = lunchstophour;
    }

    public void setLunchstopminute(int lunchstopminute) {
        this.lunchstopminute = lunchstopminute;
    }
        
        
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setStop(LocalTime stop) {
        this.stop = stop;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setGraceperiod(int graceperiod) {
        this.graceperiod = graceperiod;
    }

    public void setDock(int dock) {
        this.dock = dock;
    }

    public void setLunchstart(LocalTime lunchstart) {
        this.lunchstart = lunchstart;
    }

    public void setLunchstop(LocalTime lunchstop) {
        this.lunchstop = lunchstop;
    }

    public void setLunchdeduct(int lunchdeduct) {
        this.lunchdeduct = lunchdeduct;
    }
    
    //Getter Mrthods
     
    public int getStartHour() {
        
        return starthour;
    }

    public int getStartminute() {
        return startminute;
    }

    public int getStophour() {
        return stophour;
    }

    public int getStopminute() {
        return stopminute;
    }

    public int getLunchstarthour() {
        return lunchstarthour;
    }

    public int getLunchstartminute() {
        return lunchstartminute;
    }

    public int getLunchstophour() {
        return lunchstophour;
    }

    public int getLunchstopminute() {
        return lunchstopminute;
    }
    
   
    
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
   
        long shiftTime = (MINUTES.between(start, stop));
        long lunchTime = (MINUTES.between(lunchstart, lunchstop));
        
        return description + ": " + start + " - " + stop + " " + "(" + shiftTime + " minutes)" + "; " + "Lunch: " + lunchstart + " - " + lunchstop + " " + "(" + lunchTime + " minutes)";
        
    }
    
    
}


