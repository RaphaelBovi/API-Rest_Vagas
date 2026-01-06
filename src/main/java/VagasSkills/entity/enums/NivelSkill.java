package VagasSkills.entity.enums;

public enum NivelSkill {
    INICIANTE("Iniciante"),
    INTERMEDIARIO("Intermediário"),
    AVANCADO("Avançado"),
    ESPECIALISTA("Especialista");
    
    private final String descricao;
    
    NivelSkill(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

