package frontend;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.*;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import frontend.interfaces.Drawable;
import frontend.model.DrawableCircle;
import frontend.model.DrawableEllipse;
import frontend.model.DrawableRectangle;
import frontend.model.DrawableSquare;
import javafx.geometry.Insets;
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

import java.util.ResourceBundle;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");

	ToggleButton copyForButton = new ToggleButton("Cop. Form.");

	//Botones Barra superior (cpy Menu)
	Button copyButton = new Button ("Copiar", getImage("copyIcon"));
	Button cutButton = new Button ("Cortar", getImage("cutIcon"));
	Button pasteButton = new Button ("Pegar", getImage("pasteIcon"));

	Label lineLbl = new Label("Borde");
	Slider lineSlider = new Slider(1, 50, 5);
	ColorPicker lineColorPicker = new ColorPicker();

	Label fillLbl = new Label("Relleno");
	ColorPicker fillColorPicker = new ColorPicker();

	// Dibujar una figura
	private Point startPoint;

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
		copyButtonsBox.setStyle("-fx-background-color: #999");

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
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selectedFigure.move(diffX, diffY);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		copyButton.setOnAction(event -> {
			if ( selectedFigure != null ){
				copiedFigure = selectedFigure;
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
				copiedFigure.move(200,290);
				canvasState.addFigure(copiedFigure);
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
		setTop(copyButtonsBox);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
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
