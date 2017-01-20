package org.map.zk.controllers.editor;

import java.time.LocalDate;

import org.map.zk.database.dao.*;
import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.datamodel.*;
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
import commonlibs.extendedlogger.CExtendedLogger;

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
    protected Button buttonAdd;
    protected Button buttonModify;
    protected Execution execution = Executions.getCurrent();
    TBLPerson personToModify = (TBLPerson) execution.getArg().get("personToModify");
    protected CDatabaseConnection database = null;
    public static final String dbkey = "database";

    public void doAfterCompose(Component comp) {
        try {
            super.doAfterCompose(comp);
            controllerLogger = (CExtendedLogger) Sessions.getCurrent().getWebApp()
                    .getAttribute(ConstantsCommonClasses._Webapp_Logger_App_Attribute_Key);
            dateboxFecha.setFormat("dd-MM-yyyy");
            datamodel.add("Female");
            datamodel.add("Male");
            selectboxGenero.setModel(datamodel);
            selectboxGenero.setSelectedIndex(0);
            datamodel.addSelection("Female");
            Session sesion = Sessions.getCurrent();
            if (sesion.getAttribute(dbkey) instanceof CDatabaseConnection) {
                database = (CDatabaseConnection) sesion.getAttribute(dbkey);
                if (execution.getArg().get("PersonaCi") instanceof String) {
                    personToModify = PersonDAO.loadData(database, (String) execution.getArg().get("PersonaCi"),
                            controllerLogger, controllerLanguage);
                }
            }
            buttonModify = (Button) execution.getArg().get("buttonmodify");// TypeCast
            buttonAdd = (Button) execution.getArg().get("buttonadd");// TypeCast
            textboxCi.setValue(personToModify.getID());
            textboxNombre.setValue(personToModify.getFirstName());
            textboxApellido.setValue(personToModify.getLastName());
            if (personToModify.getGender() == 0) {
                datamodel.addToSelection("Femenino");
            } else {
                datamodel.addToSelection("Masculino");
            }
            if (personToModify.getBirthdate() != null) {
                dateboxFecha.setValue(java.sql.Date.valueOf(personToModify.getBirthdate()));
            }
            textboxComentario.setValue(personToModify.getComment());
        } catch (Exception e) {
            if (controllerLogger != null) {
                controllerLogger.logException("-1021", e.getMessage(), e);

            }
        }//
    }

    @Listen("onClick=#buttonguardar")
    public void onClickButtonGuardar(Event event) {
        if(dateboxFecha.getValue()!=null){
        LocalDate id = new java.sql.Date(dateboxFecha.getValue().getTime()).toLocalDate();
        personToModify.setID(textboxCi.getValue());
        personToModify.setFirstName(textboxNombre.getValue());
        personToModify.setLastName(textboxApellido.getValue());        
        personToModify.setGender(selectboxGenero.getSelectedIndex());
        personToModify.setBirthdate(id);
        personToModify.setComment(textboxComentario.getValue());
        if ((!personToModify.getID().equals("")) && (!personToModify.getFirstName().equals("")) && (!personToModify.getLastName().equals("")) && personToModify.getGender()>=0 && personToModify.getBirthdate()!=null && !personToModify.getComment().equals("")){            
            Events.echoEvent(new Event("onKek", buttonModify, personToModify));
            windowPerson.detach();                
        }else{
            Messagebox.show("       Error, all fields must be filled", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        }else{
            Messagebox.show("       Error, the field date is empty", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#buttoncancelar")
    public void onClickButtonCancelar(Event event) {
        Messagebox.show("       No change was made", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
        windowPerson.detach();
    }
}
