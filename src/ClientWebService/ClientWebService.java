package ClientWebService;

import javax.xml.soap.*;

/**
 * Created with IntelliJ IDEA.
 * User: ty
 * Date: 30/01/14
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
public class ClientWebService {

    public Boolean login(String login, String pwd) throws SOAPException {

        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "http://127.0.0.1:8089/Projet5A/gameGeneratorMVC/ServiceWeb/server.php";
        SOAPMessage soapResponse = null;
        try {
            soapResponse = soapConnection.call(createSOAPRequest(login, pwd), url);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        soapConnection.close();

        try{
            return Boolean.valueOf(soapResponse.getSOAPBody().getFirstChild().getFirstChild().getFirstChild().getNodeValue());
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private SOAPMessage createSOAPRequest(String login, String pwd) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://127.0.0.1:8089/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("isAuthentified", "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("login", "example");
        soapBodyElem1.addTextNode(login);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("pwd", "example");
        soapBodyElem2.addTextNode(pwd);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "isAuthentified");

        soapMessage.saveChanges();

        return soapMessage;
    }
}
