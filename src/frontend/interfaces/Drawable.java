package frontend.interfaces;


import backend.model.Figure;
import backend.model.Point;


public interface Drawable {

    // Se invoca cuando se crea una figura con el mouse
    void draw();

    // Devuelve una copia de la Figura recibida
    // la copia se ubicará en el centro del canvas
    Figure getDuplicate( Point center );

}
