package loschmidt.clustering.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

/**
 *
 * @author Jan Stourac
 */
public class DistanceMatrix {

	public static final float DEFAULT_VALUE = -1.0f;

	private final float[][] triangle_;
	private final int size_;

	public DistanceMatrix(int size) {
		this.size_ = size;
		this.triangle_ = new float[size - 1][];
		for (int i = 0; i < size - 1; i++) {
			triangle_[i] = new float[i + 1];
			Arrays.fill(triangle_[i], DEFAULT_VALUE);
		}
	}

	public void set(int x, int y, float value) {
		assert x != y;
		if (y < x) {
			triangle_[x - 1][y] = value;
		} else {
			triangle_[y - 1][x] = value;
		}
	}

	/**
	 * Important it returns double to prevent rounding errors when computing
	 * with returned value.
	 */
	public double get(int x, int y) {
		assert x != y;
		return y < x ? triangle_[x - 1][y] : triangle_[y - 1][x];
	}

	public int size() {
		return size_;
	}

	public void save(File out) throws IOException {
		try (FileWriter fw = new FileWriter(out)) {
			save(fw);
		}
	}

	public void save(Writer out) throws IOException {
		BufferedWriter bw = new BufferedWriter(out);
		bw.write(size_ + "");
		bw.newLine();
		for (int i = 0; i < triangle_.length; ++i) {
			float[] row = triangle_[i];
			for (int j = 0; j < row.length; ++j) {
				bw.write(i + "\t" + j + "\t" + row[j]);
				bw.newLine();
			}
		}
		bw.flush();
	}

	public static DistanceMatrix load(File in) throws IOException {
		try (FileReader fr = new FileReader(in)) {
			return load(fr);
		}
	}

	public static DistanceMatrix load(Reader in) throws IOException {
		BufferedReader br = new BufferedReader(in);
		String line = br.readLine().trim();
		DistanceMatrix matrix = new DistanceMatrix(Integer.parseInt(line));
		while ((line = br.readLine()) != null) {
			line = line.trim();

			if (line.isEmpty()) {
				continue;
			}

			String[] tokens = line.split("\t");
			matrix.set(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Float.parseFloat(tokens[2]));
		}

		return matrix;
	}
}
