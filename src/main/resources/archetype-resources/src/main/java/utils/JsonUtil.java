#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	private JsonUtil(){}

	public static <T> T parseObject(String json,Class<T> clazz) {
			T readValue = null;
			try {
				readValue = mapper.readValue(json, clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return readValue;
	}

	public static String toJSONString(Object object){
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
