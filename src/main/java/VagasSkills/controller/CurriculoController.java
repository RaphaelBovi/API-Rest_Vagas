package VagasSkills.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import VagasSkills.dto.CurriculoRequestDTO;
import VagasSkills.dto.CurriculoResponseDTO;
import VagasSkills.dto.VagaResponseDTO;
import VagasSkills.service.CurriculoService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/curriculos")
@RequiredArgsConstructor
public class CurriculoController {
    
    private final CurriculoService curriculoService;
    
    @PostMapping
    public ResponseEntity<CurriculoResponseDTO> criarCurriculo(
            @Valid @RequestBody CurriculoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(curriculoService.criarCurriculo(request));
    }
    
    @GetMapping("/{id}/vagas")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagas(@PathVariable Long id) {
        return ResponseEntity.ok(curriculoService.buscarVagasPorCurriculo(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CurriculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(curriculoService.buscarPorId(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CurriculoResponseDTO> atualizarCurriculo(
            @PathVariable Long id,
            @Valid @RequestBody CurriculoRequestDTO request) {
        return ResponseEntity.ok(curriculoService.atualizarCurriculo(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurriculo(@PathVariable Long id) {
        curriculoService.deletarCurriculo(id);
        return ResponseEntity.noContent().build();
    }
}
