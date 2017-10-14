package testingClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OpMarker {

	public String ansFilePath = "";
	public String markFilePath = "";
	public String attrName = "";
	public int opMiss = 0;
	public int opDiff = 0;

	public int getOpMiss() {
		return opMiss;
	}


	public int getOpDiff() {
		return opDiff;
	}


	public OpMarker() {

	}

	// number of match
	int opMatch = 0;

	int opDeMatch = 0;

	// match rate of existence
	float opRate = 0;

	// match rate of detail
	float opDeRate = 0;

	// total number
	int opSum = 0;

	// total class number
	int ansClassNo = 0;
	int markClassNo = 0;

	// summary feedback of incorrect feature
	String feedBack = "";

	// extra information in the student work
	String extra = "";

	public void executeMarker(ArrayList<MClass> ansClassList, ArrayList<MClass> markClassList) {

		long startTime = System.currentTimeMillis();

		// initialise match
		opMatch = 0;

		opDeMatch = 0;
		extra = "";
		opSum = 0;
		feedBack = "";
		ansClassNo = ansClassList.size();
		markClassNo = markClassList.size();

		System.out.println(ansClassNo);
		System.out.println(markClassNo);

		// check matchness of operation
		// traverse model answer class list

		for (int i = 0; i < ansClassNo; i++) {
			HashMap<Operation, Integer> opMap = new HashMap<Operation, Integer>();
			MClass mClass4Model = (MClass) ansClassList.get(i);
			System.out.println(mClass4Model.name);
			opSum = opSum + mClass4Model.getOpList().size();
			for (Operation op : mClass4Model.getOpList()) {

				System.out.println(op.getOpName());

				attrName = op.getOpName();
				opMap.put(op, 1);
			}
			Iterator<Operation> test = opMap.keySet().iterator();
			while (test.hasNext()) {
				Operation a = (Operation) test.next();
				int value = opMap.get(a);
				System.out.println(a.getOpName() + "," + value);

			}

			System.out.println("done");

			markClassNo = markClassList.size();
			System.out.println(markClassNo);

			// traverse mark class list

			for (int j = 0; j < markClassNo; j++) {
				MClass mClass4Mark = (MClass) markClassList.get(j);
				System.out.println(mClass4Mark.getName() + mClass4Model.getName());

				if (sameWith(mClass4Model.getName(), mClass4Mark.getName())) {
					System.out.println(mClass4Mark.name + mClass4Model.getName());
					System.out.println("");
					for (Operation op : mClass4Mark.getOpList()) {
						System.out.println(op.getOpName());

						Integer cc = opMap.get(op);
						System.out.println(cc);
						if (cc != null) {
							opMap.put(op, ++cc);

							continue;

						}

						// if extra class that model answer does not have, then
						// ignore
						opMap.put(op, 0);

					}

					Iterator<Operation> test1 = opMap.keySet().iterator();
					while (test1.hasNext()) {
						Operation op = (Operation) test1.next();
						int value = opMap.get(op);
						System.out.println(op.getOpName() + "," + value);

					}

					System.out.println("done");

					validateMap(opMap);
					Iterator<Operation> test2 = opMap.keySet().iterator();
					while (test2.hasNext()) {
						Operation a = (Operation) test2.next();
						int value = opMap.get(a);
						System.out.println(a.getOpName() + "," + value);

					}

					System.out.println("done");

					Iterator<Operation> iter = opMap.keySet().iterator();
					while (iter.hasNext()) {

						Operation key = (Operation) iter.next();
						System.out.println("(key,value): (" + key.getOpName() + "," + opMap.get(key));
						if (opMap.get(key) == 2) {
							// totally match
							opMatch++;
							opDeMatch++;
							System.out.println("{" + key.getOpName() + "} totally match");
							// feedBack = feedBack + "{"+ key.getOpName()+"}
							// totally match\n";
						}
						if (opMap.get(key) == 1) {
							// ignore those long names (e.g. Client/Customer)

							if (!key.getOpName().contains("/")) {

								// not found,missing class
								System.out.println("Missing operation : " + key.getOpName());
								feedBack += "Missing operation : " + key.getOpName() + "\n";

							}

						}
						if (opMap.get(key) == 0) {
							boolean attrFound = false;
							// find it in answer attrList
							for (Operation op : mClass4Model.getOpList()) {

								System.out.println(op.getOpName() + key.getOpName());
								if (sameWith(op.getOpName(), key.getOpName())) {

									Boolean reTypeMatch = false;
									attrFound = true;
									opMatch++;

									// Synonyms , compare detail
									// check visibility,opReType,parameters
									System.out.println(op.getVisibility() + key.getVisibility());

									System.out.println(op.getOpReType() + key.getOpReType());

									System.out.println(op.getParams() + key.getParams());
									if (!op.getVisibility().equals(key.getVisibility())) {
										// visibility not match
										System.out.println("{" + op.getOpName() + "} visibility not match");
										feedBack += "{" + op.getOpName() + "} visibility not match,which should be {"
												+ op.getVisibility() + "}\n";
									}
									if (op.getOpReType() != null || key.getOpReType() != null) {
										// both not null or either not null
										if (op.getOpReType() != null && key.getOpReType() != null) {
											// both not null
											if (!op.getOpReType().equals(key.getOpReType())) {
												// return type not match
												System.out.println("{" + key.getOpName() + "} return type not match");
												feedBack += "{" + key.getOpName()
														+ "} return type not match,which should be {" + op.getOpReType()
														+ "}\n";
											}
										} else {
											// one is not null one is null
											System.out.println("{" + key.getOpName() + "} return type not match");
											feedBack += "{" + key.getOpName()
													+ "} return type not match,which should be {" + op.getOpReType()
													+ "}\n";
										}
									}
									if (op.getOpReType() == null && key.getOpReType() == null) {
										// return type match
										reTypeMatch = true;
									}

									if (!op.getParams().equalsIgnoreCase(key.getParams())) {
										// parameter not match
										System.out.println("{" + key.getOpName() + "} parameter(s) not match");
										feedBack += "{" + key.getOpName() + "} parameter(s) not match,which should be {"
												+ op.getParams() + "}\n";

									} else if (op.getParams().equalsIgnoreCase(key.getParams()) && reTypeMatch
											&& op.getVisibility().equals(key.getVisibility())) {

										// match

										System.out.println("{" + op.getOpName() + "} ");

										opDeMatch++;
									}

								}

							}
							if (!attrFound) {
								// if not found, extra attr which answer does
								// not have
								System.out.println("Extra operation : " + key.getOpName());
								extra += "Extra operation : " + key.getOpName() + "\n";

							}

						}
					}

					break;
				} // if
			} // for loop end (j)

		} // for loop end (i)

		feedBack += opSum + "== total operation number in model answer\n";
		System.out.println(opMatch + " == operation generally matched number");
		feedBack += opMatch + " == operation generally matched number\n";
		System.out.println(opDeMatch + " == operation totally matched number");
		feedBack += opDeMatch + " == operation totally matched number\n";
		System.out.println(opSum + "== total operation number in model answer");

		System.out.println(feedBack);
		System.out.println("done");
		
		opMiss = opSum - opMatch;
		opDiff = opSum - opDeMatch;
 

		opRate = ((float) opMatch) / ((float) opSum) * 100;

		System.out.println("[" + opRate + "%] operation generally matched");
		feedBack +="[" + opRate + "%] operation generally matched\n";

		opDeRate = ((float) opDeMatch) / ((float) opSum) * 100;
		feedBack +="[" + opDeRate + "%] attibute totally matched\n";
		System.out.println("[" + opDeRate + "%] attibute totally matched");

		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");

	}
	

	public static boolean sameWith(String model, String mark) {
		boolean nameFound = false;
		String[] possibleNames = model.split("/");

		System.out.println(possibleNames.length);
		for (int j = 0; j < possibleNames.length; j++) {
			System.out.println(possibleNames[j]);
			if (possibleNames[j].equalsIgnoreCase(mark)) {
				nameFound = true;
			}
		}
		return nameFound;
	}

	public void validateMap(HashMap<Operation, Integer> map) {
		Iterator<Operation> it1 = map.keySet().iterator();
		while (it1.hasNext()) {

			Operation key1 = ((Operation) it1.next());
			String name1 = key1.getOpName();
			System.out.println(name1);
			Iterator<Operation> it2 = map.keySet().iterator();
			while (it2.hasNext()) {
				Operation key2 = ((Operation) it2.next());
				String name2 = key2.getOpName();
				System.out.println(name2);

				System.out.println(map.get(key1));
				System.out.println(map.get(key2));
				if (name1.equalsIgnoreCase(name2) && !map.get(key1).equals(map.get(key2))) {
					// Attribute key = (Attribute)it1.next();
					if (map.get(key1) == 1) {
						map.put(key1, 3);
						Iterator<Operation> test1 = map.keySet().iterator();
						while (test1.hasNext()) {
							Operation op = (Operation) test1.next();
							int value = map.get(op);
							System.out.println(op.getOpName() + "," + value);
						}
						break;
					} else if (map.get(key2) == 1) {
						map.put(key2, 3);

						Iterator<Operation> test1 = map.keySet().iterator();
						while (test1.hasNext()) {
							Operation a = (Operation) test1.next();
							int value = map.get(a);
							System.out.println(a.getOpName() + "," + value);
						}
						break;
					}

				}
			}
		}

	}

	public float getOpRate() {
		return opRate;
	}

	public float getOpDeRate() {
		return opDeRate;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public String getExtra() {
		return extra;
	}

}
