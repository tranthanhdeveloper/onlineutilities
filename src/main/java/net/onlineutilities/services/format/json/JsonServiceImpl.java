package net.onlineutilities.services.format.json;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonServiceImpl implements JsonService {

	@Override
	public String formatJsonBeautify(String jsonData) throws JsonMappingException, 
								JsonProcessingException {
		System.out.println("jsonTxt: " + jsonData);
		
		ObjectMapper mapper = new ObjectMapper();
		Object obj;
		obj = mapper.readValue(jsonData, Object.class);
		String result =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		
		return result;
	}
}
