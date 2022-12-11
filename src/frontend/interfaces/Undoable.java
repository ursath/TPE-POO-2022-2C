package frontend.interfaces;

public interface Undoable {
    // Ejecuta la acción
    void press();

    // Deshace la ejecución de la acción
    void undo();
}
