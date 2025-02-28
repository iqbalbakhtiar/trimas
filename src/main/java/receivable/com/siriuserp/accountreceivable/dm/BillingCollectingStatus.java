package com.siriuserp.accountreceivable.dm;

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_collection_status")
public class BillingCollectingStatus extends Model implements JSONSupport {

    private static final long serialVersionUID = 889409818347715033L;

    @Enumerated(EnumType.STRING)
	@Column(name = "collecting_status")
	private CollectingStatus status = CollectingStatus.CREATED;

	@Column(name = "collecting_date")
	private Date collectingDate;

	@Column(name = "acceptance_date")
	private Date acceptanceDate;

	@Column(name = "on_finance_date")
	private Date onFinanceDate;

	@Column(name = "due_date")
	private Date dueDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_collector")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party collector;

    @OneToOne(mappedBy = "collectingStatus", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Billing billing;

    @Override
    public String getAuditCode() {
        return "";
    }
}
