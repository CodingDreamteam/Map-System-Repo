package org.map.zk.utilities;

import org.zkoss.zk.ui.Session;

import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;

public class CSystemUtilities {
    
    public static CExtendedConfigLogger initLoggerConfig( String strRunningPath, Session currentSession ) {
        
        CExtendedConfigLogger result = ( CExtendedConfigLogger ) currentSession.getAttribute( SystemConstants._Config_Logger_Session_Key );
        
        if ( result == null ) {
            
            result = new CExtendedConfigLogger();
            
            String strConfigPath = strRunningPath + SystemConstants._Config_Dir;
            
            CExtendedLogger webAppLogger = CExtendedLogger.getLogger( SystemConstants._Webapp_Logger_Name );
            
            if ( result.loadConfig( strConfigPath + SystemConstants._Logger_Config_File_Name, webAppLogger, null ) == false ) {
                
                result = null;
                
            }
            
        }
        
        return result;
        
    }
    
}