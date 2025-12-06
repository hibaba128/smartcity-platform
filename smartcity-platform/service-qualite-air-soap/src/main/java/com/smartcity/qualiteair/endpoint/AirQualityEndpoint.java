// AirQualityEndpoint.java
package com.smartcity.qualiteair.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
// AirQualityEndpoint.java → remplace les imports par :
import schema.GetAQIRequest;
import schema.GetAQIResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Endpoint
public class AirQualityEndpoint {

    private static final String NAMESPACE = "http://smartcity.com/schema/airquality";
    private final Map<String, Integer> aqiData = new ConcurrentHashMap<>();

    public AirQualityEndpoint() {
        aqiData.put("centre", 65);
        aqiData.put("nord", 120);
        aqiData.put("sud", 45);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetAQIRequest")
    @ResponsePayload
    public GetAQIResponse getAQI(@RequestPayload GetAQIRequest request) {
        GetAQIResponse response = new GetAQIResponse();
        response.setAqi(aqiData.getOrDefault(request.getQuartier(), 999));
        return response;
    }
}