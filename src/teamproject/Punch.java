package teamproject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private boolean TookLunch = false;
    private ArrayList PunchList;

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
    
    public boolean getTookLunch() {
        return TookLunch;
    }
    
    public void setTookLunch(boolean TookLunch){
        this.TookLunch = TookLunch;
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
    
    public String printAdjustedTimestamp() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(adjustedtimestamp);
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        String adjustedtime = (sdf.format(gc.getTime()).toUpperCase());
        
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
        
        return ("#" + badgeid + status + adjustedtime + ' ' + EventData);
    
    }
    
    public void adjust(Shift s) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        
        //TASDatabase db = new TASDatabase();
        
        //Create calendar object for initial punch time
        GregorianCalendar OriginClock = new GregorianCalendar();
        OriginClock.setTimeInMillis(originaltimestamp);
        
        GregorianCalendar OriginClockSecondsReset = new GregorianCalendar();
        OriginClockSecondsReset.setTimeInMillis(originaltimestamp);
        OriginClockSecondsReset.set(Calendar.SECOND, 00);
        
        //create instance variables to hold time adjustments
        int shiftStartHr = s.getStartHour(); 
        int shiftStartMin = s.getStartminute();     
        int lunchStartHr = s.getLunchstarthour();
        int lunchStartMin = s.getLunchstartminute();
        int shiftStopHr = s.getStophour();
        int shiftStopMin = s.getStopminute();
        //double PunchMin = OriginClock.MINUTE;//why is this not updating with a new punch?
        long adjustment; //Will be used for the adjusted timestamp as needed
        boolean weekend = true; //flag that checks for weekend
        boolean IsFirstPunch = false;
        long Interval = s.getInterval();
        long Dock = s.getDock();
        long Grace = s.getGraceperiod();
        boolean adjusted = false;
        
        
        
        //Create a calendar object adjusted to shift start time
        GregorianCalendar StartShift = new GregorianCalendar();
        StartShift.setTimeInMillis(originaltimestamp);
        StartShift.set(Calendar.HOUR_OF_DAY, shiftStartHr);
        StartShift.set(Calendar.MINUTE, shiftStartMin);
        StartShift.set(Calendar.SECOND, 00);
        
        //Create a calendar object adjusted to shift stop time
        GregorianCalendar StopShift = new GregorianCalendar();
        StopShift.setTimeInMillis(originaltimestamp);
        StopShift.set(Calendar.HOUR_OF_DAY, shiftStopHr);
        StopShift.set(Calendar.MINUTE, shiftStopMin);
        StopShift.set(Calendar.SECOND, 00);
        
        //Create a calendar object adjusted to lunch start time
        GregorianCalendar StartLunch = new GregorianCalendar();
        StartLunch.setTimeInMillis(originaltimestamp);
        StartLunch.set(Calendar.HOUR_OF_DAY, lunchStartHr);
        StartLunch.set(Calendar.MINUTE, lunchStartMin);
        StartLunch.set(Calendar.SECOND, 00);
        
        //Create a calendar object adjusted to lunch stop time
        GregorianCalendar StopLunch = new GregorianCalendar();
        StopLunch.setTimeInMillis(originaltimestamp);
        StopLunch.set(Calendar.HOUR_OF_DAY, lunchStartHr);
        StopLunch.set(Calendar.MINUTE, lunchStartMin);
        StopLunch.set(Calendar.SECOND, 00);
        StopLunch.add(Calendar.MINUTE, 30);
        
        //Create a calendar object adjusted to interval before start
        GregorianCalendar StartInterval = new GregorianCalendar();
        StartInterval.setTimeInMillis(originaltimestamp);
        StartInterval.set(Calendar.HOUR_OF_DAY, shiftStartHr);
        StartInterval.set(Calendar.MINUTE, shiftStartMin);
        StartInterval.set(Calendar.SECOND, 00);
        StartInterval.add(Calendar.MINUTE, -(s.getInterval()));
        
        //Create a calendar object adjusted to interval before stop
        GregorianCalendar StopInterval = new GregorianCalendar();
        StopInterval.setTimeInMillis(originaltimestamp);
        StopInterval.set(Calendar.HOUR_OF_DAY, shiftStopHr);
        StopInterval.set(Calendar.MINUTE, shiftStopMin);
        StopInterval.set(Calendar.SECOND, 00);
        StopInterval.add(Calendar.MINUTE, s.getInterval());
        
        //Create a calendar object adjusted to start grace
        GregorianCalendar StartGrace = new GregorianCalendar();
        StartGrace.setTimeInMillis(originaltimestamp);
        StartGrace.set(Calendar.HOUR_OF_DAY, shiftStartHr);
        StartGrace.set(Calendar.MINUTE, shiftStartMin);
        StartGrace.set(Calendar.SECOND, 00);
        StartGrace.add(Calendar.MINUTE, s.getGraceperiod());
        
        //Create a calendar object adjusted to stop grace
        GregorianCalendar StopGrace = new GregorianCalendar();
        StopGrace.setTimeInMillis(originaltimestamp);
        StopGrace.set(Calendar.HOUR_OF_DAY, shiftStopHr);
        StopGrace.set(Calendar.MINUTE, shiftStopMin);
        StopGrace.set(Calendar.SECOND, 00);
        StopGrace.add(Calendar.MINUTE, -(s.getGraceperiod()));
        
        //Create a calendar object adjusted to start dock
        GregorianCalendar StartDock = new GregorianCalendar();
        StartDock.setTimeInMillis(originaltimestamp);
        StartDock.set(Calendar.HOUR_OF_DAY, shiftStartHr);
        StartDock.set(Calendar.MINUTE, shiftStartMin);
        StartDock.set(Calendar.SECOND, 00);
        StartDock.add(Calendar.MINUTE, s.getDock());
        
        //Create a calendar object adjusted to stop dock
        GregorianCalendar StopDock = new GregorianCalendar();
        StopDock.setTimeInMillis(originaltimestamp);
        StopDock.set(Calendar.HOUR_OF_DAY, shiftStopHr);
        StopDock.set(Calendar.MINUTE, shiftStopMin);
        StopDock.set(Calendar.SECOND, 00);
        StopDock.add(Calendar.MINUTE, -(s.getDock()));
        
        //Create calendar object for adjusted clock in to use with interval round
        GregorianCalendar AdjustedPunchIn = new GregorianCalendar();
        AdjustedPunchIn.setTimeInMillis(originaltimestamp);
        AdjustedPunchIn.set(Calendar.HOUR_OF_DAY, shiftStartHr);
        AdjustedPunchIn.set(Calendar.MINUTE, shiftStartMin);
        AdjustedPunchIn.set(Calendar.SECOND, 00);
        
        //Create a calendar object for adjusted clokc out to use with interval round
        GregorianCalendar AdjustedPunchOut = new GregorianCalendar();
        AdjustedPunchOut.setTimeInMillis(originaltimestamp);
        AdjustedPunchOut.set(Calendar.HOUR_OF_DAY, shiftStopHr);
        AdjustedPunchOut.set(Calendar.MINUTE, shiftStopMin);
        AdjustedPunchOut.set(Calendar.SECOND, 00);
        
        double PunchMin = OriginClock.get(Calendar.MINUTE);
        double PunchSec = OriginClock.get(Calendar.SECOND);
        
                
        //DIAGNOSTIC RETURNS
        System.err.println(s);
        //Diagnostics for Construction
        System.err.println("Insance Variables");
        System.err.println("shiftStartHr:  " + shiftStartHr);
        System.err.println("shiftStartMin: " + shiftStartMin);
        System.err.println("lunchStartHr:  " + lunchStartHr);
        System.err.println("lunchStartMin: " + lunchStartMin);
        System.err.println("shiftStopHr:   " + shiftStopHr);
        System.err.println("shiftStopMin:  " + shiftStopMin);
        System.err.println("PunchMin:      " + PunchMin);
        System.err.println("PunchSec:      " + PunchSec);
        System.err.println("Interval:      " + Interval);
        System.err.println("Grace:         " + Grace);
        System.err.println("Dock:          " + Dock);
        System.err.println("");
        //Diagnostics for Time Stamps
        System.err.println("Timestamp objects");        
        System.err.println("Employee's Punch          EEE MM/DD/YYYY HH:mm:ss In Millis");
        System.err.println(" OriginClock:             " + sdf.format(OriginClock.getTimeInMillis()) + " " + OriginClock.getTimeInMillis());
        System.err.println(" OriginClockSecondsReset: " + sdf.format(OriginClockSecondsReset.getTimeInMillis()) + " " + OriginClockSecondsReset.getTimeInMillis());
        System.err.println("Start times               EEE MM/DD/YYYY HH:mm:ss In Millis");
        System.err.println(" StartShift:              " + sdf.format(StartShift.getTimeInMillis()) + " " + StartShift.getTimeInMillis());
        System.err.println(" StartLunch:              " + sdf.format(StartLunch.getTimeInMillis()) + " " + StartLunch.getTimeInMillis());
        System.err.println(" StartInterval:           " + sdf.format(StartInterval.getTimeInMillis()) + " " + StartInterval.getTimeInMillis());
        System.err.println(" StartGrace:              " + sdf.format(StartGrace.getTimeInMillis()) + " " + StartGrace.getTimeInMillis());
        System.err.println(" StartDock:               " + sdf.format(StartDock.getTimeInMillis()) + " " + StartDock.getTimeInMillis());
        System.err.println("Stop times                EEE MM/DD/YYYY HH:mm:ss In Millis");
        System.err.println(" StopShift:               " + sdf.format(StopShift.getTimeInMillis()) + " " + StopShift.getTimeInMillis());
        System.err.println(" StopLunch:               " + sdf.format(StopLunch.getTimeInMillis()) + " " + StopLunch.getTimeInMillis());
        System.err.println(" StopInterval:            " + sdf.format(StopInterval.getTimeInMillis()) + " " + StopInterval.getTimeInMillis());
        System.err.println(" StopGrace:               " + sdf.format(StopGrace.getTimeInMillis()) + " " + StopGrace.getTimeInMillis());
        System.err.println(" StopDock:                " + sdf.format(StopDock.getTimeInMillis()) + " " + StopDock.getTimeInMillis());
        //System.err.println("AdjustedPunchIn:         " + sdf.format(AdjustedPunchIn.getTimeInMillis()) + " " + AdjustedPunchIn.getTimeInMillis());
        //System.err.println("AdjustedPunchOut:        " + sdf.format(AdjustedPunchOut.getTimeInMillis()) + " " + AdjustedPunchOut.getTimeInMillis());
        System.err.println("\n");
        
        
        //Convert calendar objects to long int for comparison
        long OriginPunch = (OriginClock.getTimeInMillis()); //When the employee made their punch
        long StartWork = (StartShift.getTimeInMillis()); //Start of Shift
        long EarlyStart = (StartInterval.getTimeInMillis()); //Arrive Early
        long OkStart = (StartGrace.getTimeInMillis()); //Grace Arrival
        long LateStart = (StartDock.getTimeInMillis()); //Late Arrival
        long StopWork = (StopShift.getTimeInMillis()); //End of Shift
        long LateStop = (StopInterval.getTimeInMillis()); //Late Leave
        long OkStop = (StopGrace.getTimeInMillis()); //Grace Leave
        long EarlyStop = (StopDock.getTimeInMillis()); //Early Leave
        long BeginBreak = (StartLunch.getTimeInMillis()); //Start of Lunch
        long CeaseBreak = (StopLunch.getTimeInMillis()); //End of Lucnh
        
        long AdjustedOriginPunch = (OriginClockSecondsReset.getTimeInMillis()); //Original pucnh with seconds field set to zero, useful in IntervalRound
        
        
        /*int PunchNum = db.getDailyPunchList(badge, OriginPunch).size();
        if (PunchNum == 1){
            IsFirstPunch = true;
        }*/
                
        //Check weekend
        GregorianCalendar ots = new GregorianCalendar();
        ots.setTimeInMillis(originaltimestamp);
        
        int otsDay = ots.get(Calendar.DAY_OF_WEEK);
        
        if (!((otsDay == Calendar.SATURDAY)||(otsDay == Calendar.SUNDAY))){
            weekend = false;
        }
        System.err.println("Weekend: " + weekend);
        System.err.println("Punchtypeid: " + punchtypeid);
        System.err.println("TookLunch: " + TookLunch);
              
        //Begin Adjusting
        if (punchtypeid == 1 && !weekend){ //Clock in on a weekday
            if (TookLunch != true){ //need to add in TookLunch and logic to toggle it, but I am unsure where to do so -J. Moses
                if (OriginPunch >= EarlyStart && OriginPunch <= StartWork){ //If clock in punch is between StartInterval and StartShift (Clock in within interval)
                    adjustment = StartWork;
                    adjusted = true;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Start)");
                }else if (OriginPunch <= OkStart && OriginPunch >= StartWork){ //If clock in punch is between StartGrace abd StartShift (Clock in within grace period)
                    adjustment = StartWork;
                    adjusted = true;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Start)");
                }else if (OriginPunch > OkStart && OriginPunch <= LateStart){ //If clock in punch is between StartGrace and StartDock (Clock in between Grace period and Dock)
                    adjustment = LateStart;
                    adjusted = true;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Dock)");
                }else { // Interval Round clock
                    if (PunchMin%s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                        adjustment = AdjustedOriginPunch;
                        setAdjustedtimestamp(adjustment);
                        setEventData("(None)");

                    }else if ((PunchMin%s.getInterval())/s.getInterval() > 0.5){ //Pucnh happend in the second half of an interval

                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = StartWork - OriginPunch; // time elapsed from start of shift to clock in. This should be in millis
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed 
                        long Intervals = FI+1; //advance to next interval
                        long IntervalRoundMinutes = (Interval * Intervals); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                        adjustment = (AdjustedPunchIn.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");
                    }else{ //punch happened in first half of interval

                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = StartWork - OriginPunch; // time elapsed from start of shift to clock in. This should be in millis
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed
                        long IntervalRoundMinutes = (Interval * FI); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                        adjustment = (AdjustedPunchIn.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");
                    }
                }                
            }else { //Clocking in from lunch
                if (OriginPunch < CeaseBreak && OriginPunch < BeginBreak){ //If clock in punch is duirng lunch window
                    setTookLunch(false);
                    adjusted = true;
                    adjustment = CeaseBreak;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Lunch Stop)");
                }
            }
        }else if (punchtypeid == 0 && !weekend){ //Clock out on a week day
            System.err.print("Punch was clcok out for ");
            if (TookLunch != true){
                System.err.println("the day.\n");
                System.err.println("Clock out during clock out interval? " + (OriginPunch < LateStop && OriginPunch >= StopWork));
                System.err.println("Clock out within clock out grace?    " + (OriginPunch > OkStop && OriginPunch < StopWork));
                System.err.println("Clock out inside normal dock range?  " + (OriginPunch < OkStop && OriginPunch > EarlyStop));
                if (OriginPunch <= LateStop && OriginPunch >= StopWork){ //If clock out is between StopInterval and StopShift (clock out inside interval window)
                    adjustment = StopWork;
                    adjusted = true;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Stop)");
                }else if (OriginPunch >= OkStop && OriginPunch <= StopWork){ //If clock out is between StopGrace and StopShift (clock out inside grace period)
                    System.err.println("I am now in Clock Out by grace Period");
                    adjustment = StopWork;
                    System.err.println(adjustment);
                    adjusted = true;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Stop)");
                    System.err.println(printAdjustedTimestamp());
                }else if (OriginPunch < OkStop && OriginPunch >= EarlyStop){ //If clock out is between StopGrace and StopDock (clock out before grace period)
                    adjustment = EarlyStop;
                    adjusted = true;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Shift Dock)");
                }else{ //Interval Round Check
                    System.err.println("PunchMin = " + PunchMin + " Interval = " + Interval);
                    System.err.println("Punchmin % Interval = " + PunchMin%Interval);
                    System.err.println("PunchMin + PunchSec/60 / Interval = " + (PunchMin+(PunchSec/60))/Interval + " aka true value of PunchMoment/Interval");
                    if (PunchMin%s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                        System.err.println("Checking for Minutes % 15 =0 (Punch event occured on an interval)");
                        adjustment = AdjustedOriginPunch;
                        setAdjustedtimestamp(adjustment);
                        setEventData("(None)");

                    }else if ((PunchMin+(PunchSec/60))/s.getInterval() < 0.5){ //Pucnh happend in the second half of an interval
                        System.err.println("Checking for Minutes % 15 / 15 < 0.5 (Punch event occured during First half of an interval)");
                        System.err.println((PunchMin%s.getInterval())/s.getInterval() < 0.5);
                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = StopWork - OriginPunch; // time elapsed from clock out to end of shift. This should be in millis
                        System.err.println("TE = " + TE);
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed 
                        System.err.println("FI = " + FI);
                        long Intervals = FI; //advance to next interval
                        long IntervalRoundMinutes = (Interval * (Intervals-1)); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        AdjustedPunchOut.add(Calendar.MINUTE, i); //advance the time to the next interval
                        adjustment = (AdjustedPunchOut.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");

                    }else{ //punch happened in first half of interval
                        System.err.println("Checking for Minutes % 15 / 15 > 0.5 (Punch event occured during last half of an interval)");
                        //calculate how many intervals have passed since the start of the shift, 
                        long TE = StopWork - OriginPunch; // time elapsed from clock out to end of shift. This should be in millis
                        System.err.println("TE = " + TE);
                        long IM = s.getInterval()*60000; // interval in milliseconds
                        long FI = TE/IM; // number of full intervals elapsed
                        System.err.println("FI = " + FI);
                        long IntervalRoundMinutes = (Interval * (FI)); //convert number of intervals into minutes
                        int i = (int)IntervalRoundMinutes;
                        AdjustedPunchOut.add(Calendar.MINUTE, (i)); //advance the time to the next interval
                        adjustment = (AdjustedPunchOut.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                        setAdjustedtimestamp(adjustment);
                        setEventData("(Interval Round)");
                    }
                }
            }else{ //Clocking out for lunch
                System.err.println("lunch.");
                if (OriginPunch < CeaseBreak && OriginPunch > BeginBreak){ //Clock out during lunch window
                    setTookLunch(true);
                    adjusted = true;
                    adjustment = BeginBreak;
                    setAdjustedtimestamp(adjustment);
                    setEventData("(Lunch Start)");
                }
            }
        }

        //Interval round
        if (punchtypeid == 1 && weekend && !adjusted){ //Clock in
            if (PunchMin%s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                adjustment = AdjustedOriginPunch;
                setAdjustedtimestamp(adjustment);
                setEventData("(None)");
                
            }else if ((PunchMin%s.getInterval())/s.getInterval() > 0.5){ //Pucnh happend in the second half of an interval
                
                //calculate how many intervals have passed since the start of the shift, 
                long TE = StartWork - OriginPunch; // time elapsed from start of shift to clock in. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed 
                long Intervals = FI; //advance to next interval
                long IntervalRoundMinutes = (Interval * Intervals); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                AdjustedPunchIn.add(Calendar.MINUTE, -i); //advance the time to the next interval
                adjustment = (AdjustedPunchIn.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
            }else{ //punch happened in first half of interval
                
                //calculate how many intervals have passed since the start of the shift, 
                long TE = OriginPunch - StartWork; // time elapsed from start of shift to clock in. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed
                long IntervalRoundMinutes = (Interval * (FI-1)); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                AdjustedPunchIn.add(Calendar.MINUTE, + i); //advance the time to the next interval
                adjustment = (AdjustedPunchIn.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
            }
        }else if (punchtypeid == 0 && !adjusted){ //Interval round for clock out
            if (PunchMin%s.getInterval() == 0){ //None Clause: PunchMin/Inter = 0 :: reset seconds field to 0
                adjustment = AdjustedOriginPunch;
                setAdjustedtimestamp(adjustment);
                setEventData("(None)");
                
            }else if ((PunchMin%s.getInterval())/s.getInterval() < 0.5){ //Pucnh happend in the second half of an interval
                
                //calculate how many intervals until the end of the shift, 
                long TE = OriginPunch - StopWork; // time elapsed from clock out to end of shift. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed 
                long Intervals = FI; //advance to next interval
                long IntervalRoundMinutes = (Interval * (Intervals-1)); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                AdjustedPunchOut.add(Calendar.MINUTE, i); //advance the time to the next interval
                adjustment = (AdjustedPunchOut.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
                
            }else{ //punch happened in first half of interval
                //calculate how many intervals until the end of the shift, 
                long TE = OriginPunch - StopWork; // time elapsed from clock out to end of shift. This should be in millis
                long IM = s.getInterval()*60000; // interval in milliseconds
                long FI = TE/IM; // number of full intervals elapsed
                long IntervalRoundMinutes = (Interval * FI); //convert number of intervals into minutes
                int i = (int)IntervalRoundMinutes;
                AdjustedPunchOut.add(Calendar.MINUTE, i); //advance the time to the next interval
                adjustment = (AdjustedPunchOut.getTimeInMillis()); //convert calendar objecto to long and capture it for sending to setAdjustedtimestamp
                setAdjustedtimestamp(adjustment);
                setEventData("(Interval Round)");
            }
        }
        System.err.println("AdjustedPunchIn:         " + sdf.format(AdjustedPunchIn.getTimeInMillis()) + " " + AdjustedPunchIn.getTimeInMillis());
        System.err.println("AdjustedPunchOut:        " + sdf.format(AdjustedPunchOut.getTimeInMillis()) + " " + AdjustedPunchOut.getTimeInMillis() + "\n\n\n");
    }
}
