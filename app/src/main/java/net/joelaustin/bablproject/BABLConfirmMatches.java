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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Joel on 3/17/2017.
 */
//This Class is for Confirming if there are any new Matches where 1 == 1 in the database.
public class BABLConfirmMatches extends AsyncTask<Void, Void, Boolean> {

    BABLMatchesDataLocal Matcheslocaldata = new BABLMatchesDataLocal();
    BABLDataLocal datalocal = new BABLDataLocal();

    Context context;
    private int intUserID = datalocal.get_intUserID();
    private boolean boolIsUserId = false;

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    Connection conn;
    String ConnURL;
    PreparedStatement pstmt;
    ResultSet rs;

    public BABLConfirmMatches(Context context) {
        this.context = context;
    }

    protected Boolean doInBackground(Void...voids) {
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "SELECT UserId, MatchingId FROM Matches WHERE (UserConfirm=1 AND MatchingConfirm=1) AND (UserId=? OR MatchingId=?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, intUserID);
            pstmt.setInt(2, intUserID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int dbUserId = rs.getInt("UserId");
                int dbMatchingId = rs.getInt("MatchingId");

                if (intUserID == dbUserId) {
                    boolIsUserId = true;
                }
                else if(intUserID == dbMatchingId) {
                    boolIsUserId = false;
                }
                if(boolIsUserId) {
                    datalocal.stkConfirmedMatches.push(dbMatchingId);

                }
                else {
                    datalocal.stkConfirmedMatches.push(dbUserId);
                }

            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
            return false;
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
            return false;
        }
    }
    protected void onPostExecute(Boolean results){
        if(results) {
            for (int i = 0; i <= datalocal.stkConfirmedMatches.size(); i++){
                new BABLShowConfirmedMatches(context).execute();
            }


        }
        else {
            Toast.makeText(context, "No Match Found", Toast.LENGTH_LONG).show();
        }


    }


}
