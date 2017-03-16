package net.joelaustin.bablproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gregm on 3/16/2017.
 */

public class BABLMatchRetrieve extends AsyncTask<Void, Void, String> {

    BABLMatchesDataLocal Matcheslocaldata = new BABLMatchesDataLocal();

    Context context;
    private int intUserID;
    private String strFirstname;
    private String strLang1;
    private String strLang2;
    private String strLang3;
    private String strLang4;
    private String strLang5;

    private int intCampusAttend;

    private String strFacebookId;

    public BABLMatchRetrieve(Context context, int intUserID, String strFirstname, String strLang1, String strLang2, String strLang3, String strLang4, String strLang5, int intCampusAttend, String strFacebookId) {
        this.context = context;
        this.strFirstname = strFirstname;
        this.strLang1 = strLang1;
        this.strLang2 = strLang2;
        this.strLang3 = strLang3;
        this.strLang4 = strLang4;
        this.strLang5 = strLang5;
        this.intCampusAttend = intCampusAttend;
        this.strFacebookId = strFacebookId;

    }
    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    Connection conn;
    String ConnURL;
    PreparedStatement pstmt;
    ResultSet rs;


    protected String doInBackground(Void... voids) {
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "SELECT UserId, MatchingId FROM Matches WHERE UserId=? OR MatchingId=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, intUserID);
            pstmt.setInt(2, intUserID);
            rs = pstmt.executeQuery();

            while (rs.next()) {

            }

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
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

}
