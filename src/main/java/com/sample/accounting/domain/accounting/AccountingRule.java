package com.sample.accounting.domain.accounting;

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
public class AccountingRule {

    private String context;
    private String dimension;
    private String category;
    private String condition;

    public AccountingRule(String context, String dimension) {
        this.context = context;
        this.dimension = dimension;
    }

    public AccountingRule(String context, String dimension, String category) {
        this.context = context;
        this.dimension = dimension;
        this.category = category;
    }
}
