package edu.luc.clearing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
* Depends On:
*
* Check
* StringToNumberParser
*/

public class TEST_CheckProcessor {
Check testCheck;

@Before
public void setUp() {
testCheck = new Check();
}

@After
public void tearDown() {
testCheck = null;
}

/*
* UNIT Tests
*/

@Test
public void shouldReturnNullForNullInput() {
assertNull(CheckProcessor.processCheck(null));
}

@Test
public void shouldReturnNullForBlankCheck() {
assertNull(CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptSingleWordValues() {
testCheck.setStringValue("one");
assertEquals(new Long(100), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptAdjectivesInAllCaps() {
testCheck.setStringValue("one THOUSAND dollars");
assertEquals(new Long(100000), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptCentAsCents() {
testCheck.setStringValue("one cent");
assertEquals(new Long(1), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptMisspelledAdjectives() {
testCheck.setStringValue("one hundered");
assertEquals(new Long(10000), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one hundred");
assertEquals(new Long(10000), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one hundrd");
assertEquals(new Long(10000), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one mille");
assertEquals(new Long(100000000), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptSymbolsAndAbbreviationsAsAdjectives() {
testCheck.setStringValue("one $");
assertEquals(new Long(100), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one ¢");
assertEquals(new Long(1), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one d");
assertEquals(new Long(100), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one c");
assertEquals(new Long(1), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldIgnoreCheckWhenAdjectivesAreInWrongOrder() {
testCheck.setStringValue("one thousand and 0 dollars");
assertEquals(new Long(100000), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptPlusSymbolForAndAdjective() {
testCheck.setStringValue("one thousand + 0 dollars");
assertEquals(new Long(100000), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptAmpersandForAndAdjective() {
testCheck.setStringValue("one thousand & 1 dollars");
assertEquals(new Long(100100), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptCTSForCents() {
testCheck.setStringValue("one thousand & 1 cts");
assertEquals(new Long(100001), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptTildeAndDashAsDollarsCentsBreak() {
testCheck.setStringValue("one thousand - one cts");
assertEquals(new Long(100001), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("one thousand ~ one cts");
assertEquals(new Long(100001), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptMultipleDashesAsDollarsCentsBreak() {
testCheck.setStringValue("one thousand --- one cts");
assertEquals(new Long(100001), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldAcceptCommaAsDollarsCentsBreak() {
testCheck.setStringValue("one thousand , one cts");
assertEquals(new Long(100001), CheckProcessor.processCheck(testCheck));
}

@Test
public void shouldIgnorePhraseSumOf(){
testCheck.setStringValue("The sum of twenty dollars and thirty c");
assertEquals(new Long(2030), CheckProcessor.processCheck(testCheck));

testCheck.setStringValue("sum of twenty dollars and thirty c");
assertEquals(new Long(2030), CheckProcessor.processCheck(testCheck));


}
}

