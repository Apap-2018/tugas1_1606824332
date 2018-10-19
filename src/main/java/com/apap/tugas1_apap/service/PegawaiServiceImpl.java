package com.apap.tugas1_apap.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;
import com.apap.tugas1_apap.repository.PegawaiDb;

@Service
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public Optional<PegawaiModel> getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public BigDecimal calculateGaji(PegawaiModel pegawai) {
		Double persen_tunjangan = pegawai.getInstansi().getProvinsi().getPresentase_tunjangan();
		Double gaji_pokok = pegawai.getJabatan().get(0).getGajiPokok();
		Double gaji = gaji_pokok + ((persen_tunjangan/100) * gaji_pokok);
		BigDecimal bd = new BigDecimal(gaji).setScale(0);
		return bd;
	}

	@Override
	public List<PegawaiModel> findByInstansiSortTglLahirDesc(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
	}
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
//		idInstansi+tgllahir dd-mm-yy + mulai kerja + nomor urut

		String kodeInstansi = String.valueOf(pegawai.getInstansi().getId());
		String mulaiMasuk = String.format("%04d", Integer.parseInt(pegawai.getTahunMasuk()));
		int nomorUrut = 1;
		
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String kodeTglLahir = df.format(pegawai.getTanggalLahir());
		System.out.println("Kode tanggal lahir: "+kodeTglLahir);
				
				
		String nip = kodeInstansi.concat(kodeTglLahir).concat(mulaiMasuk).concat(String.format("%02d", nomorUrut));
		while(pegawaiDb.existsByNip(nip)) {
			nomorUrut+=01;
			nip = nip.substring(0,14).concat(String.format("%02d", nomorUrut));
			System.out.println(nip);
		}
		return nip;
	}
	
	@Override
	public List<PegawaiModel> findByInstansi(InstansiModel instansi){
		return pegawaiDb.findByInstansi(instansi);
	}
	
	@Override
	public List<PegawaiModel> findByJabatan(JabatanModel jabatan){
		return pegawaiDb.findByJabatan(jabatan);
	}
}
