package com.siriuserp.sdk.dm;

import javax.persistence.Column;
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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Lot 
{
	@Column(name = "serial_no", length = 100, unique = true)
	private String serial;
	
	@Column(name = "code")
	private String code;

	@Column(name = "info")
	private String info;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_from_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product fromProduct;
}
