package player.model;

public class FileMediaMessageSocketModel extends MessageSocketModel{

	private String path;
	
	private String fileName;	
	
	public FileMediaMessageSocketModel() {
		super();
		setCode(3);
	}


	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String toString() {
		return fileName;
	}
}
