package edu.luc.clearing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
* Depends On:
*
* JsonHandler
* Check
* CheckProcessor
*/

public class TEST_RequestHandler {
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

/*
* UNIT Tests
*/
    @Test
    public void shouldReturnEmptyJsonObjectForAnEmptyRequest() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("[]");
        assertEquals("{}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldReturnEmptyJsonForListOfBlankChecks() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("[,,,]");
        assertEquals("{}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldReturnEmptyJsonForListOfEmptyChecks() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("\",\"\",\"");
        assertEquals("{}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldAcceptMultipleChecksInOneRequests() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("one");
     check.add("two");
     check.add("three");
     check.add("four");
        assertEquals("{\"one\":100,\"two\":200,\"three\":300,\"four\":400}", RequestHandler.respond(check,testLogger));
    }

/*
* END TO END tests
*/

    @Test
    public void shouldReturnCentsForDollarValues() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("one");
     assertEquals("{\"one\":100}", RequestHandler.respond(check,testLogger));
    
     List<String> checktwo = new ArrayList<String>();
     checktwo.add("seven dollars");
     assertEquals("{\"seven dollars\":700}", RequestHandler.respond(checktwo,testLogger));
    }
    
    @Test
    public void shouldReturnCentsForCentValues() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("twelve cents");
     assertEquals("{\"twelve cents\":12}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldThrowAwayChecksWithIrelevantWords() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("three thousand purple chipmunks");
     assertEquals("{}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldAcceptAllIntegersAsStrings() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("0");
     assertEquals("{\"0\":0}", RequestHandler.respond(check,testLogger));
    
     List<String> check2 = new ArrayList<String>();
     check2.add("1");
     assertEquals("{\"1\":100}", RequestHandler.respond(check2,testLogger));
    
     List<String> check3 = new ArrayList<String>();
     check3.add("2");
     assertEquals("{\"2\":200}", RequestHandler.respond(check3,testLogger));
    
     List<String> check4 = new ArrayList<String>();
     check4.add("1000000");
     assertEquals("{\"1000000\":100000000}", RequestHandler.respond(check4,testLogger));
    }
    
    @Test
    public void shouldAcceptAmountsUnderOneHundred() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("ten");
     assertEquals("{\"ten\":1000}", RequestHandler.respond(check,testLogger));
    
     List<String> check2 = new ArrayList<String>();
     check2.add("thirteen");
     assertEquals("{\"thirteen\":1300}", RequestHandler.respond(check2,testLogger));
    
     List<String> check3 = new ArrayList<String>();
     check3.add("twenty");
     assertEquals("{\"twenty\":2000}", RequestHandler.respond(check3,testLogger));
    }
    
    @Test
    public void shouldAcceptCompoundWordsUnderOneHundred() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("thirty three");
     assertEquals("{\"thirty three\":3300}", RequestHandler.respond(check,testLogger));
    
     List<String> check2 = new ArrayList<String>();
     check2.add("fifty seven");
     assertEquals("{\"fifty seven\":5700}", RequestHandler.respond(check2,testLogger));
    
     List<String> check3 = new ArrayList<String>();
     check3.add("nintey nine");
     assertEquals("{\"nintey nine\":9900}", RequestHandler.respond(check3,testLogger));
    }

    @Test
    public void shouldAcceptCentsAsADecimal() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add(".42");
     assertEquals("{\".42\":42}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldAcceptDollarsAndCentsAsADecimal() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("6.76");
     assertEquals("{\"6.76\":676}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldAcceptCentsAsAFraction() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("99/100");
     assertEquals("{\"99/100\":99}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldAcceptDollarsAndCentsAsAFraction() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("nintey nine and 99/100");
     assertEquals("{\"nintey nine and 99/100\":9999}", RequestHandler.respond(check,testLogger));
    }
    
    @Test
    public void shouldAcceptCentsAsWords() throws Exception {
     List<String> check = new ArrayList<String>();
     check.add("twenty two cents");
     assertEquals("{\"twenty two cents\":22}", RequestHandler.respond(check,testLogger));
    }
    
}
    

  