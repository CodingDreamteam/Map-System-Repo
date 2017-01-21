package org.map.zk.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class CDatabaseConnection implements Serializable {

       private static final long serialVersionUID = 2311816988364334105L;
    
       protected Connection DatabaseConnection;        
       
       protected CDatabaseConnectionConfig DatabaseConnectionConfig;
       
       public CDatabaseConnection(){
       	
       	DatabaseConnection =null;
       	
       	DatabaseConnectionConfig =null;
       	
       }
       
       public Connection getDatabaseConnection(){
       	
       	return  DatabaseConnection;
       	
       }
       
       public CDatabaseConnectionConfig  getDatabaseConnectionConfig() {
           
           return DatabaseConnectionConfig;
           
       }
       
       
       public void setDatabaseConnection( Connection DatabaseConnection, CExtendedLogger localLogger, CLanguage localLanguage ){
           
       	localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "Set database connection manually" ) );
           
           this.DatabaseConnection = DatabaseConnection;
       	
       }
       
       
       public void setDatabaseConnectionConfig( CDatabaseConnectionConfig DatabaseConnectionConfig, CExtendedLogger localLogger, CLanguage localLanguage  ) {
           
           localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "Set database connection config manually" ) );

           this.DatabaseConnectionConfig = DatabaseConnectionConfig;
           
       }
       
       
       //este metodo se encarga de crear la coneccion con la base de datos 
       //obteniendo su configuracion y direccion con ayuda de la clase CdatabaseConnentionConfig
       public boolean makeConnectionToDatabase( CDatabaseConnectionConfig localDBConnectionConfig, CExtendedLogger localLogger, CLanguage localLanguage ) {
           
           boolean bResult = false;
           
           try {

               if ( this.DatabaseConnection == null ) {

                   Class.forName( localDBConnectionConfig.Driver );

                   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded driver [%s]", localDBConnectionConfig.Driver ) );
                   
                   String strDatabaseURL = localDBConnectionConfig.Prefix + localDBConnectionConfig.Host + ":" + localDBConnectionConfig.Port + "/" + localDBConnectionConfig.Database;

                   localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Try to connecting to [%s] user [%s] password [%s]", strDatabaseURL, localDBConnectionConfig.User, localDBConnectionConfig.Password ) );

                   Connection localDBConnection = DriverManager.getConnection( strDatabaseURL, localDBConnectionConfig.User, localDBConnectionConfig.Password );
                                   
                   localDBConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                   
                   localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connected to [%s] user [%s] password [%s]", strDatabaseURL, localDBConnectionConfig.User, localDBConnectionConfig.Password ) );

                   bResult = localDBConnection != null && localDBConnection.isValid( 5 );
                   
                   if ( bResult ) {
                    
                       localDBConnection.setAutoCommit( false );
     
                       localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connection auto commit set to false" ) );

                       this.DatabaseConnection = localDBConnection; //guarda la conecion de la database en la variable global de esta clase
                       
                       this.DatabaseConnectionConfig = localDBConnectionConfig; //guarda la configuracion de la DB en la variable global de esta clase

                       localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connection checked" ) );
                       
                   }    
                   else {
                   
                       localDBConnection.close();
                       
                       localDBConnection = null;

                       localLogger.logError( "-1001" , CLanguage.translateIf( localLanguage, "Failed check the connection" ) );
                   
                   }   

               }
               else {
                   
                   localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "The database is already initiated" ) );
                   
               }
               
           }
           catch ( Exception Ex ) {

               if ( localLogger != null ) {
                
                   localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                   
               }
               
           }       
           
           return bResult;
           
       }
       
       
       // cierra la coneccion con la base de datos

       public boolean closeConnectionToDB( CExtendedLogger localLogger, CLanguage localLanguage ) {
       
       boolean bResult = false;
       
       try {

           localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Trying to close the connection to the database" ) );

           if ( DatabaseConnection != null ) {
               
               DatabaseConnection.close();//cerramos
               
               localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Database connection closed successfully" ) );
               
           }
           else {
               
               localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "The connection variable is null" ) );
               
           }
           //estos null son para liberar espacio
           DatabaseConnection = null;
           DatabaseConnectionConfig = null;
           
           localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Set to null connection and conection config variable" ) );

           bResult = true;
       
       }
       catch ( Exception Ex ) {

           if ( localLogger != null ) {
               
               localLogger.logException( "-1021" , Ex.getMessage(), Ex );
               
           }

       }       

       return bResult;
           
   }

   public boolean isValid( CExtendedLogger localLogger, CLanguage localLanguage ) {
       
       boolean bResult = false;
       
       try {
           
           localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Checking for database connection is valid" ) );
           
           bResult = DatabaseConnection.isValid( 5 ); 
           
       } 
       catch ( Exception Ex ) {
           
           if ( localLogger != null ) {
               
               localLogger.logException( "-1021" , Ex.getMessage(), Ex );
               
           }
           
       }
       
       return bResult;
       
   }
    
    
    
}
