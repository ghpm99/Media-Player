package player.model;

public enum SystemCommands {

	OFF(0),NULL(-1);
	
	private int id;

	private SystemCommands(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static SystemCommands getCommandById(int id) {
		for(SystemCommands command : values()) {
			if(command.getId() == id) {
				return command;
			}
		}
		
		return NULL;
	}
	
}
