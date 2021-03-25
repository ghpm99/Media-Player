package player.interfaces;

import player.model.MessageSocketModel;

public interface SocketEventListener {

	public static enum CONNECTIONSTATUS {
		CONNECTED, DISCONNECT;
	};
	
	public void messageReceived(MessageSocketModel message);
	
	public void connectionStatusChanged(CONNECTIONSTATUS status);
	
	
	
}
