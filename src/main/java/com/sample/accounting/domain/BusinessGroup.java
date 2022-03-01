package com.sample.accounting.domain;

import java.util.List;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.AccountingItemAccounting;
import com.sample.accounting.domain.accounting.AccountingItemUtils;
import com.sample.accounting.domain.accounting.AccountingRule;
import com.sample.accounting.gateway.RulesGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("businessGroup")
public class BusinessGroup extends AccountingItemAccounting {

    @Autowired
    RulesGateway rulesGateway;

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        List<String> includeCondition = rulesGateway.infer(new AccountingRule("node", "product", "businessGroup", "include"));
        List<String> excludeCondition = rulesGateway.infer(new AccountingRule("node", "product", "businessGroup", "exclude"));
        return account(items, "businessGroup", includeCondition, excludeCondition);
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        List<String> includeCondition = rulesGateway.infer(new AccountingRule("node", "channel", "businessGroup", "include"));
        List<String> excludeCondition = rulesGateway.infer(new AccountingRule("node", "channel", "businessGroup", "exclude"));
        return account(items, "businessGroup", includeCondition, excludeCondition);
    }

    @Override
    protected boolean cannotExpand(AccountingItem item) {
        List<String> expressions = rulesGateway.infer(new AccountingRule("expand", "both", "businessGroup"));
        return AccountingItemUtils.evaluateCannotExpand(item, expressions);
    }

}
