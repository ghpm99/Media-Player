package player.model;

public class StatusMessageSocketModel extends MessageSocketModel{

	public StatusMessageSocketModel() {
		// TODO Auto-generated constructor stub
		setCode(1);
	}
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
