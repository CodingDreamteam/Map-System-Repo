package org.map.zk.controllers.manager;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.utilities.SystemUtilities;
import org.map.zk.database.dao.*;
import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.CDatabaseConnectionConfig;
import org.map.zk.database.datamodel.TBLPerson;
import org.map.zk.database.datamodel.TBLUser;
import org.map.zk.controllers.manager.CManagerController.MyRenderer;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;

public class CManagerController extends SelectorComposer<Component> {
    
    private static final long serialVersionUID = 5931956523173112752L;
    
    protected ListModelList<TBLPerson> datamodelpersona = null; //new ListModelList<TBLPerson>();
    
    @Wire
    Button buttonConnection;
    
    @Wire
    Button buttonLoad;
    
    @Wire
    Button buttonAdd;
    
    @Wire
    Button buttonModify;
    
    @Wire
    Button buttonDelete;
    
    @Wire
    Listbox listboxPersons;
    
    @Wire
    Window windowmanager;
    
    protected Execution execution = Executions.getCurrent();
    
    protected CDatabaseConnection database = null;
    
    protected CExtendedLogger controllerLogger = null;
    
    protected CLanguage controllerLanguage = null;
    
    public class MyRenderer implements ListitemRenderer<TBLPerson> {
        
        @Override
        public void render( Listitem listitem, TBLPerson persona, int arg2 ) throws Exception {
            
            try {
                
                Listcell cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getID() );// Se le asigna un valor a
                                                 // la// celda
                listitem.appendChild( cell );// Se añade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getFirstName() );// Se le asigna un valor a
                                                        // la// celda
                listitem.appendChild( cell );// Se añade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getLastName() );// Se le asigna un valor a
                                                       // la// celda
                listitem.appendChild( cell );// Se añade la celda a la lista                
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getGender() == 0 ? "Femenino" : "Masculino" );// Se le asigna un valor a
                // la celda                
                listitem.appendChild( cell );// Se añade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getBirthdate().toString() );// Se le asigna un valor a
                // la celda
                listitem.appendChild( cell );// Se añade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getComment() );// Se le asigna un valor a
                                                      // la// celda
                listitem.appendChild( cell );// Se añade la celda a la lista
                
            }
            catch ( Exception ex ) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
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
        
