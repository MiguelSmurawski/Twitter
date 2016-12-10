package masterApp.trandingTopology;

public class TrendingInput { 
	public enum LangList{
		EN("house"), PL("universidade"), AR("carro"), ES("ordenador");
		private String value;
		private LangList(String value){ 
			this.value=value; 
		}
		public static String getValue(LangList lang){ 
			return lang.value; 
		}
	}
	public enum Twit{
		EN_0("en:house"),
		EN_1("en:elevator"),
		EN_2("en:television"),
		EN_3("en:headphones"),
		EN_4("en:swagg"),
		EN_5("en:lamp"),
		EN_6("en:telephone"),
		PL_0("pl:universidade"), 
		PL_1("pl:poster"), 
		PL_2("pl:desenho"), 
		PL_3("pl:ovo"), 
		PL_4("pl:universidade"), 
		PL_5("pl:chapéu"), 
		PL_6("pl:livro"), 
		AR_0("ar:carro"),
		AR_1("ar:asd"),
		AR_2("ar:dfg"),
		AR_3("ar:hjk"),
		AR_4("ar:qwe"),
		AR_5("ar:rty"),
		AR_6("ar:uio"),
		ES_0("es:ordenador"),
		ES_1("es:teclado"),
		ES_2("es:nevera"),
		ES_3("es:noticias"),
		ES_4("es:caja"),
		ES_5("es:petardo"),
		ES_6("es:zapatos");
		private String value;
		private Twit(String value){ 
			this.value=value; 
		}
	}
	public static Twit getRandomTwit(){ 
		int index = (int) (Math.random()*28); 
		return Twit.values()[index]; 
	}
	public static String getValue(Twit twit){ 
		return twit.value; 
	}
	
}