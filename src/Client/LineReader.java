package Client;

import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LineReader {

	ArrayList<Line2D> lines = new ArrayList<>();
	
	
	public LineReader() {
		try (BufferedReader br = new BufferedReader(new FileReader("src/files/houses.svg"))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	        	if(line.contains("line")) {
	        		line.split(" ");
	        		System.out.println(line);
	        	}
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