        final String strLoggerName = SystemConstants._Person_Manager_Controller_Logger_Name;
        final String strLoggerFileName = SystemConstants._Person_Manager_Controller_File_Log;
        
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
        
    }
    
    public void doAfterCompose( Component comp ) {
        
        try {
            super.doAfterCompose( comp );
            final String strRunningpath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._WEB_INF_DIR );
            initcontrollerLoggerAndcontrollerLanguage( strRunningpath, Sessions.getCurrent() );
            listboxPersons.setModel( datamodelpersona );
            listboxPersons.setItemRenderer( new MyRenderer() );
            Session sesion = Sessions.getCurrent();//Se crea variable sesion
            controllerLogger = ( CExtendedLogger ) Sessions.getCurrent().getWebApp().getAttribute( ConstantsCommonClasses._Webapp_Logger_App_Attribute_Key );
            if ( sesion.getAttribute( SystemConstants._DB_Connection_Session_Key ) instanceof CDatabaseConnection ) {
                database = ( CDatabaseConnection ) sesion.getAttribute( SystemConstants._DB_Connection_Session_Key );
                buttonConnection.setLabel( "Desconectar" );
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }//
    }
    
    @Listen( "onClick=#buttonLoad" )
    public void onClickbuttoncargar( Event event ) {
        
        listboxPersons.setModel( ( ListModelList<?> ) null );//Se limpia la listbox
        Session sesion = Sessions.getCurrent();//Se recupera la sesión
        if ( sesion.getAttribute( SystemConstants._DB_Connection_Session_Key ) instanceof CDatabaseConnection ) {//Si se está conectado
            database = ( CDatabaseConnection ) sesion.getAttribute( SystemConstants._DB_Connection_Session_Key );//Se asigna la dirección de la bd
            List<TBLPerson> listData = PersonDAO.loadAllData( database, controllerLogger, controllerLanguage );//Se llama al método de búsqueda y se asigna a la lista de persona            
            datamodelpersona = new ListModelList<TBLPerson>( listData );//Se crea un nuevo modelo con la lista de personas
            listboxPersons.setModel( datamodelpersona );//se le asigna al listbox
            listboxPersons.setItemRenderer( ( new MyRenderer() ) );
        }
    }
    
    @Listen( "onClick=#buttonconnection" )
    public void onClickbuttonconnection( Event event ) {
        
        Session sesion = Sessions.getCurrent();
        if ( database == null ) {//Si se va a conectar            
            database = new CDatabaseConnection();//Se instancia     
            CDatabaseConnectionConfig databaseconnectionconfig = new CDatabaseConnectionConfig();//Se instancia una variable de clase databaseconnectionconfig
            String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._WEB_INF_DIR ) + File.separator + SystemConstants._CONFIG_DIR + File.separator; //Se asigna a una cadena la dirección la carpeta del archivo del archivo
            
            if ( databaseconnectionconfig.LoadConfig( strRunningPath + File.separator + SystemConstants._DATABASE_CONFIG_FILE, controllerLogger, controllerLanguage ) ) {//Se selecciona el archivo y se carga la configuración                                     
                if ( database.makeConnectionToDatabase( databaseconnectionconfig, controllerLogger, controllerLanguage ) ) {//Si se logra conectar
                    database.setDatabaseConnectionConfig( databaseconnectionconfig, controllerLogger, controllerLanguage );
                    sesion.setAttribute( SystemConstants._DB_Connection_Session_Key, database );//Se crea la sesión
                    buttonConnection.setLabel( "Desconectar" );//Se cambia el contexto                
                    Messagebox.show( "       ¡Conexión exitosa!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );//Mensaje de exito
                    Events.echoEvent( "onClick", buttonLoad, null );
                }
                else {//Sino
                    Messagebox.show( "       ¡Conexión fallida!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );//Mensaje de fracaso
                }
            }
            else {
                Messagebox.show( "       ¡Error al leer el archivo de configuración!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );//Mensaje de fracaso
            }
        }
        else {//Si se va a desconectar
            if ( database != null ) {//Si la variable no es nula
                sesion.setAttribute( SystemConstants._DB_Connection_Session_Key, null );//Se limpia la sesión
                buttonConnection.setLabel( "Conectar" );//Se cambia el contexto
                
                if ( database.closeConnectionToDB( controllerLogger, controllerLanguage ) ) {//Se cierra la conexión
                    database = null;
                    Messagebox.show( "       ¡Conexión cerrada!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
                    listboxPersons.setModel( ( ListModelList<?> ) null );//Se limpia la listbox
                }
                else {
                    Messagebox.show( "       ¡Fallo al cerrar conexión!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
                }
            }
            else {
                Messagebox.show( "       ¡No estás conectado!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            }
        }
    }
    
    @Listen( "onClick=#buttonadd" )
    public void onClickbuttonadd( Event event ) {
        
        TBLPerson vacio = new TBLPerson();//Esto solía tener un constructor con parámetros
        listboxPersons.setSelectedIndex( -1 );
        Map<String, Object> arg = new HashMap<String, Object>();
        arg.put( "personToModify", vacio );
        arg.put( "buttonadd", buttonAdd );
        arg.put( "buttonmodify", buttonModify );
        arg.put( "ModifyModel", datamodelpersona );
        Window win = ( Window ) Executions.createComponents( "/views/person/editor/editor.zul", null, arg );
        win.doModal();
    }
    
    @Listen( "onClick=#buttonmodify" )//
    public void onClickbuttonmodify( Event event ) {
        
        if ( listboxPersons.getSelectedIndex() >= 0 ) {
            Set<TBLPerson> selecteditems = datamodelpersona.getSelection();//Se crea una lista de personas con los elementos seleccionados
            if ( ( selecteditems != null ) && ( datamodelpersona.getSelection().size() > 0 ) ) {//Si hay elementos
                TBLPerson person = selecteditems.iterator().next();
                Map<String, Object> arg = new HashMap<String, Object>();
                arg.put( "personToModify", person );
                arg.put( "buttonadd", buttonAdd );
                arg.put( "buttonmodify", buttonModify );
                arg.put( "PersonaCi", person.getID() );
                Window win = ( Window ) Executions.createComponents( "/views/person/editor/editor.zul", null, arg );
                win.doModal();
            }
            else { //sino
                Messagebox.show( "       Error, nothing selected.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
                //Se da un mensaje de error
            }
        }
        else {
            Messagebox.show( "       Error, nothing selected.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            //Se da un mensaje de error
        }
    }
    
    @Listen( "onKek=#buttonmodify" )
    public void onDialogFinishbuttonmodify( Event event ) {
        
        TBLPerson personToModif = ( TBLPerson ) event.getData();
        int index = listboxPersons.getSelectedIndex();
        if ( index >= 0 ) {
            datamodelpersona.remove( index );
            datamodelpersona.add( index, personToModif );
            listboxPersons.setModel( datamodelpersona );
            listboxPersons.setItemRenderer( ( new MyRenderer() ) );
            PersonDAO.updateData( database, personToModif, controllerLogger, controllerLanguage );
            Messagebox.show( "       The person was modified!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            Events.echoEvent( "onClick", buttonLoad, null );
        }
        else {
            PersonDAO.insertData( database, personToModif, controllerLogger, controllerLanguage );
            datamodelpersona.add( personToModif );
            listboxPersons.setModel( datamodelpersona );
            listboxPersons.setItemRenderer( ( new MyRenderer() ) );
            Messagebox.show( "       Person added!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            Events.echoEvent( "onClick", buttonLoad, null );
        }
    }
    
    @SuppressWarnings( { "rawtypes", "unchecked" } )
    @Listen( "onClick=#buttondelete" )
    public void onClickbuttondelete( Event event ) {//Si se hace click en el botón borrar
        
        if ( listboxPersons.getSelectedIndex() >= 0 ) {
            Set<TBLPerson> selecteditems = datamodelpersona.getSelection();//Se crea una lista de personas con los elementos seleccionados
            if ( ( selecteditems != null ) && ( datamodelpersona.getSelection().size() > 0 ) ) {//Si hay elementos
                String buffer = null;//Se crea un buffer
                for ( TBLPerson persona : selecteditems ) {//Por cada persona seleccionada
                    if ( buffer == null ) {//Si el buffer está vacío
                        buffer = persona.getID() + " " + persona.getFirstName() + " " + persona.getLastName() + " ";//Se toma el primer elemento
                    }
                    else {//sino
                        buffer = buffer + "\n" + persona.getID() + " " + persona.getFirstName() + " " + persona.getLastName() + " ";//se toman el siguiente                    
                    }//fin si
                }//fin por
                Messagebox.show( "Are you sure you wish to delete the following" + Integer.toString( selecteditems.size() ) + " selected elements? \n" + buffer, "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {//Validación
                    
                    public void onEvent( Event evt ) throws InterruptedException {
                        
                        if ( evt.getName().equals( "onOK" ) ) {//Si la respuesta es sí
                            alert( "Elements erased!" );//Se da un aviso
                            while ( selecteditems.iterator().hasNext() ) {//mientras haya elementos seleccionados
                                TBLPerson persona = selecteditems.iterator().next();//se toma el elemento
                                //selecteditems.iterator().remove();
                                PersonDAO.deleteData( database, persona.getID(), controllerLogger, controllerLanguage );
                                datamodelpersona.remove( persona );//Se destruye
                            }//fin mientras
                        }//fin si
                    }
                } );
            }
            else { //sino
                Messagebox.show( "       Error, nothing selected.", "Ok", Messagebox.OK, Messagebox.EXCLAMATION );
                //Se da un mensaje de error
            }
        }
        else {
            Messagebox.show( "       Error, nothing selected.", "Ok", Messagebox.OK, Messagebox.EXCLAMATION );
        }
    }
}
