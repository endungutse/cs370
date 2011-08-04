
    
package edu.luc.clearing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class RejectedChecksServlet extends HttpServlet{

public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
String requests;
try{
     requests = JsonHandler.stringListToJson(JsonHandler.jsonToStringListParser(req.getReader()));
        }catch(Exception e){
         requests = "Failed to Parse Json, returning toString():" + "\n";
         BufferedReader reqReader = new BufferedReader(req.getReader());
         while(reqReader.ready()){
         requests = requests + reqReader.readLine();
         }
       
        }
        
        logRejectedChecks(requests);
        
        PrintWriter httpWriter = resp.getWriter();
        httpWriter.print("202 Accepted");
    }

private void logRejectedChecks(String requests) {
System.err.println("!!!RejectedChecks: " + requests);
// try{
// Mailer.mail("Rejected Requests" + "\n\n\n" + requests);
// }catch(Exception e){
// System.err.println("!!!MailFailure,RejectedChecks: " + requests);
// }
}
}

