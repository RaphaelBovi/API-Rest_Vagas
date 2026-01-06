package VagasSkills.exception;

public class CurriculoNotFoundException extends RuntimeException {
    
    public CurriculoNotFoundException(String message) {
        super(message);
    }
    
    public CurriculoNotFoundException(Long id) {
        super("Currículo não encontrado com id: " + id);
    }
}

