package com.example.ipingpong.shared.datasource;

import com.example.ipingpong.shared.entities.Player;

import java.util.ArrayList;

public class Constants {

    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    public static final String table_name_address = "tb_Address";
    public static final String table_name_classification_result = "tb_ClassificationResult";
    public static final String table_name_club = "tb_Club";
    public static final String table_name_notification = "tb_Notification";
    public static final String table_name_players = "tb_Players";
    public static final String table_name_reports = "tb_Report";
    public static final String table_name_session = "tb_Session";
    public static final String table_name_sessionData = "tb_SessionData";
    public static final String table_name_users = "tb_Users";
    public static final String table_name_userType = "tb_userType";

    public static final String table_name_usertype = "UserType";

    // URLs
    //private static final String ROOT_URL = "http://192.168.64.2/IPingPong/";
    private static final String ROOT_URL = "https://traintit-ipingpong.000webhostapp.com/";
    public static final String URL_SELECT = ROOT_URL + "select.php";
    public static final String URL_REPORT = ROOT_URL + "selectReport.php";
    public static final String URL_INSERT = ROOT_URL + "insert.php";
    public static final String URL_UPDATE = ROOT_URL + "update.php";
    public static final String URL_DELETE = ROOT_URL + "delete.php";

    // some Strings
    public static final String view_update_reason = "ViewUpdate";

    public static ArrayList<Player> playerArrayList = new ArrayList<>();




}
