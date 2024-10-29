package com.siriuserp.accounting.dm;

public enum FinancialStatus {
    PAID,UNPAID;

    public String getMessageName()
    {
        return this.toString().toLowerCase();
    }

    public String getFormattedName() {
        String lowerCase = this.toString().toLowerCase(); // Contoh: "paid"
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1); // Contoh: "Paid"
    }
}
