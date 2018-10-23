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

    //Constructor
    public Punch(int id, long originaltimestamp, int terminalid, String badgeid, int punchtypeid) {
        this.id = id;
        this.originaltimestamp = originaltimestamp;
        this.adjustedtimestamp = adjustedtimestamp;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.punchtypeid = punchtypeid;
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
    
    public String printOriginalTimestamp() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(originaltimestamp);
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String s = (sdf.format(gc.getTime()));
        
        return s;
    }
}
