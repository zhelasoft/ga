package ga;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Export {

	public static List<String> header() {
		return Arrays.asList(
				"Generation", 
				"Best Candidate Fitness", 
				"Best Candidate Standard Deviation", 
				"Mean Fitness", 
				"Elapsed Time(Milli Second)",
				"Best Candidate"
		);	
	}
	
	public static List<String> methodInfoHeader() {
		return Arrays.asList(
				"Class name", 
				"Method name", 
				"Return type",
				"Parameters"
		);	
	}
	public static void toCsv(int round, String[] methodInfo, ArrayList<String[]> data) throws IOException {
		round += 1;
		String methodName = methodInfo[1];
		String csvFileName = Export.getFileName(round, methodName);
		
		FileWriter out = new FileWriter(csvFileName);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
	    	.withFirstRecordAsHeader())) {
	    	printer.printRecord(Export.methodInfoHeader());
	    	printer.printRecord(methodInfo);
	    	printer.printRecord(Arrays.asList(""));
	    	printer.printRecord(Export.header());
	    	for(String[] record: data) {
	    		printer.printRecord(record);
	    	}
	    	printer.flush();
	    	System.out.println("Finished exporting file to " + csvFileName + " \t of method " + methodName  + " \t for round " + round );
	    }
	}
	
	public static String getFileName(int round, String methodName) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		return "./exports/ga_" + methodName + "_round_" + round + "_" + formatter.format(date) + ".csv";
	}

}
