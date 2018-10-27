package com.apap.tugas1_apap.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;
import com.apap.tugas1_apap.model.ProvinsiModel;
import com.apap.tugas1_apap.service.InstansiService;
import com.apap.tugas1_apap.service.JabatanService;
import com.apap.tugas1_apap.service.PegawaiService;
import com.apap.tugas1_apap.service.ProvinsiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value="/")
	private String home(Model model) {
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		List<InstansiModel> allInstansi = instansiService.getAll();
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allInstansi", allInstansi);
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method = RequestMethod.GET)
	private String view(@RequestParam(value="nip") String nip, Model model, RedirectAttributes ra) {
		Optional<PegawaiModel> pegawai =  pegawaiService.getPegawaiByNip(nip);
		if (pegawai.isPresent()) {
			PegawaiModel recPegawai = pegawai.get();
			List<JabatanModel> allJabatan = jabatanService.getListJabatanOrderByPriceDesc(recPegawai);
			recPegawai.setJabatan(allJabatan);
			
			BigDecimal gaji = pegawaiService.calculateGaji(recPegawai);
			System.out.println("gaji = " + String.valueOf(gaji));
			
			model.addAttribute("pegawai", recPegawai);
			model.addAttribute("gaji", String.valueOf(gaji));
			model.addAttribute("card-title","Lihat Data Pegawai - " + recPegawai.getNama() + " (" + String.valueOf(recPegawai.getNip()) + ")");
		}else {
			ra.addFlashAttribute("alert", "alert-red");
			ra.addFlashAttribute("alertText", "Data Tidak Ditemukan");
			return "redirect:/";
		}
		return "view-pegawai";
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String termuda_tertua(@RequestParam(value="idInstansi") long idInstansi, Model model, RedirectAttributes ra){
		Optional<InstansiModel> instansi = instansiService.getInstansiById(idInstansi);
		List<PegawaiModel> allPegawai = pegawaiService.findByInstansiSortTglLahirDesc(instansi.get());
		
		if (allPegawai.size() != 0) {
			PegawaiModel pegTermuda = allPegawai.get(0);
			PegawaiModel pegTertua = allPegawai.get(allPegawai.size()-1);
			System.out.println(pegTermuda.getNip());
			
			BigDecimal gajiTermuda = pegawaiService.calculateGaji(pegTermuda);
			System.out.println(gajiTermuda);
			BigDecimal gajiTertua = pegawaiService.calculateGaji(pegTertua);
			System.out.println(gajiTertua);
			
			model.addAttribute("pegTertua", pegTertua);
			model.addAttribute("pegTermuda", pegTermuda);
			model.addAttribute("gajiTermuda", gajiTermuda);
			model.addAttribute("gajiTertua", gajiTertua);
			
			return "view-pegTertuaTermuda";
		}
		ra.addFlashAttribute("alert", "alert-red");
		ra.addFlashAttribute("alertText", "Tidak Ada Pegawai");
		return "redirect:/";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		List<ProvinsiModel> allProvinsi = provinsiService.getAll();
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		PegawaiModel pegawai = new PegawaiModel();
		ArrayList<JabatanModel> jabatan = new ArrayList<JabatanModel>();
		jabatan.add(new JabatanModel());
		pegawai.setJabatan(jabatan);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST)
	private String tambah(@ModelAttribute PegawaiModel pegawai, RedirectAttributes ra) {
		System.out.println("id Pegawai: "+String.valueOf(pegawai.getId()));
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		
		
		ra.addFlashAttribute("alert", "alert-green");
		ra.addFlashAttribute("alertText", "Pegawai Berhasil Ditambahkan");
		
		String url = "redirect:/pegawai?nip=" + pegawai.getNip();
		return url;
	}
	

	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.getJabatan().add(new JabatanModel());
		List<ProvinsiModel> allProvinsi = provinsiService.getAll();
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		List<InstansiModel> allInstansi = instansiService.getByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("allInstansi", allInstansi);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		List<ProvinsiModel> allProvinsi = provinsiService.getAll();
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		List<InstansiModel> allInstansi = instansiService.getByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("allInstansi", allInstansi);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		if (pegawai.getJabatan().size() > rowId.intValue()) {
			pegawai.getJabatan().remove(rowId.intValue());
		}
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	private String ubah(@RequestParam(value="nip") String nip, Model model, RedirectAttributes ra) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip).get();
		if (pegawai != null) {
			List<ProvinsiModel> allProvinsi = provinsiService.getAll();
			List<JabatanModel> allJabatan = jabatanService.getListJabatan();
			List<InstansiModel> allInstansi = instansiService.getByProvinsi(pegawai.getInstansi().getProvinsi());
			model.addAttribute("allInstansi", allInstansi);
			model.addAttribute("allJabatan", allJabatan);
			model.addAttribute("allProvinsi",allProvinsi);
			
			model.addAttribute("pegawai", pegawai);
			return "ubah-pegawai";
		}else {
			ra.addFlashAttribute("alert", "alert-red");
			ra.addFlashAttribute("alertText", "Tidak Ada Pegawai");
			String url = "redirect:/pegawai?nip=" + pegawai.getNip();
			return url;
		}
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addOnUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.getJabatan().add(new JabatanModel());
		List<ProvinsiModel> allProvinsi = provinsiService.getAll();
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		List<InstansiModel> allInstansi = instansiService.getByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("allInstansi", allInstansi);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteOnUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		List<ProvinsiModel> allProvinsi = provinsiService.getAll();
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		List<InstansiModel> allInstansi = instansiService.getByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("allInstansi", allInstansi);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		pegawai.getJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String ubah(@ModelAttribute PegawaiModel pegawai, RedirectAttributes ra) {
		System.out.println(pegawai.getInstansi().getNama());
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		
		ra.addFlashAttribute("alert", "alert-green");
		ra.addFlashAttribute("alertText", "Data Berhasil Diubah");
		String url = "redirect:/pegawai?nip=" + pegawai.getNip();
		return url;
	}
	
	@RequestMapping(value="/pegawai/cari", method=RequestMethod.GET)
	private String cari(@RequestParam(value="idProvinsi", required=false) Optional<Integer> idProvinsi, @RequestParam(value="idInstansi", required=false) Optional<Long> idInstansi, @RequestParam(value="idJabatan", required= false) Optional<Integer> idJabatan, Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> allProvinsi = provinsiService.getAll();
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		List<PegawaiModel> allPegawai = pegawaiService.findAll();
		List<PegawaiModel> selectedPegawai = new ArrayList<PegawaiModel>();
		
		// Kalau cari by instansi 
		if (idInstansi.isPresent()) {
			InstansiModel instansi = instansiService.getInstansiById(idInstansi.get()).get(); //get instance instansi
			System.out.println(instansi.getNama());
			// kalau cari pegawai by jabatan dan instansi
			if (idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.findJabatanByIdJabatan(idJabatan.get()).get();
				System.out.println(jabatan.getNama());
				selectedPegawai = pegawaiService.findByInstansiJabatan(instansi, jabatan);
			}else {
				// cuma cari berdasarkan instansi
				selectedPegawai = pegawaiService.findByInstansi(instansi);
			}
		}else if(idProvinsi.isPresent()) {
			// get instance provsinsi
			ProvinsiModel provinsi = provinsiService.getById(idProvinsi.get()).get(); 
			// get all possible instansi in the province
			List<InstansiModel> selectedInstansi = instansiService.getByProvinsi(provinsi); 
			
			// kalau cari by jabatan dan provinsi
			if (idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.findJabatanByIdJabatan(idJabatan.get()).get(); 
				// cari pegawai yang punya jabatan tersebut di list possible instansi
				for (InstansiModel instansi : selectedInstansi) {
					System.out.println("selected instansi: "+instansi.getNama());
					selectedPegawai.addAll(pegawaiService.findByInstansiJabatan(instansi, jabatan));
				}
			}else {
				//cari by provinsi aja
				for (InstansiModel instansi : selectedInstansi) {
					System.out.println("selected instansi: "+instansi.getNama());
					selectedPegawai.addAll(pegawaiService.findByInstansi(instansi));
				}
			}
		}else if (idJabatan.isPresent()) {
			// cari by jabatan aja
			// get instance provinsi
			JabatanModel jabatan = jabatanService.findJabatanByIdJabatan(idJabatan.get()).get();
			selectedPegawai = pegawaiService.findByJabatan(jabatan);
		}
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		model.addAttribute("selectedPegawai", selectedPegawai);
		return "cari-pegawai";
	}
	
//	Ada error karena saat ubah ke json, dia butuh ngeparse semua attributenya kan, sementara di @ManyToMany,
//	dia masing2 nyimpen datanya juga jadi infinitie loop
//	@RequestMapping(value="/pegawai/cari-lagi", method=RequestMethod.GET)
//	private @ResponseBody Map<String, Object> cari(@RequestParam(value="idInstansi") long idInstansi, @RequestParam(value="idJabatan") int idJabatan, Model model) {
//		InstansiModel instansi = instansiService.getInstansiById(idInstansi).get();
//		JabatanModel jabatan = jabatanService.findJabatanByIdJabatan(idJabatan);
//		List<PegawaiModel> selectedPegawai = pegawaiService.findByInstansi(instansi);
//		
//		System.out.println("coba");
//		
//		Map<String, Object> output = new HashMap<String, Object>();
//		output.put("selectedPegawai", selectedPegawai);
//		return output;
//	}
//	
}



