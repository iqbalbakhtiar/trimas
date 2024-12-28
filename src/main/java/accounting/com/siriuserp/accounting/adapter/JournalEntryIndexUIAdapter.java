package com.siriuserp.accounting.adapter;

import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 * 
 * Version 1.5
 */

@Getter
@Setter
public class JournalEntryIndexUIAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 8276145929379481790L;

	private IndexType type;
	private Long id;
	private String text;
}
