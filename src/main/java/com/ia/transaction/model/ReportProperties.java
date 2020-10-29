package com.ia.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "default.report")
@Data
public class ReportProperties {
    private int monthBeginingIndex = 3;
    private int monthEndingIndex = 5;
    private int minimunLineLen = 6;
    private int descriptionBeginingIndex = 5;
    private int dateBeginingIndex = 0;
    private int dateEndingIndex = 2;
    private int transactionNumberIndex = 4;
    private int extensionLenIndex = 4;
    private String lineDelimitationPattern;
    private String creditAmountIndicator="CR";
    private String defaultCategoryDesc="NA";
    private Locations locations;


    @Builder
    @AllArgsConstructor
    @Component
    @Data
    @NoArgsConstructor
    public static class Locations {
        private String capitaleOne;
        private String desjardinsCc;
        private String desjardinsEop;
    }
}
