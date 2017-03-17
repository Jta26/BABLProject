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
 * Created by Joel on 3/17/2017.
 */
//This Class is for Entering the user's chosen match result, default 0, Yes 1, No 2;
public class BABLEnterMatchResult extends AsyncTask<Boolean, Void, String>{

    BABLDataLocal localdata = new BABLDataLocal();
    BABLMatchesDataLocal matchlocaldata = new BABLMatchesDataLocal();

    private int intUserId = localdata.get_intUserID();
    private int intMatchId = matchlocaldata.get_intUserID();

    private int intMatchChoice = 0;

    private Boolean boolIsUserId = false;

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    Context context;
    Connection conn;
    String ConnURL;
    PreparedStatement pstmt;
    ResultSet rs;

    public BABLEnterMatchResult(Context context) {
        this.context = context;
    }
    protected String doInBackground(Boolean...boolMatchType) {

        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            //This Query get both the UserID and MatchingID from the Database, and then finds which one IS == to the User's locally stored UserID. Then it determines if you are the UserId or the MatchingId.
            String query = "SELECT UserId, MatchingId FROM Matches WHERE (UserId=? AND MatchingId=?) OR (UserId=? AND MatchingId=?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,intUserId);
            pstmt.setInt(2,intMatchId);

            pstmt.setInt(3,intMatchId);
            pstmt.setInt(4, intUserId);

            rs = pstmt.executeQuery();


            while (rs.next()) {
                int dbUserId = rs.getInt("UserId");
                int dbMatchingId = rs.getInt("MatchingId");

                if (intUserId == dbUserId) {
                    boolIsUserId = true;
                }
                else if(intUserId == dbMatchingId) {
                    boolIsUserId = false;
                }
            }
            if (boolIsUserId) {
                query = "INSERT INTO Matches UserConfirm VALUES (?)";
                pstmt = conn.prepareStatement(query);
                if (boolMatchType[0]) {
                   intMatchChoice = 1;
                }
                else {
                    intMatchChoice = 2;
                }
                pstmt.setInt(1, intMatchChoice);
                pstmt.execute();
            }
            else {
                query = "UPDATE Matches SET MatchingConfirm=? WHERE (UserId=? AND MatchingId=?) OR (UserId=? AND MatchingId=?)";
                pstmt = conn.prepareStatement(query);
                if (boolMatchType[0].booleanValue()) {
                    intMatchChoice = 1;
                }
                else {
                    intMatchChoice = 2;
                }
                pstmt.setInt(1, intMatchChoice);
                pstmt.setInt(2, intUserId);
                pstmt.setInt(3, intMatchId);

                pstmt.setInt(4, intMatchId);
                pstmt.setInt(5, intUserId);
                pstmt.executeUpdate();
            }

            return "Match Choice Recorded";
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
    protected void onPostExecute(String results) {
        Toast.makeText(context, results, Toast.LENGTH_LONG).show();
    }
}
