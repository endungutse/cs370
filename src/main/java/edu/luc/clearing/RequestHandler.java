
    package edu.luc.clearing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO Add internal check history
public class RequestHandler {

    public static String respond(List<String> listOfCheckStrings, DataStoreWriter logger){
     List<Check> listOfChecks = stringListToCheckList(listOfCheckStrings);
    
    Iterator<Check> checkListIterator = listOfChecks.iterator();
   
String unHandledChecksAggregator = "";
    
    while(checkListIterator.hasNext()){
    Check myCheck = checkListIterator.next();
   
    Long checkValue = null;
    try{
    checkValue = CheckProcessor.processCheck(myCheck);
   
    if(checkValue == null){
    //The Check was unreadable
    checkValue = new Long(-1);
    checkListIterator.remove();
    unHandledChecksAggregator = unHandledChecksAggregator + ("!!!UnreadableCheck: " + myCheck.getStringValue() + "\n");
    }
    }catch(NumberFormatException e){
    //The Check was unreadable
    checkValue = new Long(-1);
    checkListIterator.remove();
   
    unHandledChecksAggregator = unHandledChecksAggregator + ("!!!NumberFormatException: " + e.getMessage() + " ; " + myCheck.getStringValue() + "\n");
    }finally{
    myCheck.setNumberValue(checkValue);
    logger.addCheck(myCheck);
    }
    }
    
    String responseJson = "{}";
     if(listOfChecks.size() != 0 ){
     responseJson = JsonHandler.checkListToJson(listOfChecks);
     }
    
     if( !(unHandledChecksAggregator.equals("")) ){
     System.err.println("!!!Unhandled Checks: " + unHandledChecksAggregator);
// try{
// Mailer.mail("Unhandled Checks" + "\n\n\n" + unHandledChecksAggregator);
// }catch(Exception e){
// System.err.println("!!!Unhandled Checks: " + unHandledChecksAggregator);
// }
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

