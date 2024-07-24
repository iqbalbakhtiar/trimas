package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="rest_api_configuration")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class RestApiConfiguration extends Model {

	private static final long serialVersionUID = -7050787677101562110L;

	@Column(name = "popup_api", nullable = false)
	private String popupApi;
	
	@Column(name = "report_api", nullable = false)
	private String reportApi;
	
	@Column(name = "dashboard_api", nullable = false)
	private String dashboardApi;
	
	@Column(name = "active")
	private boolean active;

	public String getPopupApi() {
		return popupApi;
	}

	public void setPopupApi(String popupApi) {
		this.popupApi = popupApi;
	}

	public String getReportApi() {
		return reportApi;
	}

	public void setReportApi(String reportApi) {
		this.reportApi = reportApi;
	}

	public String getDashboardApi() {
		return dashboardApi;
	}

	public void setDashboardApi(String dashboardApi) {
		this.dashboardApi = dashboardApi;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String getAuditCode() {
		return getId().toString();
	}

}
