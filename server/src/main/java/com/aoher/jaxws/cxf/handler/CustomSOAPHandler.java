package com.aoher.jaxws.cxf.handler;

import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class CustomSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger logger = Logger.getLogger(CustomSOAPHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        logger.info("=========  handleMessage ========= ");

        boolean outboundProperty = (boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        SOAPMessage message = soapMessageContext.getMessage();

        if (outboundProperty) {
            logger.info("mensaje de salida");
        } else {
            logger.info("mensaje de entrada");

            QName qname = new QName("http://cxf.jaxws.aoher.com/", "ejemplo");
            try {
                SOAPHeaderElement soapHeaderElement = message.getSOAPHeader().addHeaderElement(qname);
                soapHeaderElement.addTextNode("valor");
                message.saveChanges();
            } catch (SOAPException ex) {
                logger.error("error añadiendo cabecera", ex);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            message.writeTo(out);
            logger.info("SOAP\n" + new String(out.toByteArray(), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            logger.error("error imprimiendo mensaje SOAP", ex);
        }

        logger.info("======= fin  handleMessage ========= ");
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // nothing here

    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
