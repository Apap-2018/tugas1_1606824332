package com.apap.tugas1_apap.service;

import java.util.List;

import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;

public interface JabatanService {

	List<JabatanModel> getListJabatanOrderByPriceDesc(PegawaiModel pegawai);
	
	void addJabatan(JabatanModel jabatan);
	
	JabatanModel findJabatanByIdJabatan(long idJabatan);
	
	List<JabatanModel> getListJabatan();

	void deleteJabatan(long idJabatan);
}
