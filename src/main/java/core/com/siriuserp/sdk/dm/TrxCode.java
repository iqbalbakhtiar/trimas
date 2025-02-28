package com.siriuserp.sdk.dm;

import lombok.Getter;

/**
 * @author ferdinand
 */

@Getter
public enum TrxCode 
{
	TRX_01("Kepada Selain Pemungut PPN"),
	TRX_02("Kepada Pemungut PPN Instansi Pemerintah"),
	TRX_03("Kepada Pemungut PPN Selain Instansi Pemerintah"),
	TRX_04("DPP Nilai Lain"),
	TRX_05("Besaran Tertentu"),
	TRX_06("Kepada Orang Pribadi Pemegang Paspor Luar Negeri (16E UU PPN)"),
	TRX_07("Penyerahan dengan Fasilitas PPN atau PPN dan PPnBM Tidak Dipungut/Ditanggung Pemerintah"),
	TRX_08("Penyerahan dengan Fasilitas Dibebaskan PPN atau PPN dan PPnBM"),
	TRX_09("Penyerahan Aktiva yang Menurut Tujuan Semula Tidak Diperjualbelikan (16D UU PPN)"),
	TRX_10("Penyerahan Lainnya");
	
	private String description;
	
	private TrxCode(String descripton)
	{
		this.description = descripton;
	}
	
	public String getShortCode()
	{
		return this.toString().substring(4);
	}
}
