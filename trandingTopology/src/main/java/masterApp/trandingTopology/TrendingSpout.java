package masterApp.trandingTopology;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class TrendingSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841070233462916618L;
	private SpoutOutputCollector collector;
	public static final String CURRENCYFIELDNAME = "twitID";
	public static final String CURRENCYOUTSTREAM = "twitStream";

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		Values randomValue = new Values(TrendingInput.getRandomTwit());
		//System.out.println("emitting " + randomValue);
		collector.emit(CURRENCYOUTSTREAM, randomValue);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream(CURRENCYOUTSTREAM, new Fields(CURRENCYFIELDNAME));
	}
}