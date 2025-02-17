package com.siriuserp.sdk.dm;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * @author Ferdinand
 * @author Rama Almer Felix
 *  Sirius Indonesia, PT
 *  www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "barcode_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BarcodeGroup extends Model {
    private static final long serialVersionUID = -3784809459364279072L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facility;

    @Column(name = "type")
	@Enumerated(EnumType.STRING)
	private BarcodeGroupType barcodeGroupType = BarcodeGroupType.PRODUCTION;

    @Column(name = "status")
    @Type(type = "yes_no")
    private boolean active = false;

    // Set Of Barcode
    @OneToMany(mappedBy = "barcodeGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id ASC")
    private Set<Barcode> barcodes = new FastSet<Barcode>();

    @Override
    public String getAuditCode() {
        return this.id + "," + this.code;
    }
}
