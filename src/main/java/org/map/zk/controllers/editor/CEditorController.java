package org.map.zk.controllers.editor;

import java.time.LocalDate;
import java.util.LinkedList;

import org.map.zk.database.dao.*;
import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.datamodel.*;
import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.utilities.SystemUtilities;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;

public class CEditorController extends SelectorComposer<Component> {
    
    private static final long serialVersionUID = -4893774424235516302L;
    
    @Wire
    Window windowPerson;
    
    @Wire
    Label labelCi;
    
    @Wire
    Textbox textboxCi;
    
    @Wire
    Label labelNombre;
    
    @Wire
    Textbox textboxNombre;
    
    @Wire
    Label labelApellido;
    
    @Wire
    Textbox textboxApellido;
    
    @Wire
    Label labelFecha;
    
    @Wire
    Datebox dateboxFecha;
    
    @Wire
    Label labelGenero;
    
    @Wire
    Selectbox selectboxGenero;
    
    @Wire
    Label labelComentario;
    
    @Wire
    Textbox textboxComentario;
    
    @Wire
    Button buttonGuardar;
    
    @Wire
    Button buttonCancelar;
    
    protected CExtendedLogger controllerLogger = null;
    
    protected CLanguage controllerLanguage = null;
    
    protected ListModelList<String> datamodel = new ListModelList<String>();
       
    protected Button buttonModify;
    
    protected final Execution execution = Executions.getCurrent();
    
    TBLPerson personToModify = null;
    
    protected CDatabaseConnection dbConnection = null;
    
    protected String PersonaCi = null;
    
    
    public void initcontrollerLoggerAndcontrollerLanguage( String strRunningPath, Session currentSession ) {
        
        CExtendedConfigLogger extendedConfigLogger = SystemUtilities.initLoggerConfig( strRunningPath, currentSession );
        
        TBLUser operatorCredential = ( TBLUser ) currentSession.getAttribute( SystemConstants._Operator_Credential_Session_Key );
        
        String strOperator = SystemConstants._Operator_Unknown;
        String strLoginDateTime = ( String ) currentSession.getAttribute( SystemConstants._Login_Date_Time_Session_Key );
        String strLogPath = ( String ) currentSession.getAttribute( SystemConstants._Log_Path_Session_Key );
        
        if ( operatorCredential != null )
            strOperator = operatorCredential.getName();
        
        if ( strLoginDateTime == null )
            strLoginDateTime = Utilities.getDateInFormat( ConstantsCommonClasses._Global_Date_Time_Format_File_System_24, null );
        
        final String strLoggerName = SystemConstants._Person_Editor_Controller_Logger_Name;
        final String strLoggerFileName = SystemConstants._Person_Editor_Controller_File_Log;
        
        controllerLogger = CExtendedLogger.getLogger( strLoggerName + " " + strOperator + " " + strLoginDateTime );
        
        if ( controllerLogger.getSetupSet() == false ) {
            
            if ( strLogPath == null )
                strLogPath = strRunningPath + "/" + SystemConstants._Logs_Dir;
            
            if ( extendedConfigLogger != null )
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, extendedConfigLogger.getClassNameMethodName(), extendedConfigLogger.getExactMatch(), extendedConfigLogger.getLevel(), extendedConfigLogger.getLogIP(), extendedConfigLogger.getLogPort(), extendedConfigLogger.getHTTPLogURL(), extendedConfigLogger.getHTTPLogUser(), extendedConfigLogger.getHTTPLogPassword(), extendedConfigLogger.getProxyIP(), extendedConfigLogger.getProxyPort(), extendedConfigLogger.getProxyUser(), extendedConfigLogger.getProxyPassword() );
            else
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, SystemConstants.LOG_CLASS_METHOD, SystemConstants.LOG_EXACT_MATCH, SystemConstants.log_level, "", -1, "", "", "", "", -1, "", "" );
            
            controllerLanguage = CLanguage.getLanguage( controllerLogger, strRunningPath + SystemConstants._Langs_Dir + strLoggerName + "." + SystemConstants._Lang_Ext );
            
