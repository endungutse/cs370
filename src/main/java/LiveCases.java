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

public class LiveCases {
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
public void a() throws Exception {
setDataAndPost("[\"59 dollars and one cent\"]");
assertThat(writer.toString(), is(equalTo("{\"59 dollars and one cent\":5901}")));
}

@Test
public void b() throws Exception {
setDataAndPost("[\"20 dollar and 5 cent\"]");
assertThat(writer.toString(), is(equalTo("{\"20 dollar and 5 cent\":2005}")));
}

@Test
public void c() throws Exception {
setDataAndPost("[\"Four dollars and no cents\"]");
assertThat(writer.toString(), is(equalTo("{\"Four dollars and no cents\":400}")));
}

@Test
public void d() throws Exception {
setDataAndPost("[\"Three dollars and 0/100 cents\"]");
assertThat(writer.toString(), is(equalTo("{\"Three dollars and 0/100 cents\":300}")));
}

@Test
public void e() throws Exception {
setDataAndPost("[\"five and no cents\"]");
assertThat(writer.toString(), is(equalTo("{\"five and no cents\":500}")));
}

@Test
public void f() throws Exception {
setDataAndPost("[\"one dollars and no cents\"]");
assertThat(writer.toString(), is(equalTo("{\"one dollars and no cents\":100}")));
}

@Test
public void g() throws Exception {
setDataAndPost("[\"fifty cent\"]");
assertThat(writer.toString(), is(equalTo("{\"fifty cent\":50}")));
}

@Test
public void h() throws Exception {
setDataAndPost("[\"1 dollar and one cent\"]");
assertThat(writer.toString(), is(equalTo("{\"1 dollar and one cent\":101}")));
}

@Test
public void i() throws Exception {
setDataAndPost("[\"ten cent\"]");
assertThat(writer.toString(), is(equalTo("{\"ten cent\":10}")));
}

@Test
public void j() throws Exception {
setDataAndPost("[\"1 dollar and 1 cent\"]");
assertThat(writer.toString(), is(equalTo("{\"1 dollar and 1 cent\":101}")));
}

@Test
public void k() throws Exception {
setDataAndPost("[\"one dollars and one cent\"]");
assertThat(writer.toString(), is(equalTo("{\"one dollars and one cent\":101}")));
}

@Test
public void l() throws Exception {
setDataAndPost("[\"one cent\"]");
assertThat(writer.toString(), is(equalTo("{\"one cent\":1}")));
}

@Test
public void m() throws Exception {
setDataAndPost("[\"SEVEN dollars no cents\"]");
assertThat(writer.toString(), is(equalTo("{\"SEVEN dollars no cents\":700}")));
}

@Test
public void n() throws Exception {
setDataAndPost("[\"ninety nine and 99/100 dollars\"]");
assertThat(writer.toString(), is(equalTo("{\"ninety nine and 99/100 dollars\":9999}")));
}

@Test
public void o() throws Exception {
setDataAndPost("[\"one dollar and 1 cent\"]");
assertThat(writer.toString(), is(equalTo("{\"one dollar and 1 cent\":101}")));
}

@Test
public void p() throws Exception {
setDataAndPost("[\"forty four and 5/100\"]");
assertThat(writer.toString(), is(equalTo("{\"forty four and 5/100\":4405}")));
}

@Test
public void q() throws Exception {
setDataAndPost("[\"fifty nine and 1/100 dollars\"]");
assertThat(writer.toString(), is(equalTo("{\"fifty nine and 1/100 dollars\":5901}")));
}

@Test
public void r() throws Exception {
setDataAndPost("[\"forty five cents\"]");
assertThat(writer.toString(), is(equalTo("{\"forty five cents\":45}")));
}

@Test
public void s() throws Exception {
setDataAndPost("[\"Two and 21/100 dollars\"]");
assertThat(writer.toString(), is(equalTo("{\"Two and 21/100 dollars\":221}")));
}

@Test
public void t() throws Exception {
setDataAndPost("[\"one dollars and one cent\"]");
assertThat(writer.toString(), is(equalTo("{\"one dollars and one cent\":101}")));
}

@Test
public void u() throws Exception {
setDataAndPost("[\"twenty dollar and 5 cent\"]");
assertThat(writer.toString(), is(equalTo("{\"twenty dollar and 5 cent\":2005}")));
}

@Test
public void v() throws Exception {
setDataAndPost("[\"sixty eight and TWENTY ONE CENTS\"]");
assertThat(writer.toString(), is(equalTo("{\"sixty eight and TWENTY ONE CENTS\":6821}")));
}

@Test
public void w() throws Exception {
setDataAndPost("[\"FIVE DOLLARS AND TWELVE CENTS\"]");
assertThat(writer.toString(), is(equalTo("{\"FIVE DOLLARS AND TWELVE CENTS\":512}")));
}

@Test
public void x() throws Exception {
setDataAndPost("[\"fifty nine and 1/100 dollars\"]");
assertThat(writer.toString(), is(equalTo("{\"fifty nine and 1/100 dollars\":5901}")));
}

@Test
public void y() throws Exception {
setDataAndPost("[\"fifty nine dollars and 1 cent\"]");
assertThat(writer.toString(), is(equalTo("{\"fifty nine dollars and 1 cent\":5901}")));
}


}

