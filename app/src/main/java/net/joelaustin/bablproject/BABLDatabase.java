package net.joelaustin.bablproject;

/**
 * Created by Joel-PC on 2/3/2017.
 */
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

//This Class is responsible for Inputing Users or Updating Users in the Database.
public class BABLDatabase extends AsyncTask<String, Void, String>{

    public AsyncResponse delegate = null;

    private Context context;
    private String strUsername;
    private String strPassword;
    private String strFirstName;
    private String strFacebookId;
    private Integer intCampusSelect;
    private Boolean boolMain;
    private Boolean boolJohnstown;
    private Boolean boolBradford;
    private Boolean boolTitusville;
    private Boolean boolGreensburg;
    private Boolean boolNewUser;

    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    BABLDataLocal localdata = new BABLDataLocal();

    ResultSet rs;
    PreparedStatement pstmt;

    public BABLDatabase(Context context,Boolean boolNewUser, String strUsername, String strPassword, String strFirstName,Integer intCampusSelect, Boolean boolMain, Boolean boolJohnstown, Boolean boolBradford, Boolean boolTitusville, Boolean boolGreensburg, String strFacebookId) {
        this.context = context;
        this.strUsername = strUsername;
        this.strPassword = strPassword;
        this.strFirstName = strFirstName;
        this.strFacebookId = strFacebookId;
        this.intCampusSelect = intCampusSelect;
        this.boolMain = boolMain;
        this.boolJohnstown = boolJohnstown;
        this.boolBradford = boolBradford;
        this.boolTitusville = boolTitusville;
        this.boolGreensburg = boolGreensburg;
        this.boolNewUser = boolNewUser;
    }
    protected String doInBackground(String... strArr){

        String languageString = strArr[0] + "," + strArr[1] + "," + strArr[2] + "," + strArr[3] + "," + strArr[4];
        languageString = languageString.replace(",null", "");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;



        if (boolNewUser == true) {
            //Checks if USERNAMES are Equal, if they are, stops process;
            try {
                Class.forName(Dbclass).newInstance();
                ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                        + "databaseName=" + db + ";user=" + un + ";password="
                        + password + ";";
                conn = DriverManager.getConnection(ConnURL);

                String query = "SELECT Username FROM Users";

                pstmt = conn.prepareStatement(query);


                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String strUsernameVerify = rs.getString("Username");
                    if (strUsernameVerify.equals(strUsername)) {
                        return "Username Already Exists";
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "exception";
            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
                return "exception";
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
                return "exception";
            }
            //Inputs to the Database
            try {



                Class.forName(Dbclass).newInstance();
                ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                        + "databaseName=" + db + ";user=" + un + ";password="
                        + password + ";";
                conn = DriverManager.getConnection(ConnURL);

                String query = "EXEC InsertUser @username=?, @password=?, @firstname=?, @attending=?, @main=?, @johnstown=?, @bradford=?, @titusville=?, @greensburg=?, @facebookid=?, @languages=?;";
                //Self Notes:
                //Location Matching, I.E. campus selection matching will be done AFTER all the matches of your language have been pulled.
                //This solution is suitable because we wont be pulling that much data anyways, The stored procedure is too difficult
                // with all the variables.
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, strUsername);
                pstmt.setString(2, strPassword);
                pstmt.setString(3, strFirstName);
                pstmt.setInt(4, intCampusSelect);
                pstmt.setBoolean(5, boolMain);
                pstmt.setBoolean(6, boolJohnstown);
                pstmt.setBoolean(7, boolBradford);
                pstmt.setBoolean(8, boolTitusville);
                pstmt.setBoolean(9, boolGreensburg);
                pstmt.setString(10,strFacebookId);
                pstmt.setString(11, languageString);



                pstmt.execute();
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
        else {
            //Updates UserData in Database
            try {

                int intUserID = localdata.get_intUserID();


                Class.forName(Dbclass).newInstance();
                ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                        + "databaseName=" + db + ";user=" + un + ";password="
                        + password + ";";
                conn = DriverManager.getConnection(ConnURL);

                String query = "UPDATE Users SET Attending=?, Main=?, Johnstown=?, Bradford=?, Titusville=?, Greensburg=?,FacebookId=? WHERE UserID=?";
                //Self Notes:
                //Location Matching, I.E. campus selection matching will be done AFTER all the matches of your language have been pulled.
                //This solution is suitable because we wont be pulling that much data anyways, The stored procedure is too difficult
                // with all the variables.
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, intCampusSelect);
                pstmt.setBoolean(2, boolMain);
                pstmt.setBoolean(3, boolJohnstown);
                pstmt.setBoolean(4, boolBradford);
                pstmt.setBoolean(5, boolTitusville);
                pstmt.setBoolean(6, boolGreensburg);
                pstmt.setString(7, strFacebookId);
                pstmt.setInt(8,intUserID );
                pstmt.executeUpdate();

                query = "DELETE FROM Matches WHERE userID=? OR MatchingId=?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, intUserID);
                pstmt.setInt(2, intUserID);
                pstmt.execute();

                query = "DELETE FROM UserLanguages Where UserID=?";

                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, intUserID);

                pstmt.execute();

                query = "INSERT INTO UserLanguages VALUES (?, ?)";


                for (String language:strArr) {
                    if (language == null) {

                    }
                    else {
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, intUserID);
                        pstmt.setString(2, language);
                        pstmt.execute();
                    }

                }

                query = "EXEC UpdateMatches @userId=?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, intUserID);
                pstmt.execute();


                return "User Data Successfully Updated";



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


        }

    protected void onPostExecute(String result) {

        if (boolNewUser == true) {
            if (result.equals("Username Already Exists") ) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intentRegister = new Intent(context, StartActivity.class);
                intentRegister.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentRegister);
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }

        }
        else if (boolNewUser == false) {
            BABLDataRetrieve bablDataRetrieve = new BABLDataRetrieve(context);
            bablDataRetrieve.delegate = delegate;
            bablDataRetrieve.execute();
        }
        else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }


    }

}