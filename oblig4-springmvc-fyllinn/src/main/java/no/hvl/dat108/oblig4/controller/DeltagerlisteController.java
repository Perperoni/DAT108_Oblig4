package no.hvl.dat108.oblig4.controller;

import jakarta.servlet.http.HttpServletRequest;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.model.Feilmeldinger;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import no.hvl.dat108.oblig4.service.LogginnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class DeltagerlisteController {
	
	/**
	 * GET /deltagerliste - Henter deltagerlisten og spiller ballen videre til
     * deltagerliste-viewet for visning.
     *
     * Krever at man er innlogget.
     * Hvis ikke bruker er innlogget, skal det gjøres er redirect til /innlogging
     * med en feilmelding satt som en flash-attributt.
	 */
    @GetMapping("/deltagerliste")
    public String henteDeltagerliste(HttpServletRequest request, Model model,
									 RedirectAttributes ra) {
		if(!LogginnService.erBrukerLoggetInn(request)) {
			ra.addFlashAttribute("redirectMelding", Feilmeldinger.KREVER_INNLOGGET_MLD);
			return "redirect:/innlogging";
		}
		List<Deltager> deltagere = new ArrayList<>(DeltagerDbService.finnAlleSortertPaaFulltNavn());
		model.addAttribute("deltagere", deltagere);
		return "deltagerliste";
    }
}
