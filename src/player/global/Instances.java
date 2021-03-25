package player.global;

import player.service.MessageSocketService;
import player.socket.SocketService;

public class Instances {

	public static SocketService socketService = new SocketService();

	public static MessageSocketService messageService = new MessageSocketService();

	public static void init() {
		socketService.init();
	}
	
	public static void close() {
		socketService.close();
	}

}
