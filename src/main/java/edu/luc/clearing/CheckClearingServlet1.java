package edu.luc.clearing;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CheckClearingServlet1 extends HttpServlet {
	private RequestReader requestReader;
	private CheckHistory checkHistory;
	
	public CheckClearingServlet1(){
		requestReader = new RequestReader();
		checkHistory = new CheckHistory(new DataStoreAdapter());
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.getWriter().print(requestReader.respond(req.getReader()));
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().print(checkHistory.getAmounts());
	}
}