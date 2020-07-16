package producer;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TwelvedataAPI {
	
	public String getStocks(){
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.get("https://api.twelvedata.com/time_series?interval=1min&symbol=EUR/USD&format=JSON&apikey=0f23bd092f644011889de648fde28d90")
			  .header("Cookie", "__cfduid=d38f7dde9c7a26ce6fed228bcb35255a41593440950")
			  .asString();
			return response.getBody();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return "";
	}
}
