package player;

import javafx.application.Application;
import javafx.stage.Stage;
import player.gui.Main;

public class MediaPlayer extends Application {

	Main main = new Main();

	public static void main(String[] args) {
		System.out.println("iniciando");
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		main.start(arg0);

	}
}
