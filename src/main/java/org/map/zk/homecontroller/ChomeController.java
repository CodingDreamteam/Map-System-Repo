package org.map.zk.homecontroller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//
import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.utilities.SystemUtilities;
import org.map.zk.database.datamodel.TBLUser;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;

public class ChomeController extends SelectorComposer<Component> {
    
    private static final long serialVersionUID = -6992273830457634170L;
    
    protected CExtendedLogger controllerLogger = null;
    
    protected CLanguage controllerLanguage = null;
    
    @Wire( "#includeNorthContent #labelHeader" )
    Label labelHeader;
       
    @Wire
    Tabbox tabboxMainContent;
    
    public void initControllerLoggerAndControllerLanguage( String RunningPath, Session currentSession ) {
        
        CExtendedConfigLogger extendedConfigLogger = SystemUtilities.initLoggerConfig( RunningPath, currentSession );
        
        TBLUser operatorCredential = ( TBLUser ) currentSession.getAttribute( SystemConstants._Operator_Credential_Session_Key );
        
        String strOperator = SystemConstants._Operator_Unknown; //Esto es un valor por defecto no debería quedar con el pero si lo hacer el algoritmo no falla
        String strLoginDateTime = ( String ) currentSession.getAttribute( SystemConstants._Login_Date_Time_Session_Key ); //Recuperamos información de fecha y hora del inicio de sesión Login
        String strLogPath = ( String ) currentSession.getAttribute( SystemConstants._Log_Path_Session_Key ); //Recuperamos el path donde se guardarn los log ya que cambia según el nombre de l operador que inicie sesion  
        
        if ( operatorCredential != null )
            strOperator = operatorCredential.getName();  //Obtenemos el nombre del operador que hizo login
            
        if ( strLoginDateTime == null ) //En caso de ser null no ha fecha y hora de inicio de sesión colocarle una por defecto
            strLoginDateTime = Utilities.getDateInFormat( ConstantsCommonClasses._Global_Date_Time_Format_File_System_24, null );
        
        final String LoggerName = SystemConstants._Home_Controller_Logger_Name;
        final String LoggerFileName = SystemConstants._Home_Controller_File_Log;
        
        controllerLogger = CExtendedLogger.getLogger( LoggerName + " " + strOperator + " " + strLoginDateTime );
        
        if ( controllerLogger.getSetupSet() == false ) {
            
            if ( strLogPath == null )
                
                strLogPath = RunningPath + "/" + SystemConstants._Logs_Dir;
            
            if ( extendedConfigLogger != null )
                
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, LoggerFileName, extendedConfigLogger.getClassNameMethodName(), extendedConfigLogger.getExactMatch(), extendedConfigLogger.getLevel(), extendedConfigLogger.getLogIP(), extendedConfigLogger.getLogPort(), extendedConfigLogger.getHTTPLogURL(), extendedConfigLogger.getHTTPLogUser(), extendedConfigLogger.getHTTPLogPassword(), extendedConfigLogger.getProxyIP(), extendedConfigLogger.getProxyPort(), extendedConfigLogger.getProxyUser(), extendedConfigLogger.getProxyPassword() );
            
            else
                
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, LoggerFileName, SystemConstants.LOG_CLASS_METHOD, SystemConstants.LOG_EXACT_MATCH, SystemConstants.log_level, "", -1, "", "", "", "", -1, "", "" );
            
            //Inicializamos el lenguage para ser usado por el logger
            controllerLanguage = CLanguage.getLanguage( controllerLogger, RunningPath + SystemConstants._Langs_Dir + LoggerName + "." + SystemConstants._Lang_Ext );
            
            //Protección para el multi hebrado, puede que dos usuarios accedan exactamente al mismo tiempo a la página web, este código en el servidor se ejecuta en dos hebras
            synchronized ( currentSession ) {
                
                //Guardamos en la sesisón los logger que se van creando para luego ser destruidos.
                @SuppressWarnings( "unchecked" )
                LinkedList<String> loggedSessionLoggers = ( LinkedList<String> ) currentSession.getAttribute( SystemConstants._Logged_Session_Loggers );
                
                if ( loggedSessionLoggers != null ) {
                    
                    synchronized ( loggedSessionLoggers ) {
                        
                        //Lo agregamos a la lista
                        loggedSessionLoggers.add( LoggerName + " " + strOperator + " " + strLoginDateTime );
                        
                    }
                    
                    //Lo retornamos la sesión de este operador
                    currentSession.setAttribute( SystemConstants._Logged_Session_Loggers, loggedSessionLoggers );
                    
                }
                
            }
            
        }
        
    }
    
    public void doAfterCompose( Component comp ) {
        
        try {
            //
            super.doAfterCompose( comp );
            
            final String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._WEB_INF_DIR ) + File.separator;
            
            initControllerLoggerAndControllerLanguage( strRunningPath, Sessions.getCurrent() );
            
        }
        catch ( Exception ex ) {
            
            if ( controllerLogger != null )
                controllerLogger.logException( "-1021", ex.getMessage(), ex );
            
        }
        
    }
    

    @Listen( "onClick = #includeNorthContent #buttonChangePassword" )  
    public void onClickbuttonChangePassword( Event event ) {

        if ( controllerLogger != null )
            controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Button change password clicked" ) );
        
    }    
    
    @Listen ("onClick= #buttonManager")
    public void onClickbuttonPersonManager (Event event) { 
     
        if ( controllerLogger != null )
            controllerLogger.logMessage( "1", CLanguage.translateIf( controllerLanguage, "Button person manager clicked" ) );  
        
        Map<String,Object> params = new HashMap<String, Object>();
        
        params.put( "callerComponent", event.getTarget() );
        
        Window win = (Window) Executions.createComponents( "/views/person/manager/manager.zul", null, params );
        
        win.doModal();
        
    }
    
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @Listen( "onClick = #includeNorthContent #buttonLogout" )  
    public void onClickbuttonLogout( Event event ) {
        
        if ( controllerLogger != null )
            controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Button change password clicked" ) );
        
        Messagebox.show( "Are you sure do you want logout from system?", "Logout", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            
            public void onEvent(Event evt) throws InterruptedException {
                
                if ( evt.getName().equals( "onOK" ) ) {
                    
                    if ( controllerLogger != null )
                        controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Logout confirm accepted" ) );
                    
                    //Ok aquí vamos hacer el logout
                    Sessions.getCurrent().invalidate(); //Listo obliga limpiar la sessión mejor que ir removeAttribute a removeAttribute
                   
                    Executions.sendRedirect( "/index.zul"); //Lo enviamos al login
                    
                }
                else {
                    
                    if ( controllerLogger != null )
                        controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Logout confirm canceled" ) );
                    
                }
                
            }
            
        });         
        
       }
    
      }
