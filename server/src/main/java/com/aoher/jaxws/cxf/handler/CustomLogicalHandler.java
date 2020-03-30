package com.aoher.jaxws.cxf.handler;

import org.apache.log4j.Logger;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class CustomLogicalHandler implements LogicalHandler<LogicalMessageContext> {

    private static final Logger logger = Logger.getLogger(CustomLogicalHandler.class);

    @Override
    public boolean handleMessage(LogicalMessageContext logicalMessageContext) {
        logger.info("=========  handleMessage ========= ");

        boolean outboundProperty = (boolean) logicalMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        logger.info(outboundProperty ? "mensaje de salida" : "mensaje de entrada");

        LogicalMessage logicalMessage = logicalMessageContext.getMessage();
        Source payload = logicalMessage.getPayload();

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Result result = new StreamResult(out);
            transformer.transform(payload, result);
            logger.info("SOAP\n" + new String(out.toByteArray(), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            logger.error("error procesando xml de payload", ex);
        }

        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext logicalMessageContext) {
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {
    }
}
