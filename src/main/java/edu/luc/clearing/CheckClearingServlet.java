package edu.luc.clearing;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CheckClearingServlet extends HttpServlet {
DataStoreWriter logger;

public CheckClearingServlet(){
logger = null;
}

public CheckClearingServlet(DataStoreWriter mylogger){
logger = mylogger;
}


    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> requests = JsonHandler.jsonToStringListParser(req.getReader());
     if(logger == null){
         createLogger(new Date(), JsonHandler.stringListToJson(requests));
        }
    
     resp.setContentType("application/json");
        PrintWriter httpWriter = resp.getWriter();
        String response = RequestHandler.respond(requests, logger);
        
        httpWriter.print(response);
        
        //after replying, write to the datastore
     try{
     logger.submit(new Date(), response);
     }catch(Exception loggerException){
     System.err.println("!!!DataStoreException: " + loggerException.getMessage() + "\n");
// try{
// Mailer.mail("DataStoreException: " + loggerException.getMessage() + "\n\n\n");
// }catch(Exception mailerException){
// System.err.println("!!!DataStoreException: " + loggerException.getMessage() + "\n" + "MailerException: " + mailerException.getMessage());
// }
     }
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
     resp.getWriter().print("{}");
    }
    
    private void createLogger(Date start, String requestJson){
     logger = new DataStoreWriter(start, requestJson);
    }
    
}
    
