package edu.luc.clearing;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;


public class CheckHistory {
	private DataStoreWriter storeWriter;
	private Gson gson;
	
	public CheckHistory(DataStoreWriter storeWriter) {
		this.storeWriter = storeWriter;
		gson = new Gson();
	}

	public String getAmounts(String limitStr) {
		Integer l= null;
		if (limitStr !=null) {
			l = Integer.parseInt(limitStr);
		}
		Set<String> amounts = new Hashset<String>();
		List<Map<String, Object>> runQuery = storeWriter.runQuery("Checks");
		for(Map<String, Object> properties : runQuery) {
			amounts.add(properties.get ("amount").toString());
		}
		return gson.toJson(amounts);
	}
}
