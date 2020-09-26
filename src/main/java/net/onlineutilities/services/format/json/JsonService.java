package net.onlineutilities.services.format.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonService {
	String formatJsonBeautify(String data) 
				throws JsonMappingException, JsonProcessingException;
}
