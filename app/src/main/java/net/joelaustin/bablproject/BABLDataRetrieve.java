package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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
    public BABLDataRetrieve(Context context){
        this.context = context;
    }


    protected String doInBackground(Void... voids) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        //Finds where USERNAME is equal, then sets all other data under that username.
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


                if (strUsernameVerify.equals(strUsername)) {
                    //sets First Name
                    String strFirstName = rs.getString("FirstName");
                    localData.set_strFirstName(strFirstName);
                    //Sets Languages 1-5
                    String strLang1 = rs.getString("Lang1");
                    localData.set_strLang1(strLang1);

                    String strLang2 = rs.getString("Lang2");
                    localData.set_strLang2(strLang2);

                    String strLang3 = rs.getString("Lang3");
                    localData.set_strLang3(strLang3);

                    String strLang4 = rs.getString("Lang4");
                    localData.set_strLang4(strLang4);

                    String strLang5 = rs.getString("Lang5");
                    localData.set_strLang5(strLang5);
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


        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        Toast.makeText(context, R.string.UserDataRetrieved, Toast.LENGTH_SHORT).show();
    }




}
