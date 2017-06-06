package common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图对象，表示返回一个JSP页面
 * @author cs
 * @since 1.0.0
 */
public class View {
	//视图路径
	private String path;
	
	//模型数据
	private Map<String,Object> model;

	public View(String path) {
		this.path = path;
		this.model = model = new HashMap<String, Object>();
	}

	 public View addModel(String key, Object value) {
	        model.put(key, value);
	        return this;
	    }
	
	public String getPath() {
		return path;
	}


	public Map<String, Object> getModel() {
		return model;
	}
	
	
}
