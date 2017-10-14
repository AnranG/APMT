package testingClass;


import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.NamedNodeMap;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
import java.util.ArrayList;

import testingClass.MClass;
import testingClass.Attribute;
import testingClass.Diagram;
import testingClass.Relationship;

public class DOMParser {
	
	public String filePath;


	public DOMParser(String filePath) {
	this.filePath = filePath;
}

	String className = "";

	// attribute
	String attrName = "";
	String attrType = "";
	String attrVis = "";

	// operation
	String opName = "";
	String opReType = "";
	String opVis = "";
	String opReTypeID = "";

	// operationï¼šparameter
	String paraTypeID = "";
	String paraName = "";
	String paraType = "";

	// relationship
	String relationName = "";
	String relationType = "";
	String reEndName = "";
	int multi = 0;
	String classInRe = "";

	// ClassID for relationship type
	String ID4Type = "";
	Diagram diagram = new Diagram();

	public ArrayList<MClass> getClassInfo() {
		// DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			
			 System.out.println("!DOM!"+this.filePath);
			
			Document document = db.parse(this.filePath);

			// get all the tag start with UML:Class
			NodeList mClassBook = document.getElementsByTagName("UML:Class");

			// for each tag
			for (int i = 0; i < mClassBook.getLength(); i++) {

				// get all the attributes of the class
				NamedNodeMap classAttrs = mClassBook.item(i).getAttributes();
				
				// Create a MClass
				MClass markClass = new MClass();
				
				// for each attribute
				for (int j = 0; j < classAttrs.getLength(); j++) {
					

					Node classAttr = classAttrs.item(j);

					if (classAttr.getNodeName() == "name") {
						if (!classAttr.getNodeValue().isEmpty()) {

							className = classAttr.getNodeValue();
							System.out.println(className);
							
							String classNames[] = className.split("/");
							for (int q = 0; q < classNames.length; q++) {
								//System.out.println(classNames[q]);
								markClass.setClassName(classNames[q]);
							}
							

							// class constructor

							// get name and value
							// System.out.println("UML:Class " +
							// classAttr.getNodeName() + ": " + className);

							// Store the class name
							markClass.setName(className);
						} else {

							// XML code for relationship will also have
							// UML:Class (with no class name)
							break;
						}

					}
					if (classAttr.getNodeName() == "xmi.id") {
						// System.out.println("UML:Class " +
						// classAttr.getNodeName() + ": " +
						// classAttr.getNodeValue());
						markClass.setClassID(classAttr.getNodeValue());
					}

				}

				// UML:Attribute

				NodeList attrBook = ((Element) mClassBook.item(i)).getElementsByTagName("UML:Attribute");

				// System.out.println("Class " + className + " has " +
				// attrBook.getLength() + " attribute(s) in total\n ");

				for (int a = 0; a < attrBook.getLength(); a++) {
					Attribute attribute = new Attribute();
					// get all the attributes of the Attribute
					NamedNodeMap attrAttrs = attrBook.item(a).getAttributes();
					// System.out.println("ATTRIBUTE(" + (a + 1) + ")has " +
					// attrAttrs.getLength() + " attribute(s)");

					// for each attribute of ATTRIBUTEss
					for (int b = 0; b < attrAttrs.getLength(); b++) {

						// get current node
						Node attrAttr = attrAttrs.item(b);

						// attribute name
						if (attrAttr.getNodeName() == "name") {
							
							 System.out.println(
							 "UML:Attribute " + attrAttr.getNodeName() + ": "
							 + attrAttr.getNodeValue());

							attrName = attrAttr.getNodeValue();
							attribute.setName(attrName);
							
							String attrNames[] = attrName.split("/");
							for (int q = 0; q < attrNames.length; q++) {
								
								attribute.setAttrName(attrNames[q]);
							}

							// attribute type
							if(((Element) attrBook.item(a)).getElementsByTagName("UML:DataType").getLength() != 0){
							Node aType = ((Element) attrBook.item(a)).getElementsByTagName("UML:DataType").item(0)
									.getAttributes().item(0);
							String attrTypeID = aType.getNodeValue();

							// get the attribute type via customized function
							// System.out.println("*** Data type is " +
							// typeChecker(attrTypeID));
							attrType = typeChecker(attrTypeID);
							attribute.setAttrType(attrType);
							}
						}

						// visibility

						if (attrAttr.getNodeName() == "visibility") {
							
							// System.out.println("*** " +
							// attrAttr.getNodeName() + ": " +
							// attrAttr.getNodeValue() + "\n");
							attrVis = attrAttr.getNodeValue();
							attribute.setAttrVis(attrVis);
						}

					}
					//System.out.println(attribute.getAttrDetail());

					markClass.addAttr(attribute);

				}
				// markClass.printAttrList();

				// UML:Operation

				NodeList opBook = ((Element) mClassBook.item(i)).getElementsByTagName("UML:Operation");

				for (int a = 0; a < opBook.getLength(); a++) {

					// get all the attributes of the Operation
					NamedNodeMap opAttrs = opBook.item(a).getAttributes();

					// for each attributes of OPERATION
					for (int b = 0; b < opAttrs.getLength(); b++) {

						// current node
						Node opAttr = opAttrs.item(b);

						// Operation name
						if (opAttr.getNodeName() == "name") {
							
						//	System.out.println("UML:Operation " + opAttr.getNodeName() + ": " + opAttr.getNodeValue());
							opName = opAttr.getNodeValue();
						}
						// visibility

						if (opAttr.getNodeName() == "visibility") {
							// get node name and node value
							// System.out.println("*** " + opAttr.getNodeName()
							// + ": " + opAttr.getNodeValue());
							opVis = opAttr.getNodeValue();

						}

					}//for loop-end(b)
					Operation operation = new Operation(opName, opVis);
					markClass.addOp(operation);
					

					// UML:Parameter
					NodeList paraBook = ((Element) opBook.item(a)).getElementsByTagName("UML:Parameter");
					// System.out.println("Operation " + " has " +
					// paraBook.getLength() + " parameter(s) in total");
					for (int c = 0; c < paraBook.getLength(); c++) {

						// get all the attributes of the Parameter
						NamedNodeMap paraAttrs = paraBook.item(c).getAttributes();

						// for each attribute of parameter
						for (int d = 0; d < paraAttrs.getLength(); d++) {

							Node paraAttr = paraAttrs.item(d);

							if (paraAttr.getNodeName() == "name") {
								
								// get the parameter name
								if (!paraAttr.getNodeValue().equals("return")) {
								
									paraName = paraAttr.getNodeValue();
                                   
									// for return type other than bool
									if (((Element) paraBook.item(c)).getElementsByTagName("UML:DataType")
											.getLength() != 0) {
										NodeList dataTypeBook = ((Element) paraBook.item(c))
												.getElementsByTagName("UML:DataType");
										for (int e = 0; e < dataTypeBook.getLength(); e++) {
											Node dataTypeAttr = dataTypeBook.item(e).getAttributes().item(0);

											paraTypeID = dataTypeAttr.getNodeValue();

										//	System.out.println("!!! Parameter type is :" + typeChecker(paraTypeID));

											paraType = typeChecker(paraTypeID);

											Parameter parameter = new Parameter(paraName, paraType);
											//System.out.println(parameter.getParaDetail());
											operation.addPara(parameter);
											
										}
										
									}
									
									
									

								}else{
									if(((Element) paraBook.item(c))
											.getElementsByTagName("UML:DataType").getLength() != 0){
									Node dataTypeAttr = ((Element) paraBook.item(c))
									.getElementsByTagName("UML:DataType").item(0).getAttributes().item(0);
									opReTypeID = dataTypeAttr.getNodeValue();

									//System.out.println("!!! Return type is :" + typeChecker(opReTypeID));

									opReType = typeChecker(opReTypeID);
									operation.setOpReType(opReType);
								}
									if(((Element) paraBook.item(c))
											.getElementsByTagName("UML:Enumeration").getLength() != 0){
									Node dataTypeAttr = ((Element) paraBook.item(c))
									.getElementsByTagName("UML:Enumeration").item(0).getAttributes().item(0);
									opReTypeID = dataTypeAttr.getNodeValue();

									//System.out.println("!!! Return type is :" + typeChecker(opReTypeID));

									opReType = typeChecker(opReTypeID);
									operation.setOpReType(opReType);
								}
									
									
								
								}
							}// if name
							
							
							
						//operation.getOpDetail();	
						} // for loop-end(d)
						
						// System.out.println("@@@");
					

						

					}
				}
				
		//	markClass.printClassDetail();
				 diagram.addClass(markClass);
			markClass.validateAttr();
			}
			
			diagram.validateClass();
			 System.out.println("!!!!!!!!");
			 System.out.println( diagram.classList.size());
			diagram.printClassInfo();

			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return diagram.classList;
	}

