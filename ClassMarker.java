package testingClass;

import java.util.*;
import testingClass.MClass;

public class ClassMarker {



	public ClassMarker() {

	}

	
	// for class (class+attribute+operation)

	// number of match
	int classMatch = 0;

	// match rate of existence
	float classRate = 0;
	
	int classMiss = 0;

	// total class number
	int ansClassNo = 0;
	int markClassNo = 0;

	// summary feedBack of incorrect feature
	String feedBack = "";

	public float getClassRate() {
		return classRate;
	}

	public void setClassRate(float classRate) {
		this.classRate = classRate;
	}

	public String getFeedBack() {
		return this.feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	// extra information in the student work
	String extra = "";

	public void executeMarker(ArrayList<MClass> ansClassList, ArrayList<MClass> markClassList) {

		long startTime = System.currentTimeMillis();

		ansClassNo = ansClassList.size();
		markClassNo = markClassList.size();

		//initialise match rate
		classMatch = 0;
		extra = "";
		feedBack = "";
		System.out.println(ansClassNo);
		System.out.println(markClassNo);
		System.out.println(classMatch);
		
		

		HashMap<String, Integer> classNameMap = new HashMap<String, Integer>();

		// check matchness of class
		// traverse model answer class list
		for (int i = 0; i < ansClassNo; i++) {
			MClass mClass4Model = (MClass) ansClassList.get(i);
			
			
			// put className into map

			classNameMap.put(mClass4Model.name.toLowerCase(), 1);

		}

		Iterator<String> test = classNameMap.keySet().iterator();
		while (test.hasNext()) {

			String key = (String) test.next();
			int value = classNameMap.get(key);
			System.out.println(key + "," + value);
		}
		System.out.println("");

		// traverse mark class list
		for (int i = 0; i < markClassNo; i++) {
			MClass mClass4Mark = (MClass) markClassList.get(i);
			

			Integer cc = classNameMap.get(mClass4Mark.name.toLowerCase());

			/*if a same name exist, value ++
			 * if not ,value = 0 
			 */
			if (cc != null) {
				classNameMap.put(mClass4Mark.name.toLowerCase(), ++cc);

				continue;

			}

			// if extra class that model answer does not have, then ignore
			classNameMap.put(mClass4Mark.name.toLowerCase(), 0);

		}

		Iterator<String> test1 = classNameMap.keySet().iterator();
		while (test1.hasNext()) {

			String key = (String) test1.next();
			int value = classNameMap.get(key);
			System.out.println(key + "," + value);
		}
		System.out.println("");

		validateMap(classNameMap);
		Iterator<String> test2 = classNameMap.keySet().iterator();
		while (test2.hasNext()) {

			String key = (String) test2.next();
			int value = classNameMap.get(key);
			System.out.println(key + "," + value);
		}
		System.out.println("");

		Iterator<String> iter = classNameMap.keySet().iterator();
		while (iter.hasNext()) {

			String key = (String) iter.next();
			System.out.println("current key: " + key);
			if (classNameMap.get(key) == 2) {
				classMatch++;
			}
			if (classNameMap.get(key) == 1) {

				// missing class
				System.out.println("Class {" + key + "}is missing");
				this.feedBack += "Class {" + key + "}is missing\n";

			}
			if (classNameMap.get(key) == 0) {

				System.out.println("Extra class :{" + key + "} ");
				this.extra += "Extra class {" + key + "} \n";

			}
		}
		System.out.println(this.feedBack);
		System.out.println(classMatch + "== class matched number");
		System.out.println(ansClassNo + "== total class number");
		
		feedBack += classMatch + "== class matched number\n";
		feedBack += ansClassNo + "== total class number in model answer\n";
		
		classMiss = ansClassNo - classMatch;

		classRate = ((float) classMatch) / ((float) ansClassNo) * 100;

		System.out.println("[" + classRate + "%] class matched");
		feedBack +="[" + classRate + "%] class matched\n";

		long endTime = System.currentTimeMillis();
		System.out.println("Time： " + (endTime - startTime) + "ms");
	}

	public int getClassMiss() {
		return classMiss;
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

	public void validateMap(HashMap<String, Integer> map) {
		Iterator<String> it1 = map.keySet().iterator();
		while (it1.hasNext()) {

			String key1 = (String) it1.next();
			System.out.println(key1);
			System.out.println(key1);

			Iterator<String> it2 = map.keySet().iterator();
			while (it2.hasNext()) {
				String key2 = (String) it2.next();
				System.out.println(key2);
				System.out.println(key1);

				System.out.println(map.get(key1));
				System.out.println(map.get(key2));
				if (map.get(key1) == 0 && sameWith(key2, key1) && !key2.equalsIgnoreCase(key1)) {

					map.put(key1, 3);
					map.put(key2, 2);
					Iterator<String> test2 = map.keySet().iterator();
					while (test2.hasNext()) {
						String a = (String) test2.next();
						int value = map.get(a);
						System.out.println(a + "," + value);
					}

				}

			}
		}

	}
}
