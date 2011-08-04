package edu.luc;

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

import edu.luc.clearing.CheckClearingServlet;
import edu.luc.clearing.DataStoreWriter;

public class AcceptanceTest {
private CheckClearingServlet servlet;
private HttpServletResponse mockResponse;
private HttpServletRequest mockRequest;
private CharArrayWriter writer;
private DataStoreWriter testLogger;

@Before
public void setUp() {
testLogger = new DataStoreWriter();
testLogger.enableTestMode();
servlet = new CheckClearingServlet(testLogger);

mockResponse = mock(HttpServletResponse.class);
mockRequest = mock(HttpServletRequest.class);

writer = new CharArrayWriter();

try {
when(mockResponse.getWriter()).thenReturn(new PrintWriter(writer));
} catch (IOException e) {
e.printStackTrace();
}
}

private void setDataAndPost(String myData) throws Exception {
BufferedReader reader = new BufferedReader(new StringReader(myData));

try {
when(mockRequest.getReader()).thenReturn(reader);
} catch (IOException e) {
e.printStackTrace();
}

servlet.doPost(mockRequest, mockResponse);
}

@Test
public void shouldAcceptHttpPostRequest() throws Exception {
setDataAndPost("[\"nine\"]");
assertThat(writer.toString(), is(equalTo("{\"nine\":900}")));
}

@Test
public void shouldHandleVeryLargeNumbers() throws Exception {
setDataAndPost("[\"14 trillion five hundred sixty 5 billion one hundered eighty three million and 03 cnts\"]");
assertThat(writer.toString(), is(equalTo("{\"14 trillion five hundred sixty 5 billion one hundered eighty three million and 03 cnts\":1456518300000003}")));
}

@Test
public void megaTest() throws Exception {
setDataAndPost("[\"two million three hundred fourty six thousand and two dollars and 10/100\"]");
assertThat(writer.toString(), is(equalTo("{\"two million three hundred fourty six thousand and two dollars and 10/100\":234600210}")));
}

@Test
public void shouldProperlyInterpretInlineHundredAdjectives() throws Exception {
setDataAndPost("[\"three hundred thousand\"]");
assertThat(writer.toString(), is(equalTo("{\"three hundred thousand\":30000000}")));

}

}