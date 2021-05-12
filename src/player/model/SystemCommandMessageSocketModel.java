package player.model;

public class SystemCommandMessageSocketModel extends MessageSocketModel {

	private SystemCommands command;
	
	public SystemCommandMessageSocketModel() {
		// TODO Auto-generated constructor stub
		setCode(5);
	}

	public SystemCommands getCommand() {
		return command;
	}

	public void setCommand(SystemCommands command) {
		this.command = command;
	}
	
	
	
}
