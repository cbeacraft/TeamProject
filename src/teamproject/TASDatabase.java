package teamproject;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;



public class TASDatabase{

    Connection conn = null;
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;

    boolean hasresults;
    int resultCount, columnCount, updateCount = 0;


    /* Identify the Server */
   String server = ("jdbc:mysql://localhost/tas");
   String username = "tasuser";
   String password = "teamc";
    
    public Connection initiateConnection() throws SQLException{

        try
        {
            /* Load the MySQL JDBC Driver */
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Open Connection */
            conn = DriverManager.getConnection(server, username, password);
            return conn;
        }

        catch (InstantiationException ex) 
        {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    //Punch Method
    public Punch getPunch(int a)
    {
        Punch punch = null;

        try
        {
            conn = initiateConnection();
            String  query = "SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS ts FROM punch WHERE id =" + a;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

             while (rs.next())
            {
                int id = rs.getInt("id");
                int terminalId = rs.getInt("terminalid");
                String badgeId = rs.getString("badgeid");
                long timeStamp = rs.getLong("ts");
                int punchTypeId = rs.getInt("punchtypeid");

                punch = new Punch(id, timeStamp, terminalId, badgeId, punchTypeId);
            }
            st.close();
            return punch;
              
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return punch;
    }

    //Badge Method
    public Badge getBadge(String a)
    {
        Badge badge = null; 
        try
        {
            conn = initiateConnection();
            String  query = "SELECT * FROM badge WHERE id ='" + a + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                String badgeId = rs.getString("id");
                String description = rs.getString("description");
                badge = new Badge(badgeId,description);
            }

          st.close();                      
        }
        catch (SQLException ex) 
        {
          Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return badge;   
    }

    public Shift getShift(int a)
    {
       Shift shift = null;
       try
        {
            conn = initiateConnection();
            String  query = "SELECT * FROM shift WHERE id =" + a;
            String startStopQuery = "SELECT *, MINUTE(`start`) AS startminute, HOUR(`start`) AS starthour, MINUTE(`stop`) AS stopminute, HOUR(`stop`) AS stophour FROM shift WHERE id =" + a;
            String lunchQuery = "SELECT *, MINUTE(`lunchstart`) AS lunchstartminute, HOUR(`lunchstart`) AS lunchstarthour, MINUTE(`lunchstop`) AS lunchstopminute, HOUR(`lunchstop`) AS lunchstophour FROM shift WHERE id =" + a;
            //MINUTE(`start`) AS startminute, HOUR(`start`) AS starthour FROM shift
            // turn the time to int
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Statement sta = conn.createStatement();
            ResultSet rst = sta.executeQuery(startStopQuery);
            Statement start = conn.createStatement();
            ResultSet rset = start.executeQuery(lunchQuery);
            
             while (rs.next())
             {
                 rst.next();
                 rset.next();
                 
                 int id = rs.getInt("id");
                 String description = rs.getString("description");
                 int startHour = rst.getInt("starthour");
                 int stopHour = rst.getInt("stophour");
                 int startMin = rst.getInt("startminute");
                 int stopMin = rst.getInt("stopminute");
                 int lunchStartHour = rset.getInt("lunchstarthour");
                 int lunchStopHour = rset.getInt("lunchstophour");
                 int lunchStartMin = rset.getInt("lunchstartminute");
                 int lunchStopMin = rset.getInt("lunchstopminute");
                 int interval = rs.getInt("interval");
                 int gracePeriod = rs.getInt("graceperiod");
                 int dock = rs.getInt("dock");
                 int lunchDeduct = rs.getInt("lunchdeduct");
                 shift = new Shift(id, description, startHour, startMin, stopHour, stopMin, interval, gracePeriod, dock, lunchStartHour,lunchStartMin, lunchStopHour, lunchStopMin, lunchDeduct);
                 //having issues with above, may need Shift to accept some time types
                 // create q default contructor to break up the above line
             }

             st.close();
        }

        catch (SQLException ex) 
        {
          Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }    

       return shift;
    }

    //is there a reason why we have 2 getShifts? Only difference are the parameter types
    public Shift getShift(Badge a)
    {
       Shift shift = null;
        try {
            conn = initiateConnection();
            String  query = ("SELECT * FROM shift WHERE id ='" + a.getId() + "'" );
            String startStopQuery = "SELECT *, MINUTE(`start`) AS startminute, HOUR(`start`) AS starthour, MINUTE(`stop`) AS stopminute, HOUR(`stop`) AS stophour FROM shift WHERE id ='" + a.getId() + "'";
            String lunchQuery = "SELECT *, MINUTE(`lunchstart`) AS lunchstartminute, HOUR(`lunchstart`) AS lunchstarthour, MINUTE(`lunchstop`) AS lunchstopminute, HOUR(`lunchstop`) AS lunchstophour FROM shift WHERE id ='" + a.getId() + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Statement sta = conn.createStatement();
            ResultSet rst = sta.executeQuery(startStopQuery);
            Statement start = conn.createStatement();
            ResultSet rset = start.executeQuery(lunchQuery);

            while (rs.next())
            {
                int id = rs.getInt("id");
                 String description = rs.getString("description");
                 int startHour = rst.getInt("starthour");
                 int stopHour = rst.getInt("stophour");
                 int startMin = rst.getInt("startminute");
                 int stopMin = rst.getInt("stopminute");
                 int lunchStartHour = rset.getInt("lunchstarthour");
                 int lunchStopHour = rset.getInt("lunchstophour");
                 int lunchStartMin = rset.getInt("lunchstartminute");
                 int lunchStopMin = rset.getInt("lunchstopminute");
                 int interval = rs.getInt("interval");
                 int gracePeriod = rs.getInt("graceperiod");
                 int dock = rs.getInt("dock");
                 int lunchDeduct = rs.getInt("lunchdeduct");
                 shift = new Shift(id, description, startHour, startMin, stopHour, stopMin, interval, gracePeriod, dock, lunchStartHour,lunchStartMin, lunchStopHour, lunchStopMin, lunchDeduct);
            }
            st.close();
        } 

        catch (SQLException ex) 
        {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
      return shift;
    }
    
    public int insertPunch(Punch p){
       long adjustedTime = p.getAdjustedtimestamp();
       String badgeId = p.getBadgeid();
       int id = p.getId();
       long originalTime = p.getOriginaltimestamp();
       int punchTypeId = p.getPunchtypeid();
       int terminalId = p.getTerminalid();
       
       try{
           conn = initiateConnection();
           String insertPunchQuery = "";
           Statement st = conn.createStatement();
           ResultSet rs = st.executeQuery(insertPunchQuery);
           
       }
       catch (SQLException ex) 
       {
           Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return id;
    }
    
    public ArrayList getDailyPunchList(Badge b, long ts) {
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(ts);
        
	ArrayList dailyPunchList = new ArrayList();
	//dailyPunchList.add();
	return dailyPunchList;
    }
}



             

        
        
        

    
