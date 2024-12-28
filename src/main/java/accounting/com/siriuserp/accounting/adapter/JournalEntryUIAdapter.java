/**
 * Nov 19, 2008 6:04:34 PM
 * com.siriuserp.accounting.adapter
 * JournalEntryDetailAdapter.java
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class JournalEntryUIAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -8642364076760369993L;

	private JournalEntry journalEntry;
	private FastList<JournalEntryIndexUIAdapter> indexes = new FastList<JournalEntryIndexUIAdapter>();

	public JournalEntryUIAdapter()
	{
	}

	public JournalEntryUIAdapter(JournalEntry journalEntry)
	{
		if (journalEntry != null)
			this.journalEntry = journalEntry;
	}

	public BigDecimal getDebet()
	{
		BigDecimal amount = BigDecimal.valueOf(0);
		BigDecimal detailAmount = BigDecimal.valueOf(0);

		for (JournalEntryDetail detail : this.journalEntry.getDetails())
		{
			if (detail.getPostingType().equals(GLPostingType.DEBET))
			{
				detailAmount = detail.getAmount();
				if (detailAmount.compareTo(BigDecimal.ZERO) < 0)
					detailAmount = detailAmount.multiply(BigDecimal.valueOf(-1));

				amount = amount.add(detailAmount);
			}
		}

		return amount;
	}

	public BigDecimal getDebetRp()
	{
		return this.getDebet().multiply(journalEntry.getRate());
	}

	public BigDecimal getCredit()
	{
		BigDecimal amount = BigDecimal.valueOf(0);
		BigDecimal detailAmount = BigDecimal.valueOf(0);

		for (JournalEntryDetail detail : this.journalEntry.getDetails())
		{
			if (detail.getPostingType().equals(GLPostingType.CREDIT))
			{
				detailAmount = detail.getAmount();

				if (detailAmount.compareTo(BigDecimal.ZERO) < 0)
					detailAmount = detailAmount.multiply(BigDecimal.valueOf(-1));

				amount = amount.add(detailAmount);
			}
		}

		return amount;
	}

	public BigDecimal getCreditRp()
	{
		return this.getCredit().multiply(journalEntry.getRate());
	}
}
