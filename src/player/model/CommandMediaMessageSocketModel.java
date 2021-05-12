package player.model;

public class CommandMediaMessageSocketModel extends MessageSocketModel {

	private CommandsMedia command;
	
	public CommandMediaMessageSocketModel() {
		// TODO Auto-generated constructor stub
		setCode(7);
	}

	public CommandsMedia getCommand() {
		return command;
	}

	public void setCommand(CommandsMedia command) {
		this.command = command;
	}
	
	
}
