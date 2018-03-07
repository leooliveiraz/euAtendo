package rocha.euAtendo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static Date stringParaData(String dataRecebida) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date data;
		try {
			data = (Date) formatter.parse(dataRecebida);
			return data;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String dataParaString(Date dataRecebida) {
		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");  		   

		try {
			String date = out.format(dataRecebida);
			return date;
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
