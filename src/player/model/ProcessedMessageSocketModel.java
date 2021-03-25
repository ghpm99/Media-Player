package player.model;

public class ProcessedMessageSocketModel {

	private boolean needResponse;

	private MessageSocketModel message;

	private ResponseSocketModel response;

	public boolean isNeedResponse() {
		return needResponse;
	}

	public void setNeedResponse(boolean needResponse) {
		this.needResponse = needResponse;
	}

	public MessageSocketModel getMessage() {
		return message;
	}

	public void setMessage(MessageSocketModel message) {
		this.message = message;
	}

	public ResponseSocketModel getResponse() {
		return response;
	}

	public void setResponse(ResponseSocketModel response) {
		this.response = response;
	}

}
