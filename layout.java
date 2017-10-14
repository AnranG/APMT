package testingClass;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import testingClass.SpellCheck.Counter;
import testingClass.SpellCheck.SetElement;


public class layout {
	JFrame frame = new JFrame("AMT");
	
	// two button for xml file submission
	public static JButton button1 = new JButton("Select file");
	public static JButton button2 = new JButton("Select file");
	
	// marking button
	public static JButton button3 = new JButton("Marking");
	public static JTextField sourcefile = new JTextField();
	public static JTextField targetfile = new JTextField();
	
	// marking result overview
	public static JLabel overview = new JLabel();

	public static JLabel ansFilePrompt = new JLabel("Please select model answer:");
	public static JLabel markFilePrompt = new JLabel("Please select student submission(s):");

	// whether file is selected 
	public boolean ansSelect;
	public boolean markSelect;
	
	public static JLabel ansSource = new JLabel();
	public String ansText = "";
	public File[] files;
	
	private static PrintStream ps;

	public static JLabel markSource = new JLabel();
	
	public String markText = "";
	public int fileNum = 0;
	public String fileName = "";
	public String markOverview = "";
	public String typo = "";

    // ArrayList to handle relationship and class 
	ArrayList<Relationship> ansRelationList = new ArrayList<Relationship>();
	ArrayList<MClass> ansClassList = new ArrayList<MClass>();
	
	
	// initialize parser
	DOMParser ansParser;
	DOMParser markParser;

	ClassMarker ClassMarker = new ClassMarker();
	AttrMarker AttrMarker = new AttrMarker();
	OpMarker OpMarker = new OpMarker();
	RelationMarker RelationMarker = new RelationMarker();
	
	ArrayList<Result> resultList = new ArrayList<Result>();

	JFileChooser chooser;
	String filePath = "";
	String ansFilePath = "";
	String markFilePath = "";
	private HSSFWorkbook wb;
	long endTime = 0;

	
	public static void main(String argv[]) throws IOException {
		
		new layout();
		 
		
	}

	public layout() throws IOException {
		// set start time
		long startTime = System.currentTimeMillis();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		// set frame attribute
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Container countainer = frame.getContentPane();
		countainer.setLayout(null);

		
		// prompt for answer file submission
		ansFilePrompt.setBounds(110, 50, 200, 40);
		countainer.add(ansFilePrompt);

		// set the button for answer submission
		button1.setBounds(300, 50, 100, 40);
		countainer.add(button1);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
        // display the answer file selected
		ansSource.setBounds(70, 80, 500, 40);
	    countainer.add(ansSource);
		
	    // prompt for marked file submission
		markFilePrompt.setBounds(50, 160, 250, 40);
		countainer.add(markFilePrompt);
		
		// set the button for student submission
		button2.setBounds(300, 160, 100, 40);
		countainer.add(button2);
		
        
		markSource.setBounds(70, 190, 500, 40);
		countainer.add(markSource);
		
		// marking button
		button3.setBounds(440, 105, 100, 40);
		countainer.add(button3);
		
		
		// display marking overview
	    overview.setBounds(230, 220, 500, 100);
	    countainer.add(overview);
		
		// file selection for answer
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Please select a file");
				
				// restriction for xml file only
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML(*.xml;*.xmi)", "xml", "xmi");
				fc.setFileFilter(filter);

				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePath = fc.getSelectedFile().getPath();
					layout.sourcefile.setText(filePath);
					System.out.println(filePath + " is selected");
					
					// enable button3(marking), only when passed validation
					button3.setEnabled(validate(ansText,markText));
				}
				
