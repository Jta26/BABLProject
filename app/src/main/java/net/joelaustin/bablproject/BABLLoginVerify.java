package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Joel on 2/6/2017.
 */

//WIP

public class BABLLoginVerify extends AsyncTask<Void, Void, Boolean> {

    ResultSet rs;
    PreparedStatement pstmt;

    BABLDataLocal localData = new BABLDataLocal();

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    private String strUsername;
    private String strPassword;
    private Context context;

    public BABLLoginVerify(Context context, String strUsername, String strPassword) {
        this.context = context;
        this.strUsername = strUsername;
        this.strPassword = strPassword;
    }

    public Boolean doInBackground(Void... Void) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;


        //Checks if USERNAMES and PASSWORDS are Equal, if they are, stops process;
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "SELECT UserID, Username, Password FROM Users WHERE Username=?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, strUsername.toUpperCase());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer intUserID = rs.getInt("UserID");
                String strUsernameVerify = rs.getString("Username");
                String strPasswordVerify = rs.getString("Password");
                if (strUsername.toUpperCase().equals(strUsernameVerify.toUpperCase()) && BCrypt.checkpw(strPassword,strPasswordVerify)) {
                    localData.set_intUserID(intUserID);
                    localData.set_strUsername(strUsername);
                    return true;
                }


            }
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
        return false;
    }

    protected void onPostExecute(Boolean result){

        if (result == true) {

            strPassword = null;
            new BABLDataRetrieve(context).execute();
        }
        if (result == false) {
            Toast.makeText(context, R.string.incorrectPass, Toast.LENGTH_LONG).show();
        }


    }




}
