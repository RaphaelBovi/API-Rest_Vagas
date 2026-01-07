package VagasSkills.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import VagasSkills.dto.CandidaturaRequestDTO;
import VagasSkills.dto.CandidaturaResponseDTO;
import VagasSkills.dto.VagaRequestDTO;
import VagasSkills.dto.VagaResponseDTO;
import VagasSkills.service.VagaService;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
@RequiredArgsConstructor
public class VagaController {
    
    private final VagaService vagaService;
    
    @PostMapping
    public ResponseEntity<VagaResponseDTO> criarVaga(@Valid @RequestBody VagaRequestDTO request) {
        VagaResponseDTO vaga = vagaService.criarVaga(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaga);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> buscarPorId(@PathVariable Long id) {
        VagaResponseDTO vaga = vagaService.buscarPorId(id);
        return ResponseEntity.ok(vaga);
    }
    
    @GetMapping
    public ResponseEntity<List<VagaResponseDTO>> listarTodas(
        @RequestParam(required = false) String palavraChave,
        @RequestParam(required = false) String cidade,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) String tipoContrato,
        @RequestParam(required = false) String modalidade,
        @RequestParam(required = false) String nivelExperiencia
    ) {
        List<VagaResponseDTO> vagas = vagaService.listarTodas(
            palavraChave, cidade, estado, tipoContrato, modalidade, nivelExperiencia
        );
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<VagaResponseDTO>> buscarPorPalavraChave(
        @RequestParam String q
    ) {
        List<VagaResponseDTO> vagas = vagaService.buscarPorPalavraChave(q);
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/regiao")
    public ResponseEntity<List<VagaResponseDTO>> buscarPorRegiao(
        @RequestParam(required = false) String cidade,
        @RequestParam(required = false) String estado
    ) {
        List<VagaResponseDTO> vagas = vagaService.buscarPorRegiao(cidade, estado);
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/destaque")
    public ResponseEntity<List<VagaResponseDTO>> buscarDestaques() {
        List<VagaResponseDTO> vagas = vagaService.buscarDestaques();
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/recentes")
    public ResponseEntity<List<VagaResponseDTO>> buscarRecentes(
        @RequestParam(required = false, defaultValue = "10") Integer limite
    ) {
        List<VagaResponseDTO> vagas = vagaService.buscarRecentes(limite);
        return ResponseEntity.ok(vagas);
    }
    
    @PostMapping("/{vagaId}/candidatar")
    public ResponseEntity<CandidaturaResponseDTO> candidatar(
        @PathVariable Long vagaId,
        @Valid @RequestBody CandidaturaRequestDTO request
    ) {
        CandidaturaResponseDTO candidatura = vagaService.candidatar(vagaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatura);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> atualizarVaga(
        @PathVariable Long id,
        @Valid @RequestBody VagaRequestDTO request
    ) {
        VagaResponseDTO vaga = vagaService.atualizarVaga(id, request);
        return ResponseEntity.ok(vaga);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVaga(@PathVariable Long id) {
        vagaService.deletarVaga(id);
        return ResponseEntity.noContent().build();
    }
}

