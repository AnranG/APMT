package testingClass;

import java.util.ArrayList;
import testingClass.Parameter;

public class Operation {
	private String opName;
	private String opReType;
	private String visibility;
	private ArrayList<Parameter> paraList= new ArrayList<Parameter>();

	public Operation() {

	}
	public Operation(String opName,String visibility) {
  this.opName = opName;
  this.visibility = visibility;
	}


	public void setOpName(String opName) {
		this.opName = opName;
	}

	public void setOpReType(String opReType) {
		this.opReType = opReType;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public void addPara(Parameter parameter){
		paraList.add(parameter);
	}
	
public void printParaList() {
		
		for(Parameter para : paraList){
			 System.out.println(para.getParaDetail());  
		 }
	}

	public void getOpDetail() {
		System.out.println("[Operation name]: " + this.opName + "\n" + "[Operation Return Type]: " + this.opReType + "\n"
				+ "[Operation visibility]: " + this.visibility );
		for(Parameter para : paraList){
			 System.out.println(para.getParaDetail());  
		 }
	}
	

	public String getOpName() {
		return opName;
	}

	public String getOpReType() {
		return opReType;
	}

	public String getVisibility() {
		return visibility;
	}
	
	public String getParams() {
		String params = "";
		for(Parameter p : paraList){
			params = params + p.getParaDetail() + "\n";
			
			
	
		}
		return params;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opName == null) ? 0 : opName.hashCode());
		result = prime * result + ((opReType == null) ? 0 : opReType.hashCode());
		result = prime * result + ((paraList == null) ? 0 : paraList.hashCode());
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
		Operation other = (Operation) obj;
		if (opName == null) {
			if (other.opName != null)
				return false;
		} else if (!opName.equals(other.opName))
			return false;
		if (opReType == null) {
			if (other.opReType != null)
				return false;
		} else if (!opReType.equals(other.opReType))
			return false;
		if (paraList == null) {
			if (other.paraList != null)
				return false;
		} else if (!paraList.equals(other.paraList))
			return false;
		if (visibility == null) {
			if (other.visibility != null)
				return false;
		} else if (!visibility.equals(other.visibility))
			return false;
		return true;
	}

}
