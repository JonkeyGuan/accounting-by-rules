package com.sample.accounting.gateway;

import java.util.List;

import com.sample.accounting.domain.accounting.AccountingRule;

public interface RulesGateway {

    public List<String> infer(AccountingRule accountingRule);

}