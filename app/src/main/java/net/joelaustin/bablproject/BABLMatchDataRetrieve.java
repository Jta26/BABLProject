package net.joelaustin.bablproject;

import android.database.CursorJoiner;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gregm on 3/16/2017.
 */

public class BABLMatchDataRetrieve extends AsyncTask<Void, Void, String> {

    BABLMatchesDataLocal localmatchdata = new BABLMatchesDataLocal();

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    Connection conn;
    String ConnURL;
    PreparedStatement pstmt;
    ResultSet rs;
    int intMatchId;

    public BABLMatchDataRetrieve(){

    }
    protected String doInBackground(Void...voids) {
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            intMatchId = (Integer) localmatchdata.stackMatchID.pop();
            String query = "SELECT * FROM Users Where UserId=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, intMatchId);
            rs = pstmt.executeQuery();



            return "User Data Retrieved Successfully";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Not Successful";
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
            return "Not Successful";
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
            return "Not Successful";
        }
    }

    protected void onPostExecute(String results){

    }
}
