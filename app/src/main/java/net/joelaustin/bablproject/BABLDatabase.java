package net.joelaustin.bablproject;

/**
 * Created by Joel-PC on 2/3/2017.
 */

import android.app.Application;
import java.sql.*;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.*;


public class BABLDatabase extends Application {


    String ip = "databaseforbabl.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "DbBABL";
    String un = "gregmckibbin";
    String password = "password";
    String test = "Test";

    ResultSet rs;
    Statement stmt;


    public void DbLoginInput(String strUsername, String strPassword) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "INSERT INTO Users (Username, Password) VALUES ('" + strUsername + "','" + strPassword + "')";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);


        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
    }
}