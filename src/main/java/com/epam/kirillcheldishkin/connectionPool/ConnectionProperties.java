package com.epam.kirillcheldishkin.connectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperties {
    public Properties getConnectionProperties(String fileName){
        try(InputStream iStream = new FileInputStream(fileName)){
            Properties properties=new Properties();
            properties.load(iStream);
            return properties;
        }catch(IOException e){
            throw new RuntimeException("Property file is not found",e);
        }
    }
}
