package org.map.zk.database.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TBLUser implements Serializable {

    private static final long serialVersionUID = -5049054171540438957L;
    
    protected String strID;
    protected String strName;
    protected String strPassword;
    protected String strRole;
    protected String strComment;
    protected String strCreatedBy;
    protected LocalDate createdAtDate;
    protected LocalTime createdAtTime;
    protected String strDisabledBy;
    protected LocalDate disabledAtDate;
    protected LocalTime disabledAtTime;
    protected String strUpdatedBy;
    protected LocalDate updatedAtDate;
    protected LocalTime updatedAtTime;
    protected LocalDate lastLoginAtDate;
    protected LocalTime lastLoginAtTime;
    
    
    
    
    
    public TBLUser() {

    }

    public TBLUser(String strID, String strName, String strPassword, String strRole,String strComment , String strCreatedBy, LocalDate createdAtDate, LocalTime createdAtTime, String strDisabledBy, LocalDate disabledAtDate, LocalTime disabledAtTime, String strUpdatedBy, LocalDate updatedAtDate, LocalTime updatedAtTime, LocalDate lastLoginAtDate, LocalTime lastLoginAtTime ) {
        super();
        this.strID = strID;
        this.strName = strName;
        this.strPassword = strPassword;
        this.strRole = strRole;
        this.strComment = strComment;
        this.strCreatedBy = strCreatedBy;
        this.createdAtDate = createdAtDate;
        this.createdAtTime = createdAtTime;
        this.strDisabledBy = strDisabledBy;
        this.disabledAtDate = disabledAtDate;
        this.disabledAtTime = disabledAtTime;
        this.strUpdatedBy = strUpdatedBy;
        this.updatedAtDate = updatedAtDate;
        this.updatedAtTime = updatedAtTime;
        this.lastLoginAtDate = lastLoginAtDate;
        this.lastLoginAtTime = lastLoginAtTime;
    }

    public String getName() {
        
        return strName;
        
    }
    
    public void setName( String strUser ) {
        
        this.strName = strUser;
        
    }
    
    public String getPassword() {
        
        return strPassword;
        
    }
    
    public void setPassword( String strPassword ) {
        
        this.strPassword = strPassword;
        
    }
    
    public String getRole() {
        
        return strRole;
        
    }
    
    public void setRole( String strRole ) {
        
        this.strRole = strRole;
        
    }
    
    public String getID() {
        
        return strID;
        
    }

    
    public void setID( String strID ) {
        
        this.strID = strID;
        
    }

    
    public String getComment() {
        
        return strComment;
        
    }

    
    public void setComment( String strComment ) {
        
        this.strComment = strComment;
        
    }
    
    public String getCreatedBy() {
        
        return strCreatedBy;
        
        
    }
    
    public void setCreatedBy( String strCreatedBy ) {
        
        this.strCreatedBy = strCreatedBy;
        
    }
    
    public LocalDate getCreatedAtDate() {
        
        return createdAtDate;
        
    }
    
    public void setCreatedAtDate( LocalDate createdAtDate ) {
        
        this.createdAtDate = createdAtDate;
        
    }
    
    public LocalTime getCreatedAtTime() {
        
        return createdAtTime;
        
    }
    
    public void setCreatedAtTime( LocalTime createdAtTime ) {
        
        this.createdAtTime = createdAtTime;
        
    }
    
    public String getDisabledBy() {
        
        return strDisabledBy;
        
    }
    
    public void setDisabledBy( String strDisabledBy ) {
        
        this.strDisabledBy = strDisabledBy;
        
    }
    
    public LocalDate getDisabledAtDate() {
        
        return disabledAtDate;
        
    }
    
    public void setDisabledAtDate( LocalDate disabledAtDate ) {
        
        this.disabledAtDate = disabledAtDate;
        
    }
    
    public LocalTime getDisabledAtTime() {
        
        return disabledAtTime;
    }
    
    public void setDisabledAtTime( LocalTime disabledAtTime ) {
        
        this.disabledAtTime = disabledAtTime;
        
    }
    
    public String getUpdatedBy() {
        
        return strUpdatedBy;
        
    }
    
    public void setUpdatedBy( String strUpdatedBy ) {
        
        this.strUpdatedBy = strUpdatedBy;
        
    }
    
    public LocalDate getUpdatedAtDate() {
        
        return updatedAtDate;
        
    }
    
    public void setUpdatedAtDate( LocalDate updatedAtDate ) {
        
        this.updatedAtDate = updatedAtDate;
        
    }   
    
    public LocalTime getUpdatedAtTime() {
        
        return updatedAtTime;
        
    }
    
    public void setUpdatedAtTime( LocalTime updatedAtTime ) {
        
        this.updatedAtTime = updatedAtTime;
        
    }
    
    public LocalDate getLastLoginAtDate() {
        
        return lastLoginAtDate;
        
    }
    
    public void setLastLoginAtDate( LocalDate lastLoginAtDate ) {
        
        this.lastLoginAtDate = lastLoginAtDate;
        
    }
    
    public LocalTime getLastLoginAtTime() {
        
        return lastLoginAtTime;
        
    }
    
    public void setLastLoginAtTime( LocalTime lastLoginAtTime ) {
        
        this.lastLoginAtTime = lastLoginAtTime;
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
