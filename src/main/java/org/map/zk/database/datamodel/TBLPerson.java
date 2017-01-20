package org.map.zk.database.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TBLPerson implements Serializable {

    private static final long serialVersionUID = 7942170937955285681L;
    
    protected String strID;
    protected String strFirstName;
    protected String strLastName;
    protected int intGender; //0 female 1 male
    protected LocalDate birthdate;
    protected String strComment;
    protected String strCreatedBy;
    protected LocalDate createdAtDate;
    protected LocalTime createdAtTime;
    protected String strUpdatedBy;
    protected LocalDate updatedAtDate;
    protected LocalTime updatedAtTime;
    
    public String getStrID() {
        
        return strID;
        
    }
    
    public void setStrID( String strID ) {
        
        this.strID = strID;
        
    }
    
    public String getFirstName() {
        
        return strFirstName;
        
    }
    
    public void setFirstName( String strFirstName ) {
        
        this.strFirstName = strFirstName;
        
    }
    
    public String getLastName() {
        
        return strLastName;
        
    }
    
    public void setLastName( String strLastName ) {
        
        this.strLastName = strLastName;
        
    }
    
    public int getGender() {
        
        return intGender;
    }
    
    public void setGender( int intGender ) {
        
        this.intGender = intGender;
    }
    
    public LocalDate getBirthdate() {
        
        return birthdate;
        
    }
    
    public void setBirthdate( LocalDate birthdate ) {
        
        this.birthdate = birthdate;
        
    }
    
    public String getStrcomment() {
        
        return strComment;
        
    }
    
    public void setcomment( String strComment ) {
        
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
    
    
    
    
}
