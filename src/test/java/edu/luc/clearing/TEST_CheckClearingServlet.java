
   package edu.luc.clearing;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class TEST_CheckClearingServlet {
private CheckClearingServlet servlet;
private HttpServletResponse mockResponse;
private HttpServletRequest mockRequest;
private CharArrayWriter writer;
private BufferedReader reader;
private DataStoreWriter testLogger;

@Before
public void setUp() {
testLogger = new DataStoreWriter();
testLogger.enableTestMode();
servlet = new CheckClearingServlet(testLogger);

mockResponse = mock(HttpServletResponse.class);
mockRequest = mock(HttpServletRequest.class);

reader = new BufferedReader(new StringReader("[]"));
writer = new CharArrayWriter();

try {
when(mockRequest.getReader()).thenReturn(reader);
when(mockResponse.getWriter()).thenReturn(new PrintWriter(writer));
} catch (IOException e) {
e.printStackTrace();
}
}

@Test
public void setsContentTypeForTheResponse() throws Exception {
servlet.doPost(mockRequest, mockResponse);
verify(mockResponse).setContentType("application/json");
}

@Test
public void writesResponseAsAnObject() throws Exception {
servlet.doPost(mockRequest, mockResponse);
assertThat(writer.toString(), is(equalTo("{}")));
}

@Test
public void returnsCheckAmountsInAJSONArray() throws Exception {
servlet.doGet(mockRequest, mockResponse);
assertThat(writer.toString(), is(equalTo("{}")));
}

// @Test
// public void shouldLogRequests(){
// DataStoreWriter mockLogger = mock(DataStoreWriter.class);
//
// try {
// when(mockLogger.write()).;
// when(mockResponse.getWriter()).thenReturn(new PrintWriter(writer));
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
}

