package org.map.zk.controllers.manager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.map.zk.constants.CConstantes;
import org.map.zk.database.dao.*;
import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.CDatabaseConnectionConfig;
import org.map.zk.database.datamodel.TBLPerson;
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
import commonlibs.extendedlogger.CExtendedLogger;

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
    protected CExtendedLogger controllerLogger=null;
    protected CLanguage controllerLanguage=null;
    public class MyRenderer implements ListitemRenderer<TBLPerson> {    
        @Override
        public void render(Listitem listitem, TBLPerson persona, int arg2) throws Exception {
            try {

                Listcell cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getID());// Se le asigna un valor a
                                                  // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getFirstName());// Se le asigna un valor a
                                                   // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getLastName());// Se le asigna un valor a
                                                     // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista                
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getGender() == 0 ? "Femenino" : "Masculino");// Se le asigna un valor a
                                                     // la celda                
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getBirthdate().toString());// Se le asigna un valor a
                                                     // la celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getComment());// Se le asigna un valor a
                                                   // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }}
    public void doAfterCompose(Component comp) {
        try {
            super.doAfterCompose(comp);                       
            listboxPersons.setModel(datamodelpersona);
            listboxPersons.setItemRenderer(new MyRenderer());            
            Session sesion = Sessions.getCurrent();//Se crea variable sesion
            controllerLogger = (CExtendedLogger) Sessions.getCurrent().getWebApp().getAttribute(ConstantsCommonClasses._Webapp_Logger_App_Attribute_Key);
            if(sesion.getAttribute(CConstantes.Database_Connection_Session_Key)instanceof CDatabaseConnection){
                database=(CDatabaseConnection) sesion.getAttribute(CConstantes.Database_Connection_Session_Key);
                buttonConnection.setLabel("Desconectar");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }//
    }
    @Listen("onClick=#buttoncargar")
    public void onClickbuttoncargar (Event event){
        listboxPersons.setModel((ListModelList<?>) null);//Se limpia la listbox
        Session sesion = Sessions.getCurrent();//Se recupera la sesi�n
        if(sesion.getAttribute(CConstantes.Database_Connection_Session_Key)instanceof CDatabaseConnection){//Si se est� conectado
            database=(CDatabaseConnection) sesion.getAttribute(CConstantes.Database_Connection_Session_Key);//Se asigna la direcci�n de la bd
            List<TBLPerson>listData=PersonDAO.searchData(database,controllerLogger, controllerLanguage);//Se llama al m�todo de b�squeda y se asigna a la lista de persona            
            datamodelpersona=new ListModelList<TBLPerson>(listData);//Se crea un nuevo modelo con la lista de personas
            listboxPersons.setModel(datamodelpersona);//se le asigna al listbox
            listboxPersons.setItemRenderer((new MyRenderer()));
        }
    }
    @Listen("onClick=#buttonconnection")    
    public void onClickbuttonconnection(Event event){
        Session sesion = Sessions.getCurrent();
        if(database==null){//Si se va a conectar            
            database = new CDatabaseConnection();//Se instancia     
            CDatabaseConnectionConfig databaseconnectionconfig = new CDatabaseConnectionConfig();//Se instancia una variable de clase databaseconnectionconfig
            String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath(CConstantes.WEB_INF_Dir)+File.separator+CConstantes.Config_Dir+File.separator; //Se asigna a una cadena la direcci�n la carpeta del archivo del archivo
            
            
            
            if(databaseconnectionconfig.loadConfig(strRunningPath+File.separator+CConstantes.Database_Connection_Config_File_Name,controllerLogger,controllerLanguage)){//Se selecciona el archivo y se carga la configuraci�n                                     
                if(database.makeConnectionToDB( databaseconnectionconfig, controllerLogger, controllerLanguage)){//Si se logra conectar
                    database.setDBConnectionConfig(databaseconnectionconfig, controllerLogger, controllerLanguage);
                    sesion.setAttribute(CConstantes.Database_Connection_Session_Key, database);//Se crea la sesi�n
                    buttonConnection.setLabel("Desconectar");//Se cambia el contexto                
                    Messagebox.show("       �Conexi�n exitosa!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);//Mensaje de exito
                    Events.echoEvent("onClick", buttonLoad, null);
                }else{//Sino
                    Messagebox.show("       �Conexi�n fallida!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);//Mensaje de fracaso
                    }
            }else{
                Messagebox.show("       �Error al leer el archivo de configuraci�n!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);//Mensaje de fracaso
                }
        }else{//Si se va a desconectar
            if (database!=null){//Si la variable no es nula
             sesion.setAttribute(CConstantes.Database_Connection_Session_Key, null);//Se limpia la sesi�n
             buttonConnection.setLabel("Conectar");//Se cambia el contexto
             
             if(database.closeConnectionToDB(controllerLogger, controllerLanguage)){//Se cierra la conexi�n
                 database=null;
                 Messagebox.show("       �Conexi�n cerrada!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
                 listboxPersons.setModel((ListModelList<?>) null);//Se limpia la listbox
             }else{
                 Messagebox.show("       �Fallo al cerrar conexi�n!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
             }
         }else{
             Messagebox.show("       �No est�s conectado!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
         }
        }
    }
    
    @Listen("onClick=#buttonadd")
    public void onClickbuttonadd(Event event) {
        TBLPerson vacio = new TBLPerson();//Esto sol�a tener un constructor con par�metros
        listboxPersons.setSelectedIndex(-1);        
        Map<String,Object> arg = new HashMap<String,Object>();
        arg.put("personToModify", vacio);
        arg.put("buttonadd", buttonAdd);
        arg.put("buttonmodify", buttonModify);
        arg.put("ModifyModel", datamodelpersona);
        Window win = (Window) Executions.createComponents("/views/person/editor/editor.zul", null,arg);
        win.doModal();
    }    
    @Listen("onClick=#buttonmodify")//
    public void onClickbuttonmodify(Event event) {
        if (listboxPersons.getSelectedIndex()>=0){
        Set<TBLPerson> selecteditems = datamodelpersona.getSelection();//Se crea una lista de personas con los elementos seleccionados
        if ((selecteditems != null) && (datamodelpersona.getSelection().size() > 0)) {//Si hay elementos
            TBLPerson person = selecteditems.iterator().next();
            Map<String,Object> arg = new HashMap<String,Object>();
            arg.put("personToModify", person);
            arg.put("buttonadd", buttonAdd);
            arg.put("buttonmodify", buttonModify);
            arg.put("PersonaCi", person.getID());
            Window win = (Window) Executions.createComponents("/views/person/editor/editor.zul", null , arg);
            win.doModal();                        
        }else{ //sino
            Messagebox.show("       Error, nothing selected.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            //Se da un mensaje de error
        }
        }else{
            Messagebox.show("       Error, nothing selected.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            //Se da un mensaje de error
        }
    }
    @Listen("onKek=#buttonmodify")
    public void onDialogFinishbuttonmodify(Event event) {
        TBLPerson personToModif = (TBLPerson) event.getData();
        int index = listboxPersons.getSelectedIndex();
        if (index>=0){
            datamodelpersona.remove(index);
            datamodelpersona.add(index, personToModif);
            listboxPersons.setModel(datamodelpersona);
            listboxPersons.setItemRenderer((new MyRenderer()));
            PersonDAO.updateData(database, personToModif,controllerLogger, controllerLanguage);
            Messagebox.show("       The person was modified!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            Events.echoEvent("onClick", buttonLoad, null);
        }else{
            PersonDAO.insertData(database, personToModif,controllerLogger, controllerLanguage);
            datamodelpersona.add(personToModif);
            listboxPersons.setModel(datamodelpersona);
            listboxPersons.setItemRenderer((new MyRenderer()));
            Messagebox.show("       Person added!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            Events.echoEvent("onClick", buttonLoad, null);
        }        
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Listen("onClick=#buttondelete")
    public void onClickbuttondelete(Event event) {//Si se hace click en el bot�n borrar
        if (listboxPersons.getSelectedIndex()>=0){
        Set<TBLPerson> selecteditems = datamodelpersona.getSelection();//Se crea una lista de personas con los elementos seleccionados
        if ((selecteditems != null) && (datamodelpersona.getSelection().size() > 0)) {//Si hay elementos
            String buffer = null;//Se crea un buffer
            for (TBLPerson persona : selecteditems) {//Por cada persona seleccionada
                if (buffer == null) {//Si el buffer est� vac�o
                    buffer = persona.getID() + " " + persona.getFirstName() + " " + persona.getLastName() + " ";//Se toma el primer elemento
                } else {//sino
                    buffer = buffer + "\n" + persona.getID() + " " + persona.getFirstName() + " "+ persona.getLastName()+ " ";//se toman el siguiente                    
                }//fin si
            }//fin por
                Messagebox.show("Are you sure you wish to delete the following" + Integer.toString(selecteditems.size())+ " selected elements? \n"+buffer,"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,new org.zkoss.zk.ui.event.EventListener() {//Validaci�n
                            public void onEvent(Event evt) throws InterruptedException {
                                if (evt.getName().equals("onOK")) {//Si la respuesta es s�
                                    alert("Elements erased!");//Se da un aviso
                                    while (selecteditems.iterator().hasNext()) {//mientras haya elementos seleccionados
                                        TBLPerson persona = selecteditems.iterator().next();//se toma el elemento
                                        //selecteditems.iterator().remove();
                                        PersonDAO.deleteData(database, persona.getID(),controllerLogger,controllerLanguage);
                                        datamodelpersona.remove(persona);//Se destruye
                                    }//fin mientras
                                }//fin si
                            }
                        });
            }else{ //sino
            Messagebox.show("       Error, nothing selected.", "Ok", Messagebox.OK, Messagebox.EXCLAMATION);
            //Se da un mensaje de error
        }       
    }else{
        Messagebox.show("       Error, nothing selected.", "Ok", Messagebox.OK, Messagebox.EXCLAMATION);
    }
    }
}