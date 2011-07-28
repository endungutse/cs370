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
	private DataStoreAdapter mockDataStore1;
	
	@Before
		
	public void setUp(){
		mockDataStore = mock(DataStoreAdapter.class);
		history = new CheckHistory(mockDataStore);
	}
		
	@Test
		
		public void getRequestReturnAllThePreviouslyEncounteredCheckAmounts() throws Exception {
		Map<String, Object> checks = new HashMap<String, Object>();
		check.put("amount", "one");
		List<Map<String, Object>> checks1 = asList(check);
		when(mockDataStore.runQuery("Checks")).thenReturn(checks1 );
		
		assertEquals("[\"one\")]", history.getAmounts());
	}
		   DataStoreAdapter mockDataStore = mock (DataStoreAdapter.class);
			Map<String, Object> check = new HashMap<String, Object>();

}
