package player.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class UiToolkit {

	public static <T> T loadingFXML(URL fxml, Object controller) {

		FXMLLoader fxmlLoader = new FXMLLoader(fxml);

		fxmlLoader.setController(controller);
		
		try {
			return fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
