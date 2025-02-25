package com.siriuserp.accounting.dm;

import com.siriuserp.sdk.dm.Approvable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fixed_asset_approvable_bridge")
public class FixedAssetApprovableBridge extends Approvable {
	private static final long serialVersionUID = -4822692060473909440L;

	@OneToOne(mappedBy = "approvable", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FixedAsset fixedAsset;

	@Override
	public Long getNormalizedID() {

		return getFixedAsset().getId();
	}

	@Override
	public String getReviewID() {
		return String.valueOf(getFixedAsset().getId());
	}
}
