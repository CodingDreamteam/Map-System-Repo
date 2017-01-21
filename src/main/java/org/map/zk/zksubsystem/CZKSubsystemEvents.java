package org.map.zk.zksubsystem;

import java.io.File;
import java.util.List;

import org.map.zk.systemconstans.SystemConstants;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.DesktopInit;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;

public class CZKSubsystemEvents implements DesktopInit, DesktopCleanup, SessionInit, SessionCleanup, WebAppInit, WebAppCleanup, ExecutionInit, ExecutionCleanup  {

    @Override
    public void cleanup( WebApp webApp ) throws Exception {
        
        try {
            
            CExtendedLogger webAppLogger = CExtendedLogger.getLogger( SystemConstants._Webapp_Logger_Name );
            
            if (webAppLogger != null) {
                
                webAppLogger.logMessage( "1",CLanguage.translateIf( null, "Webapp ending now." ) );
                
                webAppLogger.flushAndClose();
                
            }
            
            webApp.removeAttribute( SystemConstants._Webapp_Logger_Name );
            
         }
         catch (Exception ex) {
             
             System.out.println( ex.getMessage()  );
         }
         
     }
    
    @Override
    public void init( WebApp webApp ) throws Exception {
        
        System.out.println( "inicio" );
        
        try {      
            
            String strRunningPath = webApp.getRealPath(SystemConstants._WEB_INF_DIR)  + File.separator;
            
            CExtendedConfigLogger configLogger = new CExtendedConfigLogger();
            
            String strConfigPath = strRunningPath + SystemConstants._CONFIG_DIR + SystemConstants._Logger_Config_Name;
            
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
    public void cleanup( Session session ) throws Exception {
        

        
    }

    @Override
    public void init( Session session, Object object ) throws Exception {
        

        
    }

    @Override
    public void cleanup( Execution arg0, Execution arg1, List<Throwable> arg2 ) throws Exception {
        

        
    }

    @Override
    public void init( Execution arg0, Execution arg1 ) throws Exception {
        

        
    }

    @Override
    public void cleanup( Desktop arg0 ) throws Exception {
        

        
    }

    @Override
    public void init( Desktop arg0, Object arg1 ) throws Exception {
        

        
    }
    
}
