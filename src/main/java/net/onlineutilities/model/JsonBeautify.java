package net.onlineutilities.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonBeautify {
    private String jsonData;
    private String result;
    
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
