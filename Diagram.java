package testingClass;

import testingClass.MClass;


import testingClass.Relationship;


import java.util.ArrayList;


public class Diagram {

	public ArrayList<MClass> classList = new ArrayList<MClass>();
	public ArrayList<Relationship> reList = new ArrayList<Relationship>();

	public Diagram() {

	}

	public void validateClass() {
		for (int i = classList.size() - 1; i >= 0; i--) {
			System.out.println(classList.get(i).getName());
			if (classList.get(i).getName() == "" || classList.get(i).getName().equals("Stringc")  ||classList.get(i).getName().equals("Integerc")  || classList.get(i).getName().equals("Integercc") ) {
				classList.remove(i);

			}
		}

	}

	public void validateRelation() {
		for (int i = reList.size() - 1; i >= 0; i--) {

			if (reList.get(i).getClassPair()[0] == null && reList.get(i).getClassPair()[1] == null) {
				reList.remove(i);

			}
		}

	}

	public void addClass(MClass mClass) {
		classList.add(mClass);
	}

	public void printClassInfo() {
		int counter = 0;
		for (MClass mClass : classList) {
			counter ++;
			mClass.printClassDetail();

		}
		System.out.println(counter);
	}

	public void printRelationInfo() {
		System.out.println("let's print Relation Info~~~");
		for (Relationship relation : reList) {
			relation.printRelationDetail();

		}
	}

	public void addRelation(Relationship relationship) {
		reList.add(relationship);
	}
	public ArrayList<MClass> getClassList(){
		return this.classList;
	}
}



