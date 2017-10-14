package testingClass;

import java.util.ArrayList;
import testingClass.Attribute;
import testingClass.Operation;;

public class MClass {
  public String name = "";
  public ArrayList<String> className = new ArrayList<String>();

  private String classID;
  private ArrayList<Attribute> attrList = new ArrayList<Attribute>();
  private ArrayList<Operation> opList = new ArrayList<Operation>();
  
  
	public MClass(){
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setClassName(String name) {
		this.className.add(name);
	}
	
	public void setClassID(String classID) {
	this.classID = classID;
}


	public void setAttrList(ArrayList<Attribute> attrList) {
		this.attrList = attrList;
	}
	
	public void setOpList(ArrayList<Operation> opList) {
		this.opList = opList;
	}


	

	public String getName() {
		return name;
	}
	
	public String getClassID() {
		return classID;
	}
	
	public void addAttr(Attribute attr) {
		
		attrList.add(attr);
		
	}
	
	public void printAttrList() {
		
		for(Attribute attr : attrList){
			 System.out.println(attr.getAttrDetail());  
		 }
	}

	public void addOp(Operation op) {
		
		opList.add(op);
	}
	
	public void printOpList() {
		
		for(Operation op : opList){
			 op.getOpDetail();
		 }
	}
public void printClassDetail() {
		
		System.out.println("{Class Name}"+this.name);
		for(String n: className){
		System.out.println("{Class Possible Names}"+n);
		}
		System.out.println("{Class ID}"+this.classID);
		for(Attribute attr : attrList){
			 System.out.println(attr.getAttrDetail());  
		 }
		
		 System.out.println("@@@@");  
		for(Operation op : opList){
			 op.getOpDetail();
		 }
	}
	
public ArrayList<Attribute> getAttrList(){
	return this.attrList;
}

public ArrayList<Operation> getOpList(){
	return this.opList;
}

public void validateAttr(){
	for(int i = attrList.size()-1 ; i >= 0 ;i--){
		//System.out.println("current attr is " + attrList.get(i).getName());
		if(attrList.get(i).getName()==null){
			this.attrList.remove(i);
			
		}
	}
}


}
