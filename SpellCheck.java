package testingClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class SpellCheck {
 public String input="";
 public SpellCheck(String input) {
		this.input = input;
	}
 public static final char[] c={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
  'q','r','s','t','u','v','w','x','y','z'};
 List<?>  wordlist = new ArrayList<Object>();


 
 public List<String> words() throws IOException {
  BufferedReader in;
  List<String> list = new ArrayList<String>();
  try {
   //****** set the file path of corpus here*******
   //big.txt as corpus
   in = new BufferedReader(new FileReader(
     "/Users/Agnes/Desktop/big.txt"));
   String s;
   while ((s = in.readLine()) != null)
   {
    //remove characters except letter (a-z& A-Z)
    s = s.replaceAll("\\pP|\\pS|\\pM|\\pN|\\pC", "");
    
    String[] str = new String[100];
    
    //to lower case
    String s1 = s.toLowerCase();
    
    // split into single word
    str = s1.split(" ");
    for(int j=0;j<str.length;j++){
     if(!" ".equals(str[j])&&!"".equals(str[j])&&!str[j].equals(null))
      list.add(str[j]);
    }
   }
   in.close();
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  }

  return list;
  
 }

 
 // get the frequency of each letter, put it in hashMap (string,count)
 // class count own a field count for counting purpose
 public HashMap<String, Counter> frequency(List<String> wordlist) throws IOException {
  HashMap<String, Counter> hashmap = new HashMap<String, Counter>();
  for (int i = 0; i < wordlist.size(); i++) {
    if (hashmap.containsKey(wordlist.get(i))) {
     ((Counter) hashmap.get(wordlist.get(i))).count++;
    } else
     hashmap.put(wordlist.get(i), new Counter());
   }

  return hashmap;
 }
 

 //for words whose edit distance is 1
 public Set<SetElement> getEdits1(String word) {
  int n = word.length();
  Set<SetElement> set = new HashSet<SetElement>();
  //deletion   n
  for (int i = 0; i < n; i++) {
   String word1 = null;
   word1=word.substring(0, i)+word.substring(i+1,n);
   set.add(new SetElement(word1));
  }
  //transposition  n-1
  for (int i = 0; i < n-1; i++){
   String word1 = null;
   word1 = word.substring(0, i)+word.substring(i+1, i+2)+
   word.substring(i, i+1)+word.substring(i+2, n);
   set.add(new SetElement(word1));
  }
  //alteration 26n
  for (int i = 0; i < n; i++){
   for(int j=0;j<26;j++){
   String word1 = null;
   word1 = word.substring(0, i)+c[j]+word.substring(i+1, n);
   set.add(new SetElement(word1));
   }
  }
  //insertion 26n
  for (int i = 0; i < n; i++){
   for(int j=0;j<26;j++){
   String word1 = null;
   word1 = word.substring(0, i)+c[j]+word.substring(i, n);
   set.add(new SetElement(word1));
   }
  }
  //insert the letter at the end  n
  for(int j=0;j<26;j++){
   set.add(new SetElement(word+c[j]));
  }
 
  return set;
 }
 
 //for words whose edit distance is 2 
 public Set<String> getEdits2(Set<SetElement> set,HashMap<String, Counter> hashmap) throws IOException{
  Set<SetElement> set2 = new HashSet<SetElement>();
  Set<String> set3 = new HashSet<String>();
  Iterator<SetElement> it = set.iterator();
        while(it.hasNext()){
         Set<SetElement> tset = new HashSet<SetElement>();
         tset = getEdits1(it.next().toString());
         Iterator<SetElement> it1 = tset.iterator();
         while(it1.hasNext())
          set2.add(new SetElement(it1.next().toString()));
        }
        Iterator<SetElement> it2 = set2.iterator();
        //remove the words which do not in the corpus
        while(it2.hasNext()){
         String res = it2.next().toString();
         if(hashmap.containsKey(res))
         {
          set3.add(res);
         }
        }
  return set3;
 }
 
 
 //tell whether a word exist in the corpus, remove the not exist words
 public Set<SetElement> existOrNot(List<?> list,HashMap<?, ?> hashmap) throws IOException{
  Set<SetElement> set = new HashSet<SetElement>();
  for(int i=0;i<list.size();i++)
  {
   if(hashmap.containsKey(list.get(i)))
   {
    set.add(new SetElement((String) list.get(i)));
   }
  }
  return set;
 }

 //remove the not exist words with edit distance 1
 public Set<?> existOrNot(Set<SetElement> set,HashMap<String, Counter> hashmap) throws IOException{
  Set<?> set1 = new HashSet<Object>();
  Iterator<SetElement> it = set.iterator();
  while(it.hasNext()){
   String res = it.next().toString();
   if(hashmap.containsKey(res))
   {
    set.add(new SetElement(res));
   }
  }
  return set1;
 }

 public String correct(String word,List<String> list,HashMap<String, Counter> hashmap,Set<?> set1,Set<String> set2) throws IOException{
  
  String cword = word;//correct word initially
  int count = 0;
  Set<SetElement> candidates = new HashSet<SetElement>();
 
  
  // calculate the portion of the set of ed1 and ed2
  // if not in the corpus, return the word itself
 
  Iterator<?> it1 = set1.iterator();
   while(it1.hasNext())
   {
    candidates.add(new SetElement(it1.next().toString()));
   }
   //already delete the not exists
  Iterator<String> it2 = set2.iterator();
   while(it2.hasNext())
   {
    candidates.add(new SetElement(it2.next().toString()));
   }
  Iterator<SetElement> cit = candidates.iterator();
  while(cit.hasNext()){
   String res = cit.next().toString();
    //compare the count, return max
   if(((Counter)hashmap.get(res)).count > count){
    cword = res;
    count = ((Counter)hashmap.get(res)).count;
   }
  }
  return cword;
 }
 

 // counting the frequency
 class Counter {
  int count = 1;
  public String toString() {
   return Integer.toString(count);
  }
 }
//hashcode and equal
 static class SetElement {
  String s1;
  public SetElement(String s2) {
   this.s1 = s2;
  }
  public String toString() {
   return s1;
  }
  public boolean equals(Object obj) {
   return s1.equals(((SetElement) obj).s1);
  }
  public int hashCode() {
   return s1.hashCode();
  }
 }
}