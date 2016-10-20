package loschmidt.clustering.beststays;

import java.util.ArrayList;
import java.util.List;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class BestStaysOutput extends ClusteringOutput {

	public List<Integer> getCores() {
		List<Integer> cores = new ArrayList<>();
		for (IndexCluster cluster : this) {
			cores.add(((BestStaysCluster) cluster).getCore());
		}

		return cores;
	}

}
