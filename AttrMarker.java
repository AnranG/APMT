package testingClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AttrMarker {



	
	
	public String ansFilePath = "";
	public String markFilePath = "";
	public String attrName = "";
	public	String feedBack = "";
	public	String extra = "";
	public int attrMiss = 0;
	public int attrDiff = 0;



		// number of match
		
		int attrMatch = 0;

		int attrDeMatch = 0;
	
		// match rate of existence
	
		float attrRate = 0;
		
		// match rate of detail
		float attrDeRate = 0;
	
		// total number of attribute
		int attrSum = 0;


		//total class number
		int ansClassNo = 0;
		int markClassNo = 0;
		
		
		public AttrMarker() {
		
	}


		public String getFeedBack() {
			return feedBack;
		}
		
		

		public float getAttrRate() {
			return attrRate;
		}

		public float getAttrDeRate() {
			return attrDeRate;
		}

		public String getExtra() {
			return extra;
		}
		
		

		MClass ansClass = new MClass();
		String ansClassName = "";
		ArrayList<Attribute> ansAttrList = new ArrayList<Attribute>();
		ArrayList<Operation> ansOpList = new ArrayList<Operation>();

		MClass markClass = new MClass();
	
		ArrayList<Attribute> markAttrList = new ArrayList<Attribute>();
		ArrayList<Operation> markOpList = new ArrayList<Operation>();

		
public void executeMarker(ArrayList<MClass> ansClassList,ArrayList<MClass> markClassList) {
	
	long startTime=System.currentTimeMillis();

		
	//initialise match
	attrMatch = 0;

	attrDeMatch = 0;
	extra = "";
	attrSum = 0;
	feedBack = "";
		
		
		ansClassNo = ansClassList.size();
		
		System.out.println(ansClassNo);
		
		

		// check matchness of class
		// traverse model answer class list

		for (int i = 0; i < ansClassNo; i++) {
			HashMap<Attribute, Integer> attrNameMap = new HashMap<Attribute, Integer>();
			MClass mClass4Model = (MClass) ansClassList.get(i);
			System.out.println(mClass4Model.name);
			attrSum = attrSum+mClass4Model.getAttrList().size();
			for(Attribute a:mClass4Model.getAttrList()){
				
				System.out.println(a.getName());
			// put each attribute into map
            attrName = a.getName();
			attrNameMap.put(a, 1);
			}
			Iterator<Attribute> test = attrNameMap.keySet().iterator();
			while (test.hasNext()) {
				Attribute a = (Attribute) test.next();
				int value  = attrNameMap.get(a);
				System.out.println(a.getName()+","+value);
				
			}
			
			System.out.println("done");
			
			markClassNo = markClassList.size();
			System.out.println(markClassNo);
			
		
			
			
			// traverse mark class list
			
			for (int j = 0; j < markClassNo; j++) {
				MClass mClass4Mark = (MClass) markClassList.get(j);
				System.out.println(mClass4Mark.name + mClass4Model.getName());
				
				if(sameWith(mClass4Model.getName(),mClass4Mark.getName())){
					System.out.println(mClass4Mark.name + mClass4Model.getName());
					System.out.println("");
				for(Attribute a:mClass4Mark.getAttrList()){
				System.out.println(a.getName());

				/*if a same attr exist, value ++
				 * if not ,value = 0 
				 */
				Integer cc = attrNameMap.get(a);
				System.out.println(cc);
				if (cc != null) {
					attrNameMap.put(a, ++cc);
					
					continue;

				}

				// if extra class that model answer does not have, then ignore
				attrNameMap.put(a, 0);
		
			}
				
				Iterator<Attribute> test1 = attrNameMap.keySet().iterator();
				while (test1.hasNext()) {
					Attribute a = (Attribute) test1.next();
					int value  = attrNameMap.get(a);
					System.out.println(a.getName()+","+value);
					
				}
				System.out.println("done");
				validateMap(attrNameMap);
				
				Iterator<Attribute> test2 = attrNameMap.keySet().iterator();
				while (test2.hasNext()) {
					Attribute a = (Attribute) test2.next();
					int value  = attrNameMap.get(a);
					System.out.println(a.getName()+","+value);
					
				}
				
				System.out.println("done");
				
				
				Iterator<Attribute> iter = attrNameMap.keySet().iterator();
				while (iter.hasNext()) {

					Attribute key = (Attribute) iter.next();
					System.out.println("(key,value): (" + key.getName() +"," +attrNameMap.get(key) );
					if (attrNameMap.get(key) == 2){
						// totally match
						attrMatch++;
						attrDeMatch++;
//						System.out.println("{"+ key.getName()+"} totally match" );
//						this.feedBack = this.feedBack + "{"+ key.getName()+"} totally match\n";
					}
					if (attrNameMap.get(key) == 1) {
						// ignore those long names (e.g. Client/Customer)
					
						if(!key.getName().contains("/")){
		
								// not found,missing class
								System.out.println("Missing attribute : {" +key.getName()+"}");
								this.feedBack = this.feedBack + "Missing attribute :{ " +key.getName()+"}\n";
							
							
						}
						
					}
					if (attrNameMap.get(key) == 0) {
						boolean attrFound = false;
						//find it in answer attrList
						for(Attribute a:mClass4Model.getAttrList()){
							
							System.out.println( a.getName()+key.getName());
							if(sameWith(a.getName(),key.getName())){
							
								
								attrFound = true;
								attrMatch ++;
								
								//Synonyms , compare detail
								System.out.println( a.getVisibility()+key.getVisibility());
								
								System.out.println( a.getAttrType()+key.getAttrType());
								if(!a.getVisibility().equals(key.getVisibility())){
									// visibility not match
									System.out.println("{"+a.getName()+"} visibility not match" );
									
									this.feedBack = this.feedBack +"{"+a.getName()+"} visibility not match, which should be {"+a.getVisibility()+"} \n";
								}
								if(!a.getAttrType().equals(key.getAttrType())){
									// data type not match
									System.out.println("{"+a.getName()+"} data type not match" );
									this.feedBack = this.feedBack +"{"+a.getName()+"} data type not match, which should be {"+a.getAttrType()+"} \n";
									
								}else if(a.getVisibility().equals(key.getVisibility()) &&a.getAttrType().equals(key.getAttrType())){
//									System.out.println("{"+a.getName()+"} totally match" );
//									this.feedBack = this.feedBack +"{"+a.getName()+"} totally match\n";
									attrDeMatch ++;
								}
								
							}
						
							}
						if(!attrFound){
							//if not found, extra attr which answer does not have
							System.out.println("Extra attribute : " +key.getName());	
							this.extra = this.extra +"Extra attribute : " +key.getName()+"\n";
							
						}

				}
				}
				
				
				break;
				}//if
			}//for loop end (j)
			
			
		}//for loop end (i)
		 System.out.println(attrSum + "== total attibute number");
		 feedBack += attrSum + "== total attibute number in model answer\n";
		 
		System.out.println(attrMatch + " == attibute generally matched number");
		feedBack += attrMatch + " == attibute generally matched number\n";
		
		 System.out.println(attrDeMatch + " == attibute totally matched number");
		 feedBack += attrDeMatch + " == attibute totally matched number\n";
		
		System.out.println(this.feedBack );
		System.out.println("done" );
		
		attrMiss = attrSum - attrMatch;
		attrDiff = attrSum - attrDeMatch;

		attrRate = ((float) attrMatch) / ((float) attrSum) * 100;
		

		System.out.println("[" + attrRate + "%] attibute generally matched");
		feedBack += "[" + attrRate + "%] attibute generally matched\n";
		
		attrDeRate = ((float) attrDeMatch) / ((float) attrSum) * 100;

		System.out.println("[" + attrDeRate + "%] attibute totally matched");
		feedBack += "[" + attrDeRate + "%] attibute totally matched\n";
		
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("Time： "+(endTime-startTime)+"ms");
	
}

	public int getAttrMiss() {
	return attrMiss;
}


public int getAttrDiff() {
	return attrDiff;
}


	public static boolean sameWith(String model,String mark){
		boolean nameFound = false;
		String[] possibleNames  =	model.split("/");
		
		System.out.println(possibleNames.length);
		for (int j=0;j<possibleNames.length;j++) {
			System.out.println(possibleNames[j]);
			if(possibleNames[j].equalsIgnoreCase(mark)){
				nameFound = true;
			}
		}
		return nameFound;
	}
	
	public void validateMap(HashMap<Attribute,Integer> map){
		Iterator<Attribute> it1 = map.keySet().iterator();
		while (it1.hasNext()) {
			
			Attribute key1 = ((Attribute)it1.next());
			String name1 = key1.getName();
			System.out.println(name1);
			Iterator<Attribute> it2 = map.keySet().iterator();
			while (it2.hasNext()) {
				Attribute key2 = ((Attribute)it2.next());
				String name2 = key2.getName();
				System.out.println(name2);
				
				System.out.println(map.get(key1));
				System.out.println(map.get(key2));
				if(name1.equalsIgnoreCase(name2)&& !map.get(key1).equals(map.get(key2))){
					//Attribute key = (Attribute)it1.next();
					if(map.get(key1) ==1){
					map.put(key1, 3);
					Iterator<Attribute> test1 = map.keySet().iterator();
					while (test1.hasNext()) {
						Attribute a = (Attribute) test1.next();
						int value  = map.get(a);
						System.out.println(a.getName()+","+value);
					}
					break;
					}else if (map.get(key2) ==1){
						map.put(key2, 3);
					
						Iterator<Attribute> test1 = map.keySet().iterator();
						while (test1.hasNext()) {
							Attribute a = (Attribute) test1.next();
							int value  = map.get(a);
							System.out.println(a.getName()+","+value);
						}
						break;
					}
					
				}
			}
		}
		
		
		}
	
	
	
	
	
			

	}
		
		

	
