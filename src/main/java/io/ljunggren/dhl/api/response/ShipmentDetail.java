package io.ljunggren.dhl.api.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ShipmentDetail {

    private Organization carrier;
    private Product product;
    private Person receiver;
    private Person sender;
    private ProofOfDelivery proofOfDelivery;
    private Boolean proofOfDeliverySignedAvailable;
    private Integer totalNumberOfPieces;
    private List<String> pieceIds;
    @JsonProperty("dimensions")
    private Dimension dimension;
    private Map<String, String> references;

    
}
