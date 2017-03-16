package net.joelaustin.bablproject;

import java.util.Stack;

/**
 * Created by gregm on 3/15/2017.
 */

public class BABLMatchesDataLocal {

    private static Integer _intUserID;
    private static String _strFirstName;
    private static String _strLang1;
    private static String _strLang2;
    private static String _strLang3;
    private static String _strLang4;
    private static String _strLang5;
    private static Integer _intCampusAttend;
    private static String _strFacebookID;
    public static Stack _stackMatchID = new Stack();

    //set UserID
    public void set_intUserID(Integer intUserID) {
        this._intUserID = intUserID;
    }
    //get UserID
    public Integer get_intUserID() {
        return _intUserID;
    }
    //set First Name
    public void set_strFirstName(String strFirstName) { this._strFirstName = strFirstName; }
    //get First Name
    public String get_strFirstName() { return _strFirstName; }
    //set Language 1
    public void set_strLang1(String strLang1){
        this._strLang1 = strLang1;
    }
    //get Language 1
    public String get_strLang1(){
        return _strLang1;
    }
    //set Language 2
    public void set_strLang2(String strLang2){
        this._strLang2 = strLang2;
    }
    //get Language 2
    public String get_strLang2(){
        return _strLang2;
    }
    //set Language 3
    public void set_strLang3(String strLang3){
        this._strLang3 = strLang3;
    }
    //get Language 3
    public String get_strLang3(){
        return _strLang3;
    }
    //set Language 4
    public void set_strLang4(String strLang4){
        this._strLang4 = strLang4;
    }
    //get Language 4
    public String get_strLang4() {
        return _strLang4;
    }
    //set Language 5
    public void set_strLang5(String strLang5) {
        this._strLang5 = strLang5;
    }
    //get Language 5
    public String get_strLang5(){
        return _strLang5;
    }
    //set Campus Attendance
    public void set_intCampusAttend(Integer intCampusAttend) {
        this._intCampusAttend = intCampusAttend;
    }
    //get Campus Attendance
    public Integer get_intCampusAttend(){
        return _intCampusAttend;
    }
    //set FacebookID
    public void set_strFacebookID(String strFacebookID) { this._strFacebookID = strFacebookID; }
    //get FacebookID
    public String get_strFacebookID() { return _strFacebookID; }
}
