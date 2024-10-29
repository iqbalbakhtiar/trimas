package com.siriuserp.sales.dm;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "warehouse_reference_item")
@Inheritance(strategy = InheritanceType.JOINED)
public class WarehouseReferenceItem extends Model implements JSONSupport {

    private static final long serialVersionUID = -4018513192207581952L;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "reference_code")
    private String referenceCode;

    @Column(name = "date")
    private Date date;

    @Embedded
    private Lot lot;

    @Column(name = "reference_from")
    private String referenceFrom;

    @Column(name = "reference_to")
    private String referenceTo;

    @Column(name = "note")
    private String note;

    @Column(name = "verificated")
    @Type(type = "yes_no")
    private boolean verificated = Boolean.FALSE;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facility;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facilitySource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_destination")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facilityDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_grid_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Grid gridSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_grid_destination")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Grid gridDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product product;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax_ext")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax extTax1;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_currency")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Currency currency;

    @Override
    public String getAuditCode() {
        return id + "," + referenceCode;
    }
}
