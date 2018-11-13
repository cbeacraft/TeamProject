package teamproject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        //Create calendar object for initial punch time
        GregorianCalendar OriginClock = new GregorianCalendar();
        OriginClock.setTimeInMillis(originaltimestamp);
        GregorianCalendar OriginClockSecondsReset = OriginClock;
        OriginClockSecondsReset.set(Calendar.SECOND, 00);
        
        //create instance variables to hold time adjustments
        int shiftStartHr; 
        int shiftStartMin;
        int lunchStartHr;
        int lunchStartMin;
        int shiftStopHr;
        int shiftStopMin;
        double PunchMin = OriginClock.MINUTE;
        long adjustment; //Will be used for the adjusted timestamp as needed
        boolean weekend = false; //flag that checks for weekend
        long Interval = s.getInterval();
        long Dock = s.getDock();
        long Grace = s.getGraceperiod();
        
        // Extract and store hour and minute portion from our local time variables.
        /*
        shiftStartHr
        shiftStartMin
        lunchStartHr
        lunchStartMin
        shiftStopHr
        shiftStopMin
        */
        
        //Create a calendar object adjusted to shift start time
        GregorianCalendar StartShift = OriginClock;
        StartShift.set(Calendar.HOUR_OF_DAY, shiftStartHr);
        StartShift.set(Calendar.MINUTE, shiftStartMin);
        StartShift.set(Calendar.SECOND, 00);
        
        //Create a calendar object adjusted to shift stop time
        GregorianCalendar StopShift = OriginClock;
        StopShift.set(Calendar.HOUR_OF_DAY, shiftStopHr);
        StopShift.set(Calendar.MINUTE, shiftStopMin);
        StopShift.set(Calendar.SECOND, 00);
        
        //Create a calendar object adjusted to lunch start time
        GregorianCalendar StartLunch = OriginClock;
        StartLunch.set(Calendar.HOUR_OF_DAY, lunchStartHr);
        StartLunch.set(Calendar.MINUTE, lunchStartMin);
        StartLunch.set(Calendar.SECOND, 00);
        
        //Create a calendar object adjusted to lunch stop time
        GregorianCalendar StopLunch = StartLunch;
        StopLunch.add(Calendar.MINUTE, 30);
        
        //Create a calendar object adjusted to interval before start
        GregorianCalendar StartInterval = StartShift;
        StartInterval.add(Calendar.MINUTE, -(s.getInterval()));
        
        //Create a calendar object adjusted to interval before stop
        GregorianCalendar StopInterval = StopShift;
        StopInterval.add(Calendar.MINUTE, s.getInterval());
        
        //Create a calendar object adjusted to start grace
        GregorianCalendar StartGrace = StartShift;
        StartGrace.add(Calendar.MINUTE, s.getGraceperiod());
        
        //Create a calendar object adjusted to stop grace
        GregorianCalendar StopGrace = StopShift;
        StopGrace.add(Calendar.MINUTE, -(s.getGraceperiod()));
        
        //Create a calendar object adjusted to start dock
        GregorianCalendar StartDock = StartShift;
        StartDock.add(Calendar.MINUTE, s.getDock());
        
        //Create a calendar object adjusted to stop dock
        GregorianCalendar StopDock = StopShift;
        StopDock.add(Calendar.MINUTE, -(s.getDock()));
        
        //a at the front of variable identifies as or actual as opposed to expected
        
        //Create formatter to convert calendar object into long int     //Can we just pass a calendar through the parse method?
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        
        //Convert calendar objects to long int for comparison
        long OriginPunch = Long.parseLong(sdf.format(OriginClock)); //When the employee made their punch
        long StartWork = Long.parseLong(sdf.format(StartShift)); //Start of Shift
        long EarlyStart = Long.parseLong(sdf.format(StartInterval)); //Arrive Early
        long OkStart = Long.parseLong(sdf.format(StartGrace)); //Grace Arrival
        long LateStart = Long.parseLong(sdf.format(StartDock)); //Late Arrival
        long StopWork = Long.parseLong(sdf.format(StopShift)); //End of Shift
        long LateStop = Long.parseLong(sdf.format(StopInterval)); //Late Leave
        long OkStop = Long.parseLong(sdf.format(StopGrace)); //Grace Leave
        long EarlyStop = Long.parseLong(sdf.format(StopDock)); //Early Leave
        long BeginBreak = Long.parseLong(sdf.format(StartLunch)); //Start of Lunch
        long CeaseBreak = Long.parseLong(sdf.format(StopLunch)); //End of Lucnh
        
        long AdjustedOriginPunch = Long.parseLong(sdf.format(OriginClockSecondsReset)); //Original pucnh with seconds field set to zero, useful in IntervalRound
        
        //Check weekend
        SimpleDateFormat sdw = new SimpleDateFormat("EEE");
        String sW = sdw.format(originaltimestamp);
        
        if ((sW == "SAT")||(sW == "SUN")){
            weekend = true;
        }
        
        //Begin Adjusting
        if (punchtypeid == 1 && weekend != true){ //Clock in on a weekday
            if (TookLunch != true){ //need to add in TookLunch and logic to toggle it, but I am unsure where to do so -J. Moses
                if (OriginPunch > EarlyStart && OriginPunch <= StartWork){ //If clock in punch is between StartInterval and StartShift (Clock in within interval)
                    adjustment = StartWork;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Start)");
                }else if (OriginPunch < OkStart && OriginPunch > StartWork){ //If clock in punch is between StartGrace abd StartShift (Clock in within grace period)
                    adjustment = StartWork;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Start)");
                }else if (OriginPunch > OkStart && OriginPunch < LateStart){ //If clock in punch is between StartGrace and StartDock (Clock in between Grace period and Dock)
                    adjustment = LateStart;
                    setEventData("(Shift Dock)");
                }else { // Interval Round clock
                    if (PunchMin/s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                        adjustment = AdjustedOriginPunch;
                        setAdjustedtimestamp(adjustment);
                        setEventData("(None)");

                    }else if (PunchMin/s.getInterval() > 0.5){ //Pucnh happend in the second half of an interval

                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = OriginPunch - StartWork; // time elapsed from start of shift to clock in. This should be in millis
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed 
                        long Intervals = FI+1; //advance to next interval
                        long IntervalRoundMinutes = (Interval * Intervals); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        GregorianCalendar AdjustedPunchIn = StartShift; //create new calendar object to make adjustments to the timestamp
                        AdjustedPunchIn.add(Calendar.MINUTE, i); //advance the time to the next interval
                        adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");
                    }else{ //punch happened in first half of interval

                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = OriginPunch - StartWork; // time elapsed from start of shift to clock in. This should be in millis
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed
                        long IntervalRoundMinutes = (Interval * FI); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        GregorianCalendar AdjustedPunchIn = StartShift; //create new calendar object to make adjustments to the timestamp
                        AdjustedPunchIn.add(Calendar.MINUTE, i); //advance the time to the next interval
                        adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");
                    }
                }                
            }else { //Clocking in from lunch
                if (OriginPunch < CeaseBreak && OriginPunch < BeginBreak){ //If clock in punch is duirng lunch window
                    adjustment = CeaseBreak;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Lunch Stop)");
                }
            }
        }else if (punchtypeid == 0 && weekend != true){ //Clock out on a week day
            if (TookLunch == true){
                if (OriginPunch < LateStop && OriginPunch >= StopWork){ //If clock out is between StopInterval and StopShift (clock out inside interval window)
                    adjustment = StopWork;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Stop)");
                }else if (OriginPunch > OkStop && OriginPunch < StopWork){ //If clock out is between StopGrace and StopShift (clock out inside grace period)
                    adjustment = StopWork;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Stop)");
                }else if (OriginPunch < OkStop && OriginPunch < EarlyStop){ //If clock out is between StopGrace and StopDock (clock out before grace period)
                    adjustment = EarlyStop;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Dock)");
                }else{ //Interval Round Check
                    if (PunchMin/s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                        adjustment = AdjustedOriginPunch;
                        setAdjustedtimestamp(adjustment);
                        setEventData("(None)");

                    }else if (PunchMin/s.getInterval() < 0.5){ //Pucnh happend in the second half of an interval

                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = StopWork - OriginPunch; // time elapsed from clock out to end of shift. This should be in millis
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed 
                        long Intervals = FI+1; //advance to next interval
                        long IntervalRoundMinutes = (Interval * Intervals); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        GregorianCalendar AdjustedPunchIn = StopShift; //create new calendar object to make adjustments to the timestamp
                        AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                        adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");

                    }else{ //punch happened in first half of interval
                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = StopWork - OriginPunch; // time elapsed from clock out to end of shift. This should be in millis
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed
                        long IntervalRoundMinutes = (Interval * FI); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        GregorianCalendar AdjustedPunchIn = StopShift; //create new calendar object to make adjustments to the timestamp
                        AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                        adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");
                    }
                }
            }else{ //Clocking out for lunch
                if (OriginPunch < CeaseBreak && OriginPunch > BeginBreak){ //Clock out during lunch window
                    adjustment = BeginBreak;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Lunch Start)");
                }
            }
        }

        //Interval round
        if (punchtypeid == 1){ //Clock in
            if (PunchMin/s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                adjustment = AdjustedOriginPunch;
                setAdjustedtimestamp(adjustment);
                setEventData("(None)");
                
            }else if (PunchMin/s.getInterval() > 0.5){ //Pucnh happend in the second half of an interval
                
                //calculate how many intervals have passed since the start of the shift, 
                long TE = OriginPunch - StartWork; // time elapsed from start of shift to clock in. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed 
                long Intervals = FI+1; //advance to next interval
                long IntervalRoundMinutes = (Interval * Intervals); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                GregorianCalendar AdjustedPunchIn = StartShift; //create new calendar object to make adjustments to the timestamp
                AdjustedPunchIn.add(Calendar.MINUTE, i); //advance the time to the next interval
                adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
            }else{ //punch happened in first half of interval
                
                //calculate how many intervals have passed since the start of the shift, 
                long TE = OriginPunch - StartWork; // time elapsed from start of shift to clock in. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed
                long IntervalRoundMinutes = (Interval * FI); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                GregorianCalendar AdjustedPunchIn = StartShift; //create new calendar object to make adjustments to the timestamp
                AdjustedPunchIn.add(Calendar.MINUTE, i); //advance the time to the next interval
                adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
            }
        }else if (punchtypeid == 0){ //Interval round for clock out
            if (PunchMin/s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                adjustment = AdjustedOriginPunch;
                setAdjustedtimestamp(adjustment);
                setEventData("(None)");
                
            }else if (PunchMin/s.getInterval() < 0.5){ //Pucnh happend in the second half of an interval
                
                //calculate how many intervals have passed since the start of the shift, 
                long TE = StopWork - OriginPunch; // time elapsed from clock out to end of shift. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed 
                long Intervals = FI+1; //advance to next interval
                long IntervalRoundMinutes = (Interval * Intervals); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                GregorianCalendar AdjustedPunchIn = StopShift; //create new calendar object to make adjustments to the timestamp
                AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
                
            }else{ //punch happened in first half of interval
                //calculate how many intervals have passed since the start of the shift, 
                long TE = StopWork - OriginPunch; // time elapsed from clock out to end of shift. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed
                long IntervalRoundMinutes = (Interval * FI); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                GregorianCalendar AdjustedPunchIn = StopShift; //create new calendar object to make adjustments to the timestamp
                AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                adjustment = Long.parseLong(sdf.format(AdjustedPunchIn)); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
            }
        }
    }
}
