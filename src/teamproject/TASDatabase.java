package teamproject;

import java.sql.*;
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
   String server = ("jdbc:mysql://localhost/TAS_FA18");
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
    public Badge getBadge(int a)
    {
        Badge badge = null; 
        try
        {
            conn = initiateConnection();
            String  query = "SELECT * FROM badge WHERE id =" + a;
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
            //SELECT *, MINUTE(`start`) AS startminute, HOUR(`start`) AS starthour FROM shift;
            // turn the time to int
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
             while (rs.next())
             {
                 int id = rs.getInt("id");
                 String description = rs.getString("description");
                 Time start = rs.getTime("start");
                 Time stop = rs.getTime("stop");
                 int interval = rs.getInt("interval");
                 int gracePeriod = rs.getInt("graceperiod");
                 int dock = rs.getInt("dock");
                 Time lunchStart = rs.getTime("lunchstart");
                 Time lunchStop = rs.getTime("lunchstop");
                 int lunchDeduct = rs.getInt("lunchdeduct");
                 shift = new Shift(id, description, start, stop, interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
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
            String  query = ("SELECT * FROM shift WHERE id =" + a.getId());
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                Time start = rs.getTime("start");
                Time stop = rs.getTime("stop");
                int interval = rs.getInt("interval");
                int gracePeriod = rs.getInt("graceperiod");
                int dock = rs.getInt("dock");
                Time lunchStart = rs.getTime("lunchstart");
                Time lunchStop = rs.getTime("lunchstop");
                int lunchDeduct = rs.getInt("lunchdeduct");

                shift = new Shift(id, description, start, stop, interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
                //having issues with above, may need Shift to accept some time types
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
        
        return id;
    }
}



             

        
        
        

    
