package map;

import java.util.Collection;
import java.util.LinkedList;

public class Utility {

	public static Collection<? extends Character> stringToArray(String stringa) {
		LinkedList<Character> listaCaratteri = new LinkedList<>();
		for(int i = 0; i<stringa.length(); i++){
			listaCaratteri.add(stringa.charAt(i));
		}
		return listaCaratteri;
	}

}
