package agentesdabolsa.entity.elk;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	
	private long took;
	
	private Hits[] hits = new Hits[10];

	public long getTook() {
		return took;
	}

	public void setTook(long took) {
		this.took = took;
	}

	public Hits[] getHits() {
		return hits;
	}

	public void setHits(Hits[] hits) {
		this.hits = hits;
	}




}
