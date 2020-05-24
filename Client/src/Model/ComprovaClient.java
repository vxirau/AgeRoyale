package src.Model;

import src.Model.Database.DAO.usuariDAO;

import java.util.regex.Pattern;

/**
* Classe que hem emprat de manera auxiliar pre realitzar algunes comprovaciones pel client
* */
public class ComprovaClient {


    /**
    * Encarregada de detectar la validesa del correu introduit
     * @param email correu introduit per l'usuari
     * @return boolean que representa si el correu es valid o no
    * */
	public static boolean emilIsValid(String email){
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
													"[a-zA-Z0-9_+&*-]+)*@" +
													"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
													"A-Z]{2,7}$";

			Pattern pat = Pattern.compile(emailRegex);
			if (email == null)
					return false;
			return pat.matcher(email).matches();
	}


    /**
     * Encarregada de detectar la validesa de tota la informació introduida
     * @param name nom del usuari introduit
     * @param email email que ha introduit l'usuari
     * @param password contrasenya introduida
     * @param repass la verifiació de la contrasenya
     * @return un enter que ens dirà si hi ha algun error o no.
     * */
    public static int checkRegistre(String name, String email, String password, String repass){
      String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

      if(name.equals("") || email.equals("") || password.equals("") || repass.equals("")){
        return 1;
      } else if(!emilIsValid(email)){
        return 2;
      } else if(!password.matches(pattern)){
        return 3;
      } else if(!password.equals(repass)){
        return 4;
      } else {
        return 5;
      }

    }

}
