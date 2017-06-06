import java.util.HashMap;
import java.util.Map;

import common.bean.Request;


public class TestRequest {
	
	public static void main(String[] args) {
		Map<Request, Integer> map = new HashMap<Request, Integer>();
		Request r1 = new Request("get","customer");
		Request r2 = new Request("get","customer");
		map.put(r1, 1);
		int value = map.get(r2);
		System.out.println(value);
	}
}	
