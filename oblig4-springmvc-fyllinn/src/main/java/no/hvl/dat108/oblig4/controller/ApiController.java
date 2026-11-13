package no.hvl.dat108.oblig4.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.repository.DeltagerRepository;
import no.hvl.dat108.oblig4.service.ApiNokkelService;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import org.hibernate.internal.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ApiController {

    private final DeltagerRepository deltagerRepository;
    private final ApiNokkelService apiNokkelService;

    public ApiController(DeltagerRepository deltagerRepository, ApiNokkelService apiNokkelService) {
        this.deltagerRepository = deltagerRepository;
        this.apiNokkelService = apiNokkelService;
    }

    @GetMapping("api/deltagere")
    public ResponseEntity<List<Deltager>> getDeltagers(HttpServletRequest request, Model model,
                                                       RedirectAttributes ra) {
        if (apiNokkelService.erNokkelGyldig(request.getHeader("Api-Key"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Deltager> deltagers = deltagerRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(deltagers);
    }

    @GetMapping("api/deltagere/{mobil}")
    public ResponseEntity<Deltager> getDeltagerByMob (HttpServletRequest request, Model model,
                                                      RedirectAttributes ra, @PathVariable String mobil) {
        if (apiNokkelService.erNokkelGyldig(request.getHeader("Api-Key"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        if (deltager == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
    }
}
