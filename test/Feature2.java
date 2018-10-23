package tas_fa18;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;
import static org.junit.Assert.*;

public class Feature2 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        db = new TASDatabase();
    }
    
    @Test
    public void testInsertCheckPunch() {
		
        /* Create New Punch Object */

        Punch p1 = new Punch(db.getBadge("021890C0"), 101, 1);
        
        /* Create Timestamp Objects */
        
        GregorianCalendar ots = new GregorianCalendar();
        GregorianCalendar rts = new GregorianCalendar();
		
        /* Get Punch Properties */
        
        String badgeid = p1.getBadgeid();
        
        ots.setTimeInMillis(p1.getOriginaltimestamp());
        String originaltimestamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ots.getTime());
        
        int terminalid = p1.getTerminalid();
        int eventtypeid = p1.getPunchtypeid();
		
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
		
        /* Retrieve New Punch */
        
        Punch p2 = db.getPunch(punchid);
		
        /* Compare Punches */

        assertEquals(badgeid, p2.getBadgeid());

        rts.setTimeInMillis(p2.getOriginaltimestamp());
        
        assertEquals(originaltimestamp, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(rts.getTime()));
        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(eventtypeid, p2.getPunchtypeid());
        
    }
    
    @Test
    public void testRetrievePunchList1() {
        
        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        GregorianCalendar ts = new GregorianCalendar();
        
        ts.set(Calendar.DAY_OF_MONTH, 17);
        ts.set(Calendar.YEAR, 2018);
        ts.set(Calendar.MONTH, 8);
        ts.set(Calendar.HOUR, 0);
        ts.set(Calendar.MINUTE, 0);
        ts.set(Calendar.SECOND, 0);
        
        Badge b = db.getBadge("67637925");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts.getTimeInMillis());
        
        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginalTimestamp());
            s1.append("\n");
        }
        
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();
        
        /* Add Punches */
        
        p2.add(db.getPunch(4716));
        p2.add(db.getPunch(4811));
        p2.add(db.getPunch(4813));
        p2.add(db.getPunch(4847));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginalTimestamp());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals( s1.toString(), s2.toString() );
        
    }
    
    @Test
    public void testRetrievePunchList2() {
        
        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        GregorianCalendar ts = new GregorianCalendar();
        
        ts.set(Calendar.DAY_OF_MONTH, 27);
        ts.set(Calendar.YEAR, 2018);
        ts.set(Calendar.MONTH, 8);
        ts.set(Calendar.HOUR, 0);
        ts.set(Calendar.MINUTE, 0);
        ts.set(Calendar.SECOND, 0);
        
        Badge b = db.getBadge("87176FD7");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts.getTimeInMillis());
        
        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginalTimestamp());
            s1.append("\n");
        }
        
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();
        
        /* Add Punches */
        
        p2.add(db.getPunch(6089));
        p2.add(db.getPunch(6112));
        p2.add(db.getPunch(6118));
        p2.add(db.getPunch(6129));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginalTimestamp());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals( s1.toString(), s2.toString() );
        
    }

    @Test
    public void testRetrievePunchList3() {
        
        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        GregorianCalendar ts = new GregorianCalendar();
        
        ts.set(Calendar.DAY_OF_MONTH, 5);
        ts.set(Calendar.YEAR, 2018);
        ts.set(Calendar.MONTH, 8);
        ts.set(Calendar.HOUR, 0);
        ts.set(Calendar.MINUTE, 0);
        ts.set(Calendar.SECOND, 0);
        
        Badge b = db.getBadge("95497F63");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts.getTimeInMillis());
        
        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginalTimestamp());
            s1.append("\n");
        }
        
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();
        
        /* Add Punches */
        
        p2.add(db.getPunch(3463));
        p2.add(db.getPunch(3482));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginalTimestamp());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals( s1.toString(), s2.toString() );
        
    }
    
}