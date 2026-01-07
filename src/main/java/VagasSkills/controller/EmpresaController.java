package VagasSkills.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import VagasSkills.dto.EmpresaRequestDTO;
import VagasSkills.dto.EmpresaResponseDTO;
import VagasSkills.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {
    
    private final EmpresaService empresaService;
    
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criarEmpresa(@Valid @RequestBody EmpresaRequestDTO request) {
        EmpresaResponseDTO empresa = empresaService.criarEmpresa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }
    
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodas() {
        List<EmpresaResponseDTO> empresas = empresaService.listarTodas();
        return ResponseEntity.ok(empresas);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizarEmpresa(
        @PathVariable Long id,
        @Valid @RequestBody EmpresaRequestDTO request
    ) {
        EmpresaResponseDTO empresa = empresaService.atualizarEmpresa(id, request);
        return ResponseEntity.ok(empresa);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}

