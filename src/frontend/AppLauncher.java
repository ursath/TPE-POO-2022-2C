package frontend;

import backend.CanvasState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		CanvasState canvasState = new CanvasState(); // BackEnd
		MainFrame frame = new MainFrame(canvasState);
		Scene scene = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		scene.setOnKeyPressed(event -> {
			if ( event.isControlDown() )
				switch (event.getCode()){
					case C:
						System.out.println("funciona c");
						break;
					case X:
						System.out.println("funciona x");
						break;
					case V:
						System.out.println("funciona paste");
						break;
				}

		});

		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}

}
