package com.siriuserp.inventory.dm;

public interface TransactionItem 
{
	public Long getTransactionId();
	public String getTransactionCode();
	
	public WarehouseTransactionSource getTransactionSource();
}
