package edu.luc.clearing;

import static org.junit.Assert.*;

import org.junit.Test;

/*
* No Dependencies
*/

public class TEST_StringToNumberParser {

    @Test
    public void shouldAcceptWords() {
     assertEquals(1, StringToNumberParser.parse(new String("one")).intValue());
    }
    
    @Test
    public void shouldAcceptZeroDollarsAsWord() {
     assertEquals(0, StringToNumberParser.parse(new String("0")).intValue());
    }

    @Test
    public void shouldAcceptWordsBelowTwentyOne() {
     assertEquals(13, StringToNumberParser.parse(new String("thirteen")).intValue());
    }
    
    @Test
    public void shouldIgnoreWordCase() {
     assertEquals(16, StringToNumberParser.parse(new String("SiXtEEn")).intValue());
    }
    
    @Test(expected=NumberFormatException.class)
    public void shouldThrowExceptionForInvalidWords() {
     StringToNumberParser.parse(new String("whambat"));
    }

    @Test
    public void shouldAcceptCentsAsAFraction() {
     assertEquals(13, StringToNumberParser.parse(new String("13/100")).intValue());
    }
    
    @Test
    public void shouldAcceptCentsAsAFractionWithoutDenominator() {
     assertEquals(13, StringToNumberParser.parse("13/").intValue());
    }
    
    @Test
    public void shouldAcceptZeroCentsAsAFraction() {
     assertEquals(0, StringToNumberParser.parse(new String("0/100")).intValue());
    }
    
    @Test
    public void shouldAcceptZeroWithExtraCharactersAsAFraction() {
     assertEquals(0, StringToNumberParser.parse(new String("00/100")).intValue());
    }
    
    @Test
    public void shouldAcceptCentsAsDecimals() {
     assertEquals(new Double(".42"), StringToNumberParser.parse(new String(".42")));
    }
    
    @Test
    public void shouldAcceptDollarsWithCents() {
     assertEquals(new Double("7.25"), StringToNumberParser.parse(new String("7.25")));
    }
    
    @Test(expected=NumberFormatException.class)
    public void shouldThrowExceptionForInvalidDoubles() {
     StringToNumberParser.parse(new String("3.1415926"));
    }
    
    @Test
    public void shouldAcceptZeroDollarsAsNumber() {
     assertEquals(0, StringToNumberParser.parse(new String("0")).intValue());
    }
    
    @Test
    public void shouldAcceptZeroDollarsWithCents() {
     assertEquals(0, StringToNumberParser.parse(new String("0.00")).intValue());
    }
    
    @Test
    public void shouldAcceptNumbersLargerThanInt() {
     Double fiveBillion = Double.parseDouble("5000000000");
     assertEquals(fiveBillion, StringToNumberParser.parse("5000000000"));
    }
    
    @Test
    public void shouldAcceptNoAsMeaningZero() {
     /*
* I'm not sure this should be the case, or if 'no' should be a special test case in ProcessCheck
*/
     assertEquals(0, StringToNumberParser.parse(new String("no")).intValue());
    }
    
    @Test
    public void shouldAcceptNumbersWithDollarsAndCentsSymbols() {
     assertEquals(40, StringToNumberParser.parse(new String("$40")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("¢40")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("40¢")).intValue());
    }
    
