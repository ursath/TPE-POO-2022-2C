package backend;

import backend.model.Figure;
import backend.interfaces.Undoable;

import java.util.*;

public class CanvasState {

    private final Deque<Figure> list = new ArrayDeque<>();

    private final Deque<Undoable> toRedo= new ArrayDeque<>();
    private final Deque<Undoable> toUndo = new ArrayDeque<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return list;
    }
    public Iterator<Figure> figuresReversed(){
        return list.descendingIterator();
    }

    public void addUndoableAction(Undoable action){
        if(getRedoableAvailable() != 0){
            toRedo.clear();
        }
        this.toUndo.push(action);
    }

    public boolean undoLastAction(){
        if (getUndoableAvailable()!= 0) {
            Undoable action = toUndo.pop();
            action.undo();
            toRedo.push(action);
            return true;
        }
        return false;
    }
    public boolean redoLastAction(){
        if (getRedoableAvailable() != 0){
            Undoable action = toRedo.pop();
            action.press();
            toUndo.push(action);
            return true;
        }
        return false;
    }

    public Undoable getNextUndo(){
        if (getUndoableAvailable() != 0) {
            return toUndo.peek();
        }
        return null;
    }

    public Undoable getNextRedo(){
        if (getRedoableAvailable() != 0) {
            return toRedo.peek();
        }
        return null;
    }

    public int getRedoableAvailable(){
        return toRedo.size();
    }

    public int getUndoableAvailable(){
        return toUndo.size();
    }
}
