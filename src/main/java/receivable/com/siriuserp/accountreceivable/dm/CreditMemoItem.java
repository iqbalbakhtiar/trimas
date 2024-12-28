package com.siriuserp.accountreceivable.dm;

import com.siriuserp.sdk.dm.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="credit_memo_item")
public class CreditMemoItem extends Model {
    private static final long serialVersionUID = -7166040954245019901L;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_memo_item")
//    @LazyToOne(LazyToOneOption.PROXY)
//    @Fetch(FetchMode.SELECT)
//    private SalesMemoItem memoItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_credit_memo")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private CreditMemo memo;

    @Override
    public String getAuditCode() {
        return "";
    }
}
