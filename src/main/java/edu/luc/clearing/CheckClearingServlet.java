package edu.luc.clearing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CheckClearingServlet extends HttpServlet {
DataStoreWriter logger;

public CheckClearingServlet(){
logger = new DataStoreWriter();
}

public CheckClearingServlet(DataStoreWriter mylogger){
logger = mylogger;
}


    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter httpWriter = resp.getWriter();
        
        httpWriter.print(RequestHandler.respond(req.getReader(), logger));
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
     resp.getWriter().print("{}");
    }
    
}