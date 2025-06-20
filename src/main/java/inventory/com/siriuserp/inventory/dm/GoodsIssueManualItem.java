package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Money;
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Rama Almer Felix
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "goods_issue_manual_item")
public class GoodsIssueManualItem extends WarehouseReferenceItem implements Reservable {
    private static final long serialVersionUID = 7794162573313036722L;

    @Column(name = "issued")
    private BigDecimal issued = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_goods_issue_manual")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private GoodsIssueManual goodsIssueManual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container sourceContainer;

    @Override
    public Money getMoney()
    {
        Money money = new Money();
        money.setCurrency(Currency.newInstance("1"));
        money.setExchangeType(ExchangeType.MIDDLE);

        return money;
    }

    @Override
    public Grid getGrid() {
        return null;
    }

    @Override
    public Long getReferenceId() {
        return getGoodsIssueManual().getId();
    }

    @Override
    public String getReferenceCode() {
        return getGoodsIssueManual().getCode();
    }

    @Override
    public String getRefFrom() {
        return getGoodsIssueManual().getSource().getName();
    }

    @Override
    public String getRefTo() {
        Party recipient = getGoodsIssueManual().getRecipient();
        return recipient != null ? recipient.getFullName() : "";
    }

    @Override
    public WarehouseTransaction getWarehouseTransaction() {
        return getGoodsIssueManual();
    }

    @Override
    public WarehouseTransactionSource getTransactionSource() {
        return WarehouseTransactionSource.GOODS_ISSUE_MANUAL;
    }
}
