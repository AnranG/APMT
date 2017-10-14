package testingClass;

public class Result {
	public String name = "";

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String stuID = "";
	public String typo = "";
	public String explanation = "";

	public String getTypo() {
		return typo;
	}

	public void setTypo(String typo) {
		this.typo = typo;
	}

	// for general class
	public String classFeedback = "";
	public float classRate = 0;
	public float classDeRate = 0;
	public String classExtra = "";
	public int classMiss = 0;

	// for attribute
	public String attrFeedback = "";
	public float attrRate = 0;
	public float attrDeRate = 0;
	public String attrExtra = "";
	public int attrMiss = 0;
	public int attrDiff = 0;

	public int getAttrMiss() {
		return attrMiss;
	}

	public void setAttrMiss(int attrMiss) {
		this.attrMiss = attrMiss;
	}

	public int getAttrDiff() {
		return attrDiff;
	}

	public void setAttrDiff(int attrDiff) {
		this.attrDiff = attrDiff;
	}

	// for operation
	public String opFeedback = "";
	public float opRate = 0;
	public float opDeRate = 0;
	public String opExtra = "";
	public int opMiss = 0;
	public int opDiff = 0;

	// for relationship
	public String relationFeedback = "";
	public float relationRate = 0;
	public float relationDeRate = 0;
	public String relationExtra = "";
	public int relationMiss = 0;
	public int relationDiff = 0;

	// final mark
	public float mark = 0;

	public Result() {

	}

	public int getRelationMiss() {
		return relationMiss;
	}

	public void setRelationMiss(int relationMiss) {
		this.relationMiss = relationMiss;
	}

	public int getRelationDiff() {
		return relationDiff;
	}

	public void setRelationDiff(int relationDiff) {
		this.relationDiff = relationDiff;
	}

	public int getOpMiss() {
		return opMiss;
	}

	public void setOpMiss(int opMiss) {
		this.opMiss = opMiss;
	}

	public int getOpDiff() {
		return opDiff;
	}

	public void setOpDiff(int opDiff) {
		this.opDiff = opDiff;
	}

	public int getClassMiss() {
		return classMiss;
	}

	public void setClassMiss(int classMiss) {
		this.classMiss = classMiss;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStuID() {
		return stuID;
	}

	public void setStuID(String stuID) {
		this.stuID = stuID;
	}

	public String getClassFeedback() {
		return classFeedback;
	}

	public void setClassFeedback(String classFeedback) {
		this.classFeedback = classFeedback;
	}

	public float getClassRate() {
		return classRate;
	}

	public void setClassRate(float classRate) {
		this.classRate = classRate;
	}

	public float getClassDeRate() {
		return classDeRate;
	}

	public void setClassDeRate(float classDeRate) {
		this.classDeRate = classDeRate;
	}

	public String getAttrFeedback() {
		return attrFeedback;
	}

	public void setAttrFeedback(String attrFeedback) {
		this.attrFeedback = attrFeedback;
	}

	public float getAttrRate() {
		return attrRate;
	}

	public void setAttrRate(float attrRate) {
		this.attrRate = attrRate;
	}

	public float getAttrDeRate() {
		return attrDeRate;
	}

	public void setAttrDeRate(float attrDeRate) {
		this.attrDeRate = attrDeRate;
	}

	public String getOpFeedback() {
		return opFeedback;
	}

	public void setOpFeedback(String opFeedback) {
		this.opFeedback = opFeedback;
	}

	public float getOpRate() {
		return opRate;
	}

	public void setOpRate(float opRate) {
		this.opRate = opRate;
	}

	public float getOpDeRate() {
		return opDeRate;
	}

	public void setOpDeRate(float opDeRate) {
		this.opDeRate = opDeRate;
	}

	public String getRelationFeedback() {
		return relationFeedback;
	}

	public void setRelationFeedback(String relationFeedback) {
		this.relationFeedback = relationFeedback;
	}

	public float getRelationRate() {
		return relationRate;
	}

	public void setRelationRate(float relationRate) {
		this.relationRate = relationRate;
	}

	public float getRelationDeRate() {
		return relationDeRate;
	}

	public void setRelationDeRate(float relationDeRate) {
		this.relationDeRate = relationDeRate;
	}

	public String getClassExtra() {
		return classExtra;
	}

	public void setClassExtra(String classExtra) {
		this.classExtra = classExtra;
	}

	public String getAttrExtra() {
		return attrExtra;
	}

	public void setAttrExtra(String attrExtra) {
		this.attrExtra = attrExtra;
	}

	public String getOpExtra() {
		return opExtra;
	}

	public void setOpExtra(String opExtra) {
		this.opExtra = opExtra;
	}

	public String getRelationExtra() {
		return relationExtra;
	}

	public void setRelationExtra(String relationExtra) {
		this.relationExtra = relationExtra;
	}

	public float getMark() {
		return (float) (Math.round(mark * 100)) / 100;
	}

	public void setMark(float mark) {
		this.mark = mark;
	}

}
