//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v4.0.5 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
//


package com.smartcity.qualiteair.schema;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.smartcity.qualiteair.schema package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.smartcity.qualiteair.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAQIRequest }
     * 
     * @return
     *     the new instance of {@link GetAQIRequest }
     */
    public GetAQIRequest createGetAQIRequest() {
        return new GetAQIRequest();
    }

    /**
     * Create an instance of {@link GetAQIResponse }
     * 
     * @return
     *     the new instance of {@link GetAQIResponse }
     */
    public GetAQIResponse createGetAQIResponse() {
        return new GetAQIResponse();
    }

}
