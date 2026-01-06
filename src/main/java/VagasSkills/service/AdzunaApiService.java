package VagasSkills.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import VagasSkills.dto.AdzunaResponse;
import VagasSkills.dto.VagaResponseDTO;
import VagasSkills.entity.Curriculo;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdzunaApiService {
    
    private final WebClient webClient;
    
    @Value("${adzuna.api.app-id}")
    private String appId;
    
    @Value("${adzuna.api.app-key}")
    private String appKey;
    
    @Value("${adzuna.api.timeout:5000}")
    private long timeout;
    
    public List<VagaResponseDTO> buscarVagas(Curriculo curriculo) {
        String what = construirQueryWhat(curriculo);
        String where = curriculo.getResidencia();
        
        log.info("Buscando vagas na API Adzuna - what: {}, where: {}", what, where);
        
        try {
            AdzunaResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("app_id", appId)
                    .queryParam("app_key", appKey)
                    .queryParam("what", what)
                    .queryParam("where", where)
                    .build())
                .retrieve()
                .bodyToMono(AdzunaResponse.class)
                .timeout(Duration.ofMillis(timeout))
                .block();
            
            if (response != null && response.getResults() != null) {
                log.info("Encontradas {} vagas para o currículo ID: {}", response.getResults().size(), curriculo.getId());
                return response.getResults();
            } else {
                log.warn("Nenhuma vaga encontrada para o currículo ID: {}", curriculo.getId());
                return Collections.emptyList();
            }
        } catch (WebClientResponseException e) {
            log.error("Erro na resposta da API Adzuna - Status: {}, Mensagem: {}", e.getStatusCode(), e.getMessage());
            throw new RuntimeException("Erro ao buscar vagas na API Adzuna: " + e.getMessage(), e);
        } catch (WebClientException e) {
            log.error("Erro de conexão com a API Adzuna: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao conectar com a API Adzuna. Verifique sua conexão e tente novamente.", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar vagas na API Adzuna: {}", e.getMessage(), e);
            throw new RuntimeException("Erro inesperado ao buscar vagas", e);
        }
    }
    
    private String construirQueryWhat(Curriculo curriculo) {
        StringBuilder query = new StringBuilder();
        
        // Adiciona skills
        if (curriculo.getSkills() != null && !curriculo.getSkills().isEmpty()) {
            curriculo.getSkills().forEach(skill -> 
                query.append(skill.getNome()).append(" ")
            );
        }
        
        // Adiciona nível de escolaridade como cargo sugerido
        if (curriculo.getNivelEscolaridade() != null && !curriculo.getNivelEscolaridade().trim().isEmpty()) {
            query.append(curriculo.getNivelEscolaridade()).append(" ");
        }
        
        String result = query.toString().trim();
        if (result.isEmpty()) {
            log.warn("Query 'what' vazia para currículo ID: {}. Usando 'desenvolvedor' como padrão.", curriculo.getId());
            return "desenvolvedor";
        }
        
        return result;
    }
}
