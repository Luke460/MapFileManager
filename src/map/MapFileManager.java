package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import LukePack.LP;

public class MapFileManager {
	
	public static final char END_FILE_CHAR = '$';
	public static final char START_LINE_CHAR = '<';
	public static final char END_LINE_CHAR = '>';
	private HashMap<String,String> map;
	private String fileName, finalComments;
	
	public MapFileManager(String nomeFile) {
		this.fileName = nomeFile;
		this.finalComments = new String();
	}
	
	public void updateFile() {
		LinkedList<String> listaChiavi = new LinkedList<String>();
		listaChiavi.addAll(this.map.keySet());
		String line;
		LP.writeNewFile(fileName, "");
		for(String tempChiave:listaChiavi) {
			line = "<";
			line+=tempChiave;
			line+= ">";
			LP.addToFileln(fileName, line);
			line = "  <";
			line+=this.map.get(tempChiave);
			line+= ">";
			LP.addToFileln(fileName, line);
		}
		LP.addToFileln(fileName, "$" + this.finalComments);
	}
	
	public void readFile(){
		map = new HashMap<String,String>();
		String text = LP.readFile(fileName);
		String line = new String();
		String chiave = new String();
		String valore = new String();
		Boolean isChiave = false;
		ArrayList<Character> listaCaratteri = new ArrayList<>();
		listaCaratteri.addAll(Utility.stringToArray(text));

		boolean rigaValida=false;

		for(char c:listaCaratteri){
			if (c!=END_FILE_CHAR){ // finche' il file non � finito...
				// controllo
				if(c==START_LINE_CHAR){
					line = new String();
					rigaValida=true;
					if(isChiave==true) {
						isChiave = false;
					} else {
						isChiave = true;
					}
				}
				if(c==END_LINE_CHAR){
					rigaValida=false;
					if(isChiave) {
						chiave = new String(line);
					} else {
						valore = new String(line);
						map.put(chiave, valore);
					}
				}
				// fine controllo
				if(rigaValida && (c!=START_LINE_CHAR && c!=END_LINE_CHAR)){
					line+=c;
				}
			} else {
				this.finalComments+=c;
			}
		}
	}

	public String get(String chiave) {
		return this.map.get(chiave);
	}
	
	public void put(String chiave, String valore) {
		this.map.put(chiave, valore);
	}
	
	public HashMap<String, String> getMap() {
		return map;
	}
	
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

}
