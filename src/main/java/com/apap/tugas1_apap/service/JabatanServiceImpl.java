package com.apap.tugas1_apap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;
import com.apap.tugas1_apap.repository.JabatanDb;

@Service
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public List<JabatanModel> getListJabatanOrderByPriceDesc(PegawaiModel pegawai) {
		return jabatanDb.findByPegawaiOrderByGajiPokokDesc(pegawai);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public List<JabatanModel> getListJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel findJabatanByIdJabatan(long idJabatan) {
		return jabatanDb.getOne(idJabatan);
	}

	@Override
	public void deleteJabatan(long idJabatan) {
		jabatanDb.deleteById(idJabatan);
	}
	
	

}
