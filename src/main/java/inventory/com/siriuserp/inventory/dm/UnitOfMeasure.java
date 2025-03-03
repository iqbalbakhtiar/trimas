package com.siriuserp.inventory.dm;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "unit_of_measure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnitOfMeasure extends Model
{
	private static final long serialVersionUID = 5359175064975506659L;

	@Column(name = "measure_id")
	private String measureId;

	@Column(name = "name")
	private String name;

	@Column(name = "is_base", length = 1)
	@Type(type = "yes_no")
	private boolean base = false;

	@Column(name = "is_pack", length = 1)
	@Type(type = "yes_no")
	private boolean pack = false;

	@Enumerated(EnumType.STRING)
	@Column(name = "unit_type", length = 25, nullable = false)
	private UnitType type;

	@OneToMany(mappedBy = "from", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<UnitofMeasureFactor> factors = new FastSet<UnitofMeasureFactor>();

	@OneToMany(mappedBy = "unitOfMeasure", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Product> products = new FastSet<Product>();

	public static final UnitOfMeasure newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			UnitOfMeasure measure = new UnitOfMeasure();
			measure.setId(Long.valueOf(id));
			return measure;
		}

		return null;
	}

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("name", getName());
		map.put("measureId", getMeasureId());

		return map;
	}

	public boolean isDelete()
	{
		return (this.products == null || this.products.isEmpty()) && (this.factors == null || this.factors.isEmpty());
	}

	@Override
	public String getAuditCode()
	{
		return this.measureId + "," + this.name;
	}
}
