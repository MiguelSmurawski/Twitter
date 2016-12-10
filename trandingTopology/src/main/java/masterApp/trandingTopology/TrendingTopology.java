package masterApp.trandingTopology;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class TrendingTopology {
	
	public static void main(String[] args) {
		
		String csvFile = args[0];
		List<String> langs = new ArrayList<String>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

            	List<String> newList = Arrays.asList(line.split(cvsSplitBy));
            	langs.addAll(newList);
            	//System.out.println(newList);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("trendingSpout", new TrendingSpout());
		Config conf=new Config();

    	conf.put("path", args[3]);
        for (String lang : langs){
        	String language;
        	String window;
        	
        	String [] res= lang.split(":");
        	language=res[0];
        	window=res[1];
        	//System.out.println(language + "     " + window);
        	
        	builder.setBolt("converterBolt_" + language, new TrendingBolt(language, window)).localOrShuffleGrouping("trendingSpout",
    				TrendingSpout.CURRENCYOUTSTREAM);
        }
		
		
		
		
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(args[2], conf, builder.createTopology());
		
		Utils.sleep(10000);
		
		cluster.killTopology(args[2]);
		
		cluster.shutdown();
	}
}
