package com.apap.tugas1_apap.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;

public interface JabatanService {

	List<JabatanModel> getListJabatanOrderByPriceDesc(PegawaiModel pegawai);
	
	void addJabatan(JabatanModel jabatan);
	
	Optional<JabatanModel> findJabatanByIdJabatan(long idJabatan);
	
	List<JabatanModel> getListJabatan();

	void deleteJabatan(long idJabatan);

}