	public ArrayList<Relationship> getRelationInfo() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			// é€šè¿‡DocumentBuilderå¯¹è±¡çš„parseæ–¹æ³•åŠ è½½books.xmlæ–‡ä»¶åˆ°å½“å‰�é¡¹ç›®ä¸‹
			Document document = db.parse(this.filePath);

			// Parse relationship
			// 1.Association: (general)Association & Composition & Aggregation
			NodeList assoRLBook = document.getElementsByTagName("UML:Association");

			for (int i = 0; i < assoRLBook.getLength(); i++) {
				Relationship relation = new Relationship();
				// get all the attributes of the association
				NamedNodeMap assoAttrs = assoRLBook.item(i).getAttributes();

				for (int j = 0; j < assoAttrs.getLength(); j++) {

					Node assoAttr = assoAttrs.item(j);

					// Relationship name
					if (assoAttr.getNodeName() == "name") {
						if (!assoAttr.getNodeValue().isEmpty()) {
							// System.out.println("Relationship name is: " +
							// assoAttr.getNodeValue());
							relation.setRelationName(assoAttr.getNodeValue());
						}

						NodeList assoDTBook = ((Element) assoRLBook.item(i)).getElementsByTagName("UML:AssociationEnd");
						for (int a = 0; a < assoDTBook.getLength(); a++) {

							NamedNodeMap assoDTAttrs = assoDTBook.item(a).getAttributes();

							for (int b = 0; b < assoDTAttrs.getLength(); b++) {

								Node assoDTAttr = assoDTAttrs.item(b);

								// get association end name
								if (assoDTAttr.getNodeName() == "name") {
									// System.out
									// .println("!!!!!!!!!Association end name
									// is: " + assoDTAttr.getNodeValue());
									relation.setReEndName(assoDTAttr.getNodeValue());
								}
								// get the detailed Relationship type
								// aggregation/composition/association
								if (assoDTAttr.getNodeName() == "aggregation") {

									if (!assoDTAttr.getNodeValue().equals("none")) {
										// System.out.println("Relationship type
										// is: " + assoDTAttr.getNodeValue());
										relation.setRelationType(assoDTAttr.getNodeValue());

										// System.out.println("12345" +
										// relation.getRelationType());
										// both name is "none"
									} else if (assoDTBook.item(a + 1) != null) {
										if (assoDTBook.item(a + 1).getAttributes().item(b).getNodeValue()
												.equals("none")) {
											relation.setRelationType("association");
										}
									}

								}

							}
						} // for loop-end(a)

						// relationship participant class
						NodeList assoClassBook = ((Element) assoRLBook.item(i)).getElementsByTagName("UML:Class");

						for (int c = 0; c < assoClassBook.getLength(); c++) {
							Node assoClassAttr = assoClassBook.item(c).getAttributes().item(0);

							// System.out.println(assoClassAttr.getNodeValue());

						// assign the classID to ID4Type
							ID4Type = assoClassAttr.getNodeValue();

							// System.out.println(classIdentifier(ID4Type,
							 //diagram));
							relation.setClassPair(classIdentifier(ID4Type, diagram));

						} // for loop - end(c)

						// if the relationship has multiplicity
						if (((Element) assoRLBook.item(i)).getElementsByTagName("UML:MultiplicityRange")
								.getLength() != 0) {
							NodeList multiBook = ((Element) assoRLBook.item(i))
									.getElementsByTagName("UML:MultiplicityRange");
							for (int d = 0; d < multiBook.getLength(); d++) {

								NamedNodeMap multiAttrs = multiBook.item(d).getAttributes();

								for (int e = 0; e < multiAttrs.getLength(); e++) {

									Node multiAttr = multiAttrs.item(e);

									// multiplicity lower bond
									if (multiAttr.getNodeName() == "lower") {
//										System.out.println("The lower bond is: " + multiAttr.getNodeValue());
										relation.setMulti(multiAttr.getNodeValue());

									}
									// multiplicity lower bond
									if (multiAttr.getNodeName() == "upper") {

									//	 System.out.println("The upper bondis: " + multiAttr.getNodeValue());
										relation.setMulti(multiAttr.getNodeValue());
									}
								}
							} // for loop-end(d)

						} // if multi not null

					} // if name - end

				}

				diagram.addRelation(relation);
			} // for loop -end(i)

