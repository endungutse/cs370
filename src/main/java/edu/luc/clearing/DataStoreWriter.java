package edu.luc.clearing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class DataStoreWriter {
boolean isInTesting;
List<Entity> checkList;
Entity parent;
String parentName;
Date requestDate;
String requestJson;

public DataStoreWriter() {
isInTesting = false;
checkList = new ArrayList<Entity>();
requestDate = new Date();
requestJson = "[]";
parent = null;
parentName = null;
}

public DataStoreWriter(Date myStartDate, String myRequestJson){
isInTesting = false;
checkList = new ArrayList<Entity>();
//We can't create the parent at instantiation because it fails in a testing environment.
parent = null;
parentName = null;

if(myStartDate != null){
requestDate = myStartDate;
}else{
requestDate = new Date();
}

if(myRequestJson != null){
requestJson = myRequestJson;
}else{
requestJson = "[]";
}
}

public void enableTestMode(){
isInTesting = true;
}

public boolean isInTestMode(){
return isInTesting;
}

/*
* @Deprecated
*/
// public void writeRequestLog(Date receivedDate, String request, String response){
// if(isInTesting){
// System.out.println(receivedDate + request + response);
// return;
// }
//
// Key parentLogKey = KeyFactory.createKey("Log", "RequestLog");
//
// Date replyDate = new Date();
//
// Entity log = new Entity("Log", parentLogKey);
// log.setProperty("ReceivedDate", receivedDate);
// log.setProperty("ReplyDate", replyDate);
// log.setProperty("Request", new Text(request));
// log.setProperty("Reply", new Text(response));
//
// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
// datastore.put(log);
// }

public void addCheck(Check theCheck) throws IllegalArgumentException {
if((theCheck.getStringValue() == null) || (theCheck.getNumberValue() == null)){
//Should never get here. The app is returning -1 for check value instead
throw new IllegalArgumentException("Incomplete Check");
}

if(isInTesting){
System.out.println("DATASTORE: Added ; " + theCheck.getStringValue() + " ; " + theCheck.getNumberValue());
return;
}

if(parent == null){
createParent();
}

Entity check = new Entity("Check", parent.getKey());
check.setProperty("RequestName", parentName);
check.setProperty("checkText", new Text(theCheck.getStringValue()));
check.setProperty("checkValue", theCheck.getNumberValue());

checkList.add(check);
}

private void createParent(){

@SuppressWarnings("deprecation")
String name = "" + (requestDate.getYear() + 1900) +"-"+ (requestDate.getMonth()+1) +"-"+ requestDate.getDate() + " " + requestDate.getHours() + ":" + requestDate.getMinutes() + ":" + requestDate.getSeconds();
parentName = name;

parent = new Entity("Request", parentName);
parent.setProperty("ReceivedDate", requestDate);
parent.setProperty("ReceivedJson", new Text(requestJson));

}

public void submitIncomplete(Date replyDate){

try{
Mailer.mail("DataStore Early Termination" + "\n\n\n" + checkList.size() + " Checks processed");
}catch(Exception e){
System.err.println("!!!DataStore Early Termination: " + checkList.size() + " Checks processed");
}

submit(replyDate, "!!!DataStore Early Termination");
}

public void submit(Date replyDate, String replyJson) throws IllegalStateException {
if(isInTesting){
System.out.println("DATASTORE: Submitted");
return;
}

if(checkList.size() == 0){
throw new IllegalStateException("Cannot Submit Zero Checks!");
}

if(parent == null){
throw new IllegalStateException("No Log Parent found!");
}

parent.setProperty("ReplyDate", replyDate);
parent.setProperty("ReplyJson", new Text(requestJson));

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(parent);
        
        datastore.put(checkList);
}
}
 