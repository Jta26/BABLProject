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
//This Class is for retrieving Match's Data based on the Stack in Matches Data Local Class
//As well as filtering out matches that do not meet the user's selected campuses.
public class BABLMatchDataRetrieve extends AsyncTask<Void, Void, String> {

    public AsyncResponse delegate = null;
    BABLMatchesDataLocal localmatchdata = new BABLMatchesDataLocal();
    BABLDataLocal localdata = new BABLDataLocal();

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
        localmatchdata.set_strFacebookID(null);
        localmatchdata.set_intCampusAttend(null);
        localmatchdata.set_intUserID(null);
        localmatchdata.set_strUsername(null);
        localmatchdata.set_strFirstName(null);
        localmatchdata.set_strLang1(null);
        localmatchdata.set_strLang2(null);
        localmatchdata.set_strLang3(null);
        localmatchdata.set_strLang4(null);
        localmatchdata.set_strLang4(null);
        localmatchdata.set_strLang5(null);
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            try {
                intMatchId = (Integer) localmatchdata.stackMatchID.pop();
            }
            catch (Exception e){
                Toast.makeText(context, "No Matches Remain", Toast.LENGTH_SHORT).show();
                return "No Matches Remain";
            }
            int intMainFind = 6;
            int intJohnstownFind = 6;
            int intBradfordFind = 6;
            int intTitusvilleFind = 6;
            int intGreensburgFind = 6;
            if (localdata.get_boolMain()) {
                intMainFind = 0;
            }
            if (localdata.get_boolJohnstown()) {
                intJohnstownFind = 1;
            }
            if (localdata.get_boolBradford()) {
                intBradfordFind = 2;
            }
            if (localdata.get_boolTitusville()) {
                intTitusvilleFind = 3;
            }
            if (localdata.get_boolGreensburg()) {
                intGreensburgFind = 4;
            }

            String query = "SELECT * FROM Users Where UserId=? AND (Attending = ? OR Attending = ? OR Attending = ? OR Attending = ? OR Attending = ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, intMatchId);
            pstmt.setInt(2, intMainFind);
            pstmt.setInt(3, intJohnstownFind);
            pstmt.setInt(4, intBradfordFind);
            pstmt.setInt(5, intTitusvilleFind);
            pstmt.setInt(6, intGreensburgFind);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                doInBackground();
            }
            else {
                do {
                    localmatchdata.set_intUserID(intMatchId);
                    localmatchdata.set_strUsername(rs.getString("Username"));
                    localmatchdata.set_strFirstName(rs.getString("FirstName"));
                    localmatchdata.set_intCampusAttend(rs.getInt("Attending"));
                    localmatchdata.set_strFacebookID(rs.getString("FacebookID"));
                } while (rs.next());
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
            return "Match Successfully Retrieved";



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

    protected void onProgressUpdate(Void...voids) {
        new BABLMatchDataRetrieve(context).execute();
    }
    @Override
    protected void onPostExecute(String results){
        delegate.processFinish(results);

    }
}

