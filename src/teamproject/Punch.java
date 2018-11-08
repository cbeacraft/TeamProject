package teamproject;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Punch {

    private int id;
    private long originaltimestamp;
    private long adjustedtimestamp;
    private int terminalid;
    private String badgeid;
    private int punchtypeid;
    private Badge badge;
    private String EventData;

    //Constructor
    public Punch(int id, long originaltimestamp, int terminalid, String badgeid, int punchtypeid) {
        this.id = id;
        this.originaltimestamp = originaltimestamp;
        //this.adjustedtimestamp = adjustedtimestamp;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.punchtypeid = punchtypeid;
    }
    
    public Punch(Badge badge, int terminalid, int punchtypeid) {
        this.badge = badge;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        this.badgeid = badge.getId();
        
        GregorianCalendar gc = new GregorianCalendar();
        this.originaltimestamp = gc.getTimeInMillis();
    }
    //Getter Methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOriginaltimestamp() {
        return originaltimestamp;
    }

    public void setOriginaltimestamp(long originaltimestamp) {
        this.originaltimestamp = originaltimestamp;
    }

    public long getAdjustedtimestamp() {
        return adjustedtimestamp;
    }

    public void setAdjustedtimestamp(long adjustedtimestamp) {
        this.adjustedtimestamp = adjustedtimestamp;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }
    
    public void setBadgeid(String badgeid) {
            this.badgeid = badgeid;
    }

    public int getPunchtypeid() {
        return punchtypeid;
    }

    public void setPunchtypeid(int punchtypeid) {
        this.punchtypeid = punchtypeid;
    }
    
    public String getEventData(){
        return EventData;
    }
    
    public void setEventData(String EventData){
        this.EventData = EventData;
    }
    
    public String printOriginalTimestamp() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(originaltimestamp);
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        String time = (sdf.format(gc.getTime()).toUpperCase());
        
        int type = punchtypeid;
        String status;
        
        switch (type) {
            case 0:
                status = " CLOCKED OUT: ";
                break;
            case 1:
                status = " CLOCKED IN: ";
                break;
            default:
                status = " TIMED OUT: ";
                break;
        }
        
        return ("#" + badgeid + status + time);
    
    }

    public void adjust(Shift s) {
        /*
        GregorianCalendar shfitStart = new GregorianCalendar; // Holds the shift start time
        GregorianCalendar shfitStop = new GregorianCalendar; // Holds the shift stop time
        GregorianCalendar lunchStart = new GregorianCalendar; // Holds the lunch start time
        GregorianCalendar lunchStop = new GregorianCalendar; // Holds the lunch stop time
        GregorianCalendar actualTime = new GregorianCalendar; // shows the actual time of the punch
        GregorianCalendar adjustedTime = new GregorianCalendar; // holds the adjusted time according to the shift rules
        */
          
        //a at the front of variable identifies as or actual as opposed to expected
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        //String time = (sdf.format(gc.getTime()).toUpperCase());
        
        //start of shift times
        long shiftStart = Long.parseLong(sdf.format(s.getStart()));
        long aShiftStart = Long.parseLong(sdf.format(originaltimestamp));
        
        //end of shift times
        long shiftStop = Long.parseLong(sdf.format(s.getStop()));
        long aShiftStop = Long.parseLong(sdf.format(originaltimestamp));
        //start of lunch times
        long lunchStart = Long.parseLong(sdf.format(s.getLunchstart()));
        long aLunchStart = Long.parseLong(sdf.format(originaltimestamp));
        //end of lunch times
        long lunchStop = Long.parseLong(sdf.format(s.getLunchstop()));
        long aLunchStop = Long.parseLong(sdf.format(originaltimestamp));
        
        //variable to hold new timestamp
        long adjustment;

        //minutes*seconds*1000 to put into milliseconds
        long interval = ((s.getInterval()*60)+59)*1000;

        //minutes*seconds*1000 to put into milliseconds
        long grace = ((s.getGraceperiod()*60)+59)*1000;
        
        //minutes*seconds*1000 to put into milliseconds
        long dock = (s.getDock()*60)*1000;
        
        //is weekend?
        boolean weekend = false;
                
        SimpleDateFormat sdw = new SimpleDateFormat("EEE");
        String sW = sdf.format(originaltimestamp);
        
        if ((sW == "SAT")||(sW == "SUN")){
            weekend = true;
        }

        if (punchtypeid == 1 && weekend != true){ //needs a means of knowing wether or not to check shift or lunch
            //shift
            if (aShiftStart > shiftStart - interval && aShiftStart <= shiftStart){ //clock in early
                adjustment = shiftStart;
                setAdjustedtimestamp(adjustment);
                setEventData("(Shift Start)");
            }else if (aShiftStart < shiftStart + grace && aShiftStart > shiftStart){ //clock in late, forgiven
                adjustment = shiftStart;
                setAdjustedtimestamp(adjustment);
                setEventData("(Shift Start)");
            }else if (aShiftStart > shiftStart + grace && aShiftStart < shiftStart + interval){ //clock in late
                adjustment = (shiftStart + dock);
                setEventData("(Shift Dock)");
            }

            //lunch
            if (aLunchStop > lunchStop - interval && aLunchStop <= lunchStop){ //return early from lunch
                adjustment = lunchStop;
                setAdjustedtimestamp(adjustment);
                setEventData("(Lunch Stop)");
            }else if (aLunchStop < lunchStop + grace && aLunchStop > lunchStop){ //return late from lunch, forgiven
                adjustment = lunchStop;
                setAdjustedtimestamp(adjustment);
                setEventData("(Lunch Stop)");
            }/*else if (aLunchStop > lunchStop + grace && aLunchStop < lunchStop + interval){ //return late from lunch
                adjustment = lunchStop + dock;
            }*/ //Check with Snellen and see if we are supposed to dock for late return from lunch
        }else if (punchtypeid == 0 && weekend != true){
            //shift
            if (aShiftStop < shiftStop + interval && aShiftStop >= shiftStop){//clock out late
                adjustment = shiftStop;
                setAdjustedtimestamp(adjustment);
                setEventData("(Shift Stop)");
            }else if (aShiftStop > shiftStop - grace && aShiftStop < shiftStop){// clock out early, forgiven
                adjustment = shiftStop;
                setAdjustedtimestamp(adjustment);
                setEventData("(Shift Stop)");
            }else if (aShiftStop < shiftStop - grace && aShiftStop < shiftStop - interval){//clock out early
                adjustment = shiftStop - dock;
                setAdjustedtimestamp(adjustment);
                setEventData("(Shift Dock)");
            }
            //lunch
            if (aLunchStart > lunchStart + interval && aLunchStart <= lunchStart){ //go to lunch late 
                adjustment = lunchStart;
                setAdjustedtimestamp(adjustment);
                setEventData("(Lunch Start)");
            }else if (aLunchStart > lunchStart - grace && aLunchStart < lunchStart){ //go to lunch early, forgiven
                adjustment = lunchStart;
                setAdjustedtimestamp(adjustment);
                setEventData("(Lunch Start)");
            }/*else if (aLunchStart > lunchStart - grace && aLunchStart < lunchStart - interval){ //go to lunch early
                adjustment = lunchStart - dock;
            }*/ //Check with Snellen and see if we are supposed to dock for late return from lunch
        }

        //Interval round
        if (punchtypeid == 1){
            if (aShiftStart > shiftStart + dock){ //clock in more than 15 minutes late
                long c = (aShiftStart-shiftStart)/(dock/2);
                //assign a long to hold difference between actual and official start as a number of half intervals

                if (c < 3){ // if the number of half intervals is 1, 2, or 3: dock one interval
                    adjustment = shiftStart + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(None)");
                }else if (c == 4){ //if the number of half intervals is greater than 3: dock by per interval
                    adjustment = shiftStart + c/2*dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }else {
                    adjustment = shiftStart + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }
                        
            }/*else if (aLunchStop > lunchStop + dock){ //return from lunch more than 15 minutes late
                long a = (aLunchStop-lunchStop)/(dock/2);
                //assign a long to hold difference between actual and official stop as a number of half intervals

                if (a < 3){ // if the number of half intervals is 1, 2, or 3: dock one interval
                    adjustment = lunchStop + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(None)");
                }else if (a == 4){ //if the number of half intervals is greater than 3: dock by per interval
                    adjustment = lunchStop + a/2*dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }else {
                    adjustment = lunchStop + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }
            }*/ //Does this apply to Lunch?
        }else if (punchtypeid == 0){
            if (aShiftStop < shiftStop + dock){ //clocked out more than 15 minutes early
                long d = (aShiftStop-shiftStop)/(dock/2);
                //assign a long to hold difference between actual and official start as a number of half intervals

                if (d < 3){ // if the number of half intervals is 1, 2, or 3: dock one interval
                    adjustment = shiftStop + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(None)");
                }else if (d == 4){ //if the number of half intervals is greater than 3: dock by per interval
                    adjustment = shiftStop + d/2*dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }else {
                    adjustment = shiftStop + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }
            }/*else if (aLunchStart > lunchStart + dock){ //return from lunch more than 15 minutes late
                long a = (aLunchStart-lunchStart)/(dock/2);
                //assign a long to hold difference between actual and official start as a number of half intervals

                if (a < 3){ // if the number of half intervals is 1, 2, or 3: dock one interval
                    adjustment = lunchStart + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(None)");
                }else if (a == 4){ //if the number of half intervals is greater than 3: dock by per interval
                    adjustment = lunchStart + a/2*dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }else {
                    adjustment = lunchStart + dock;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Interval Round)");
                }
            }*/ //Does this apply to Lunch?
        }
    }
}
