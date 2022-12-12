package frontend;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.*;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import backend.actions.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class PaintPane extends BorderPane {

	private static final int MIN_SIZE_LABEL = 280;
	private static final String GRAY_BACKGROUND_COLOR = "-fx-background-color: #999";
	private static final int BOX_SPACING = 10;
	private static final int PADDING = 5;
	private static final String FONT_SIZE = "-fx-font-size: 14";

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;
	private double lineWidth = 1;
	private Color newLineColor = null;
	private Color newFillColor = null;
	private double newLineWidth;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");

	private final ToggleButton copyForButton = new ToggleButton("Cop. Form.");

	//Botones Barra superior
	// cpy menu
	private final Button copyButton = new Button ("Copiar", getImage("copyIcon"));
	private final Button cutButton = new Button ("Cortar", getImage("cutIcon"));
	private final Button pasteButton = new Button ("Pegar", getImage("pasteIcon"));
	// undo menu
	private final Button undoButton = new Button ("Deshacer", getImage("undoIcon"));
	private final Button redoButton = new Button("Rehacer", getImage("redoIcon"));
	// format menu
	private Label lineLbl = new Label("Borde");
	private Slider lineSlider = new Slider(1, 50, 5);
	private ColorPicker lineColorPicker = new ColorPicker(Color.BLACK);

	private Label fillLbl = new Label("Relleno");
	private ColorPicker fillColorPicker = new ColorPicker(Color.YELLOW);

	// Dibujar una figura
	private Point startPoint;
	private boolean newFormat = true;

	// Seleccionar una figura
	private Figure selectedFigure;

	// Figura copiada
	private Figure copiedFigure = null;

	// StatusBar
	private final StatusPane statusPane;

	// Label de undo y redo
	private Label undoLabel, redoLabel;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Botones

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, copyForButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			setCursor(tool);
		}

		lineSlider.setShowTickMarks(true);
		lineSlider.setShowTickLabels(true);
		setCursor(lineSlider);
		setCursor(lineColorPicker);
		setCursor(fillColorPicker);

		// Left Buttons
		VBox buttonsBox = new VBox(BOX_SPACING);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(lineLbl, lineSlider, lineColorPicker, fillLbl, fillColorPicker);
		buttonsBox.setPadding(new Insets(PADDING));
		buttonsBox.setStyle(GRAY_BACKGROUND_COLOR);
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		// Top Buttons
		// copy Menu
		HBox copyButtonsBox = new HBox(BOX_SPACING);
		Button[] copyToolsArr = { cutButton,copyButton,pasteButton};
		for(Button tool : copyToolsArr) {
			tool.setMinWidth(50);
			tool.setCursor(Cursor.HAND);
		}
		copyButtonsBox.getChildren().addAll(copyToolsArr);
		copyButtonsBox.setPadding(new Insets(PADDING));

		// Redo & undo Menu
		HBox doButtonsBox = new HBox(BOX_SPACING);
		doButtonsBox.setAlignment(Pos.CENTER);
		undoLabel = new Label("");
		redoLabel = new Label("");
		undoLabel.setText(String.format("%s %d",canvasState.getNextUndo() == null ? "" : canvasState.getNextUndo().toString(), canvasState.getUndoableAvailable()));
		redoLabel.setText(String.format("%d %s", canvasState.getRedoableAvailable(), canvasState.getNextRedo() == null ? "" : canvasState.getNextRedo().toString()));
		undoLabel.setStyle(FONT_SIZE);
		redoLabel.setStyle(FONT_SIZE);
		undoLabel.setMinWidth(MIN_SIZE_LABEL);
		redoLabel.setMinWidth(MIN_SIZE_LABEL);
		undoLabel.setAlignment(Pos.CENTER_RIGHT);
		Button[] doToolsArr = { undoButton, redoButton};
		for(Button tool : doToolsArr) {
			tool.setMinWidth(50);
			tool.setCursor(Cursor.HAND);
		}
		doButtonsBox.getChildren().add(undoLabel);
		doButtonsBox.getChildren().add(undoButton);
		doButtonsBox.getChildren().add(redoButton);
		doButtonsBox.getChildren().add(redoLabel);
		doButtonsBox.setPadding(new Insets(PADDING));

		VBox topButtonsBox = new VBox();
		topButtonsBox.getChildren().addAll(copyButtonsBox, doButtonsBox);
		topButtonsBox.setStyle(GRAY_BACKGROUND_COLOR);


		// Manejo de eventos

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null || startPoint.equals(endPoint)) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			CreateFigureAction createAction;
			if(rectangleButton.isSelected()) {
				createAction = new CreateRectangleAction(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
			} else if(circleButton.isSelected()) {
				createAction = new CreateCircleAction(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
			} else if(squareButton.isSelected()) {
				createAction = new CreateSquareAction(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
			} else if(ellipseButton.isSelected()) {
				createAction = new CreateEllipseAction(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
			} else
				return;
			createAction.press();
			canvasState.addUndoableAction(createAction);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figure.belongs(eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figure.belongs(eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());

				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
			}
			else if (copyForButton.isSelected()) {
				if (newFormat) {
					Point eventPoint = new Point(event.getX(), event.getY());
					CopyFormatAction copyFormatAction = new CopyFormatAction(selectedFigure, canvasState, eventPoint, newLineColor, newFillColor, newLineWidth);
					copyFormatAction.press();
					canvasState.addUndoableAction(copyFormatAction);
					newFormat = false;
				}
			}
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				if (selectedFigure != null) {
					Point eventPoint = new Point(event.getX(), event.getY());
					double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
					double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
					selectedFigure.move(diffX, diffY);
					redrawCanvas();
				}
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				DeleteFigureAction deleteAction = new DeleteFigureAction(selectedFigure, canvasState);
				deleteAction.press();
				canvasState.addUndoableAction(deleteAction);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		// Atajo combinación de teclas
		this.setOnKeyPressed(event -> {
			if ( event.isControlDown()  ) {
				switch (event.getCode()) {
					case C:
						copyButton.fire();
						break;
					case X:
						cutButton.fire();
						break;
					case V:
						pasteButton.fire();
						break;
				}
			}
		});

		copyButton.setOnAction(event -> {
			try {
				if (selectedFigure != null) {
					CopyAction copyAction = new CopyAction(selectedFigure);
					copyAction.press();
					canvasState.addUndoableAction(copyAction);
					copiedFigure = copyAction.getCopiedFigure();
					redrawCanvas();
				} else
					throw new NoSuchElementException("Debe seleccionar una figura para copiar");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		cutButton.setOnAction(event -> {
			try {
				if (selectedFigure != null) {
					CutAction cutAction = new CutAction(selectedFigure, canvasState);
					cutAction.press();
					canvasState.addUndoableAction(cutAction);
					copiedFigure = cutAction.getCopiedFigure();
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("Debe seleccionar una figura para cortar");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		pasteButton.setOnAction(event -> {
			try {
				if (copiedFigure != null) {
					PasteAction pasteAction = new PasteAction(copiedFigure, canvasState);
					pasteAction.press();
					canvasState.addUndoableAction(pasteAction);
					selectedFigure = null;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("Debe copiar una figura antes de pegar");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		copyForButton.setOnAction(event -> {
			try {
				if (selectedFigure != null) {
					newLineWidth = selectedFigure.getLineWidth();
					newFillColor = selectedFigure.getFillColor();
					newLineColor = selectedFigure.getLineColor();
					newFormat = true;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("Debe seleccionar una figura para copiar formato");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		lineColorPicker.setOnAction(event -> {
			lineColor = lineColorPicker.getValue();
			if (selectedFigure != null) {
				LineColorAction lineColorAction = new LineColorAction(selectedFigure, lineColor, canvasState);
				lineColorAction.press();
				canvasState.addUndoableAction(lineColorAction);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			if (selectedFigure != null) {
				FillColorAction fillColorAction = new FillColorAction(selectedFigure, fillColor, canvasState);
				fillColorAction.press();
				canvasState.addUndoableAction(fillColorAction);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		undoButton.setOnAction(event -> {
			try {
				if(canvasState.undoLastAction()) {
					selectedFigure = null;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("No hay operaciones para deshacer");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		redoButton.setOnAction(event->{
			try {
				if (canvasState.redoLastAction()) {
					selectedFigure = null;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("No hay operaciones para rehacer");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		lineSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldV, Number newV) {
				lineWidth = newV.doubleValue();
				if (selectedFigure != null) {
					for(Figure figure : canvasState.figures()) {
						if (figure == selectedFigure) {
							figure.setLineWidth(lineWidth);
						}
					}
					redrawCanvas();
				}
			}
		});

		// Colocamos las Box
		setLeft(buttonsBox);
		setRight(canvas);
		setTop(topButtonsBox);
	}

	// Actualiza el estado del canvas
	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getLineColor());
			}
			gc.setFill(figure.getFillColor());
			gc.setLineWidth(figure.getLineWidth());
			figure.draw();
		}
		undoLabel.setText(String.format("%s %d",canvasState.getNextUndo() == null ? "" : canvasState.getNextUndo().toString(), canvasState.getUndoableAvailable()));
		redoLabel.setText(String.format("%d %s", canvasState.getRedoableAvailable(), canvasState.getNextRedo() == null ? "" : canvasState.getNextRedo().toString()));
	}

	private void setCursor(Control o) {
		o.setCursor(Cursor.HAND);
	}

	private ImageView getImage(String imagePath){
		String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString(imagePath);
		Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
		return new ImageView(cutIcon);
	}

	public void throwWarning(String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Advertencia");
		alert.setHeaderText("Operacion Invalida");
		alert.setContentText(message);
		alert.showAndWait();
	}
}
