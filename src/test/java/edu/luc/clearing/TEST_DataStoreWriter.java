package edu.luc.clearing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TEST_DataStoreWriter {
DataStoreWriter testLogger;

@Before
public void setUp() {
testLogger = new DataStoreWriter();
testLogger.enableTestMode();
}

@After
public void tearDown() {
testLogger = null;
}

@Test
public void canAddIndividualChecksForSubmit() {
/*
* TODO finish implementing by check logging first
*/
// Check testCheck = new Check();
// testCheck.setStringValue("twelve");
// testCheck.setNumberValue(new Long(12));
// testLogger.addCheck(testCheck);
}

@Test (expected=IllegalArgumentException.class)
public void throwsExceptionWhenGivenUninitializedCheck() {
Check testCheck = new Check();
testLogger.addCheck(testCheck);
}

/*
* TODO finish implementing by check logging first
*/
// @Test (expected=IllegalStateException.class)
// public void throwsExceptionWhenSubmittingEmptyCheckList() {
// testLogger.submit(new Date(), "{}");
// }

@Test
public void stillSubmitsIfcalledPrematurely(){
//Not Yet Implemented
}

}


  