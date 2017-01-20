package org.map.zk.database.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.datamodel.TBLPerson;
import org.map.zk.database.datamodel.TBLUser;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class PersonDAO {
    
    public static TBLPerson loadData( final CDatabaseConnection dbConnection, final String strID, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        TBLPerson result = null;
        
        try {
            if ( dbConnection != null && dbConnection.getConnection() ) {
                
                Statement statement = dbConnection.getConnection().createStatement;
                
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM tblperson WHERE ID = '" + strID + "'" );
                
                if ( resultSet.next() ) {
                    
                    result = new TBLPerson();
                                       
                    result.setID( resultSet.getString( "ID" ) );; 
                    result.setFirstName( resultSet.getString( "FirstName" ) );
                    result.setLastName( resultSet.getString( "LastName" ) ); 
                    result.setGender( resultSet.getInt( "Gender" ) );
                    result.setBirthdate( resultSet.getDate( "BirthDate" ).toLocalDate() );
                    result.setComment( resultSet.getString( "Comment" ) );
                    result.setCreatedBy( resultSet.getString( "CreatedBy" ) );
                    result.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() );
                    result.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() );
                    result.setUpdatedBy( resultSet.getString( "UpdatedBy" ) );
                    result.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null );
                    result.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );
                   
                }
                
                resultSet.close();
                
                statement.close();
            }
        }
        catch ( Exception ex ) {
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1021", ex.getMessage(), ex );
                
            }
            
        }
        
        return result;
    }
    public static boolean insertData( final CDatabaseConnection dbConnection, final TBLPerson tblPerson, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        
        try {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                final String strSQL = "Insert Into tblperson(ID, FirstName, LastName, Gender, BirthDate, Comment, CreatedBy, CreatedAtDate, CreatedAtTime) Values ('" + tblPerson.getID() + "', '" +tblPerson.getFirstName()+"',' " + tblPerson.getLastName() + "', " + tblPerson.getGender() + ", '" + tblPerson.getBirthdate().toString() + "', '" + tblPerson.getComment() + "', 'root', '" + LocalDate.now().toString() + "', '" + LocalTime.now().toString() + "')";
                
                Statement statement = dbConnection.getConnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getConnection().commit();
                
                bresult = true;
                
                statement.close();
            }
            
        }
        
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                try {
                    
                    dbConnection.getConnection().rollback();
                    
                }
                
                catch ( Exception e ) {
                    
                    if ( localLogger != null ) {
                        
                        localLogger.logException( "-1021", e.getMessage(), e );
                        
                    }
                    
                }
            }
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1022", ex.getMessage(), ex );
                
            }
            
        }
        
        return bresult;
        
    }
    
    public static boolean deleteData( final CDatabaseConnection dbConnection, final String strID, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        
        try {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                final String strSQL = "Delete FROM tblperson Where ID = '" + strID + "'";
                
                Statement statement = dbConnection.getConnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getConnection().commit();
                
                bresult = true;
                
                statement.close();
            }
            
        }
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                try {
                    
                    dbConnection.getConnection().rollback();
                    
                }
                
                catch ( Exception e ) {
                    
                    if ( localLogger != null ) {
                        
                        localLogger.logException( "-1021", e.getMessage(), e );
                        
                    }
                    
                }
            }
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1022", ex.getMessage(), ex );
                
            }
            
        }
        
        return bresult;
        
    }
    
    public static boolean updateData( final CDatabaseConnection dbConnection, final TBLPerson tblPerson, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        
        try {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                final String strSQL = "Update tblperson Set ID = '" + tblPerson.getID() + "', FirstName = '"+ tblPerson.getFirstName() + "', LastName = '" + tblPerson.getLastName() + "', Gender = " + tblPerson.getGender() + ", BirthDate = '" + tblPerson.getBirthdate().toString() + "', Comment = '" + tblPerson.getComment() + "', UpdatedBy = 'root', UpdatedAtDate = '" + LocalDate.now().toString() + "', UpdatedAtTime = '" + LocalTime.now().toString() + "' Where ID = '" + tblPerson.getID() + "'";
               
                Statement statement = dbConnection.getConnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getConnection().commit();
                
                bresult = true;
                
                statement.close();
            }
            
        }
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                try {
                    
                    dbConnection.getConnection().rollback();
                    
                }
                
                catch ( Exception e ) {
                    
                    if ( localLogger != null ) {
                        
                        localLogger.logException( "-1021", e.getMessage(), e );
                        
                    }
                    
                }
            }
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1022", ex.getMessage(), ex );
                
            }
            
        }
        
        return bresult;
        
    }

    
    public static List<TBLPerson> loadAllData ( final CDatabaseConnection dbConnection, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        List<TBLPerson> result = new ArrayList<TBLPerson>();
        
        try {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                Statement statement = dbConnection.getConnection().createStatement();
                
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM tblperson" );
                
                while (resultSet.next()) {
                    
                   TBLPerson tblPerson = new TBLPerson();
                   
                   tblPerson.setID( resultSet.getString( "ID" ) );; 
                   tblPerson.setFirstName( resultSet.getString( "FirstName" ) );
                   tblPerson.setLastName( resultSet.getString( "LastName" ) ); 
                   tblPerson.setGender( resultSet.getInt( "Gender" ) );
                   tblPerson.setBirthdate( resultSet.getDate( "BirthDate" ).toLocalDate() );
                   tblPerson.setComment( resultSet.getString( "Comment" ) );
                   tblPerson.setCreatedBy( resultSet.getString( "CreatedBy" ) );
                   tblPerson.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() );
                   tblPerson.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() );
                   tblPerson.setUpdatedBy( resultSet.getString( "UpdatedBy" ) );
                   tblPerson.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null );
                   tblPerson.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );
                   
                   result.add( tblPerson );
                   
                }
                
                resultSet.close();
                
                statement.close();
                
            }
            
        }
        catch (Exception ex) {
            
          if ( localLogger != null ) {
                
            localLogger.logException( "-1021", ex.getMessage(), ex );
                
          }
                        
        }
        
        return result;
        
    }
    
    
    
    
    
    
}
