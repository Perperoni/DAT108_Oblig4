package no.hvl.dat108.oblig4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.model.Feilmeldinger;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import no.hvl.dat108.oblig4.service.LogginnService;
import no.hvl.dat108.oblig4.service.PassordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InnUtloggingController {

	/*
	 * GET /innlogging - Viser innloggingsskjemaet vhja logginn-viewet.
	 */
	@GetMapping(value = "/innlogging")
	public String henteSkjema() {
        return "logginn";
	}

	/*
	 * POST /innlogging - Tar i mot forsøk på innlogging, dvs: 
	 * - Sjekker at deltager finnes og at passord er korrekt
	 * - Logger inn og går (redirecter) til deltagerlisten
	 *
	 * Hvis ugyldig brukernavn og/eller passord, skal det gjøres er redirect
	 * til /innlogging med en feilmelding satt som en flash-attributt.
	 */
	@PostMapping("/innlogging")
	public String loggeInn(@RequestParam String mobil, @RequestParam String passord, 
			HttpServletRequest request, RedirectAttributes ra) {
		Deltager deltager = DeltagerDbService.finnDeltagerMedMobil(mobil);
		if (deltager == null
			|| !PassordService.erPassordKorrekt(passord, deltager.getHashetPassord())){
			ra.addFlashAttribute("redirectMelding", Feilmeldinger.UGYLDIG_BRUKERNAVN_PASSORD_MLD);
			return "redirect:/innlogging";
		}
		LogginnService.loggInnBruker(request, deltager);
		return "redirect:/deltagerliste";
	}

	/*
	 * POST /utlogging - Tar i mot forespørsel om utlogging, dvs: 
	 * - Logger ut 
	 * - Går (redirecter) til innlogging
	 */
    @PostMapping("/utlogging")
	public String loggeUt(HttpServletRequest request, RedirectAttributes ra) {
		LogginnService.loggUtBruker(request);
		ra.addFlashAttribute("redirectMelding", Feilmeldinger.UTLOGGET_MLD);
		return "redirect:/innlogging";
	}

}
