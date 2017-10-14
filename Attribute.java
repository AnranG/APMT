package testingClass;

import java.util.ArrayList;

public class Attribute {
	private String name;
private ArrayList<String> attrName = new ArrayList<String>();
private String attrType;
private String visibility;



public Attribute(){
	
}
public void setName(String name){
	this.name = name;
	
}



public void setAttrName(String name){
	this.attrName.add(name);
	
}

public void setAttrVis(String visibility){
	this.visibility = visibility;
	
}

public void setAttrType(String attrType) {
	this.attrType = attrType;
}

public String getName() {
	return name;
}


public ArrayList<String> getAttrName() {
	return attrName;
}

public String getAttrType() {
	return attrType;
}

public String getVisibility() {
	return visibility;
}
public String getAttrDetail () {
	String names = "";
	for(String n:attrName){
		names = names + n + "\n";
	}
	return "[Attribute name]: "+this.attrName + "\n[Possible Attribute name]"+ names +"\n" + "[Attribute Type]: " + this.attrType + "\n" + "[Attribute visibility]: " + this.visibility + "\n" ;
}

//@Override
//public boolean equals(Object o){
//	 if(o instanceof Attribute){
//		 System.out.println(name);
//		 System.out.println(((Attribute)o).getName());
//		 
//		   if(((Attribute)o).getAttrName().equals(name)){
//			   return true;
//		   }
//	 }else{
//		 
//    return false;
//	 }
//	 return true;
//}
//
//@Override
//public int hashCode()
//{
//	int ascii = toASCII(name);
//return ascii;
//}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((attrName == null) ? 0 : attrName.hashCode());
	result = prime * result + ((attrType == null) ? 0 : attrType.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((visibility == null) ? 0 : visibility.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Attribute other = (Attribute) obj;
	if (attrName == null) {
		if (other.attrName != null)
			return false;
	} else if (!attrName.equals(other.attrName))
		return false;
	if (attrType == null) {
		if (other.attrType != null)
			return false;
	} else if (!attrType.equals(other.attrType))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (visibility == null) {
		if (other.visibility != null)
			return false;
	} else if (!visibility.equals(other.visibility))
		return false;
	return true;
}


}
