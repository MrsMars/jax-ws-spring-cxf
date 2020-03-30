package com.aoher.jaxws.cxf.service;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import java.io.IOException;

@WebService
@MTOM
public interface FileTransferMTOMService {

    DataHandler downloadFileContent() throws IOException;
    void uploadFile(DataHandler dataHandler);
}
