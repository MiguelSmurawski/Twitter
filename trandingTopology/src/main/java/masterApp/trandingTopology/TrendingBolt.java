package masterApp.trandingTopology;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import masterApp.trandingTopology.TrendingInput.Twit;

public class TrendingBolt extends BaseRichBolt {
	private String lang;
	private String window;
	private String folder;
	private int count;
	private Map<String, Integer> topics = new HashMap <String, Integer> ();

	
	public TrendingBolt(String lang, String window) {
		this.lang=lang;
		this.window=window;
		this.count=1;
	}

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		folder = (String) stormConf.get("path");
	}
	
	static <String,Integer extends Comparable<? super Integer>> 
    List<Entry<String, Integer>> entriesSortedByValues(Map<String,Integer> map) {

		List<Entry<String,Integer>> sortedEntries = new ArrayList<Entry<String,Integer>>(map.entrySet());

		Collections.sort(sortedEntries, 
		    new Comparator<Entry<String,Integer>>() {
		        @Override
		        public int compare(Entry<String,Integer> e1, Entry<String,Integer> e2) {
		            return e2.getValue().compareTo(e1.getValue());
		        }
		    }
		);
		
		return sortedEntries;
	}

	public void execute(Tuple input) {
		
		Twit twitID = (Twit) input.getValueByField(TrendingSpout.CURRENCYFIELDNAME);
		String twit = TrendingInput.getValue(twitID);
		String [] decomposedTwit= twit.split(":");
		String lang = decomposedTwit[0];
		String topic = decomposedTwit[1];
		
		if (lang.equals(this.lang)){
			if (topics.get(topic)==null){
				topics.put(topic,1);
			} else{
				topics.put(topic, topics.get(topic) + 1);
				System.out.println(topics.get(topic));
			}
			
			if (topic.equals(this.window)){
				List<Entry<String,Integer>> sortedEntries=entriesSortedByValues(topics);
				String top1=null;
				int top1Val=0;
				String top2=null;
				int top2Val=0;
				String top3=null;
				int top3Val=0;
				
				try{
					top1=sortedEntries.get(0).getKey();
			    	top1Val=sortedEntries.get(0).getValue();
			    	System.out.println(top1 + "   "+ top1Val);
				}catch(java.lang.IndexOutOfBoundsException e){
					
				}
				try{
			    	top2=sortedEntries.get(1).getKey();
			    	top2Val=sortedEntries.get(1).getValue();
			    	//System.out.println(top2 + "   "+ top2Val);
				}catch(java.lang.IndexOutOfBoundsException e){
					
				}
				try{
			    	top3=sortedEntries.get(2).getKey();
			    	top3Val=sortedEntries.get(2).getValue();
			    	//System.out.println(top3 + "   "+ top3Val);
				}catch(java.lang.IndexOutOfBoundsException e){
					
				}
				String aux;
				while (true){
					if (top1Val==top2Val && top1Val!=0 && top2Val!=0 && top1.compareTo(top2)>0){
						aux=top1;
						top1=top2;
						top2=aux;
						continue;
					}
					if (top2Val==top3Val && top2Val!=0 && top3Val!=0 && top2.compareTo(top3)>0){
						aux=top2;
						top2=top3;
						top3=aux;
						continue;
					}
					if (top1Val==top3Val && top1Val!=0 && top3Val!=0 && top1.compareTo(top3)>0){
						aux=top1;
						top1=top3;
						top3=aux;
						continue;
					}
						
					break;
				}
				
				try {
					
					Writer output;
					output = new BufferedWriter(new FileWriter(this.folder + this.lang + "_22.log", true));
					output.append(count++ + ", " + this.lang + ", " + top1 + ", " + top1Val + ", "+ top2 + ", " + top2Val + ", "+ top3 + ", " + top3Val+ "\n");
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				topics=new HashMap <String, Integer> ();
			}
		}
		//System.out.println("LANGUAGE: " + lang + ", TOPIC " + topic);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}
