package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.ReserveBridge;

public interface Reservable extends Inventoriable 
{
	public ReserveBridge getReserveBridge();

	default public boolean isMinus() {
		return false;
	}
}
