package VagasSkills.exception;

public class MaxCursosExcedidoException extends IllegalArgumentException {
    
    public MaxCursosExcedidoException(String message) {
        super(message);
    }
    
    public MaxCursosExcedidoException(int quantidade, int maximo) {
        super(String.format("Número de cursos excedido. Máximo permitido: %d, fornecido: %d", maximo, quantidade));
    }
}

