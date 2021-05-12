package player.service;

import java.io.BufferedReader;
import java.io.IOException;

import player.model.ChangeStageSocketModel;
import player.model.CommandMediaMessageSocketModel;
import player.model.FileMediaMessageSocketModel;
import player.model.MediaMessageSocketModel;
import player.model.MessageSocketModel;
import player.model.ProcessedMessageSocketModel;
import player.model.RequestFileListMediaSocketModel;
import player.model.ResponseSocketModel;
import player.model.StatusMessageSocketModel;
import player.model.SystemCommandMessageSocketModel;
import player.model.YoutubeMessageSocketModel;
import player.model.YoutubeMessageSocketModel.Commands;

public class MessageSocketService {

	public ProcessedMessageSocketModel processMessageReceived(int code, BufferedReader input) {

		switch (code) {
		case 1:
			return messageStatus(input);

		case 2:
			return messageYoutube(input);

		case 3:
			return messageMediaFile(input);
		}

		return null;
	}

	private ProcessedMessageSocketModel messageStatus(BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setNeedResponse(true);

		StatusMessageSocketModel messageStatus = new StatusMessageSocketModel();

		try {
			messageStatus.setStatus(input.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageStatus.setStatus("Error");
		}

		processedMessage.setMessage(messageStatus);

		processedMessage.setResponse(createResponseSocket(messageStatus));

		return processedMessage;
	}

	private ProcessedMessageSocketModel messageYoutube(BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setNeedResponse(false);

		YoutubeMessageSocketModel messageYoutube = new YoutubeMessageSocketModel();

		try {
			messageYoutube.setLink(input.readLine());
			messageYoutube.setEmbed(input.read() == 1);
			messageYoutube.setCommand(Commands.getCommandByIndex(input.read()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processedMessage.setMessage(messageYoutube);

		System.out.println("Embed: " + messageYoutube.isEmbed());

		return processedMessage;

	}

	private ProcessedMessageSocketModel messageMediaFile(BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setNeedResponse(false);

		FileMediaMessageSocketModel messageMediaFile = new FileMediaMessageSocketModel();

		try {
			messageMediaFile.setPath(input.readLine());
			messageMediaFile.setFileName(input.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processedMessage.setMessage(messageMediaFile);

		return processedMessage;
	}

	public ResponseSocketModel createResponseSocket(MessageSocketModel message) {
		if (message instanceof YoutubeMessageSocketModel) {
			return createYoutubeResponseSocket((YoutubeMessageSocketModel) message);
		}
		if (message instanceof StatusMessageSocketModel) {
			return createStatusResponseSocket((StatusMessageSocketModel) message);
		}
		if (message instanceof FileMediaMessageSocketModel) {
			return createMediaFileResponseSocket((FileMediaMessageSocketModel) message);
		}
		if (message instanceof MediaMessageSocketModel) {
			return createMediaMessageResponseSocket((MediaMessageSocketModel) message);
		}
		if (message instanceof SystemCommandMessageSocketModel) {
			return createSystemCommandResponseSocket((SystemCommandMessageSocketModel) message);
		}
		if (message instanceof ChangeStageSocketModel) {
			return createChangeViewCommandResponseSocket((ChangeStageSocketModel) message);
		}
		if(message instanceof CommandMediaMessageSocketModel) {
			return createMediaCommandResponseSocket((CommandMediaMessageSocketModel)message);
		}
		if(message instanceof RequestFileListMediaSocketModel) {
			return createRequestMediaFileSocket((RequestFileListMediaSocketModel) message);
		}
		return null;
	}

	private ResponseSocketModel createStatusResponseSocket(StatusMessageSocketModel messageStatus) {
		return (output) -> {
			try {
				output.write(messageStatus.getCode());
				output.write(messageStatus.getStatus());
				output.write("\n");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		};
	}

	private ResponseSocketModel createYoutubeResponseSocket(YoutubeMessageSocketModel messageYoutube) {
		return (output) -> {
			try {
				output.write(messageYoutube.getCode());
				output.write(messageYoutube.getLink());
				output.write("\n");
				output.write(messageYoutube.isEmbed() ? 1 : 0);
				output.write(messageYoutube.getCommand().getIndex());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

	private ResponseSocketModel createMediaFileResponseSocket(FileMediaMessageSocketModel messageMedia) {
		return (output) -> {
			try {
				output.write(messageMedia.getCode());
				output.write(messageMedia.getPath());
				output.write("\n");
				output.write(messageMedia.getFileName());
				output.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

	private ResponseSocketModel createMediaMessageResponseSocket(MediaMessageSocketModel messageMedia) {
		return output -> {
			try {
				output.write(messageMedia.getCode());
				output.write(messageMedia.getPath());
				output.write("\n");
				output.write(messageMedia.getFileName());
				output.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

	private ResponseSocketModel createSystemCommandResponseSocket(SystemCommandMessageSocketModel messageSystem) {
		return output -> {
			try {
				output.write(messageSystem.getCode());
				output.write(messageSystem.getCommand().getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

	private ResponseSocketModel createChangeViewCommandResponseSocket(ChangeStageSocketModel changeMessage) {
		return output -> {
			try {
				output.write(changeMessage.getCode());
				output.write(changeMessage.getStage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};
	}
	
	private ResponseSocketModel createMediaCommandResponseSocket(CommandMediaMessageSocketModel mediaCommand) {
		return output -> {
			
			try {
				output.write(mediaCommand.getCode());
				output.write(mediaCommand.getCommand().getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}
	
	private ResponseSocketModel createRequestMediaFileSocket(RequestFileListMediaSocketModel requestCommand) {
		return output -> {
			try {
				output.write(requestCommand.getCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

}
