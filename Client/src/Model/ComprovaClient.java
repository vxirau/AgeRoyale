package src.Model;

import src.Model.Database.DAO.usuariDAO;

import java.util.regex.Pattern;

public class ComprovaClient {


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



    public static int checkRegistre(String name, String email, String password, String repass){
      boolean check = false;
      String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

      if(name.equals("") && email.equals("") && password.equals("") && repass.equals("")){
        return 3;
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


