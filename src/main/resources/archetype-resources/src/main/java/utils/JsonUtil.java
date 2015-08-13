#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

	private JsonUtil(){}

	public static <T> T parseObject(String json,Class<T> clazz) {
			T readValue = null;
			try {
				readValue = new ObjectMapper().readValue(json, clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return readValue;
	}

	public static String toJSONString(Object object){
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
