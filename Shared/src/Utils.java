package src;

public class Utils {

	public static String ferEspais(int total){
		String s= "";
		for(int i=0; i<total ;i++){
			s+="&nbsp";
		}
		return s;
	}

	public static String ferDottedLine(int total){
		String s= "";
		for(int i=0; i<total ;i++){
			s+="-";
		}
		return s;
	}

}
