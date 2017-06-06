package common.bean;

/**
 * 如果返回是Data类型的数据对象，则返回一个JSON数据
 * 框架会将该对象写入HttpServletResponse对象中，从而直接输出到浏览器中
 * @author cs
 * @since 1.0.0
 */
public class Data {
	
	/**
	 * 模型数据
	 */
	private Object model;

	public Data(Object model) {
		super();
		this.model = model;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
	
	
}
