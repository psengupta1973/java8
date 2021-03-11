

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* A Java8 sample application*/

public class CSVDataSet {

    String[] headers = null;
    List<String[]> csvLines = null;
    
    public String[] headers(){
        return headers;
    }

    public List<String[]> lines(){
        return csvLines;
    }

    public Map<String, Integer> getColumnNameIds(){
        Map<String, Integer> colNameVsId = new HashMap<String, Integer>();
        if(headers != null){
            for(int i=0; i<headers.length; i++){
                colNameVsId.put(headers[i], i);
            }
        }
        return colNameVsId;
    }

    public void readFromFile(String fileName, String delim, boolean headerExists) {
        csvLines = new ArrayList<String[]>();
        try{ 
            List<String> lines = Collections.emptyList();
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8); 
            if(lines.size() > 0 && headerExists){
                headers = lines.get(0).split("\\"+delim);
                lines.remove(0);
            }
            lines.stream().forEach(line -> csvLines.add(line.split("\\"+delim)));
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    public void loadFromStream(String fileName, String delim, boolean headerExists){
        csvLines = new ArrayList<String[]>();
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))){
            int skipCount = 0;
            if(headerExists){
                skipCount = 1;
                headers = reader.lines().findFirst().get().split("\\"+delim);
            }
            reader.lines().skip(skipCount).forEach(line -> csvLines.add(line.split("\\"+delim)));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void print(){
        Consumer<String> c1 = s -> System.out.print(s+"   ");
        if(headers != null)
            Arrays.asList(headers).stream().forEach(c1);
        if(csvLines != null){
            Consumer<String[]> c2 = arr -> Arrays.asList(arr).stream().forEach(c1);
            Consumer<String[]> c3 = arr -> System.out.println();
            csvLines.stream().forEach(c2.andThen(c3));
        }
    }

    public static void main(String[] args) {
        CSVDataSet csv = new CSVDataSet();
        //csv.readFromFile("C:\\Users\\eparsen\\Workspace\\DEVSPACE\\test_data\\RFID.csv", "|", true);
        csv.loadFromStream("C:\\Users\\eparsen\\Workspace\\DEVSPACE\\test_data\\RFID.csv", "|", true);
        csv.print();
     }
}