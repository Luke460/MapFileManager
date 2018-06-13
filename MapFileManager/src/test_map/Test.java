package test_map;

import map.MapFileManager;

public class Test {
	
	@org.junit.Test
	public void test() throws Exception {
		MapFileManager manager = new MapFileManager("testFile.txm");
		manager.readFile();
		manager.readFile();
		System.out.println(manager.getMap().toString());
		manager.put("test key", "test_value");
		manager.put("test key 2", "test_value 3");
		manager.getMap().toString();
		manager.updateFile();
	}

}
