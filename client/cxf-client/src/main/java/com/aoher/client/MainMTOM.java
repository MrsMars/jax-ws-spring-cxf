package com.aoher.client;

import com.aoher.service.FileTransferMTOMService;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MainMTOM {

    public static final Logger logger = Logger.getLogger(MainMTOM.class);

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));

        // client
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(FileTransferMTOMService.class);
        jaxWsProxyFactoryBean.setAddress(properties.getProperty("endpoint-mtom"));

        // enable mtom, it's not really neccesary due to the @MTOM annotation
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("mtom-enabled", Boolean.TRUE);
        jaxWsProxyFactoryBean.setProperties(props);

        // logging
        jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
        jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());

        FileTransferMTOMService fileTransferClient = (FileTransferMTOMService) jaxWsProxyFactoryBean
                .create();

        File fileDownloaded = download(fileTransferClient);
        upload(fileTransferClient, fileDownloaded);
    }

    private static File download(FileTransferMTOMService fileTransferClient) throws IOException {
        DataHandler dataHandlerResponse = fileTransferClient.downloadFileContent();
        File file = File.createTempFile("temp", ".pdf");
        FileOutputStream outputStream = new FileOutputStream(file);
        dataHandlerResponse.writeTo(outputStream);
        outputStream.close();
        logger.info("path = " + file.getPath());

        return file;
    }

    private static void upload(FileTransferMTOMService fileTransferClient, File file) {
        DataHandler dataHandlerSend = new DataHandler(new FileDataSource(file));
        fileTransferClient.uploadFile(dataHandlerSend);
    }
}
