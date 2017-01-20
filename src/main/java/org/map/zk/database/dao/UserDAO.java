package org.map.zk.database.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.datamodel.TBLUser;
import com.mysql.jdbc.integration.jboss.ExtendedMysqlExceptionSorter;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class UserDAO  {
    
    public static TBLUser loadData( final CDatabaseConnection dbConnection, final String strID, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        TBLUser result = null;
        
        try {
            if ( dbConnection != null && dbConnection.getConnection() ) {
                
                Statement statement = dbConnection.getConnection().createStatement;
                
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM tbloperator WHERE ID = '" + strID + "'" );
                
                if ( resultSet.next() ) {
                    
                    result = new TBLUser();
                    
                    result.setID( resultSet.getString( "ID" ) );
                    result.setName( resultSet.getString( "Name" ) );
                    result.setPassword( resultSet.getString( "Password" ) );
                    result.setRole( resultSet.getString( "Role" ) );
                    result.setComment( resultSet.getString( "Comment" ) );
                    result.setPassword( resultSet.getString( "CreatedBy" ) );
                    result.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() );
                    result.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() );
                    result.setDisabledBy( resultSet.getString( "DisabledBy" ) );
                    result.setDisabledAtDate( resultSet.getDate( "DisabledAtDate" ) != null ? resultSet.getDate( "DisabledAtDate" ).toLocalDate() : null );
                    result.setDisabledAtTime( resultSet.getTime( "DisabledAtTime" ) != null ? resultSet.getTime( "DisabledAtTime" ).toLocalTime() : null );
                    result.setUpdatedBy( resultSet.getString( "UpdatedBy" ) );
                    result.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null );
                    result.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );
                    result.setLastLoginAtDate( resultSet.getDate( "LastLoginAtDate" ) != null ? resultSet.getDate( "LastLoginAtDate" ).toLocalDate() : null );
                    result.setLastLoginAtTime( resultSet.getTime( "LastLoginAtTime" ) != null ? resultSet.getTime( "LastLoginAtTime" ).toLocalTime() : null );
                    
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
    
    public static boolean insertData( final CDatabaseConnection dbConnection, final TBLUser tblUser, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        
        try {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                final String strSQL = "INSERT INTO tbloperator (ID, Name, Role, Password, Comment, CreatedBy, CreatedAtDate, CreatedAtTime) VALUES ('" + tblUser.getID() + "', '" + tblUser.getName() + "',' " + tblUser.getRole() + "',' " + tblUser.getPassword() + "', " + tblUser.getComment() + "', 'root', '" + LocalDate.now().toString() + "', '" + LocalTime.now().toString() + "' )";
                
                Statement statement = dbConnection.getDbconnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getDbconnection().commit();
                
                bresult = true;
                
                statement.close();
            }
            
        }
        
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                try {
                    
                    dbConnection.getDbconnection().rollback();
                    
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
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                final String strSQL = "Delete * FROM tbloperator Where ID = '" + strID + "'";
                
                Statement statement = dbConnection.getDbconnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getDbconnection().commit();
                
                bresult = true;
                
                statement.close();
            }
            
        }
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                try {
                    
                    dbConnection.getDbconnection().rollback();
                    
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
    
    public static boolean updateData( final CDatabaseConnection dbConnection, final TBLUser tblUser, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        
        try {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                final String strDisabledAtDate = "', DisabledAtDate = " + tblUser.getDisabledAtDate() != null ? "'" + LocalDate.now().toString() + "'" : "null";
                
                final String strDisabledAtTime = ", DisabledAtTime = " + tblUser.getDisabledAtTime() != null ? "'" + LocalTime.now().toString() + "'" : "null";
                
                final String strSQL = "Update tbloperator Set ID = '" + tblUser.getID() + "', Name = '"+ tblUser.getName() + "', Role = '"+ tblUser.getRole() + "', Password = '" + tblUser.getPassword() + "', Comment = '" + tblUser.getComment() + "', UpdatedBy = 'root', UpdatedAtDate = '" + LocalDate.now().toString() + "', UpdatedAtTime = '" + LocalTime.now().toString() + "', DiseabledBy = '" + tblUser.getDisabledBy() + strDisabledAtDate + strDisabledAtTime + " Where ID = '" + tblUser.getID() + "'";   

                
                Statement statement = dbConnection.getDbconnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getDbconnection().commit();
                
                bresult = true;
                
                statement.close();
            }
            
        }
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                try {
                    
                    dbConnection.getDbconnection().rollback();
                    
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
    
    
    
    public static TBLUser checkData( final CDatabaseConnection dbConnection, final String strID, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        TBLUser result = null;
        try {
            if ( dbConnection != null && dbConnection.getConnection() ) {
                
                Statement statement = dbConnection.getConnection().createStatement;
                
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM tbloperator WHERE ID = '" + strID + "' AND DisabledBy <=> null AND DisabledAtDate <=> null AND DisabledAtTime <=> null" );
                
                if ( resultSet.next() ) {
                    
                    result = new TBLUser();
                    
                    result.setID( resultSet.getString( "ID" ) );
                    result.setName( resultSet.getString( "Name" ) );
                    result.setPassword( resultSet.getString( "Password" ) );
                    result.setRole( resultSet.getString( "Role" ) );
                    result.setComment( resultSet.getString( "Comment" ) );
                    result.setPassword( resultSet.getString( "CreatedBy" ) );
                    result.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() );
                    result.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() );
                    result.setDisabledBy( resultSet.getString( "DisabledBy" ) );
                    result.setDisabledAtDate( resultSet.getDate( "DisabledAtDate" ) != null ? resultSet.getDate( "DisabledAtDate" ).toLocalDate() : null );
                    result.setDisabledAtTime( resultSet.getTime( "DisabledAtTime" ) != null ? resultSet.getTime( "DisabledAtTime" ).toLocalTime() : null );
                    result.setUpdatedBy( resultSet.getString( "UpdatedBy" ) );
                    result.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null );
                    result.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );
                    result.setLastLoginAtDate( resultSet.getDate( "LastLoginAtDate" ) != null ? resultSet.getDate( "LastLoginAtDate" ).toLocalDate() : null );
                    result.setLastLoginAtTime( resultSet.getTime( "LastLoginAtTime" ) != null ? resultSet.getTime( "LastLoginAtTime" ).toLocalTime() : null );
                    
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
    
    public static boolean updateLogin( final CDatabaseConnection dbConnection, final String strID, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        try {
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                final String strSQL = "UPDATE tbloperator SET LastLoginAtDate = '" + LocalDate.now().toString() + "', LastLoginAtTime = '" + LocalTime.now().toString() + "' WHERE ID = '" + strID + "'";
                
                Statement statement = dbConnection.getDbconnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getConnection().commit();
                
                bresult = true;
                
            }
        }
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                try {
                    
                    dbConnection.getDbconnection().rollback();
                    
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
    
    public static boolean disableUser( final CDatabaseConnection dbConnection, final String strID, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        try {
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                final String strSQL = "UPDATE tbloperator SET DisabledBy = 'root', DisabledAtDate = '" + LocalDate.now().toString() + "', DisabledAtTime = '" + LocalTime.now().toString() + "' WHERE ID = '" + strID + "'";
                
                Statement statement = dbConnection.getDbconnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getConnection().commit();
                
                bresult = true;
                
                statement.close();
            }
        }
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                try {
                    
                    dbConnection.getDbconnection().rollback();
                    
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
    
    public static boolean enableUser( final CDatabaseConnection dbConnection, final String strUser, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bresult = false;
        try {
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                final String strSQL = "UPDATE tbluser SET DisabledBy = null, DisabledAtDate = null, DisabledAtTime = null WHERE User = '" + strUser + "'";
                
                Statement statement = dbConnection.getDbconnection().createStatement();
                
                statement.executeUpdate( strSQL );
                
                dbConnection.getConnection().commit();
                
                bresult = true;
                
                statement.close();
                
            }
        }
        
        catch ( Exception ex ) {
            
            if ( dbConnection != null && dbConnection.getDbconnection() != null ) {
                
                try {
                    
                    dbConnection.getDbconnection().rollback();
                    
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
    
    public static List<TBLUser> loadAllData ( final CDatabaseConnection dbConnection, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        List<TBLUser> result = new ArrayList<TBLUser>();
        
        try {
            
            if ( dbConnection != null && dbConnection.getConnection() != null ) {
                
                Statement statement = dbConnection.getConnection().createStatement();
                
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM tbloperator" );
                
                while (resultSet.next()) {
                    
                   TBLUser tblUser = new TBLUser();
                   
                   tblUser.setID( resultSet.getString( "ID" ) );
                   tblUser.setName( resultSet.getString( "Name" ) );
                   tblUser.setPassword( resultSet.getString( "Password" ) );
                   tblUser.setRole( resultSet.getString( "Role" ) );
                   tblUser.setComment( resultSet.getString( "Comment" ) );
                   tblUser.setPassword( resultSet.getString( "CreatedBy" ) );
                   tblUser.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() );
                   tblUser.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() );
                   tblUser.setDisabledBy( resultSet.getString( "DisabledBy" ) );
                   tblUser.setDisabledAtDate( resultSet.getDate( "DisabledAtDate" ) != null ? resultSet.getDate( "DisabledAtDate" ).toLocalDate() : null );
                   tblUser.setDisabledAtTime( resultSet.getTime( "DisabledAtTime" ) != null ? resultSet.getTime( "DisabledAtTime" ).toLocalTime() : null );
                   tblUser.setUpdatedBy( resultSet.getString( "UpdatedBy" ) );
                   tblUser.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null );
                   tblUser.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );
                   tblUser.setLastLoginAtDate( resultSet.getDate( "LastLoginAtDate" ) != null ? resultSet.getDate( "LastLoginAtDate" ).toLocalDate() : null );
                   tblUser.setLastLoginAtTime( resultSet.getTime( "LastLoginAtTime" ) != null ? resultSet.getTime( "LastLoginAtTime" ).toLocalTime() : null );
                   
                   result.add( tblUser );
                   
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
