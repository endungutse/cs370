package edu.luc.clearing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.PreparedQuery;

import com.google.appengine.api.datastore.Query;

public class DataStoreWriter {
boolean isInTesting;
List<Entity> checkList;
Entity parent;
Date requestDate;
String requestJson;


/*
* Deprecated
*/
public DataStoreWriter() {
isInTesting = false;
}
/*
* yet to be deployed new version
*/
// public DataStoreWriter() {
// isInTesting = false;
// checkList = new ArrayList<Entity>();
// requestDate = new Date();
// requestJson = "[]";
//
// createParent();
// }

public DataStoreWriter(Date myStartDate, String myRequestJson){
isInTesting = false;
checkList = new ArrayList<Entity>();

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

createParent();
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
public void writeRequestLog(Date receivedDate, String request, String response){
if(isInTesting){
System.out.println(receivedDate + request + response);
return;
}

Key parentLogKey = KeyFactory.createKey("Log", "RequestLog");

Date replyDate = new Date();

Entity log = new Entity("Log", parentLogKey);
log.setProperty("ReceivedDate", receivedDate);
log.setProperty("ReplyDate", replyDate);
log.setProperty("Request", new Text(request));
log.setProperty("Reply", new Text(response));

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(log);
}

public void addCheck(Check theCheck) throws IllegalArgumentException {
if((theCheck.getStringValue() == null) || (theCheck.getNumberValue() == null)){
throw new IllegalArgumentException("Incomplete Check");
}

Entity check = new Entity("Check", parent.getKey());
check.setProperty("checkText", new Text(theCheck.getStringValue()));
check.setProperty("checkValue", theCheck.getNumberValue());

checkList.add(check);
}

private void createParent(){

@SuppressWarnings("deprecation")
String name = "" + requestDate.getYear() + requestDate.getMonth() + requestDate.getDay() + " " + requestDate.getHours() + ":" + requestDate.getMinutes() + "";

parent = new Entity("Request", name);
parent.setProperty("ReceivedDate", requestDate);
parent.setProperty("ReceivedJson", new Text(requestJson));

}

public void submitIncomplete(Date replyDate){

System.err.println("!!!Early Run Termination. " + checkList.size() + " Checks processed");
submit(replyDate, "!!!Early Run Termination");
}


public void submit(Date replyDate, String replyJson) throws IllegalStateException {
if(checkList.size() == 0){
throw new IllegalStateException("Cannot Submit Zero Checks!");
}else if(isInTesting){
System.out.println(checkList.toString());
return;
}

parent.setProperty("ReplyDate", replyDate);
parent.setProperty("ReplyJson", new Text(requestJson));

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(parent);
        
        datastore.put(checkList);
}
}
