package frontend;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.*;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import frontend.model.DrawableCircle;
import frontend.model.DrawableEllipse;
import frontend.model.DrawableRectangle;
import frontend.model.DrawableSquare;
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

import javax.swing.*;
import java.util.ResourceBundle;

public class PaintPane extends BorderPane {

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

	ToggleButton copyForButton = new ToggleButton("Cop. Form.");

	//Botones Barra superior (cpy Menu)
	private final Button copyButton = new Button ("Copiar", getImage("copyIcon"));
	private final Button cutButton = new Button ("Cortar", getImage("cutIcon"));
	private final Button pasteButton = new Button ("Pegar", getImage("pasteIcon"));
	private final Button undoButton = new Button ("Deshacer", getImage("undoIcon"));
	private final Button redoButton = new Button("Rehacer", getImage("redoIcon"));
	private Label lineLbl = new Label("Borde");
	private Slider lineSlider = new Slider(1, 50, 5);
	private ColorPicker lineColorPicker = new ColorPicker(Color.YELLOW);

	private Label fillLbl = new Label("Relleno");
	private ColorPicker fillColorPicker = new ColorPicker(Color.BLACK);

	// Dibujar una figura
	private Point startPoint;
	private boolean newFormat = true;

	// Seleccionar una figura
	private Figure selectedFigure;

	// Figura copiada
	private Figure copiedFigure = null;

	// StatusBar
	private final StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
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
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(lineLbl, lineSlider, lineColorPicker, fillLbl, fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		//Top Buttons (copy Menu)
		HBox copyButtonsBox = new HBox(10);
		Button[] copyToolsArr = { cutButton,copyButton,pasteButton};
		for(Button tool : copyToolsArr) {
			tool.setMinWidth(50);
			tool.setCursor(Cursor.HAND);
		}
		copyButtonsBox.getChildren().addAll(copyToolsArr);
		copyButtonsBox.setPadding(new Insets(5));

		HBox doButtonsBox = new HBox(10);
		Button[] doToolsArr = { undoButton, redoButton};
		for(Button tool : doToolsArr) {
			tool.setMinWidth(50);
			tool.setCursor(Cursor.HAND);
		}
		doButtonsBox.getChildren().addAll(doToolsArr);
		doButtonsBox.setPadding(new Insets(5));

		VBox topButtonsBox = new VBox(0);
		topButtonsBox.getChildren().addAll(copyButtonsBox, doButtonsBox);
		topButtonsBox.setStyle("-fx-background-color: #999");

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			Figure newFigure;
			if(rectangleButton.isSelected()) {
				newFigure = new DrawableRectangle(startPoint, endPoint, gc);
			}
			else if(circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new DrawableCircle(startPoint, circleRadius, gc);
			} else if(squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new DrawableSquare(startPoint, size, gc);
			} else if(ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
				double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
				double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
				newFigure = new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis, gc);
			} else
				return;
			canvasState.addFigure(newFigure);
			startPoint = null;
			newFigure.setLineColor(lineColor);
			newFigure.setFillColor(fillColor);
			newFigure.setLineWidth(lineWidth);
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
					for (Figure figure : canvasState.figures()) {
						if (figure.belongs(eventPoint)) {
							figure.setLineColor(newLineColor);
							figure.setLineWidth(newLineWidth);
							figure.setFillColor(newFillColor);
						}
					}
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
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

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
			if ( selectedFigure != null ){
				// copyFigure(selectedFigure,)
				copiedFigure = selectedFigure.getDuplicate(new Point(400,300));
				copiedFigure.copyFormat(selectedFigure);
			}
		});
		cutButton.setOnAction(event -> {
			if ( selectedFigure != null ) {
				copyButton.fire();
				deleteButton.fire();
			}
		});

		pasteButton.setOnAction(event -> {
			if ( copiedFigure != null ){
				//ver si se puede crear un método para q no se repita cod
				Figure aux = copiedFigure.getDuplicate(new Point(400,300));
				aux.copyFormat(copiedFigure);
				canvasState.addFigure(aux);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		copyForButton.setOnAction(event -> {
			if (selectedFigure != null) {
				newLineWidth = selectedFigure.getLineWidth();
				newFillColor = selectedFigure.getFillColor();
				newLineColor = selectedFigure.getLineColor();
				newFormat = true;
				selectedFigure = null;
			}
		});

		lineColorPicker.setOnAction(event -> {
			lineColor = lineColorPicker.getValue();
			if (selectedFigure != null) {
				for(Figure figure : canvasState.figures()) {
					if(figure == selectedFigure) {
						figure.setLineColor(lineColor);
					}
				}
				redrawCanvas();
			}
		});

		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			if (selectedFigure != null) {
				for(Figure figure : canvasState.figures()) {
					if (figure == selectedFigure) {
						figure.setFillColor(fillColor);
					}
				}
				redrawCanvas();
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

		setLeft(buttonsBox);
		setRight(canvas);
		setTop(topButtonsBox);
	}

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
	}

	private void setCursor(Control o) {
		o.setCursor(Cursor.HAND);
	}

	private ImageView getImage(String imagePath){
		String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString(imagePath);
		Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
		return new ImageView(cutIcon);
	}

}
