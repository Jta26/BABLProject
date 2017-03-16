package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Joel-PC on 2/6/2017.
 */

public class BABLDataRetrieve extends AsyncTask<Void, Void, String> {

    BABLDataLocal localData = new BABLDataLocal();


    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";
    //private String test = "Test";

    //String strUsername = localData.get_strUsername();

    ResultSet rs;
    ResultSet rs2;
    ResultSet rs3;
    PreparedStatement pstmt;
    PreparedStatement pstmt2;
    PreparedStatement pstmt3;
    Context context;

    public BABLDataRetrieve(Context context){
        this.context = context;
    }


    protected String doInBackground(Void... voids) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        //Finds where UserID is equal, then sets all other data under that UserID.
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "SELECT * FROM Users WHERE UserID=?;";

            pstmt = conn.prepareStatement(query);
            int UserID = localData.get_intUserID();
            pstmt.setInt(1, UserID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                //set hashed password locally
                String strHashedPass = rs.getString("Password");
                localData.set_strHashedPass(strHashedPass);
                //sets First Name
                String strFirstName = rs.getString("FirstName");
                localData.set_strFirstName(strFirstName);
                //Sets facebook User Id
                String strFacebookId = rs.getString("FacebookID");
                localData.set_strFacebookId(strFacebookId);
                //Sets Campus Attending
                Integer intCampusAttending = rs.getInt("Attending");
                localData.set_intCampusAttend(intCampusAttending);
                //Sets Campus Preferences
                Boolean boolMain = rs.getBoolean("Main");
                localData.set_boolMain(boolMain);

                Boolean boolJohnstown = rs.getBoolean("Johnstown");
                localData.set_boolJohnstown(boolJohnstown);

                Boolean boolBradford = rs.getBoolean("Bradford");
                localData.set_boolBradford(boolBradford);

                Boolean boolTitusville = rs.getBoolean("Titusville");
                localData.set_boolTitusville(boolTitusville);

                Boolean boolGreensburg = rs.getBoolean("Greensburg");
                localData.set_boolGreensburg(boolGreensburg);



            }
            //Update Languages
            query = "SELECT Language FROM UserLanguages WHERE UserID=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, UserID);
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                switch (i) {
                    case 0:
                        localData.set_strLang1(rs.getString("Language"));
                        break;
                    case 1:
                        localData.set_strLang2(rs.getString("Language"));
                        break;
                    case 2:
                        localData.set_strLang3(rs.getString("Language"));
                        break;
                    case 3:
                        localData.set_strLang4(rs.getString("Language"));
                        break;
                    case 4:
                        localData.set_strLang5(rs.getString("Language"));
                        break;
                }


                i++;
            }
            //Update Matches
            query = "SELECT MatchingID FROM Matches WHERE UserID=?";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, UserID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String strFBID, strFirstname;
                String strLangs = "";
                Integer intUserID = rs.getInt("MatchingID");
                String query2 = "SELECT FacebookID,Firstname FROM Users WHERE UserID=?";
                pstmt2 = conn.prepareStatement(query2);
                pstmt2.setInt(1, intUserID);
                while(rs2.next()){
                    strFBID = rs2.getString("FacebookID");
                    strFirstname = rs2.getString("Firstname");
                    String query3 = "SELECT Language FROM UserLanguages WHERE UserID=?";
                    pstmt3 = conn.prepareStatement(query2);
                    pstmt3.setInt(1, intUserID);
                    while(rs3.next()){
                        strLangs += rs3.getString("Language") + "\n";
                    }
                    localData.addMatch(strFBID, intUserID, strFirstname,strLangs);
                }
            }

            query = "SELECT UserID FROM Matches WHERE MatchingID=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, UserID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String strFBID, strFirstname;
                String strLangs = "";
                Integer intUserID = rs.getInt("UserID");
                String query2 = "SELECT FacebookID,Firstname FROM Users WHERE UserID=?";
                pstmt2 = conn.prepareStatement(query2);
                pstmt2.setInt(1, intUserID);
                while(rs2.next()){
                    strFBID = rs2.getString("FacebookID");
                    strFirstname = rs2.getString("Firstname");
                    String query3 = "SELECT Language FROM UserLanguages WHERE UserID=?";
                    pstmt3 = conn.prepareStatement(query2);
                    pstmt3.setInt(1, intUserID);
                    while(rs3.next()){
                        strLangs += rs3.getString("Language") + "\n";
                    }
                    localData.addMatch(strFBID, intUserID, strFirstname,strLangs);
                }

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



    protected void onPostExecute(String result){


        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast.makeText(context, R.string.UserDataRetrieved, Toast.LENGTH_SHORT).show();
    }




}
