package com.sample.accounting.domain.accounting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountingItemUtils {

    public static String getMergeKey(AccountingItem item, List<String> condition) {
        Class<AccountingItem> clazz = AccountingItem.class;
        StringBuilder sb = new StringBuilder("");
        condition.forEach(c -> {
            try {
                Field field = clazz.getDeclaredField(c);
                field.setAccessible(true);
                Object value = field.get(item);
                if (value != null) {
                    sb.append(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return sb.toString();
    }

    public static String getValue(AccountingItem item, String name) {
        Class<AccountingItem> clazz = AccountingItem.class;
        String result = "";
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            Object value = field.get(item);
            if (value != null) {
                result = String.valueOf(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getGroupKey(AccountingItem item, List<String> condition) {
        Class<AccountingItem> clazz = AccountingItem.class;
        StringBuilder sb = new StringBuilder("");
        condition.forEach(c -> {
            try {
                Field field = clazz.getDeclaredField(c);
                field.setAccessible(true);
                Object value = field.get(item);
                if (value != null) {
                    sb.append(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return sb.toString();
    }

    public static List<AccountingItem> prorate(AccountingItem item, Map<AccountingItem, Double> percentage) {
        List<AccountingItem> result = new ArrayList<AccountingItem>();
        percentage.forEach((k, v) -> {
            AccountingItem newItem = AccountingItem.builder()
                    .month(k.getMonth())
                    .channel(k.getChannel())
                    .market(k.getMarket())
                    .buyer(k.getBuyer())
                    .businessGroup(k.getBusinessGroup())
                    .businessUnit(k.getBusinessUnit())
                    .product(k.getProduct())
                    .subCatalog(item.getSubCatalog())
                    .catalog(item.getCatalog())
                    .amount((double) Math.round(item.getAmount() * v * 100) / 100)
                    // .amount(item.getAmount() * v)
                    .build();
            result.add(newItem);
        });
        return result;
    }
}
