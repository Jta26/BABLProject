package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
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
    BABLDataLocal datalocal = new BABLDataLocal();

    Context context;
    private int intUserID = datalocal.get_intUserID();

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    Connection conn;
    String ConnURL;
    PreparedStatement pstmt;
    ResultSet rs;

    public BABLMatchRetrieve(Context context) {
        this.context = context;

    }

    protected String doInBackground(Void... voids) {
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);




            String query = "SELECT UserId, MatchingId FROM Matches WHERE (UserId=? AND UserConfirm=0) OR (MatchingId=? AND MatchingConfirm=0)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, intUserID);
            pstmt.setInt(2, intUserID);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                int tempId = rs.getInt("UserId");
                int tempMatchId = rs.getInt("MatchingId");

                if(tempId == intUserID) {
                    Matcheslocaldata.stackMatchID.push(tempMatchId);
                }
                else {
                    Matcheslocaldata.stackMatchID.push(tempId);
                }
            }

            return "Successfully Logged In";
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
        if (result.equals("Successfully Logged In")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else {

        }

    }

}
