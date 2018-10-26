package com.apap.tugas1_apap.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;
import com.apap.tugas1_apap.service.JabatanService;
import com.apap.tugas1_apap.service.PegawaiService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("title", "Tambah Jabatan");
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.POST)
	private String tambah(@ModelAttribute JabatanModel jabatan, Model model, RedirectAttributes ra) {
		jabatanService.addJabatan(jabatan);
		ra.addFlashAttribute("alert", "alert-green");
		ra.addFlashAttribute("alertText", "Data Berhasil Disimpan");
		model.addAttribute("title", "Tambah Jabatan");
		String link = "redirect:/jabatan/view?idJabatan="+String.valueOf(jabatan.getId());
		return link;
	}
	
	@RequestMapping(value="/jabatan/view", method = RequestMethod.GET)
	private String view(@RequestParam(value="idJabatan") long idJabatan, Model model) {
		Optional<JabatanModel> jabatan = jabatanService.findJabatanByIdJabatan(idJabatan);
		if (jabatan.isPresent()) {
			model.addAttribute("jabatan", jabatan.get());
			return "view-jabatan";
		}
		return "notFound";
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.GET)
	private String ubah(@RequestParam(value="idJabatan") long idJabatan, Model model) {
		Optional<JabatanModel> jabatan = jabatanService.findJabatanByIdJabatan(idJabatan);
		if (jabatan.isPresent()) {
			model.addAttribute("jabatan", jabatan.get());
			model.addAttribute("title", "Ubah Jabatan");
			return "ubah-jabatan";
		}else {
			return "notFound";
		}
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.POST)
	private String ubah(@ModelAttribute JabatanModel jabatan, Model model, RedirectAttributes ra) {
		jabatanService.addJabatan(jabatan);
		ra.addFlashAttribute("alert", "alert-green");
		ra.addFlashAttribute("alertText", "Data Berhasil Disimpan");
		String link = "redirect:/jabatan/view?idJabatan="+String.valueOf(jabatan.getId());
		return link;
	}
	
	@RequestMapping(value="/jabatan/hapus", method = RequestMethod.POST)
	private String hapus(@ModelAttribute JabatanModel jabatan, RedirectAttributes ra) {
		List<PegawaiModel> allPegawai = pegawaiService.findByJabatan(jabatan);
		
		if (allPegawai.size() > 0) {
			ra.addFlashAttribute("alert", "alert-red");
			ra.addFlashAttribute("alertText", "Data Tidak Berhasil Dihapus, masih ada pegawainya");
			String link = "redirect:/jabatan/view?idJabatan="+String.valueOf(jabatan.getId());
			return link;
		}
		System.out.println(jabatan.getDeskripsi());
		jabatanService.deleteJabatan(jabatan.getId());
		ra.addFlashAttribute("alert", "alert-green");
		ra.addFlashAttribute("alertText", "Data Berhasil Dihapus");
		return "redirect:/";
	}
	
	@RequestMapping(value="/jabatan/viewall")
	private String viewall(Model model) {
		List<JabatanModel> allJabatan = jabatanService.getListJabatan();
		model.addAttribute("allJabatan",allJabatan);
		return "viewall-jabatan";
	}
}
