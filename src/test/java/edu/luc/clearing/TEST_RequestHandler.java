package edu.luc.clearing;

import static org.junit.Assert.*;

import java.io.StringReader;

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
        assertEquals("{}", RequestHandler.respond(new StringReader("[]"),testLogger));
    }
    
    @Test
    public void shouldReturnEmptyJsonForListOfBlankChecks() throws Exception {
        assertEquals("{}", RequestHandler.respond(new StringReader("[,,,]"),testLogger));
    }
    
    @Test
    public void shouldReturnEmptyJsonForListOfEmptyChecks() throws Exception {
        assertEquals("{}", RequestHandler.respond(new StringReader("[\"\",\"\",\"\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptMultipleChecksInOneRequests() throws Exception {
        assertEquals("{\"one\":100,\"two\":200,\"three\":300,\"four\":400}", RequestHandler.respond(new StringReader("[\"one\",\"two\",\"three\",\"four\"]"),testLogger));
    }
    
    public void shouldThrowExceptionForMalformedJason() throws Exception {
     fail("Not Yet Implimented");
    }

/*
* END TO END tests
*/

    @Test
    public void shouldReturnCentsForDollarValues() throws Exception {
     assertEquals("{\"one\":100}", RequestHandler.respond(new StringReader("[\"one\"]"),testLogger));
     assertEquals("{\"seven dollars\":700}", RequestHandler.respond(new StringReader("[\"seven dollars\"]"),testLogger));
    }
    
    @Test
    public void shouldReturnCentsForCentValues() throws Exception {
     assertEquals("{\"twelve cents\":12}", RequestHandler.respond(new StringReader("[\"twelve cents\"]"),testLogger));
    }
    
    @Test
    public void shouldThrowAwayChecksWithIrelevantWords() throws Exception {
     assertEquals("{}", RequestHandler.respond(new StringReader("[\"three thousand purple chipmunks\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptAllIntegersAsStrings() throws Exception {
     assertEquals("{\"0\":0}", RequestHandler.respond(new StringReader("[\"0\"]"),testLogger));
     assertEquals("{\"1\":100}", RequestHandler.respond(new StringReader("[\"1\"]"),testLogger));
     assertEquals("{\"2\":200}", RequestHandler.respond(new StringReader("[\"2\"]"),testLogger));
     assertEquals("{\"100\":10000}", RequestHandler.respond(new StringReader("[\"100\"]"),testLogger));
     assertEquals("{\"1000\":100000}", RequestHandler.respond(new StringReader("[\"1000\"]"),testLogger));
     assertEquals("{\"1000000\":100000000}", RequestHandler.respond(new StringReader("[\"1000000\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptAmountsUnderOneHundred() throws Exception {
     assertEquals("{\"ten\":1000}", RequestHandler.respond(new StringReader("[\"ten\"]"),testLogger));
     assertEquals("{\"thirteen\":1300}", RequestHandler.respond(new StringReader("[\"thirteen\"]"),testLogger));
     assertEquals("{\"twenty\":2000}", RequestHandler.respond(new StringReader("[\"twenty\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptCompoundWordsUnderOneHundred() throws Exception {
     assertEquals("{\"thirty three\":3300}", RequestHandler.respond(new StringReader("[\"thirty three\"]"),testLogger));
     assertEquals("{\"fifty seven\":5700}", RequestHandler.respond(new StringReader("[\"fifty seven\"]"),testLogger));
     assertEquals("{\"nintey nine\":9900}", RequestHandler.respond(new StringReader("[\"nintey nine\"]"),testLogger));
    }

    @Test
    public void shouldAcceptCentsAsADecimal() throws Exception {
     assertEquals("{\".42\":42}", RequestHandler.respond(new StringReader("[\".42\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptDollarsAndCentsAsADecimal() throws Exception {
     assertEquals("{\"6.76\":676}", RequestHandler.respond(new StringReader("[\"6.76\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptCentsAsAFraction() throws Exception {
     assertEquals("{\"99/100\":99}", RequestHandler.respond(new StringReader("[\"99/100\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptDollarsAndCentsAsAFraction() throws Exception {
     assertEquals("{\"nintey nine and 99/100\":9999}", RequestHandler.respond(new StringReader("[\"nintey nine and 99/100\"]"),testLogger));
    }
    
    @Test
    public void shouldAcceptCentsAsWords() throws Exception {
     assertEquals("{\"twenty two cents\":22}", RequestHandler.respond(new StringReader("[\"twenty two cents\"]"),testLogger));
    }
    
}