			// diagram.printRelationInfo();

			// 2.Abstraction: realization
			NodeList absRLBook = document.getElementsByTagName("UML:Abstraction");
			for (int i = 0; i < absRLBook.getLength(); i++) {
				Relationship relation = new Relationship();
				relation.setRelationType("realization");
				// System.out.println(absRLBook.getLength());

				// find participant classes in a realization
				NodeList realizeClassBook = ((Element) absRLBook.item(i)).getElementsByTagName("UML:Class");
				for (int j = 0; j < realizeClassBook.getLength(); j++) {

					ID4Type = realizeClassBook.item(j).getAttributes().item(0).getNodeValue();
					// System.out.println("Relationship type is: realization");
					// access to classList and find corresponding class by ID

					System.out.println(classIdentifier(ID4Type, diagram));
					relation.setClassPair(classIdentifier(ID4Type, diagram));

				}
				diagram.addRelation(relation);
			} // for loop-end(i)

			// 3.Dependency
			NodeList depRLBook = document.getElementsByTagName("UML:Dependency");
			for (int i = 0; i < depRLBook.getLength(); i++) {
				
				Relationship relation = new Relationship();
				relation.setRelationType("dependency");
				
				NamedNodeMap depAttrs = depRLBook.item(i).getAttributes();

				for (int j = 0; j < depAttrs.getLength(); j++) {

					Node depAttr = depAttrs.item(j);

					// Relationship name
					if (depAttr.getNodeName() == "name") {
						// System.out.println("Relationship type is:
						// Dependency");
						// System.out.println("Relationship name is: " +
						// depAttr.getNodeValue());
						NodeList depClassBook = ((Element) depRLBook.item(i)).getElementsByTagName("UML:Class");
						for (int a = 0; a < depClassBook.getLength(); a++) {

							ID4Type = depClassBook.item(a).getAttributes().item(0).getNodeValue();
							relation.setClassPair(classIdentifier(ID4Type, diagram));
							// access to classList and find corresponding class
							// by ID

							// System.out.println(classIdentifier(ID4Type,
							// diagram));

						}

					}
				}
				diagram.addRelation(relation);
			}

