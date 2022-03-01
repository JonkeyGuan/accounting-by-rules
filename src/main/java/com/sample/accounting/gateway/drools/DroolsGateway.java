package com.sample.accounting.gateway.drools;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.sample.accounting.domain.accounting.AccountingRule;
import com.sample.accounting.gateway.RulesGateway;

import org.drools.core.common.InternalAgenda;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroolsGateway implements RulesGateway {

    @Autowired
    private KieContainer kieContainer;

    @Override
    public List<String> infer(AccountingRule accountingRule) {
        DroolsAccountingRule droolsAccountingRule = DroolsAccountingRule.builder()
                .context(accountingRule.getContext())
                .dimension(accountingRule.getDimension())
                .category(accountingRule.getCategory())
                .condition(accountingRule.getCondition())
                .build();

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(droolsAccountingRule);
        ((InternalAgenda) kieSession.getAgenda()).activateRuleFlowGroup(droolsAccountingRule.getContext());
        kieSession.fireAllRules();
        kieSession.dispose();

        Collection<? extends Object> collection = kieSession
                .getObjects(new ClassObjectFilter(DroolsAccountingRule.class));
        DroolsAccountingRule rule = null;
        for (Object o : collection) {
            if (o != null) {
                rule = (DroolsAccountingRule) o;
                break;
            }
        }
        return Arrays.stream(rule.getResult().split(",")).map(i -> i.trim()).collect(Collectors.toList());
    }

}
