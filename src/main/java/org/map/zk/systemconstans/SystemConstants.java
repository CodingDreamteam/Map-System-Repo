package org.map.zk.systemconstans;

import java.io.File;

public class SystemConstants {
	public static final String _Libs_Dir = "libs"+ File.separator;
	public static final String _Lins_Ext = ".jar";
	
	public static final String _Langs_Dir  = "langs"+ File.separator;
	public static final String _Lang_Ext= "init.lang";
	public static final String _Common_Lang_File = "common.init.lang";
	
	public static final String _Logs_Dir = "logs"+ File.separator;
	
	public static final String _Temp_Dir = "temp"+File.separator;
	
	public static final String _Security_Dir = "security"+File.separator;
	
	public static final String _System_Dir = "system"+File.separator;
	
	public static final String _WEB_INF_DIR = "WEB-INF";
	
	public static final String _CONFIG_DIR = "config"+File.separator;
	
	public static final String _DATABASE_CONFIG_FILE = "database.config.xml";
	
	public static final String _Database_Config_Production_File = "database.production.config.xml";
	
	public static final String _Logger_Config_Name = "logger.config.xml";
	
	public static final String _Logger_Config_Production= "logger.production.config.xml";
	
	public static final String _Operator_Unknown = "unknown@unknown.com";
	
	
    public static final String _DB_Connection_Session_Key = "DatabaseConnection";
    public static final String _Operator_Credential_Session_Key = "operatorCredential";
    public static final String _Login_Date_Time_Session_Key = "loginDateTime";
    public static final String _Log_Path_Session_Key = "logPath";
    public static final String _Config_Logger_Session_Key = "configLogger";
  
    public static final String _Webapp_Logger_App_Attribute_Key = "webAppLogger";
    public static final String _Webapp_Logger_Name = "webapplogger";
    public static final String _Webapp_Logger_File_Log = "webapplogger.log";
	
    
    public static final String CHECK_LOGGED_LOGGER_NAME = "checklogged";
    public static final String CHECK_LOGGED_FILE_log = "checklogged.log";
    
    public static final String LOG_CLASS_METHOD = "*.*";
    public static final boolean LOG_EXACT_MATCH = false;
    public static final boolean LOG_MISSING_TRASLATION = false;
    public static final String LOG_NAME_MISSING_TRASLATION = "MissingTranslations";
    public static final String MISSING_TRASLATION_FILE_ = LOG_NAME_MISSING_TRASLATION+".log";
    public static final String log_level = "ALL";
    
    public static final String _Home_Controller_Logger_Name = "home_controller";
    public static final String _Home_Controller_File_Log = "home_controller.log";

    public static final String _Logged_Session_Loggers = "loggedSessionLoggers";
    
    public static final String _Person_Manager_Controller_Logger_Name = "person_manager_controller";
    public static final String _Person_Manager_Controller_File_Log = "person_manager_controller.log";

    public static final String _Person_Editor_Controller_Logger_Name = "person_editor_controller";
    public static final String _Person_Editor_Controller_File_Log = "person_editor_controller.log";
}
