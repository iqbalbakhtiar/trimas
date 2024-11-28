package com.siriuserp.sdk.utility;

import com.siriuserp.inventory.dm.WarehouseTransactionSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseSourceHelper {
    public static final List<WarehouseTransactionSource> getVarians(String referenceType) {
        return Arrays.asList(WarehouseTransactionSource.values()).stream().filter(source -> source.toString().contains(referenceType)).collect(Collectors.toList());
    }
}
