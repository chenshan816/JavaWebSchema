package common.bean;

/**
 * 封装表单参数
 * @author cs
 * @since 1.0.0
 */
public class FormParam {
	
	private String fieldName;
	private Object fieldValue;
	
	public FormParam(String fieldName, Object fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public String getFieldName() {
		return fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	
	
}
