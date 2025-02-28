package com.siriuserp.sdk.dm;

import com.siriuserp.inventory.dm.InventoryControl;
import com.siriuserp.inventory.dm.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="reserve_control")
public class ReserveControl extends InventoryControl {
    private static final long serialVersionUID = 3671792822228238140L;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_reserve_bridge")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private ReserveBridge reserveBridge;

    @Override
    public Date getDate() {
        return getReserveBridge().getDate();
    }

    @Override
    public Product getProduct() {
        return getReserveBridge().getProduct();
    }

    @Override
    public Container getSourceContainer() {
        return getReserveBridge().getContainer();
    }

    @Override
    public Container getDestinationContainer() {
        return getReserveBridge().getContainer();
    }

    @Override
    public Grid getSourceGrid() {
        return getReserveBridge().getGrid();
    }

    @Override
    public Grid getDestinationGrid() {
        return getReserveBridge().getGrid();
    }

    @Override
    public Party getOrganization() {
        return getReserveBridge().getOrganization();
    }

    @Override
    public Container getContainer() {
        return getSourceContainer();
    }

    @Override
    public String getAuditCode() {
        return null;
    }
}
