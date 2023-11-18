package com.master.credit.card.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WebshopInformation {
    @JsonProperty(required = true)
    private String merchantId; // 30
    @JsonProperty(required = true)
    private String merchantPassword; // 100
    @JsonProperty(required = true)
    private int amount; // 10 2 --
    @JsonProperty(required = true)
    private int merchantOrderId; // 10 -- generate
    @JsonProperty(required = true)
    private LocalDateTime merchantTimestamp;
    @JsonProperty(value = "", required = true)
    private String successURL = ""; // get
    @JsonProperty(value = "", required = true)
    private String failedURL = ""; // get
    @JsonProperty(value = "", required = true)
    private String errorURL = ""; // get

}
