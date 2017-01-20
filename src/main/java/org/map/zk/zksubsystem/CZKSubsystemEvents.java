package org.map.zk.zksubsystem;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;

public class CZKSubsystemEvents implements SessionInit, SessionCleanup, WebAppInit, WebAppCleanup {

    @Override
    public void cleanup( WebApp webApp ) throws Exception {
        
        try {
            
            String strRunningPath = webApp.getRealPath(SystemConstants._WEB_INF_Dir)  + "/";
            
            CExtendedConfigLogger configLogger = new CExtendedConfigLogger();
            
            String strConfigPath = strRunningPath + SystemConstants._Config_Dir + SystemConstants._Logger_Config_File_Name;
            
            if (configLogger.loadConfig( strConfigPath, null, null )){
                
               CExtendedLogger webAppLogger = CExtendedLogger.getLogger( SystemConstants._Webapp_Logger_Name );
               
               if (webAppLogger.getSetupSet() == false) {
                   
                   String strLogPath = strRunningPath + SystemConstants._Logs_Dir + SystemConstants._System_Dir;
                  
                   webAppLogger.setupLogger( configLogger.getInstanceID(), configLogger.getLogToScreen(), strLogPath, SystemConstants._Webapp_Logger_File_Log, configLogger.getClassNameMethodName(), configLogger.getExactMatch(), configLogger.getLevel(), configLogger.getLogIP(), configLogger.getLogPort(), configLogger.getHTTPLogURL(), configLogger.getHTTPLogUser(), configLogger.getHTTPLogPassword(), configLogger.getProxyIP(), configLogger.getProxyPort(), configLogger.getProxyUser(), configLogger.getProxyPassword() );
                   
                   webApp.setAttribute( SystemConstants._Webapp_Logger_App_Attribute_Key, webAppLogger );
               }
               
               webAppLogger.logMessage( "1", CLanguage.translateIf( null, "Webapp logger loaded and configured [%s]. ", SystemConstants._Webapp_Logger_Name) );
            }
            
        }
        catch (Exception ex) {
            
            System.out.println( ex.getMessage()  );
            
        }
        
    }

    @Override
    public void init( WebApp webApp ) throws Exception {
        

        
    }

    @Override
    public void cleanup( Session session ) throws Exception {
        

        
    }

    @Override
    public void init( Session session, Object object ) throws Exception {
        

        
    }
    
}
