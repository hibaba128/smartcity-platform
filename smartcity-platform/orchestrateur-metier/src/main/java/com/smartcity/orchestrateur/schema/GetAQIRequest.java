//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v4.0.5 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
//


package com.smartcity.orchestrateur.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.</p>
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="quartier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "quartier"
})
@XmlRootElement(name = "GetAQIRequest")
public class GetAQIRequest {

    @XmlElement(required = true)
    protected String quartier;

    /**
     * Obtient la valeur de la propriété quartier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * Définit la valeur de la propriété quartier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuartier(String value) {
        this.quartier = value;
    }

}
