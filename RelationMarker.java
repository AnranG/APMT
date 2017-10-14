package testingClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RelationMarker {

	public RelationMarker() {

	}



	

	// match rate of existence
	float relationRate = 0;

	// match rate of detail
	float reDeRate = 0;

	// total Relation number
	int ansRelationNo = 0;
	int markRelationNo = 0;
	
	
	// summary feedback of incorrect feature
	String feedBack = "";

	// extra information in the student work
	String extra = "";

	public float getRelationRate() {
		return relationRate;
	}

	public float getReDeRate() {
		return reDeRate;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public String getExtra() {
		return extra;
	}
	
	int relationMiss = 0;
	public int getRelationMiss() {
		return relationMiss;
	}

	public int getRelationDiff() {
		return relationDiff;
	}





	int relationDiff = 0;

	// number of match
		int relationMatch = 0;
		int reDeMatch = 0;

	public void executeMarker(ArrayList<MClass> ansClassList, ArrayList<MClass> markClassList,
			ArrayList<Relationship> ansRelationList, ArrayList<Relationship> markRelationList) {


		extra = "";
		ansRelationNo = ansRelationList.size();
		markRelationNo = markRelationList.size();
	
		//initialise match rate
	 relationMatch = 0;
	 reDeMatch = 0;
	 feedBack = "";
	 

		for(MClass m:markClassList){
			System.out.println(m.getName());
		
			
		}
		System.out.println("");

		System.out.println(ansRelationNo);
		System.out.println(markRelationNo);
		System.out.println(relationMatch);
		System.out.println(reDeMatch);
		



		// check matchness of relation
		// traverse model answer class list
		HashMap<Relationship, Integer> relationMap = new HashMap<Relationship, Integer>();
		for (int i = 0; i < ansRelationNo; i++) {

			Relationship re4Model = (Relationship) ansRelationList.get(i);
			System.out.println(re4Model.getRelationType());

			relationMap.put(re4Model, 1);

		} // for loop end (i)

		Iterator<Relationship> test = relationMap.keySet().iterator();
		while (test.hasNext()) {
			Relationship a = (Relationship) test.next();
			int value = relationMap.get(a);
			System.out.println(a.getRelationType() + "," + a.getClassPair()[0] + a.getClassPair()[1] + "," + value);

		}

		System.out.println("done");

		// traverse mark class list
		markRelationNo = markRelationList.size();
		System.out.println(markRelationNo);
		for (int j = 0; j < markRelationNo; j++) {
			Relationship re4Mark = (Relationship) markRelationList.get(j);

			System.out.println(re4Mark.getRelationType() + "," + re4Mark.getClassPair()[0] + re4Mark.getClassPair()[1]
					+ "," + relationMap.get(re4Mark));

			Integer cc = relationMap.get(re4Mark);
			System.out.println(cc);
			if (cc != null) {
				relationMap.put(re4Mark, ++cc);

				continue;

			}

			// if extra class that model answer does not have, then ignore
			relationMap.put(re4Mark, 0);

		} // for loop end (j)

		Iterator<Relationship> test1 = relationMap.keySet().iterator();
		while (test1.hasNext()) {
			Relationship a = (Relationship) test1.next();
			int value = relationMap.get(a);
			System.out.println(a.getRelationType() + "," + a.getClassPair()[0] + a.getClassPair()[1] + "," + value);

		}

		System.out.println("done");

		validateMap(relationMap);
		Iterator<Relationship> test2 = relationMap.keySet().iterator();
		while (test2.hasNext()) {
			Relationship a = (Relationship) test2.next();
			int value = relationMap.get(a);
			System.out.println(a.getRelationType() + "," + a.getClassPair()[0] + a.getClassPair()[1] + "," + value);

		}

		System.out.println("done");

		Iterator<Relationship> iter = relationMap.keySet().iterator();
		while (iter.hasNext()) {

			Relationship key = (Relationship) iter.next();
			System.out.println(key.getRelationType() + "," + key.getClassPair() + "," + relationMap.get(key));
			if (relationMap.get(key) == 2) {
				// totally match
				relationMatch++;
				reDeMatch++;
				// System.out.println("{"+ key.getOpName()+"} totally match" );
				// feedBack = feedBack + "{"+ key.getOpName()+"} totally
				// match\n";
			}
			if (relationMap.get(key) == 1) {

				// not found,missing class
				System.out.println("Missing relationship : {" + key.getRelationType() + "} of (" + key.getClassPair()[0]
						+ "," + key.getClassPair()[1] + ")");
				this.feedBack += "Missing relationship: {" + key.getRelationType() + "} of (" + key.getClassPair()[0]
						+ "," + key.getClassPair()[1] + ")" + "\n";

			}
			if (relationMap.get(key) == 0) {
				boolean relationFound = false;
				// find it in answer attrList
				for (Relationship r : ansRelationList) {
					System.out.println(key.getRelationType() + "," + key.getClassPair()[0] + key.getClassPair()[1]);

					System.out.println(r.getRelationType() + "," + r.getClassPair()[0] + r.getClassPair()[1]);
					System.out.println("");

					if (classPairMatch(key.getClassPair(), r.getClassPair())
							&& key.getRelationType().equals(r.getRelationType())) {

						relationFound = true;
						relationMatch++;

						// Synonyms , compare detail
						// check visibility,opReType,parameters
						System.out.println(r.getRelationName() + key.getRelationName());

						System.out.println(r.getReEndNames() + key.getReEndNames());
						System.out.println(r.getMulti());
						System.out.println("");

						// check name, when the relation name in answer is not
						// empty or not null
						if (r.getRelationName() != null || !r.getRelationName().equals("")) {
							if (!r.getRelationName().equalsIgnoreCase(key.getRelationName())) {
								// relation name not match
								System.out.println(
										"Relationship : {" + r.getRelationType() + "} of (" + r.getClassPair()[0]
												+ "," + r.getClassPair()[1] + "), Wrong relationship name, which shuold be:{"+ r.getRelationName()+"}");
								this.feedBack += "Relationship : {" + key.getRelationType() + "} of ("
										+ key.getClassPair()[0] + "," + key.getClassPair()[1]
										+ "), Wrong relationship name, which shuold be:{"+r.getRelationName()+"}\n";
							}
							// check end names
						}
						if (!r.getReEndNames().equalsIgnoreCase(key.getReEndNames())) {
							// relation end name not match

							System.out.println("Relationship : {" + r.getRelationType() + "} of ("
									+ r.getClassPair()[0] + "," + r.getClassPair()[1] + "), Wrong end names");
							this.feedBack += "Relationship : {" + r.getRelationType() + "} of ("
									+ r.getClassPair()[0] + "," + r.getClassPair()[1] + "), Wrong end names, which shuold be:{"+r.getReEndNames()+"}\n";

							// check multi
						}
						if (!r.getMulti().equalsIgnoreCase(key.getMulti())) {
							// return type match
							System.out.println("Relationship : {" + key.getRelationType() + "} of ("
									+ key.getClassPair()[0] + "," + key.getClassPair()[1] + "), Wrong multiplicity");
							this.feedBack += "Relationship : {" + key.getRelationType() + "} of ("
									+ key.getClassPair()[0] + "," + key.getClassPair()[1] + "), Wrong multiplicity\n";

						}

						else {

							// match

							System.out.println("Relationship : {" + key.getRelationType() + "} of ("
									+ key.getClassPair()[0] + "," + key.getClassPair()[1] + "), special");

							reDeMatch++;
						}

					}

				}
				if (!relationFound) {
					// if not found, extra attr which answer does not have
					System.out.println("Extra relationship : {" + key.getRelationType() + "} of ("
							+ key.getClassPair()[0] + "," + key.getClassPair()[1] + ")");

					this.extra += "Extra relationship : {" + key.getRelationType() + "} of (" + key.getClassPair()[0]
							+ "," + key.getClassPair()[1] + ")\n";

				}

			}
		}
		System.out.println("@@@@@");
		
		System.out.println(ansRelationNo + "== total relationship number in model answer");
		feedBack += ansRelationNo + "== total relationship number in model answer\n";
		System.out.println(relationMatch + " == relationship generally matched number");
		feedBack += relationMatch + "== relationship generally matched number\n";
		System.out.println(reDeMatch + " == relationship totally matched number");
		feedBack += reDeMatch + "== relationship totally matched number\n";
	
		relationMiss = ansRelationNo-relationMatch;
		relationDiff = ansRelationNo-reDeMatch;
		

		relationRate = ((float) relationMatch) / ((float) ansRelationNo) * 100;

		System.out.println("[" + relationRate + "%] relationship generally matched");
		feedBack += "[" + relationRate + "%] relationship generally matched\n";

		reDeRate = ((float) reDeMatch) / ((float) ansRelationNo) * 100;

		System.out.println("[" + reDeRate + "%] relationship totally matched");
		feedBack += "[" + reDeRate + "%] relationship totally matched\n";
		System.out.println(feedBack);

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

	public void validateMap(HashMap<Relationship, Integer> map) {
		Iterator<Relationship> it1 = map.keySet().iterator();
		while (it1.hasNext()) {

			Relationship key1 = ((Relationship) it1.next());
			String type1 = key1.getRelationType();
			String[] classPair1 = key1.getClassPair();
			System.out.println(type1 + "," + classPair1[0] + "," + classPair1[1]);
			System.out.println("");
			Iterator<Relationship> it2 = map.keySet().iterator();
			while (it2.hasNext()) {
				Relationship key2 = ((Relationship) it2.next());
				String type2 = key2.getRelationType();
				String[] classPair2 = key2.getClassPair();
				System.out.println(type2 + "," + classPair2[0] + "," + classPair2[1]);
				System.out.println("");

				System.out.println(map.get(key1));
				System.out.println(map.get(key2));
				System.out.println("");

				if (classPairMatch(classPair1, classPair2) && type1.equalsIgnoreCase(type2)
						&& !map.get(key1).equals(map.get(key2))) {
					// Attribute key = (Attribute)it1.next();
					if (map.get(key1) == 1) {
						map.put(key1, 3);
						Iterator<Relationship> test1 = map.keySet().iterator();
						while (test1.hasNext()) {
							Relationship r = (Relationship) test1.next();
							int value = map.get(r);
							System.out.println(r.getRelationType() + "," + r.getClassPair()[0] + r.getClassPair()[1]
									+ "," + value);

						}
						break;
					} else if (map.get(key2) == 1) {
						map.put(key2, 3);

						Iterator<Relationship> test1 = map.keySet().iterator();
						while (test1.hasNext()) {
							Relationship r = (Relationship) test1.next();
							int value = map.get(r);
							System.out.println(r.getRelationType() + "," + r.getClassPair()[0] + r.getClassPair()[1]
									+ "," + value);

						}
						break;
					}

				}
			}
		}

	}

	public boolean classPairMatch(String[] pair1, String[] pair2) {
		int classMatch = 0;
		for (int i = 0; i < pair1.length; i++) {

			String name1 = pair1[i];
			System.out.println(name1);

			for (int j = 0; j < pair2.length; j++) {
				String name2 = pair2[j];
				System.out.println(name2);
				if (sameWith(name1, name2) || name1.equalsIgnoreCase(name2) || sameWith(name2, name1)) {

					// one class found
					classMatch++;
				}

			}

		}
		if (classMatch == 2) {
			return true;
		}

		return false;
	}

}
