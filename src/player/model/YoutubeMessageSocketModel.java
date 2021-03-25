package player.model;

public class YoutubeMessageSocketModel extends MessageSocketModel{

	public YoutubeMessageSocketModel() {
		super();
		setCode(2);
	}
	
	public enum Commands {
		PLAY(0),MUTE(1),THEATHER(2),NEXT(3),INCREASEVOLUME(4),DECREASEVOLUME(5),NULL(-1);
		
		private int index;
		
		private Commands(int index) {
			// TODO Auto-generated constructor stub
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public static Commands getCommandByIndex(int index) {
			for(Commands command : values()) {
				if(command.getIndex() == index) {
					return command;
				}
			}
			return Commands.NULL;
		}
	};
	
	private String link;
	
	private boolean embed;
	
	private Commands command;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isEmbed() {
		return embed;
	}

	public void setEmbed(boolean embed) {
		this.embed = embed;
	}

	public Commands getCommand() {
		return command;
	}

	public void setCommand(Commands command) {
		this.command = command;
	}
	
	
	
	
	
}
