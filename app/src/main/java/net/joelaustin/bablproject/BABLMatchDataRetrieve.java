package net.joelaustin.bablproject;

import android.content.Context;
import android.database.CursorJoiner;
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

public class BABLMatchDataRetrieve extends AsyncTask<Void, Void, String> {

    public AsyncResponse delegate = null;
    BABLMatchesDataLocal localmatchdata = new BABLMatchesDataLocal();

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
    int intMatchId;

    public BABLMatchDataRetrieve(Context context){
        this.context = context;

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
            while(rs.next()){
                localmatchdata.set_intUserID(intMatchId);
                localmatchdata.set_strUsername(rs.getString("Username"));
                localmatchdata.set_strFirstName(rs.getString("FirstName"));
                localmatchdata.set_intCampusAttend(rs.getInt("Attending"));
                localmatchdata.set_strFacebookID(rs.getString("FacebookID"));
            }

            query = "Select * FROM UserLanguages WHERE UserId=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, intMatchId);
            rs = pstmt.executeQuery();
                int i = 0;
                while (rs.next()) {
                    switch (i) {
                        case 0:
                            localmatchdata.set_strLang1(rs.getString("Language"));
                            break;
                        case 1:
                            localmatchdata.set_strLang2(rs.getString("Language"));
                            break;
                        case 2:
                            localmatchdata.set_strLang3(rs.getString("Language"));
                            break;
                        case 3:
                            localmatchdata.set_strLang4(rs.getString("Language"));
                            break;
                        case 4:
                            localmatchdata.set_strLang5(rs.getString("Language"));
                            break;
                    }
                    i++;
                }
            return "New Match Retrieved";
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
    @Override
    protected void onPostExecute(String results){
        delegate.processFinish(results);

    }
}

