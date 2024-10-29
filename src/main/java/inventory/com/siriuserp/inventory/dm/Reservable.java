package com.siriuserp.inventory.dm;

public interface Reservable extends Inventoriable 
{
//	public ReserveBridge getReserveBridge();

	default public boolean isMinus() {
		return false;
	}
}
