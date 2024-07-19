package io.ljunggren.dhl.api.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Shipment {

    private String id;
    private String service;
    private Place origin;
    private Place destination;
    private ShipmentEvent status;
    @JsonProperty("details")
    private ShipmentDetail detail;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date estimatedTimeOfDelivery;
    private TimeFrame estimatedDeliveryTimeFrame;
    private String serviceUrl;
    private String rerouteUrl;
    private List<ShipmentEvent> events;
    
}
