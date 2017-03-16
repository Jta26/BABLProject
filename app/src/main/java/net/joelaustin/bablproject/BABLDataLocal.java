package net.joelaustin.bablproject;

import java.util.ArrayList;

/**
 * Created by Joel on 2/6/2017.
 */

public class BABLDataLocal {

    private static Integer _intUserID;

    private static String _strUsername;
    private static String _strFirstName;
    private static String _strHashedPass;

    private static String _strLang1;
    private static String _strLang2;
    private static String _strLang3;
    private static String _strLang4;
    private static String _strLang5;

    private static Integer _intCampusAttend;

    private static Boolean _boolMain;
    private static Boolean _boolJohnstown;
    private static Boolean _boolBradford;
    private static Boolean _boolTitusville;
    private static Boolean _boolGreensburg;

    private static String _strFacebookId;

    public void set_intUserID(Integer intUserID) {
        this._intUserID = intUserID;
    }
    //get UserID;
    public Integer get_intUserID() {
        return _intUserID;
    }
    //set Username
    public void set_strUsername(String strUsername) {
        _strUsername = strUsername;
    }
    //get Username
    public String get_strUsername() {
        return _strUsername;
    }
    //set FirstName
    public void set_strFirstName(String strFirstName) {
        this._strFirstName = strFirstName;
    }
    //get FirstName
    public String get_strFirstName(){
        return _strFirstName;
    }
    //set HashedPass
    public void set_strHashedPass(String strHashedPass) {
        this._strHashedPass = strHashedPass;
    }
    //get HashedPass
    public String get_strHashedPass() {
        return _strHashedPass;
    }
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
    //set PittMain Setting
    public void set_boolMain(Boolean boolMain){
        this._boolMain = boolMain;
    }
    //get PittMain Setting
    public Boolean get_boolMain(){
        return _boolMain;
    }
    //set UPJ Setting
    public void set_boolJohnstown(Boolean boolJohnstown) {
        this._boolJohnstown = boolJohnstown;
    }
    //get UPJ Setting
    public Boolean get_boolJohnstown() {
        return _boolJohnstown;
    }
    //set UPB Setting
    public void set_boolBradford(Boolean boolBradford){
        this._boolBradford = boolBradford;
    }
    //get UPB Setting
    public Boolean get_boolBradford(){
        return _boolBradford;
    }
    //set UPT Setting
    public void set_boolTitusville(Boolean boolTitusville) {
        this._boolTitusville = boolTitusville;
    }
    //get UPT Setting
    public Boolean get_boolTitusville(){
        return _boolTitusville;
    }
    //set UPG Setting
    public void set_boolGreensburg(Boolean boolGreensburg) {
        this._boolGreensburg = boolGreensburg;
    }
    //get UPG Setting
    public Boolean get_boolGreensburg(){
        return _boolGreensburg;
    }

    public void set_strFacebookId(String strFacebookId) {
        this._strFacebookId = strFacebookId;
    }
    public String get_strFacebookId() {
        return _strFacebookId;
    }

    public String getNextMatchName(){
        return _listMatchName.get(0);
    }

    public String getNextMatchLang(){
        return _listMatchLangs.get(0);
    }

    //check if there are any potential matches left
    public boolean checkMatchesEmpty() { return _listMatchFBID.isEmpty(); }

    public void set_strFacebookId(String strFacebookId) {
        this._strFacebookId = strFacebookId;
    }
    public String get_strFacebookId() {
        return _strFacebookId;
    }

}
