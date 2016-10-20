package loschmidt.clustering.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import loschmidt.clustering.hierarchical.Merge;
import loschmidt.clustering.hierarchical.Tree;

/**
 *
 * @author Jan Stourac
 */
public class SimpleTreeWriter extends TreeWriter {

	@Override
	public void write(Tree tree, Writer out) throws IOException {
		BufferedWriter bw = new BufferedWriter(out);
		for (Merge m : tree.getMerges()) {
			bw.write((m.getX() + 1) + "\t" + (m.getY() + 1) + "\t" + m.getDistance());
			bw.newLine();
		}
		bw.flush();
	}
}
