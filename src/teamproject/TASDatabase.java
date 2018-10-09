package teamproject;

import java.sql.*;



public class TASDatabase{

 Connection conn = null;
 PreparedStatement pstSelect = null, pstUpdate = null;
 ResultSet resultset = null;
 ResultSetMetaData metadata = null;

   
   boolean hasresults;
   int resultCount, columnCount, updateCount = 0;
        
    try
    {
            /* Identify the Server */
            
            String server = ("jdbc:mysql://localhost/TAS_FA18");
            String username = "root";
            String password = "96b3812W";
            /* Load the MySQL JDBC Driver */
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            /* Open Connection */
            conn = DriverManager.getConnection(server, username, password);
       
                private int getPunch(int a)
                {
                    if (conn != null) 
                    {
                        String  query = "SELECT * FROM punch WHERE id =" + a;
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(query);
                         while (rs.next())
                         {
                           int id = rs.getInt("id");
                           int terminalId = rs.getInt("terminalid");
                           String badgeId = rs.getString("badgeid");
                           Timestamp timeStamp = rs.getTimestamp("originaltimestamp");
                           int punchTypeId = rs.getInt("punchtypeid");

                           new Punch(id,terminalId, badgeId,timeStamp, punchTypeId);
                           
                         
                         }
                         st.close();
                         
                    }
                    return Punch;
                }
                    
                private int getBadge(int a)
                {
                    if (conn != null) 
                    {
                        String  query = "SELECT * FROM badge WHERE id =" + a;
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(query);
                         while (rs.next())
                         {
                           String badgeId = rs.getString("id");
                           String description = rs.getString("description");

                           new Badge(badgeId,description);
                           
                           
                         }
                         st.close();
                         
                    }
                       return Badge;
                   
                }
                
                 private int getShift(int a)
                {
                    if (conn != null) 
                    {
                        String  query = "SELECT * FROM shift WHERE id =" + a;
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

                           new Shift(id, description, start, stop, interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
                           
                           
                         }
                         st.close();
                    }         
                    return Shift;
                }
                 
                 private int getShift(Badge a)
                {
                    if (conn != null) 
                    {
                       String  query = "SELECT * FROM shift WHERE id =" + Badge.getId());
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

                           new Shift(id, description, start, stop, interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
                           
                           
                         }
                         st.close();
                    }
                       
                   return Shift;
                }

       catch (Exception e)
            {
              System.err.println("Got an exception! ");
              System.err.println(e.getMessage());
            }
    }
}



             

        
        
        

    
