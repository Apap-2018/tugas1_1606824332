package com.apap.tugas1_apap.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiByNip(String nip);
	
	BigDecimal calculateGaji(PegawaiModel pegawai);
	
	List<PegawaiModel> findByInstansiSortTglLahirDesc(InstansiModel instansi);

	void addPegawai(PegawaiModel pegawai);

	String generateNip(PegawaiModel pegawai);

	List<PegawaiModel> findByInstansi(InstansiModel instansi);

	List<PegawaiModel> findByJabatan(JabatanModel jabatan);
}