			// 4.Generalization
			NodeList genRLBook = document.getElementsByTagName("UML:Generalization");

			for (int i = 0; i < genRLBook.getLength(); i++) {
				Relationship relation = new Relationship();
				relation.setRelationType("generalization");

				// get all the attributes of the Generalization
				NamedNodeMap genAttrs = genRLBook.item(i).getAttributes();

				for (int j = 0; j < genAttrs.getLength(); j++) {

				//	Node genAttr = genAttrs.item(j);

				
							
							
							
							NodeList genClassBook = ((Element) genRLBook.item(i)).getElementsByTagName("UML:Class");
							for (int a = 0; a < genClassBook.getLength(); a++) {

								ID4Type = genClassBook.item(a).getAttributes().item(0).getNodeValue();
								relation.setClassPair(classIdentifier(ID4Type, diagram));
						

						


					} 

				}
			
				diagram.addRelation(relation);
			} // for loop -end(i)
			
			diagram.validateRelation();
//diagram.printRelationInfo();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return diagram.reList;

	}

	public static String typeChecker(String typeID) {

		String typeIDRep = typeID.substring(typeID.length() - 3);

		switch (typeIDRep) {

		case "87C":
			return "Int";

		case "87D":
			return "UnlimitedInteger";

		case "87E":
			return "String";

		case "880":
			return "Bool";

		default:
			return "Void";
		}
	}

	public static String classIdentifier(String ID4Type, Diagram diagram) {

		String classFound = "";
		for (MClass m : diagram.getClassList()) {

			if (m.getName() != null) {
				if (m.getClassID().equals(ID4Type)) {

					System.out.println("The Class in relationship is:[ " +
				 m.className + "]");
					classFound = m.getName();
					break;
				}
			}
		}
		return classFound;
	}
}