				// set the selected files path for display
				ansText = filePath;
				ansFilePath = filePath;
				ansSource.setText(filePath);

				
				ansSource.setHorizontalTextPosition(JLabel.CENTER);
			}
       
		});

	


		
	
		// file selection for student submission
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Please select a file");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML(*.xml;*.xmi)", "xml", "xmi");
				fc.setFileFilter(filter);

				// enable multi-selection
				fc.setMultiSelectionEnabled(true);

				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					layout.sourcefile.setText(filePath);
					System.out.println(filePath + " is selected");
				}
				
				// set the selected files number for display
				files = fc.getSelectedFiles();

				fileNum = files.length;
				System.out.println("{" + fileNum + "} file(s) is/are selected");
				markText = "{" + fileNum + "} file(s) is/are selected";
				markSource.setText(markText);
				if(fc.getSelectedFile()!=null){
				markFilePath = files[0].getPath();
				
				// enable button3(marking), only when passed validation
				button3.setEnabled(validate(ansText,markText));
				}

			}

		});

		
		
	

	
        // set the marking button as disable mode initially
		button3.setEnabled(false);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (ansFilePath != null && markFilePath != null) {

					// use the answer file path for parsing
					ansParser = new DOMParser(ansFilePath);
					ansClassList = ansParser.getClassInfo();
					ansRelationList = ansParser.getRelationInfo();
					
					setCorpus(ansClassList);

					System.out.println(files.length);
					// for each student files selected
					for (int i = 0; i < files.length; i++) {
						
						
						
						
						markFilePath = files[i].getAbsolutePath();
						System.out.println(markFilePath);
						
						
						
						ArrayList<Relationship> markRelationList = new ArrayList<Relationship>();
						ArrayList<MClass> markClassList = new ArrayList<MClass>();
						
						// use the mark file path for parsing
						markParser = new DOMParser(markFilePath);
						markClassList = markParser.getClassInfo();
						markRelationList = markParser.getRelationInfo();

						System.out.println(markClassList.size());
						System.out.println(ansClassList.size());
						
						System.out.println(markRelationList.size());
						System.out.println(ansRelationList.size());
						
						Result r = new Result();
						
						

						try {
							typo = checkSpelling(markClassList,markRelationList);
							r.setTypo(typo);
							System.out.println(typo);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						

						
						
						

						ClassMarker.executeMarker(ansClassList, markClassList);
						AttrMarker.executeMarker(ansClassList, markClassList);
						OpMarker.executeMarker(ansClassList, markClassList);
						RelationMarker.executeMarker(ansClassList, markClassList, ansRelationList, markRelationList);

						

						// set class related
						r.setClassFeedback(ClassMarker.getFeedBack());
						r.setClassRate(ClassMarker.getClassRate());
						r.setClassExtra(ClassMarker.getExtra());
						r.setClassMiss(ClassMarker.getClassMiss());
						
						System.out.println(ClassMarker.getClassRate());

						// set attribute related
						r.setAttrFeedback(AttrMarker.getFeedBack());
						r.setAttrRate(AttrMarker.getAttrRate());
						r.setAttrDeRate(AttrMarker.getAttrDeRate());
						r.setAttrExtra(AttrMarker.getExtra());
						r.setAttrMiss(AttrMarker.getAttrMiss());
						r.setAttrDiff(AttrMarker.getAttrDiff());
						
//						System.out.println(AttrMarker.getAttrRate());
//						System.out.println(AttrMarker.getAttrDeRate());
						
						

						// set operation related
						r.setOpFeedback(OpMarker.getFeedBack());
						r.setOpRate(OpMarker.getOpRate());
						r.setOpDeRate(OpMarker.getOpDeRate());
						r.setOpExtra(OpMarker.getExtra());
//						System.out.println(OpMarker.getOpRate());
//						System.out.println(OpMarker.getOpDeRate());
						r.setOpMiss(OpMarker.getOpMiss());
						r.setOpDiff(OpMarker.getOpDiff());


						// set relationship related
						r.setRelationFeedback(RelationMarker.getFeedBack());
						r.setRelationRate(RelationMarker.getRelationRate());
						r.setRelationDeRate(RelationMarker.getReDeRate());
						r.setRelationExtra(RelationMarker.getExtra());
//						System.out.println(RelationMarker.getRelationRate());
//						System.out.println(RelationMarker.getReDeRate());
						r.setRelationMiss(RelationMarker.getRelationMiss());
						r.setRelationDiff(RelationMarker.getRelationDiff());
						
						
						
						

						// calculate and set mark
						r.setMark(calcMark(r));
						r.setExplanation(markExplanation(r));
						
						
						
                       
						fileName = files[i].getName();
						
						//get student name, pick letter a-z as student name
						String regEx1="[^A-Za-z]";   
						Pattern p1 = Pattern.compile(regEx1);   
						Matcher mName = p1.matcher(fileName); 
						String StuName = mName.replaceAll("").trim();
						r.setName(StuName.substring(0, StuName.length()-3));
						
						
						//get student ID,pick number 0-9 as student ID
						String regEx2="[^0-9]";   
						Pattern p2 = Pattern.compile(regEx2);   
						Matcher mID = p2.matcher(fileName); 
						String StuID = mID.replaceAll("").trim();
						r.setStuID(StuID);
						
						
						resultList.add(r);
					
						

					}
					exportExcel(resultList);
					markOverview = markOverview(resultList);
					overview.setText(markOverview);
					// get end time
					endTime = System.currentTimeMillis(); 
					System.out.println("Time： " + (endTime - startTime) + "ms");
				}
			}
		});
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "Exit Automatic marking tool?", "Confirm Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		
	}
	
   
    // examine whether both answer and mark files are selected
	public boolean validate(String s1,String s2){
		   
		   if(!s1.equals("")&&!s2.equals("")){
		     return true;
		   }else{
		     return false;
		   }
		}
	
	public static String checkSpelling(ArrayList<MClass> classList,ArrayList<Relationship> relationList) throws IOException {
        String typo = "";
		for (MClass m : classList) {
			String oldName = m.getName();
			System.out.println("Current class is : " + m.getName());
			String str = m.getName();

			System.out.println("Current word is : " + str);

			SpellCheck sc = new SpellCheck(str);
			List<String> wordlist = new ArrayList<String>();
			wordlist = sc.words();
			HashMap<String, Counter> hashMap = new HashMap<String, Counter>();
			hashMap = sc.frequency(wordlist);

			List<String> cwordList = new ArrayList<String>();
			
			//remove characters except letter (a-z& A-Z)
			str = str.replaceAll("\\pP|\\pS|\\pM|\\pN|\\pC", "");

			String[] tt = new String[100];
			tt = str.split(" ");
			for (int i = 0; i < tt.length; i++) {
				Set<SetElement> set = new HashSet<SetElement>();
				Set<?> set1 = new HashSet<Object>();
				set = sc.getEdits1(tt[i]);
				set1 = sc.existOrNot(set, hashMap);
				Set<String> set2 = new HashSet<String>();
				set2 = sc.getEdits2(set, hashMap);
				String cword = sc.correct(tt[i], wordlist, hashMap, set1, set2);
				cwordList.add(cword);
			}
			System.out.print("Correct word is{");
			for (int i = 0; i < cwordList.size(); i++) {
				 System.out.println(cwordList.get(i)+"}");
				
				//set the correct name in class
				m.setName(cwordList.get(i));
				 
				if(!oldName.equalsIgnoreCase(cwordList.get(i))){
					typo += "Potential spelling mistake ("+oldName +"," + cwordList.get(i)+")\n";
					
				}
				
				//set the correct name in relation
				for(Relationship r:relationList){
					if(oldName.equalsIgnoreCase(r.getClassPair()[0])){
						r.setClass(0, cwordList.get(i));
					}
					if(oldName.equalsIgnoreCase(r.getClassPair()[1])){
						r.setClass(1, cwordList.get(i));
					}
					
				
				
				 System.out.println("New name: "+cwordList.get(i));

			}

		}

	}
		return typo;
	}
	
	
	
	public static void setCorpus(ArrayList<MClass> classList) {

		String filePath = "/Users/Agnes/Desktop/big.txt";
		try {
			File file = new File(filePath);
			ps = new PrintStream(new FileOutputStream(file));

			for (MClass m : classList) {
				
				for (String s : m.className) {
					
					for (int i = 0; i < 100; i++) {
						ps.append(s);
						ps.append("\n");
					}
					System.out.print("done!");
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public float calcMark(Result r) {
		float mark = 0;

	float reduceMark = (float) ((100-r.getClassRate())*1.2
				+ (100-r.getAttrRate()) 
				+(100-r.getAttrDeRate()) *0.5
				+ (100-r.getOpRate()) 
				+(100-r.getOpDeRate()) *0.5
				+ (100-r.getRelationRate()) 
				+(100-r.getRelationDeRate()) *0.5);
	
				
	mark = 100- reduceMark;
	if (mark >= 40){
		return mark;
	}
	if (mark >= 30 & mark<40){

		return 40;
	}
	else{
		return 30;
	}
	}
	public void exportExcel(ArrayList<Result> RL){
		wb = new HSSFWorkbook();  
          
        //build sheet  
        HSSFSheet sheet = wb.createSheet("Marking result");  
          
        //create row 0 
        HSSFRow row = sheet.createRow(0);  
        HSSFCellStyle  style = wb.createCellStyle();      
     
        
        //creat cell  
        HSSFCell cell = row.createCell(0);         //the first row
        cell.setCellValue("Name");                  //set the value
        cell.setCellStyle(style);                   //set the style  
          
        cell = row.createCell(1);                   //the second row   
        cell.setCellValue("ID");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(2);                   //the third row     
        cell.setCellValue("Match %");  
        cell.setCellStyle(style);  
          
        cell = row.createCell(3);                   //the forth row    
        cell.setCellValue("Class");  
        cell.setCellStyle(style);  
          
        cell = row.createCell(4);                   //the fifth row    
        cell.setCellValue("Attribute");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(5);                   //the sixth row   
        cell.setCellValue("Operation");  
        cell.setCellStyle(style); 
        
        cell = row.createCell(6);                   //the seventh row   
        cell.setCellValue("Relation");  
        cell.setCellStyle(style);  
          
       
        cell = row.createCell(7);                   //the eighth row
        cell.setCellValue("Extra");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(8);                   //the ninth row
        cell.setCellValue("Spelling mistake");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(9);                   //the ninth row
        cell.setCellValue("Mark explanation");  
        cell.setCellStyle(style);  
        
        
       
         // insert value
        for (int i = 0; i < RL.size(); i++) {  
            Result r = RL.get(i);  
            //create row  
            row = sheet.createRow(i+1);  
            //creat cell and set value 
            row.createCell(0).setCellValue(r.getName());  
            row.createCell(1).setCellValue(r.getStuID());  
            row.createCell(2).setCellValue(r.getMark());  
            row.createCell(3).setCellValue(r.getClassFeedback());  
            row.createCell(4).setCellValue(r.getAttrFeedback());  
            row.createCell(5).setCellValue(r.getOpFeedback());  
            row.createCell(6).setCellValue(r.getRelationFeedback());  
            row.createCell(7).setCellValue(r.getClassExtra()+";\n"
            		+r.getAttrExtra() +";\n"
            		+r.getOpExtra() +";\n"
            		+r.getRelationExtra() +";\n"
            		); 
            row.createCell(8).setCellValue(r.getTypo());
            row.createCell(9).setCellValue(r.getExplanation());
        }  
          
        //export excel and set the filePath  
        try {  
        	
        	//****** set the file path for excel export here*******
            FileOutputStream fout = new FileOutputStream("/Users/Agnes/Desktop/markingResult.xls");  
            wb.write(fout);  
            fout.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        System.out.println("Excel exported successfully...");  
    }  
	
	public String markOverview(ArrayList<Result> RL){
		String overview = "";
		
		
		// total file number
		int totalFileNum = RL.size();
		overview += "<html><body>{"+totalFileNum +"} files have been marked";
		
		//get average/max/min
		float average = 0;
		float sum = 0;
		float max = 0;
		float min =0;
		
		ArrayList<Float> fRL = new ArrayList<Float>();
		
	
		for(Result r:RL){
			// get sum of all the mark
			sum += r.getMark();
			fRL.add(r.getMark());
			
		}
		max = Collections.max(fRL);
		min = Collections.min(fRL);
		
				
		
		
		average = sum / totalFileNum;
		overview += "<br>Average match rate: {" + (float)(Math.round(average*100))/100+"}";
		overview += "<br>Highest match rate: {" + (float)(Math.round(max*100))/100+"}";
		overview += "<br>Lowest match rate: {" + (float)(Math.round(min*100))/100+"}</body></html>";
				
		System.out.println(overview);
		
		return overview;
	}
	
	public String markExplanation(Result r){
		String explanation = "";
		
		// Class explanation
		if(r.getClassMiss() != 0){
			float rMark = (float) ((100-r.getClassRate())*1.2);
			explanation += "{" + r.getClassMiss() +"} Class(es) Missing, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Class;\n";
		}
		
		if(r.getAttrMiss() != 0){
			float rMark = (float) (100-r.getAttrRate());
			explanation += "{" + r.getAttrMiss() +"} Attribute(s) Missing, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Attribute existance;\n";
		}
		
		if(r.getAttrDiff() != 0){
			float rMark = (float) ((100-r.getAttrDeRate())*0.5);
			explanation += "{" + r.getAttrMiss() +"} Attribute(s) mismatch, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Attribute accuracy;\n";
		}
		
		if(r.getOpMiss() != 0){
			float rMark = (float) (100-r.getOpRate());
			explanation += "{" + r.getOpMiss() +"} Operation(s) Missing, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Operation existance;\n";
		}
		
		if(r.getOpDiff() != 0){
			float rMark = (float) ((100-r.getOpDeRate())*0.5);
			explanation += "{" + r.getOpDiff() +"} Operation(s) mismatch, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Operation accuracy;\n";
		}
		if(r.getRelationMiss() != 0){
			float rMark = (float) (100-r.getRelationRate());
			explanation += "{" + r.getRelationMiss() +"} Relation(s) Missing, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Relation existance;\n";
		}
		
		if(r.getRelationDiff() != 0){
			float rMark = (float) ((100-r.getRelationDeRate())*0.5);
			explanation += "{" + r.getRelationDiff() +"} Relation(s) mismatch, -"+ (float)(Math.round(rMark*100))/100+";\n";
		}else{
			explanation += "No mark reduction for Relation accuracy;\n";
		}
		
		
		
		
		
		return explanation;
	}
	
}


