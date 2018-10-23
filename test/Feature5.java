package tas_fa18;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;

public class Feature5 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        db = new TASDatabase();
    }
    
    @Test
    public void testJSONShift1Weekday() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"punchdata\":\"Shift Start\",\"originaltimestamp\":\"1536321035000\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"1536321600000\",\"punchtypeid\":\"1\",\"terminalid\":\"104\",\"id\":\"3634\"},{\"punchdata\":\"Lunch Start\",\"originaltimestamp\":\"1536339834000\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"1536339600000\",\"punchtypeid\":\"0\",\"terminalid\":\"104\",\"id\":\"3687\"},{\"punchdata\":\"Lunch Stop\",\"originaltimestamp\":\"1536341021000\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"1536341400000\",\"punchtypeid\":\"1\",\"terminalid\":\"104\",\"id\":\"3688\"},{\"punchdata\":\"Shift Stop\",\"originaltimestamp\":\"1536352453000\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"1536352200000\",\"punchtypeid\":\"0\",\"terminalid\":\"104\",\"id\":\"3716\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(3634);
        Badge b = db.getBadge(p.getBadgeid());
        Shift s = db.getShift(b);
		
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginaltimestamp());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TASLogic.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testJSONShift1Weekend() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"punchdata\":\"Interval Round\",\"originaltimestamp\":\"1533984898000\",\"badgeid\":\"F1EE0555\",\"adjustedtimestamp\":\"1533985200000\",\"punchtypeid\":\"1\",\"terminalid\":\"105\",\"id\":\"1087\"},{\"punchdata\":\"Interval Round\",\"originaltimestamp\":\"1534007042000\",\"badgeid\":\"F1EE0555\",\"adjustedtimestamp\":\"1534006800000\",\"punchtypeid\":\"0\",\"terminalid\":\"105\",\"id\":\"1162\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(1087);
        Badge b = db.getBadge(p.getBadgeid());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginaltimestamp());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TASLogic.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testJSONShift2Weekday() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"punchdata\":\"Shift Start\",\"originaltimestamp\":\"1537289973000\",\"badgeid\":\"08D01475\",\"adjustedtimestamp\":\"1537290000000\",\"punchtypeid\":\"1\",\"terminalid\":\"104\",\"id\":\"4943\"},{\"punchdata\":\"None\",\"originaltimestamp\":\"1537324227000\",\"badgeid\":\"08D01475\",\"adjustedtimestamp\":\"1537324227000\",\"punchtypeid\":\"0\",\"terminalid\":\"104\",\"id\":\"5004\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(4943);
        Badge b = db.getBadge(p.getBadgeid());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginaltimestamp());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TASLogic.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
}