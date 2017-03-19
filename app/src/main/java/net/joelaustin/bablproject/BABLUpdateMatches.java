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
 * Created by Joel on 3/18/2017.
 */

public class BABLUpdateMatches extends AsyncTask<Void, Void, String> {
    BABLMatchesDataLocal Matcheslocaldata = new BABLMatchesDataLocal();
    BABLDataLocal datalocal = new BABLDataLocal();

    Context context;
    private int intUserID = datalocal.get_intUserID();

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    Connection conn;
    String ConnURL;
    PreparedStatement pstmt;
    ResultSet rs;

    public BABLUpdateMatches(Context context) {
        this.context = context;
    }
    protected String doInBackground(Void... voids) {
        try {
            Class.forName(Dbclass).newInstance();
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

            String query = "EXEC UpdateMatches @userId=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, datalocal.get_intUserID());
            pstmt.execute();


            return "Matches Updated";
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

    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        new BABLMatchRetrieve(context).execute();
    }
}
