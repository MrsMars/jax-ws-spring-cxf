package com.aoher.jaxws.cxf.service.impl;

import com.aoher.jaxws.cxf.service.FileTransferMTOMService;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service(value = "fileTransferMTOMService")
public class FileTransferMTOMServiceImpl implements FileTransferMTOMService {

    private static final Logger logger = Logger.getLogger(FileTransferMTOMServiceImpl.class);

    @Override
    public DataHandler downloadFileContent() throws IOException {
        File file = new ClassPathResource("small.csv").getFile();
        return new DataHandler(new FileDataSource(file));
    }

    @Override
    public void uploadFile(DataHandler dataHandler) {
        try {
            File file = File.createTempFile("upload", ".pdf");
            file.deleteOnExit();

            OutputStream outputStream = new FileOutputStream(file);
            dataHandler.writeTo(outputStream);
            outputStream.close();

            logger.info("file saved in = " + file.getPath());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
