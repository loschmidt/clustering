package loschmidt.clustering.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;
import loschmidt.clustering.hierarchical.Tree;

/**
 *
 * @author Jan Stourac
 */
public class SimpleTreeReader extends TreeReader {

	@Override
	public Tree readTree(Reader in) throws IOException {
		Tree tree = new Tree();
		BufferedReader br = new BufferedReader(in);
		String line;
		while (null != (line = br.readLine())) {

			if (line.trim().length() == 0) {
				continue;
			}

			StringTokenizer st = new StringTokenizer(line, "\t ;");
			tree.add(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Float.parseFloat(st.nextToken()));
		}

		return tree;
	}

}
