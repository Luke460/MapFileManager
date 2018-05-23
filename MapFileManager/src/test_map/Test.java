package test_map;

import map.MapFileManager;

public class Test {
	
	@org.junit.Test
	public void test() throws Exception {
		MapFileManager manager = new MapFileManager("testFile.txm");
		manager.readFile();
		System.out.println(manager.getMap().toString());
		manager.getMap().put("test key", "test_value");
		manager.getMap().toString();
		manager.updateFile();
	}

}
