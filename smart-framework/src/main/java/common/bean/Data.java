package common.bean;

/**
 * ���������Data���͵����ݶ����򷵻�һ��JSON����
 * ��ܻὫ�ö���д��HttpServletResponse�����У��Ӷ�ֱ��������������
 * @author cs
 * @since 1.0.0
 */
public class Data {
	
	/**
	 * ģ������
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
