package edu.luc.clearing;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;




public class CheckHistoryTest {
	private CheckHistory history;
	private DataStoreWriter mockDataStoreWriter;
	
	@Before
		
	public void setUp(){
		mockDataStoreWriter = mock(DataStoreWriter.class);
		history = new CheckHistory(mockDataStoreWriter);
	}
		
	@Test
		
		public void getRequestReturnAllThePreviouslyEncounteredCheckAmounts() throws Exception {
		Map<String, Object> checks = new HashMap<String, Object>();
		check.put("amount", "one");
		List<Map<String, Object>> checks1 = asList(check);
		when(mockDataStoreWriter.runQuery("Checks")).thenReturn(checks1 );
		
		assertEquals("[\"one\")]", history.getAmounts());
	}
		   DataStoreWriter mockDataStore = mock(DataStoreWriter.class);
			Map<String, Object> check = new HashMap<String, Object>();

}
