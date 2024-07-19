package io.ljunggren.dhl.api.response;

import java.util.List;

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
public class TrackingResponse {

    private String url;
    private String prevUrl;
    private String nextUrl;
    private String firstUrl;
    private String lastUrl;
    private List<Shipment> shipments;
    @JsonProperty("possibleAdditionalShipmentsUrl")
    private List<String> possibleAdditionalShipmentUrls;
    
    private Error error;
    
}
