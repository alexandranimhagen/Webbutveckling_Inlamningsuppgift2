package models;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;

/**
 * Db Connector Singleton. Uses queries defined in queries.csv
 * Access object with MySQLConnector.getConnector();
 * Use one of <operation>Query methods.
 */
public class MySQLConnector {

    final private String queriesPath = "src/main/java/models/queries.csv";
    private static MySQLConnector connector = null;

    private MySQLConnector(){};

    public static MySQLConnector getConnector() {
        if (connector == null) {
            connector = new MySQLConnector();
        }
        return connector;
    }

    /**
     * Gets data from the Db, based on the query
     * @param queryName String
     * @return LinkedList with data String Arrays
     */
    public LinkedList<String[]> selectQuery(String queryName) throws NullPointerException{
        try(BufferedReader br = new BufferedReader(new FileReader(queriesPath))) {
            String query = br.readLine();
            String[] line = null;
            while (query != null){
                line = query.trim().split(",");
                if(line[0].equals(queryName)){
                    return select(line[1], line[2], line[3], line[4], line[5], line[6]);
                }
                query = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Returns a list of String Arrays with data rows recieved from Db.
     * Each datapoint has type of String or null if missing.
     * @param query String
     */
    private LinkedList<String[]> select(String query, String db, String ip, String port, String user, String password){
        LinkedList<String[]> queryReturn = new LinkedList<String[]>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();

            //add header row to queryReturn
            String[] headerRow = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                headerRow[i-1] = md.getColumnName(i);
            }
            queryReturn.add(headerRow);

            //add data row to queryReturn
            while (rs.next()){
                String[] dataRow = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    dataRow[i-1] = rs.getString(i);
                }
                queryReturn.add(dataRow);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return queryReturn;
    }

}
