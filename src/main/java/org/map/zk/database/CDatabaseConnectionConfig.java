package org.map.zk.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class CDatabaseConnectionConfig implements Serializable {

    private static final long serialVersionUID = 5521963210132512637L;
    
    protected String Driver =null;
    protected String Prefix =null;
    protected String Host =null;
    protected String Port =null;
    protected String Database=null;
    protected String User=null;
    protected String Password=null;
    
    public CDatabaseConnectionConfig() {
        
    }

    public CDatabaseConnectionConfig (String lDriver, String lPrefix, String lHost, String lPort, String lDatabase, String lUser, String lPassword) {
        this.Driver = lDriver; 
        this.Prefix =lPrefix;
        this.Host=lHost;
        this.Port = lPort;
        this.Database = lDatabase;
        this.User=lUser;
        this.Password=lPassword;
        
    }
    
    public boolean LoadConfig (String ConfigPath, CExtendedLogger localLogger, CLanguage localLanguage) {
        
        boolean bResult = false; 
        
        try {
        	
            File configFilePath = new File (ConfigPath);
            
            if (configFilePath.exists()) {
            
            	Properties configData = new Properties();
                
                FileInputStream inputStream = new FileInputStream( configFilePath );
                configData.loadFromXML( inputStream );
                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Readed config values from file [%s]", ConfigPath ));
                this.Driver = (String) configData.get("driver");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "driver", this.Driver  ) );
                this.Prefix = (String) configData.get("prefix");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "prefix", this.Prefix  ) );
                this.Host = (String) configData.get("host");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "host", this.Host  ) );
                this.Port = (String) configData.get("port");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "port", this.Port  ) );
                this.Database = (String) configData.get("database");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "database", this.Database  ) );
                this.User = (String) configData.get("user");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "user", this.User  ) );
                this.Password = (String) configData.get("password");
                localLogger.logMessage("1",CLanguage.translateIf( localLanguage, "Loaded value fo [%s] [%s]", "driver", this.Password  ) );
            
                inputStream.close();
                
                bResult = true; 
            
            }else if(localLogger !=null){
              
            	localLogger.logError( "-1001", CLanguage.translateIf( localLanguage, "Config File int path [%s] not found",ConfigPath ) );
                
            }
          
        }
        catch (Exception ex) {
           
        	if(localLogger!=null){
            
        		localLogger.logException( "-1021", ex.getMessage(), ex);
            
        	}
        }
        
        return bResult;
    }

    
    public String getDriver() {
        
        return Driver;
        
    }

    
    public void setDriver( String driver ) {
        
        this.Driver = driver;

        
    }

    
    public String getPrefix() {
        
        return Prefix;
        
    }

    
    public void setPrefix( String prefix ) {
        
        this.Prefix = prefix;
     
    }

    
    public String getHost() {
        
        return Host;
        
    }

    
    public void setHost( String host ) {
        

        this.Host = host;

        
    }

    
    public String getPort() {
        
        return Port;
        
    }

    
    public void setPort( String port ) {      

        this.Port = port;
        
    }

    
    public String getDatabase() {
        
        return Database;
        
    }

    
    public void setDatabase( String database ) {
        
        this.Database = database;
        
    }

    
    public String getUser() {
        
        return User;
        
    }

    
    public void setUser( String user ) {
        
        this.User = user;
        
    }

    
    public String getPassword() {
        
        return Password;
        
    }

    
    public void setPassword( String password ) {
        
        this.Password = password;
        
    }
}
