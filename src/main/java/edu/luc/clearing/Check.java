package edu.luc.clearing;

public class Check {
private String stringValue;
//TODO convert whole check system to LONG
private Long intValue;

public Check(){
stringValue = null;
intValue = null;
}

public Check(String value){
stringValue = value;
intValue = null;
}

public Check(String value, Long number){
stringValue = value;
intValue = number;
}

/*
* I've decided that the Check object should just be a data container. It will not have any logic.
*/

public void setStringValue(String value){
stringValue = value;
}

public String getStringValue() {
return stringValue;
}

public void setNumberValue(Long value){
intValue = value;
}

public Long getNumberValue(){
return intValue;
}

}