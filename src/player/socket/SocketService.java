package player.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import player.global.Instances;
import player.interfaces.SocketEventListener;
import player.interfaces.SocketEventListener.CONNECTIONSTATUS;
import player.model.MessageSocketModel;
import player.model.ProcessedMessageSocketModel;
import player.model.ResponseSocketModel;

public class SocketService {

	private Socket socket;

	//ip note = "192.168.100.6"
	//private String host = "192.168.100.6";
	private String host = "127.0.0.1";

	private int port = 33325;

	private BufferedWriter output;

	private BufferedReader input;

	private ArrayList<SocketEventListener> eventListener = new ArrayList<>();
	
	private boolean alive;

	public void init() {
		// TODO Auto-generated constructor stub
		try {
			System.out.println("Iniciando socket");
			socket = new Socket(host, port);

			connectionStatusChanged(CONNECTIONSTATUS.CONNECTED);
			

			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			alive = true;
			
			waitMessage();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

	}

	private void waitMessage() {
		try {
			System.out.println("Ready " + input.ready());
			int code = 0;
			while (alive) {
				System.out.println("Aguardando msg");
				code = input.read();
				System.out.println("Cliente codigo: " + code);
				ProcessedMessageSocketModel processedMessage = Instances.messageService.processMessageReceived(code,
						input);

				processMessage(processedMessage);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addListener(SocketEventListener listener) {
		eventListener.add(listener);
	}

	public void removeListener(SocketEventListener listener) {
		if (eventListener.contains(listener)) {
			eventListener.remove(listener);
		}
	}

	private void connectionStatusChanged(CONNECTIONSTATUS status) {
		eventListener.forEach((s) -> s.connectionStatusChanged(status));
	}

	public void close() {
		if (socket != null)
			try {
				alive = false;
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String getHost() {
		return host + ":" + port;
	}

	private void processMessage(ProcessedMessageSocketModel message) {
		messageReceived(message.getMessage());
		if (message.isNeedResponse()) {
			sendMessage(message.getResponse());
		}
	}

	public void sendMessage(ResponseSocketModel message) {
		try {			
			message.sendResponse(output);			
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void messageReceived(MessageSocketModel message) {
		eventListener.forEach((listener) -> {
			listener.messageReceived(message);
		});
	}

}