            synchronized ( currentSession ) {
                
                @SuppressWarnings( "unchecked" )
                LinkedList<String> loggedSessionLoggers = ( LinkedList<String> ) currentSession.getAttribute( SystemConstants._Logged_Session_Loggers );
                
                if ( loggedSessionLoggers != null ) {
                    
                    synchronized ( loggedSessionLoggers ) {
                        
                        loggedSessionLoggers.add( strLoggerName + " " + strOperator + " " + strLoginDateTime );
                        
                    }
                    
                    currentSession.setAttribute( SystemConstants._Logged_Session_Loggers, loggedSessionLoggers );
                    
                }
                
            }
            
        }
        
    }//
    
    public void doAfterCompose( Component comp ) {
        
        try {
            super.doAfterCompose( comp );
            final String strRunningpath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._WEB_INF_DIR );
            initcontrollerLoggerAndcontrollerLanguage( strRunningpath, Sessions.getCurrent() );
            controllerLogger = ( CExtendedLogger ) Sessions.getCurrent().getWebApp().getAttribute( ConstantsCommonClasses._Webapp_Logger_App_Attribute_Key );
            dateboxFecha.setFormat("dd-MM-yyyy");
            datamodel.add("Female");
            datamodel.add("Male");
            selectboxGenero.setModel(datamodel);
            selectboxGenero.setSelectedIndex(0);
            datamodel.addToSelection( "female" );
            
            buttonModify = (Button) execution.getArg().get( "buttonModify" );
            
            PersonaCi = (String) execution.getArg().get( "PersonaCi" );
            
            Session currentSession = Sessions.getCurrent();
            
            if (currentSession.getAttribute( SystemConstants._DB_Connection_Session_Key) instanceof CDatabaseConnection){
               
             dbConnection = (CDatabaseConnection) currentSession.getAttribute( SystemConstants._DB_Connection_Session_Key );
             
               if (execution.getArg().get( "PersonaCi" ) instanceof String ){
                 
                   personToModify = PersonDAO.loadData( dbConnection, PersonaCi, controllerLogger, controllerLanguage ); 
                 
               }
            }

            if (PersonaCi != null){
                
              textboxCi.setValue( personToModify.getID() );
              textboxNombre.setValue( personToModify.getFirstName() );
              textboxApellido.setValue( personToModify.getLastName() );
              textboxComentario.setValue( personToModify.getComment() );
              if (personToModify.getGender()== 0){
                                
                 datamodel.addToSelection( "female" );
              
            
              }
              else{
                
                 datamodel.addToSelection( "male" );  
                
              }
           
                 dateboxFecha.setValue( java.sql.Date.valueOf(personToModify.getBirthdate()) );
              
              }
            
            

        } 
        
        catch ( Exception e ) {
            
            if ( controllerLogger != null ) {
                
                controllerLogger.logException ( "-1021", e.getMessage(), e );

            }
        }
    }

    @Listen("onClick=#buttonguardar")
    public void onClickButtonGuardar(Event event) {
        if(dateboxFecha.getValue()!=null) {
          LocalDate localDate = new java.sql.Date(dateboxFecha.getValue().getTime()).toLocalDate();
          personToModify.setID(textboxCi.getValue());
          personToModify.setFirstName(textboxNombre.getValue());
          personToModify.setLastName(textboxApellido.getValue());        
          personToModify.setGender(selectboxGenero.getSelectedIndex());
          personToModify.setBirthdate(localDate);
          personToModify.setComment(textboxComentario.getValue());
          
          if ( ( !personToModify.getID().equals("")) && ( !personToModify.getFirstName().equals("") ) && ( !personToModify.getLastName().equals("") ) && ( personToModify.getGender()>=0 ) && ( personToModify.getBirthdate() != null ) && ( !personToModify.getComment().equals("") ) ){            
  
            if ( PersonaCi == null ) {
                
                PersonDAO.insertData( dbConnection, personToModify, controllerLogger, controllerLanguage );
                
                Events.echoEvent ( new Event ( "onKek", buttonModify, personToModify ) );
                
                windowPerson.detach();
                
            }
            else {
                               
                PersonDAO.updateData( dbConnection, personToModify, controllerLogger, controllerLanguage );
                
                Events.echoEvent ( new Event ( "onKek", buttonModify, personToModify ) );
                
                windowPerson.detach();
                
            }
              
          }
          else{
              
            Messagebox.show( "       Error, all fields must be filled", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            
          }
          
        }
        
        else {
            
            Messagebox.show ( "       Error, the field date is empty", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            
        }
    }

    @Listen("onClick=#buttoncancelar")
    public void onClickButtonCancelar( Event event ) {
        
        Messagebox.show( "       No change was made", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
        
        windowPerson.detach();
        
    }
    
}