    @Test
    public void shouldAcceptGrossMisspellingsOfWordsLessThanTwenty() {
     assertEquals(3, StringToNumberParser.parse(new String("thre")).intValue());
     assertEquals(7, StringToNumberParser.parse(new String("sevn")).intValue());
     assertEquals(11, StringToNumberParser.parse(new String("elevn")).intValue());
     assertEquals(12, StringToNumberParser.parse(new String("twelv")).intValue());
     assertEquals(13, StringToNumberParser.parse(new String("therteen")).intValue());
     assertEquals(14, StringToNumberParser.parse(new String("forteen")).intValue());
     assertEquals(14, StringToNumberParser.parse(new String("fortteen")).intValue());
     assertEquals(14, StringToNumberParser.parse(new String("fourtteen")).intValue());
     assertEquals(15, StringToNumberParser.parse(new String("fiftteen")).intValue());
     assertEquals(16, StringToNumberParser.parse(new String("sxteen")).intValue());
     assertEquals(16, StringToNumberParser.parse(new String("sxtteen")).intValue());
     assertEquals(16, StringToNumberParser.parse(new String("sixtteen")).intValue());
     assertEquals(17, StringToNumberParser.parse(new String("svnteen")).intValue());
     assertEquals(17, StringToNumberParser.parse(new String("sevnteen")).intValue());
     assertEquals(17, StringToNumberParser.parse(new String("sevntteen")).intValue());
     assertEquals(17, StringToNumberParser.parse(new String("svntteen")).intValue());
     assertEquals(18, StringToNumberParser.parse(new String("eihteen")).intValue());
     assertEquals(18, StringToNumberParser.parse(new String("eightteen")).intValue());
     assertEquals(19, StringToNumberParser.parse(new String("ninteen")).intValue());
     assertEquals(19, StringToNumberParser.parse(new String("nintteen")).intValue());
     assertEquals(19, StringToNumberParser.parse(new String("ninetteen")).intValue());
    }
    
    @Test
    public void shouldAcceptGrossMisspellingsOfWordsOverTwenty() {
     assertEquals(30, StringToNumberParser.parse(new String("3ty")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("4ty")).intValue());
     assertEquals(50, StringToNumberParser.parse(new String("5ty")).intValue());
     assertEquals(60, StringToNumberParser.parse(new String("6ty")).intValue());
     assertEquals(70, StringToNumberParser.parse(new String("7ty")).intValue());
     assertEquals(80, StringToNumberParser.parse(new String("8ty")).intValue());
     assertEquals(90, StringToNumberParser.parse(new String("9ty")).intValue());
    
     assertEquals(30, StringToNumberParser.parse(new String("thrty")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("therty")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("thirtey")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("thertey")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("thrtee")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("thertee")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("thirtee")).intValue());
     assertEquals(30, StringToNumberParser.parse(new String("thertee")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("fourty")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("fortey")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("forety")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("fourtey")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("fortee")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("fourtee")).intValue());
     assertEquals(40, StringToNumberParser.parse(new String("foretee")).intValue());
     assertEquals(50, StringToNumberParser.parse(new String("fiftey")).intValue());
     assertEquals(50, StringToNumberParser.parse(new String("fiftee")).intValue());
     assertEquals(50, StringToNumberParser.parse(new String("fity")).intValue());
     assertEquals(50, StringToNumberParser.parse(new String("fitee")).intValue());
     assertEquals(50, StringToNumberParser.parse(new String("fitey")).intValue());
     assertEquals(60, StringToNumberParser.parse(new String("sxty")).intValue());
     assertEquals(60, StringToNumberParser.parse(new String("sxtey")).intValue());
     assertEquals(60, StringToNumberParser.parse(new String("sxtee")).intValue());
     assertEquals(70, StringToNumberParser.parse(new String("sevnty")).intValue());
     assertEquals(70, StringToNumberParser.parse(new String("sevntey")).intValue());
     assertEquals(70, StringToNumberParser.parse(new String("sevntee")).intValue());
     assertEquals(70, StringToNumberParser.parse(new String("svnty")).intValue());
     assertEquals(80, StringToNumberParser.parse(new String("eitey")).intValue());
     assertEquals(80, StringToNumberParser.parse(new String("eihty")).intValue());
     assertEquals(80, StringToNumberParser.parse(new String("eihtey")).intValue());
     assertEquals(80, StringToNumberParser.parse(new String("eihtee")).intValue());
     assertEquals(90, StringToNumberParser.parse(new String("nintee")).intValue());
     assertEquals(90, StringToNumberParser.parse(new String("nintey")).intValue());
     assertEquals(90, StringToNumberParser.parse(new String("ninty")).intValue());
    }
}
    
