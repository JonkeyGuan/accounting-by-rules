package com.sample.accounting.domain.accounting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sample.accounting.gateway.RulesGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("accountingMerge")
public class AccountingMerge implements BaseAccounting {

    @Autowired
    RulesGateway rulesGateway;
    
    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return account(items);
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        return account(items);
    }

    private List<AccountingItem> account(List<AccountingItem> items) {
        List<String> condition = rulesGateway.infer(new AccountingRule("merge", "product"));
        Map<String, AccountingItem> map = new LinkedHashMap<>();
        items.forEach(item -> {
            String key = AccountingItemUtils.getMergeKey(item, condition);
            if (!map.containsKey(key)) {
                map.put(key, item);
            } else {
                AccountingItem existsItem = map.get(key);
                existsItem.setAmount(existsItem.getAmount() + item.getAmount());
            }
        });
        return map.values().stream().collect(Collectors.toList());
    }
}
