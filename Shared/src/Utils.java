package src;

/**
 * Classe auxiliar per tal de realitzar cadenes de text
 */
public class Utils {

	/**
		* Encarregada de fer espais a la finestra gràfica
		* @param total número de posicions que es vol espaiar
		* @return s retorna el string amb l'espaiat desitjat
	* */
	public static String ferEspais(int total){
		String s= "";
		for(int i=0; i<total ;i++){
			s+="&nbsp";
		}
		return s;
	}

	/**
	 * Encarregada de fer una línia amb '-'
	 * @param total número de posicions que es vol que sigui de llarga la línia
	 * @return s retorna la línia
	 * */
	public static String ferDottedLine(int total){
		String s= "";
		for(int i=0; i<total ;i++){
			s+="-";
		}
		return s;
	}

}
