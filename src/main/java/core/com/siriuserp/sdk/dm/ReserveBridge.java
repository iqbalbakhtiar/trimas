package com.siriuserp.sdk.dm;

import com.siriuserp.inventory.dm.Inventoriable;
import com.siriuserp.inventory.dm.Product;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="reserve_bridge")
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class ReserveBridge extends Model implements Inventoriable {
    private static final long serialVersionUID = 1659355337753935697L;

    @OneToMany(mappedBy = "reserveBridge", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id ASC")
    protected Set<ReserveControl> reserveControls = new FastSet<ReserveControl>();

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Product getProduct() {
        return null;
    }

    @Override
    public BigDecimal getQuantity() {
        return BigDecimal.ZERO;
    }

    @Override
    public Party getOrganization() {
        return null;
    }

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public Grid getGrid() {
        return null;
    }

    @Override
    public Container getSourceContainer() {
        return null;
    }

    @Override
    public Grid getSourceGrid() {
        return null;
    }

    @Override
    public Container getDestinationContainer() {
        return null;
    }

    @Override
    public Grid getDestinationGrid() {
        return null;
    }

    @Override
    public Lot getLot() {
        return null;
    }

    @Override
    public Tag getTag() {
        return null;
    }

    @Override
    public String getAuditCode() {
        return "";
    }
}
