package test_map;

import map.MapFileManager;

public class Test {
	
	@org.junit.Test
	public void test() {
		MapFileManager manager = new MapFileManager("testFile.txm");
		manager.readFile();
		System.out.println(manager.getMap().toString());
		manager.getMap().toString();
		manager.updateFile();
	}

}