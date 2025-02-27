package com.siriuserp.accountreceivable.dm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "credit_memo")
public class CreditMemo extends Memo {
    private static final long serialVersionUID = -3555823796076360030L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_billing")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Billing billing;

    @Column(name = "sequence")
    private Integer sequence = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private CreditMemoReferenceType creditMemoReferenceType;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_memoable")
//    @LazyToOne(LazyToOneOption.PROXY)
//    @Fetch(FetchMode.SELECT)
//    private SalesMemoable memoable;

    @OneToMany(mappedBy="memo",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type="com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    private Set<CreditMemoItem> memoItems = new HashSet<CreditMemoItem>();
}
