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


    ResultSet rs;
    PreparedStatement pstmt;
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


    }




}
