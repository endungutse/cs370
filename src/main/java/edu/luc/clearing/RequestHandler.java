package edu.luc.clearing;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;


//TODO Add internal check history
public class RequestHandler {

    public static String respond(Reader requestDataReader, DataStoreWriter logger){
     Date requestDate = new Date();
     //Parse the json to a list of check strings
     List<String> listOfStrings = JsonHandler.jsonToMapParser(requestDataReader);
     String requestJson = JsonHandler.stringListToJson(listOfStrings);
    
     List<Check> listOfChecks = stringListToCheckList(listOfStrings);
    
     //TODO Re-arrange so it doesn't rely on side effects
    
    Iterator<Check> checkListIterator = listOfChecks.iterator();
    
    while(checkListIterator.hasNext()){
    Check myCheck = checkListIterator.next();
   
    Long checkValue = null;
    try{
    checkValue = CheckProcessor.processCheck(myCheck);
   
    if(checkValue == null){
    System.err.println("!!!Unrecognizable Check: " + myCheck.getStringValue());
    checkListIterator.remove();
    }
    }catch(NumberFormatException e){
    System.err.println("!!!NumberFormatException: " + myCheck.getStringValue() + "\n\t" + e.getMessage());
    checkListIterator.remove();
    }

    myCheck.setNumberValue(checkValue);
    }
    
    String responseJson = "{}";
     if(listOfChecks.size() != 0 ){
     responseJson = JsonHandler.checkListToJson(listOfChecks);
     }
    
     try{
     logger.writeRequestLog(requestDate, requestJson, responseJson);
     }catch(DatastoreFailureException e){
     System.err.println("!!!Could not write log to Data Store: " + requestJson + " : " + responseJson);
     }
    
     return responseJson;
    }
    
    private static List<Check> stringListToCheckList(List<String> listOfStrings){
     List<Check> checkList = new ArrayList<Check>();
    
     for(String checkString : listOfStrings){
     if(checkString != null){
         checkList.add(new Check(checkString));
     }
     }
    
     return checkList;
    }
}