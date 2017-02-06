package net.joelaustin.bablproject;

import android.content.Context;
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
 * Created by Joel-PC on 2/6/2017.
 */

public class BABLDataRetrieve extends AsyncTask<Void, Void, String> {

    BABLDataLocal localData = new BABLDataLocal();


    private String ip = "databaseforbabl.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "DbBABL";
    private String un = "gregmckibbin";
    private String password = "password";
    private String test = "Test";

    String strUsername = localData.get_strUsername();

    ResultSet rs;
    PreparedStatement pstmt;
    Context context;

    public BABLDataRetrieve(Context context) {

        this.context = context;
    }


    protected String doInBackground(Void... voids) {

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

            String query = "SELECT * FROM Users";

            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                String strUsernameVerify = rs.getString("Username");
                String strFirstName = rs.getString("FirstName");
                if (strUsernameVerify.equals(strUsername)) {
                    localData.set_strFirstName(strFirstName);



                    return "User Data Successfully Retrieved";
                }

            }
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
        return "Not Successful";
    }



    protected void onPostExecute(String result){


        Toast.makeText(context, "Test", Toast.LENGTH_LONG);
    }




}
