package edu.luc.clearing;

import java.util.Stack;

public class CheckProcessor {
private static enum ADJECTIVES {
cents (0), // 1 cents
cent (0),
cts (0),
cnts (0),
c (0),
¢ (0),
and (2), // 'and' often means the break between dollars and cents, so tread as 'dollars'
dollars (2), // 100 cents
dollers (2),
dollar (2),
d (2),
$ (2),
hundred (4), // 10000 cents
hundered (4),
hunderd (4),
hundrd (4),
thousand (5), // 100000 cents 1e5
k (5),
million (8), // 100000000 cents 1e8
mille (8),
m (8),
billion (11), // 100000000000 cents 1e11
b (11),
trillion (14); // 100000000000000 cents 1e14

private final Integer value;
ADJECTIVES(Integer myValue){
value = myValue;
}

public Integer value(){
return value;
}
}

//TODO Convert to Long
public static Long processCheck(Check theCheck) throws NumberFormatException {
if(theCheck == null || theCheck.getStringValue() == null) {
return null;
}

Stack<String> theWordList = stringToWordStack(theCheck.getStringValue());

return recurseProcessSentance(theWordList, 2);

}

private static Stack<String> stringToWordStack(String theCheck){

theCheck = handleSpecialCases(theCheck);

//Split the string into words
String[] wordList = theCheck.split("\\s");

Stack<String> theStack = new Stack<String>();
for(String word : wordList){
if(word.equals("")){

}else{
theStack.push(word);
}
}

return theStack;
}

private static String handleSpecialCases(String theCheck) {
String newString = new String(theCheck);

//TODO un-hack this
//A hack to make sure that the X/100 case is recognized as cents rather than dollars
newString = newString.replaceAll("/100", " cents ");
newString = newString.replaceAll("\\100", " cents ");

newString = newString.replaceAll(" \\+ ", " and ");
newString = newString.replaceAll(" \\& ", " and ");

newString = newString.replaceAll(" \\- ", " and ");
newString = newString.replaceAll(" \\-- ", " and ");
newString = newString.replaceAll(" \\--- ", " and ");

newString = newString.replaceAll(" \\~ ", " and ");

newString = newString.replaceAll(" \\, ", " and ");

//Remove the phrase 'sum of'
newString = newString.toLowerCase().replaceFirst("the sum of", "");
newString = newString.toLowerCase().replaceFirst("sum of", "");

return newString;
}

private static Long recurseProcessSentance(Stack<String> wordList, Integer valueMultiplier){
if(wordList.empty()){
return null;
}

String word = wordList.pop();

if(isAdjective(word)) {

Integer isAdjective = getAdjectiveValue(word);

//if the adjective is 'hundred' and it's being used in combination with another adj
//like 'two hundred thirty thousand' then add 2, rather than setting the multiplier
if((isAdjective == 4) && (valueMultiplier > 4)) {
valueMultiplier +=2;
}else{
valueMultiplier = isAdjective;
}

//remove the adjective and move on to the next word.
return recurseProcessSentance(wordList, valueMultiplier);
}

//Get the values of each word
Long wordValue = null;
Double decimalWordValue = StringToNumberParser.parse(word);
if(decimalWordValue != null){
wordValue = new Double( decimalWordValue * (java.lang.Math.pow(10,valueMultiplier)) ).longValue();
}

//Recurse
Long nextValue = recurseProcessSentance(wordList, valueMultiplier);

//One of both of the parsed values could be null. Handle each possible case so that
//we return a valid value if we have one.
if((wordValue == null) && (nextValue == null)){
return null;
}else if(wordValue == null){
return nextValue;
}else if(nextValue == null){
return wordValue;
}

//Add and return
return (wordValue + nextValue);
}

private static boolean isAdjective(String word){
if(getAdjectiveValue(word) != null){
return true;
}
return false;
}

private static Integer getAdjectiveValue(String word){
try{
return Enum.valueOf(ADJECTIVES.class, word.toLowerCase()).value();
}catch(Exception e){
return null;
}
}
}
  