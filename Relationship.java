package testingClass;

import java.util.ArrayList;
import java.util.Arrays;

public class Relationship {
	
	public String[] classPair = new String[2];
	public String relationName = "";
	public String relationType = "";
	public String[] reEndNames = new String[2];

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(classPair);
		result = prime * result + ((multiList == null) ? 0 : multiList.hashCode());
		result = prime * result + Arrays.hashCode(reEndNames);
		result = prime * result + ((relationName == null) ? 0 : relationName.hashCode());
		result = prime * result + ((relationType == null) ? 0 : relationType.hashCode());
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
		Relationship other = (Relationship) obj;
		if (!Arrays.equals(classPair, other.classPair))
			return false;
		if (multiList == null) {
			if (other.multiList != null)
				return false;
		} else if (!multiList.equals(other.multiList))
			return false;
		if (!Arrays.equals(reEndNames, other.reEndNames))
			return false;
		if (relationName == null) {
			if (other.relationName != null)
				return false;
		} else if (!relationName.equals(other.relationName))
			return false;
		if (relationType == null) {
			if (other.relationType != null)
				return false;
		} else if (!relationType.equals(other.relationType))
			return false;
		return true;
	}

	public ArrayList<String> multiList = new ArrayList<String>();

	public Relationship(){
		
	}
	
	public String getClassInRelation() {
		
		return "[" + classPair[0] +","+ classPair[1]+"]";
	}


	public String[] getClassPair() {
		return this.classPair;
	}


	public String getRelationName() {
		return relationName;
	}

	public String getRelationType() {
		return relationType;
	}

	public String getReEndNames() {
		
		return "[" + reEndNames[0] +","+ reEndNames[1]+"]";
	}


	public String getMulti() {
		String multi = "";
		for(int i = 0; i < this.multiList.size(); i++){
			multi = multi + multiList.get(i);
		}
		return multi;
	}


	




public void setClassPair(String className) {
	System.out.println(classPair[0]);
	if(this.classPair[0] == null){
		classPair[0] = className;
	}else{
		classPair[1] = className;
	}
}
public void setClass(int i,String className) {
	if(i<=1){
	this.classPair[i] = className;
	}
	
}




public void setRelationName(String relationName) {
	this.relationName = relationName;
}

public void setRelationType(String relationType) {
	this.relationType = relationType;
}


public void setReEndName(String reEndName) {
	System.out.println(this.reEndNames[0]);
	if(this.reEndNames[0] == null){
		reEndNames[0] = reEndName;
	}else{
		reEndNames[1] = reEndName;
	}
}

public void setMulti(String multi){
	multiList.add(multi);
}

public void printRelationDetail(){
	System.out.println("{Relation Name}"+this.relationName);
	System.out.println("{Relation Type}"+this.relationType);
	System.out.println("{Classes:}" + this.classPair[0] + this.classPair[1]);
	System.out.println("{Relationship end names:}" + this.reEndNames[0] + this.reEndNames[1]);
	
	for(String multi : multiList){
		 System.out.println(multi);  
	 }
	
	
}
}


