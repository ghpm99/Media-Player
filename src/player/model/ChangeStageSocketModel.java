package player.model;

public class ChangeStageSocketModel extends MessageSocketModel{

	private int stage;
	
	public ChangeStageSocketModel() {
		setCode(6);
	}

	/*
	 * 0 = idle
	 * 1 = youtubePlayer
	 * 2 = mediaPlayer
	 */
	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
	
	
}
