package player.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import player.global.Instances;
import player.interfaces.SocketEventListener;
import player.model.ChangeStageSocketModel;
import player.model.CommandMediaMessageSocketModel;
import player.model.CommandsMedia;
import player.model.FileMediaMessageSocketModel;
import player.model.MediaMessageSocketModel;
import player.model.MessageSocketModel;
import player.model.RequestFileListMediaSocketModel;
import player.model.SystemCommandMessageSocketModel;
import player.model.SystemCommands;
import player.model.YoutubeMessageSocketModel;
import player.model.YoutubeMessageSocketModel.Commands;

public class MainFrameController implements SocketEventListener {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="youtubePane"
	private AnchorPane youtubePane; // Value injected by FXMLLoader

	@FXML // fx:id="linkVideoTextField"
	private TextField linkVideoTextField; // Value injected by FXMLLoader

	@FXML // fx:id="isEmbedCheckBox"
	private CheckBox isEmbedCheckBox; // Value injected by FXMLLoader

	@FXML // fx:id="sendVideoProgressBar"
	private ProgressBar sendVideoProgressBar; // Value injected by FXMLLoader

	@FXML // fx:id="commandBarYoutube"
	private ButtonBar commandBarYoutube; // Value injected by FXMLLoader

	@FXML // fx:id="statusPane"
	private AnchorPane statusPane; // Value injected by FXMLLoader

	@FXML // fx:id="ipTextField"
	private TextField ipTextField; // Value injected by FXMLLoader

	@FXML // fx:id="statusLabel"
	private Label statusLabel; // Value injected by FXMLLoader

	// controla visibilidade do Panel = [status,youtube]
	private boolean[] paneVisible = { false, false, false };

	@FXML
	private AnchorPane mediaPane;

	@FXML
	private ListView<FileMediaMessageSocketModel> mediaFilesList;

	@FXML
	void muteMediaFile(ActionEvent event) {
		sendMediaCommandMessage(CommandsMedia.MUTE);
	}

	@FXML
	void pauseMediaFile(ActionEvent event) {
		sendMediaCommandMessage(CommandsMedia.STOP);
	}

	@FXML
	void playMediaFile(ActionEvent event) {
		sendMediaCommandMessage(CommandsMedia.PLAY);
	}

	@FXML
	void updateMediaFileList(ActionEvent event) {
		mediaFilesList.getItems().clear();		
		Instances.socketService.sendMessage(Instances.messageService.createResponseSocket(new RequestFileListMediaSocketModel()));
	}

	@FXML
	void changeIpAddress(InputMethodEvent event) {

	}

