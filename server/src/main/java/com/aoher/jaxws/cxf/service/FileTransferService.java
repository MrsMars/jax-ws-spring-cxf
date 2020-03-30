package com.aoher.jaxws.cxf.service;

import javax.jws.WebService;
import java.io.IOException;

@WebService
public interface FileTransferService {

    byte[] downloadFileContent() throws IOException;
    void uploadFile(byte[] fileContent);
}
