package com.siriuserp.accounting.adapter;

import com.siriuserp.sdk.utility.DateHelper;

import java.util.Comparator;
import java.util.Date;

public class LedgerDetailAdapterComparator implements Comparator<LedgerDetailAdapter> {
    @Override
    public int compare(LedgerDetailAdapter o1, LedgerDetailAdapter o2)
    {
        int result = 0;

        Date date1 = o1.getDate();
        Date date2 = o2.getDate();

        long diff = DateHelper.getDiff2Day(date1, date2);

        if (diff < 0)
            result = 1;
        else if (diff == 0)
            result = 0;
        else
            result= -1;

        return result;
    }
}
