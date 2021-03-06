package edu.luc.clearing;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class CheckClearingServletTest {

private CheckClearingServlet servlet;
HttpServletResponse mockResponse;
HttpServletRequest mockRequest;
CharArrayWriter writer;

@Before
public void setUp() throws IOException {
DataStoreAdapter datastore = Mockito.mock(DataStoreAdapter.class);
servlet = new CheckClearingServlet(datastore);
mockResponse = mock(HttpServletResponse.class);
mockRequest = mock(HttpServletRequest.class);

BufferedReader reader = new BufferedReader(new StringReader("[]"));
writer = new CharArrayWriter();

when(mockRequest.getReader()).thenReturn(reader);
when(mockResponse.getWriter()).thenReturn(new PrintWriter(writer));
}

@Test
public void setsContentTypeForTheResponse() throws Exception {
servlet.doPost(mockRequest, mockResponse);
verify(mockResponse).setContentType("application/json");
}

@Test
public void writesAResponseObject() throws Exception {
servlet.doPost(mockRequest, mockResponse);
Assert.assertThat(writer.toString(), is(equalTo("{}")));
}

@Test
public void returnsCheckAmountsInAJSONArray() throws Exception {
servlet.doGet(mockRequest, mockResponse);
Assert.assertThat(writer.toString(), is(equalTo("[]")));
}

@Test
public void canLimitTheNumberOfCheckAmountsReturned() throws Exception {
when(mockRequest.getParameter("limit")).thenReturn("1000");
servlet.doGet(mockRequest, mockResponse);
verify(mockRequest).getParameter("limit");
}
}

