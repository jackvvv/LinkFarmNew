package sinia.com.linkfarmnew.utils.album.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UploadImgEntity implements Serializable {

	@Expose
	private String filepath;
	@Expose
	private String filename;

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
