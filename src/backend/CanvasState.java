package backend;

import backend.model.Figure;
import frontend.interfaces.Undoable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CanvasState {

    private final List<Figure> list = new ArrayList<>();

    private Stack<Undoable> toRedo= new Stack<>();
    private Stack<Undoable> toUndo = new Stack<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
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
        return toUndo.peek();
    }

    public Undoable getNextRedo(){
        return toRedo.peek();
    }

    public int getRedoableAvailable(){
        return toRedo.size();
    }

    public int getUndoableAvailable(){
        return toUndo.size();
    }
}
