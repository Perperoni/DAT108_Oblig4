package no.hvl.dat108.oblig4.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import no.hvl.dat108.oblig4.dto.DeltagerPostRequestDTO;
import no.hvl.dat108.oblig4.dto.DeltagerResponseDTO;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.repository.DeltagerRepository;
import no.hvl.dat108.oblig4.service.ApiNokkelService;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import no.hvl.dat108.oblig4.service.PassordService;
import org.hibernate.internal.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ApiController {

    private final DeltagerRepository deltagerRepository;
    private final ApiNokkelService apiNokkelService;
    private final DeltagerDbService deltagerDbService;

    public ApiController(DeltagerRepository deltagerRepository, ApiNokkelService apiNokkelService, DeltagerDbService deltagerDbService) {
        this.deltagerRepository = deltagerRepository;
        this.apiNokkelService = apiNokkelService;
        this.deltagerDbService = deltagerDbService;
    }

    @GetMapping("api/deltagere")
    public ResponseEntity<List<DeltagerResponseDTO>> getDeltagers(HttpServletRequest request, Model model,
                                                                  RedirectAttributes ra) {
        if (apiNokkelService.erNokkelGyldig(request.getHeader("Api-Key"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<DeltagerResponseDTO> deltagers = deltagerRepository.findAll().stream().map(DeltagerResponseDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(deltagers);
    }

    @GetMapping("api/deltagere/{mobil}")
    public ResponseEntity<DeltagerResponseDTO> getDeltagerByMob (HttpServletRequest request, Model model,
                                                      RedirectAttributes ra, @PathVariable String mobil) {
        if (apiNokkelService.erNokkelGyldig(request.getHeader("Api-Key"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        DeltagerResponseDTO deltager = deltagerRepository.findByMobil(mobil);
        if (deltager == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(deltager);
    }

    @PostMapping("api/deletagere")
    public ResponseEntity<DeltagerResponseDTO> nyDeltager(HttpServletRequest request, Model model,
                                                          RedirectAttributes ra, @Valid @RequestBody DeltagerPostRequestDTO dto) {
        if (apiNokkelService.erNokkelGyldig(request.getHeader("Api-Key"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String hashetPassord = PassordService.lagHashetPassord(dto.passord());
        Deltager nyDeltager = dto.lagDeltager(hashetPassord);

        if (deltagerDbService.erDeltagerPaameldt(dto.mobil())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Deltager lagraDeltager = deltagerDbService.opprett(nyDeltager);
        DeltagerResponseDTO responseDTO = new DeltagerResponseDTO(lagraDeltager);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
