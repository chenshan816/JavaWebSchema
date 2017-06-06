package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json������
 * @author cs
 * @since 1.0.0
 */
public final class JsonUtil {
	 private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	 
	 private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	 
	 /**
	  * ��POJOתΪjson
	  */
	 public static <T> String toJson(T obj) {
		 String json;
		 try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
		}
		 return json;
	}
	 
	 /**
	  * ��JsonתΪPOJO
	  */
	 public static <T> T fromJson(String json, Class<T> type) {
	        T pojo;
	        try {
	            pojo = OBJECT_MAPPER.readValue(json, type);
	        } catch (Exception e) {
	            LOGGER.error("convert JSON to POJO failure", e);
	            throw new RuntimeException(e);
	        }
	        return pojo;
	    }
}
