package VagasSkills.entity.enums;

public enum NivelIdioma {
    BASICO("Básico"),
    INTERMEDIARIO("Intermediário"),
    AVANCADO("Avançado"),
    FLUENTE("Fluente");
    
    private final String descricao;
    
    NivelIdioma(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

