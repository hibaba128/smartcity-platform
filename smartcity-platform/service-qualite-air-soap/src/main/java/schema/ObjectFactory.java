package schema;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public GetAQIRequest createGetAQIRequest() {
        return new GetAQIRequest();
    }

    public GetAQIResponse createGetAQIResponse() {
        return new GetAQIResponse();
    }
}