	@FXML
	void decreaseVolumeYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.DECREASEVOLUME);
	}

	@FXML
	void increaseVolumeYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.INCREASEVOLUME);
	}

	@FXML
	void linkYoutubeChange(InputMethodEvent event) {

	}

	@FXML
	void muteYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.MUTE);
	}

	@FXML
	void nextYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.NEXT);
	}

	@FXML
	void playYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.PLAY);
	}

	@FXML
	void sendYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.NULL);
	}

	@FXML
	void swapPaneView(ActionEvent event) {
		int id = Integer.valueOf(((Node) event.getSource()).getId());
		if (id >= paneVisible.length)
			return;
		resetPaneVisible();
		paneVisible[id] = true;
		updateVisibilityPane();

	}

	private void resetPaneVisible() {
		for (int i = 0; i < paneVisible.length; i++) {
			paneVisible[i] = false;
		}
	}

	private void updateVisibilityPane() {
		statusPane.setVisible(paneVisible[0]);
		youtubePane.setVisible(paneVisible[1]);
		mediaPane.setVisible(paneVisible[2]);
	}

	@FXML
	void theaterYoutube(ActionEvent event) {
		sendYoutubeMessage(Commands.THEATHER);
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert youtubePane != null
				: "fx:id=\"youtubePane\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert linkVideoTextField != null
				: "fx:id=\"linkVideoTextField\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert isEmbedCheckBox != null
				: "fx:id=\"isEmbedCheckBox\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert sendVideoProgressBar != null
				: "fx:id=\"sendVideoProgressBar\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert commandBarYoutube != null
				: "fx:id=\"commandBarYoutube\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert statusPane != null
				: "fx:id=\"statusPane\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert ipTextField != null
				: "fx:id=\"ipTextField\" was not injected: check your FXML file 'MainFrameStage.fxml'.";
		assert statusLabel != null
				: "fx:id=\"statusLabel\" was not injected: check your FXML file 'MainFrameStage.fxml'.";

		new Thread(() -> {
			Instances.init();
		}).start();

		Instances.socketService.addListener(this);

		ipTextField.setText(Instances.socketService.getHost());

		mediaFilesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	@Override
	public void connectionStatusChanged(CONNECTIONSTATUS status) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			if (status == CONNECTIONSTATUS.CONNECTED) {
				statusLabel.setText("Conectado");
			} else {
				statusLabel.setText("Desconectado");
			}
		});
	}

	@Override
	public void messageReceived(MessageSocketModel message) {
		System.out.println("Chegou msg " + message);
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			if (message instanceof YoutubeMessageSocketModel) {
				YoutubeMessageSocketModel youtubeMessage = (YoutubeMessageSocketModel) message;
				commandBarYoutube.setDisable(false);
				linkVideoTextField.setText(youtubeMessage.getLink());
				isEmbedCheckBox.setSelected(youtubeMessage.isEmbed());
				sendVideoProgressBar.setProgress(1.0);
			} else {
				commandBarYoutube.setDisable(true);
				sendVideoProgressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
			}

			if (message instanceof FileMediaMessageSocketModel) {
				FileMediaMessageSocketModel fileMediaMessage = (FileMediaMessageSocketModel) message;
				addFileMediaList(fileMediaMessage);
			}
		});

	}

	private void addFileMediaList(FileMediaMessageSocketModel fileMediaMessage) {
		mediaFilesList.getItems().add(fileMediaMessage);
	}

	private void sendYoutubeMessage(Commands command) {
		YoutubeMessageSocketModel message = new YoutubeMessageSocketModel();
		message.setLink(linkVideoTextField.getText());
		message.setEmbed(isEmbedCheckBox.isSelected());
		message.setCommand(command);
		Instances.socketService.sendMessage(Instances.messageService.createResponseSocket(message));
	}

	@FXML
	void runMediaFile(ActionEvent event) {
		sendMediaMessage(mediaFilesList.getSelectionModel().getSelectedItems());
	}

	private void sendMediaMessage(ObservableList<FileMediaMessageSocketModel> selectedItems) {
		// TODO Auto-generated method stub
		selectedItems.forEach((fileMedia) -> {
			sendMediaMessage(fileMedia);
		});
	}

	private void sendMediaMessage(FileMediaMessageSocketModel fileMedia) {
		MediaMessageSocketModel mediaMessage = new MediaMessageSocketModel();
		mediaMessage.setPath(fileMedia.getPath());
		mediaMessage.setFileName(fileMedia.getFileName());		

		Instances.socketService.sendMessage(Instances.messageService.createResponseSocket(mediaMessage));
	}
	
	private void sendMediaCommandMessage(CommandsMedia command) {
		CommandMediaMessageSocketModel commandMessage = new CommandMediaMessageSocketModel();
		commandMessage.setCommand(command);
		
		Instances.socketService.sendMessage(Instances.messageService.createResponseSocket(commandMessage));
	}


	@FXML
	void nextMediaFile(ActionEvent event) {
		sendMediaCommandMessage(CommandsMedia.NEXT);
	}

	@FXML
	void requestTurnOff(ActionEvent event) {
		SystemCommandMessageSocketModel systemCommand = new SystemCommandMessageSocketModel();
		systemCommand.setCommand(SystemCommands.OFF);
		Instances.socketService.sendMessage(Instances.messageService.createResponseSocket(systemCommand));
	}

	@FXML
	void requestIdleView(ActionEvent event) {
		requestChangeView(0);

	}

	@FXML
	void requestMediaView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			requestChangeView(2);
		}
	}

	@FXML
	void requestYoutubeView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			requestChangeView(1);
		}
	}

	private void requestChangeView(int stage) {
		ChangeStageSocketModel message = new ChangeStageSocketModel();
		message.setStage(stage);
		Instances.socketService.sendMessage(Instances.messageService.createResponseSocket(message));
	}
	
}
