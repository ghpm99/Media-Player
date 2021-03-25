package player.gui;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.global.Instances;
import player.gui.controller.MainFrameController;
import player.util.UiToolkit;

public class Main {	

	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		System.out.println("iniciou");
		
		MainFrameController controller = new MainFrameController();
		
		Parent root = UiToolkit.loadingFXML(Main.class.getResource("fxml/MainFrameStage.fxml"), controller);
		
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Media Player");	
		
		primaryStage.setResizable(false);		

		primaryStage.setScene(scene);

		primaryStage.setOnCloseRequest((s) -> {
			Platform.exit();
			Instances.close();
			System.exit(0);
		});
		
		primaryStage.show();

	}

}
