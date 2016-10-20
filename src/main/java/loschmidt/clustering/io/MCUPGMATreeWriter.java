package loschmidt.clustering.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import loschmidt.clustering.hierarchical.Merge;
import loschmidt.clustering.hierarchical.Tree;

/**
 * Writes tree in the MCUPGMA program file format. For the program itself, see
 * Loewenstein Y, Portugaly E, Fromer M, Linial M. Efficient algorithms for
 * accurate hierarchical clustering of huge datasets: tackling the entire
 * protein space. Bioinformatics 2008 24: i41-i49; Presented at ISMB 2008,
 * Toronto.
 *
 * http://www.protonet.cs.huji.ac.il/mcupgma/
 *
 * @author Antonin Pavelka
 * @author Jan Stourac
 */
public class MCUPGMATreeWriter extends TreeWriter {

	@Override
	public void write(Tree tree, Writer out) throws IOException {
		tree.generateMergeIDs();
		BufferedWriter bw = new BufferedWriter(out);
		// for every merge create new cluster with ID starting above size of tree
		Map<Integer, Integer> map = new HashMap<>();
		float last = Float.NEGATIVE_INFINITY;
		for (Merge m : tree.getMerges()) {
			int x = m.getX();
			int y = m.getY();
			if (map.containsKey(x)) {
				x = map.get(x);
			}
			if (map.containsKey(y)) {
				y = map.get(y);
			}
			float d = m.getDistance();
			if (d < last) {
				throw new IOException("Clustering tree is not correct "
						+ "UPGMA tree, merging may end prematurely and cluster "
						+ "sizes may be smaller than should be at given "
						+ "threshold!");
			}
			bw.write((x + 1) + "\t" + (y + 1) + "\t" + d + "\t" + (m.getID() + 1));
			bw.newLine();
			map.put(m.getX(), m.getID());
		}
		bw.flush();
	}
}
