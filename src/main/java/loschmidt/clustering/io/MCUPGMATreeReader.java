package loschmidt.clustering.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import loschmidt.clustering.hierarchical.Tree;

/**
 * Loads tree in the MCUPGMA program file format. For the program itself, see
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
public class MCUPGMATreeReader extends TreeReader {

	@Override
	public Tree readTree(Reader in) throws IOException {
		BufferedReader br = new BufferedReader(in);
		Map<Integer, Integer> map = new HashMap<>();
		Tree tree = new Tree();
		String line;
		double dist = 0;
		double last;
		boolean noViolation = true;
		while ((line = br.readLine()) != null && noViolation) {
			if (line.trim().length() == 0) {
				continue;
			}

			StringTokenizer st = new StringTokenizer(line, "\t ;");

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			dist = Double.parseDouble(st.nextToken());
			int c = Integer.parseInt(st.nextToken()) - 1;
			last = dist;

			if (map.containsKey(a)) {
				a = map.get(a);
			}
			if (map.containsKey(b)) {
				b = map.get(b);
			}
			map.put(c, a);

			if (dist < last) {
				noViolation = false;
				throw new IOException("Tree is not valid, only merging operations "
						+ "with distances up to " + last + " were used, "
						+ "clustering granularity might be smaller "
						+ "than desired.");
			}

			tree.add(a, b, (float) dist);
		}

		return tree;
	}
}
