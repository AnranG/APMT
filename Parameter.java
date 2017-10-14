package testingClass;

public class Parameter {
private String paraName;
private String paraType;

public Parameter(){
	
}
public Parameter(String paraName,String paraType){
	this.paraName = paraName;
	this.paraType = paraType;
	
}

public String getParaName() {
	return paraName;
}

public String getParaType() {
	return paraType;
}

public void setParaName(String paraName) {
	this.paraName = paraName;
}

public void setParaType(String paraType) {
	this.paraType = paraType;
}

public String getParaDetail () {
	return "[Parameter name]: "+this.paraName + "\n" + "[Parameter Type]: " + this.paraType ;
}



}


