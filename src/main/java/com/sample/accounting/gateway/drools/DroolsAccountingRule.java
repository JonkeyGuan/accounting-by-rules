package com.sample.accounting.gateway.drools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class DroolsAccountingRule {

    private String context;
    private String dimension;
    private String category;
    private String condition;
    private String result;

}
