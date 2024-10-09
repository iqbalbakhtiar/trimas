package com.siriuserp.sdk.dm;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Lot {
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_from_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product fromProduct;
}
