package net.joelaustin.bablproject;

/**
 * Created by Joel-PC on 2/3/2017.
 */

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


public class BABLDatabase extends AsyncTask<String, Void, String>{


    private Context context;
    private String strUsername;
    private String strPassword;
    private String strFirstName;
    private Integer intCampusSelect;
    private Boolean boolMain;
    private Boolean boolJohnstown;
    private Boolean boolBradford;
    private Boolean boolTitusville;
    private Boolean boolGreensburg;
    public BABLDatabase(Context context, String strUsername, String strPassword, String strFirstName,Integer intCampusSelect, Boolean boolMain, Boolean boolJohnstown, Boolean boolBradford, Boolean boolTitusville, Boolean boolGreensburg) {
        this.context = context;
        this.strUsername = strUsername;
        this.strPassword = strPassword;
        this.strFirstName = strFirstName;
        this.intCampusSelect = intCampusSelect;
        this.boolMain = boolMain;
        this.boolJohnstown = boolJohnstown;
        this.boolBradford = boolBradford;
        this.boolTitusville = boolTitusville;
        this.boolGreensburg = boolGreensburg;
    }


    private String ip = "databaseforbabl.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String classs = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "DbBABL";
    private String un = "gregmckibbin";
    private String password = "password";
    private String test = "Test";

    ResultSet rs;
    PreparedStatement pstmt;

    protected String doInBackground(String... strArr){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        //Checks if USERNAMES are Equal, if they are, stops process;
        try {
            Class.forName(classs).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "SELECT * FROM Users";

            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            while (rs.next()){
                String strUsernameVerify = rs.getString("Username");
                if (strUsernameVerify.equals(strUsername)){
                    return "Username Already Exists";
                }

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return "exception";
        }
        catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
            return "exception";
        }
        catch (Exception e) {
            Log.e("ERRO", e.getMessage());
            return "exception";
        }

        //Inputs to the Database
        try {

            Class.forName(classs).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "INSERT INTO Users (Username, Password, FirstName, Lang1, Lang2, Lang3, Lang4, Lang5, Attending, Main, Johnstown, Bradford, Titusville, Greensburg) VALUES " +
                    "(" +
                    "?," + //1
                    "?," + //2
                    "?," + //3
                    "?," + //4
                    "?," + //5
                    "?," + //6
                    "?," + //7
                    "?," + //8
                    "?," + //9
                    "?," + //10
                    "?," + //11
                    "?," + //12
                    "?," + //13
                    "?" + //14
                    ")";


            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, strUsername);
            pstmt.setString(2, strPassword);
            pstmt.setString(3, strFirstName);
            pstmt.setString(4, strArr[0]);
            pstmt.setString(5, strArr[1]);
            pstmt.setString(6, strArr[2]);
            pstmt.setString(7, strArr[3]);
            pstmt.setString(8, strArr[4]);
            pstmt.setInt(9, intCampusSelect);
            pstmt.setBoolean(10, boolMain);
            pstmt.setBoolean(11, boolJohnstown);
            pstmt.setBoolean(12, boolBradford);
            pstmt.setBoolean(13, boolTitusville);
            pstmt.setBoolean(14, boolGreensburg);

            pstmt.executeUpdate();
            return "New User Added Successfully";



        }
        catch (SQLException e) {
            e.printStackTrace();
            return "exception";
        }
        catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
            return "exception";
        }
        catch (Exception e) {
            Log.e("ERRO", e.getMessage());
            return "exception";
        }
    }

    protected void onPostExecute(String result) {


        Toast.makeText(context, result, Toast.LENGTH_LONG).show();


    }

}