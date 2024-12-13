package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.accounting.dm.FinancialStatus;
import com.siriuserp.accountpayable.dm.PaymentApplication;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="payable")
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Payable extends Model implements JSONSupport {
    private static final long serialVersionUID = 7614117724195870029L;
    @Column(name="code")
    protected String code;

    @Column(name="date")
    protected Date date;

    @Column(name="due_date")
    protected Date dueDate;

    @Column(name="paid_date")
    protected Date paidDate;

    @Column(name="tax_date")
    protected Date taxDate;

    @Column(name="invoice")
    protected String invoice;

    @Column(name="document_no")
    protected String documentNo;

    @Column(name="tax_no")
    protected String tax_no;

    @Column(name="note")
    protected String note;

    @Column(name="unpaid")
    protected BigDecimal unpaid = BigDecimal.ZERO;

    @Column(name="clearing")
    protected BigDecimal clearing = BigDecimal.ZERO;

    @Column(name="rounding")
    protected BigDecimal rounding = BigDecimal.ZERO;
    
    @Embedded
    protected Money money = new Money();

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status")
    protected FinancialStatus status = FinancialStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_method_type")
    protected PaymentMethodType paymentMethodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Tax tax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_party_supplier")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Party supplier;
    
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "fk_journal_entry")
//	@LazyToOne(LazyToOneOption.PROXY)
//	@Fetch(FetchMode.SELECT)
//	protected JournalEntry journalEntry;

    //Set PaymentApplication
    @OneToMany(mappedBy="payable",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type="com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    protected Set<PaymentApplication> applications = new FastSet<PaymentApplication>();

    //Set DebitMemoManual
    //Memo & Debit Memo Manual Not Implement Yet
//    @OneToMany(mappedBy = "verification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @LazyCollection(LazyCollectionOption.EXTRA)
//    @Fetch(FetchMode.SELECT)
//    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
//    @OrderBy("id ASC")
//    protected Set<DebitMemoManual> debitMemoManuals = new FastSet<DebitMemoManual>();

    public abstract String getUri();
}
