package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import LukePack.LP;

public class MapFileManager {

	public static final char END_FILE_CHAR = '$';
	public static final char START_LINE_CHAR = '<';
	public static final char END_LINE_CHAR = '>';
	public static final char COMMENT_CHAR = '#';
	public static final String END_LINE = "\n\r";
	private HashMap<String,String> map;
	private String fileName, finalComments;
	private LinkedList<String> keyList;
	private HashMap<Integer,String> commentsMap;
	private String filePayload;

	public MapFileManager(String nomeFile) {
		this.fileName = nomeFile;
		this.finalComments = new String();
		map = new HashMap<String,String>();
		this.keyList = new LinkedList<>();
	}

	public void updateFile() {
		//LP.writeNewFile(fileName, "");
		this.filePayload = "";
		int keyCount = 0;
		for(String tempChiave:this.keyList) {
			keyCount = writeValues(keyCount, tempChiave);
		}
		for(String tempChiave:this.map.keySet()) {
			if(!keyList.contains(tempChiave)) {
				writeValues(tempChiave);
			}
		}
		//LP.addToFileln(fileName, "$" + this.finalComments);
		this.filePayload +=  "$" + this.finalComments + END_LINE;
		LP.writeNewFile(fileName, this.filePayload);
	}

	private int writeValues(int keyCount, String tempChiave) {
		String line;
		if(commentsMap.containsKey(keyCount)) {
			String newComment = "#"+commentsMap.get(keyCount)+"#";
			//LP.addToFileln(fileName, newComment+"\n\r");
			this.filePayload+=newComment+END_LINE+END_LINE;
		}
		line = "<";
		line+=tempChiave;
		keyCount++;
		line+= ">";
		//LP.addToFileln(fileName, line);
		this.filePayload+=line+END_LINE;
		line = "  <";
		line+=this.map.get(tempChiave);
		line+= ">";
		//LP.addToFileln(fileName, line+"\n\r");
		this.filePayload+=line+END_LINE+END_LINE;
		return keyCount;
	}

	private void writeValues(String tempChiave) {
		String line;
		line = "<";
		line+=tempChiave;
		line+= ">";
		//LP.addToFileln(fileName, line);
		this.filePayload+=line+END_LINE;
		line = "  <";
		line+=this.map.get(tempChiave);
		line+= ">";
		//LP.addToFileln(fileName, line+"\n\r");
		this.filePayload+=line+END_LINE+END_LINE;

	}

	public void readFile() throws Exception{
		String text = LP.readFile(fileName);
		String line = new String();
		String chiave = new String();
		String valore = new String();
		this.commentsMap = new HashMap<>();
		Boolean isChiave = false;
		Boolean isComment = false;
		ArrayList<Character> listaCaratteri = new ArrayList<>();
		listaCaratteri.addAll(Utility.stringToArray(text));

		if(!wellFormed(listaCaratteri)) {
			throw new Exception("Format Exception: corrupted file!");
		}

		boolean rigaValida=false;
		boolean end = false;
		int keyCount = 0;
		String comment="";

		for(char c:listaCaratteri){
			if (c==END_FILE_CHAR){ 
				end=true;
			} else {
				if (!end){ // finche' il file non � finito...
					// controllo
					if(c==COMMENT_CHAR) {
						if(isComment) {
							isComment=false;
							if(commentsMap.containsKey(keyCount)) {
								comment = commentsMap.get(keyCount).concat("#\n\r#"+comment);
							}
							commentsMap.put(keyCount, comment);

						} else {
							isComment=true;
							comment = "";
						}

					} else if(isComment) {
						comment+=c;
					} else if(!isComment){

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
								keyList.add(chiave);
								keyCount++;
							} else {
								valore = new String(line);
								map.put(chiave, valore);
							}
						}
						// fine controllo
						if(rigaValida && (c!=START_LINE_CHAR && c!=END_LINE_CHAR)){
							line+=c;
						}
					}

				} else if(!isComment){
					this.finalComments+=c;
				}
			}
		}
	}

	private boolean wellFormed(ArrayList<Character> listaCaratteri) {
		boolean lineOpen=false;
		boolean openKeyValue=false;
		boolean isValue = false;
		boolean isKey = false;
		boolean nextIsValue = false;
		for(char c:listaCaratteri) {

			//System.out.println(c);

			if(c==END_FILE_CHAR)return true;

			if(lineOpen && c==START_LINE_CHAR) return false;
			if(!lineOpen && c==END_LINE_CHAR) return false;
			if(openKeyValue && c==END_FILE_CHAR) return false;

			if(c==START_LINE_CHAR) {
				lineOpen=true;
			}

			if(c==END_LINE_CHAR) {
				lineOpen=false;
			}

			if(c==START_LINE_CHAR && isKey==false && isValue==false && !nextIsValue) {
				isKey=true;
				openKeyValue=true;
			}

			if(c==END_LINE_CHAR && isKey==true && isValue==false) {
				nextIsValue=true;
				isKey=false;
			}

			if(c==START_LINE_CHAR && nextIsValue==true) {
				isValue=true;
				nextIsValue=false;
			}

			if(c==END_LINE_CHAR && isValue==true) {
				isValue=false;
				openKeyValue=false;
			}

		}
		return false;
	}

	public String get(String chiave) {
		return this.map.get(chiave);
	}

	public void put(String chiave, String valore) {
		this.map.put(chiave, valore);
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public HashMap<String, String> getMap() {
		return map;
	}
	
	public String getFilePayload() {
		return filePayload;
	}

}
