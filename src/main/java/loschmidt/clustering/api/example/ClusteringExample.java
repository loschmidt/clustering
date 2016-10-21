package loschmidt.clustering.api.example;

import java.io.File;
import java.io.IOException;

import loschmidt.clustering.api.ClusterFactory;
import loschmidt.clustering.api.Clusters;
import loschmidt.clustering.hierarchical.CompleteLinkage;
import loschmidt.clustering.hierarchical.Tree;
import loschmidt.clustering.hierarchical.WeightedAverageLinkage;
import loschmidt.clustering.hierarchical.murtagh.MurtaghParams;
import loschmidt.clustering.io.MCUPGMATreeReader;
import loschmidt.clustering.io.MCUPGMATreeWriter;
import loschmidt.clustering.io.SimpleTreeWriter;

public class ClusteringExample {

    private final String home = "/Users/antonin/data/clustering_example/";
    private final File treeFile = new File(home + "tree");
    private final File treeFileCompatible = new File(home + "tree_compatible");
    private final int elements = 10;
    private final int dimensions = 1;
    private final double clusteringThreshold = 0.1;
    private final boolean complete = true;
    private int murtaghMatrixSize = 100000;

    private void cluster() {
        EuclideanSpace matrix = new EuclideanSpace(elements, dimensions);        
        MurtaghParams params = new MurtaghParams().setDistanceMatrixThreshold(murtaghMatrixSize);
        if (complete) {
            params.setLinkage(new WeightedAverageLinkage<>());
        } else {
            params.setLinkage(new CompleteLinkage<>());
        }                
        ClusterFactory<VectorInstance> cf = new ClusterFactory<>(matrix, params);
        Clusters<VectorInstance> cs = cf.cluster(clusteringThreshold);
        cs.print();
    }

    private void saveTree(Tree tree) {
        try {	
            new SimpleTreeWriter().write(tree, treeFile);
            new MCUPGMATreeWriter().write(tree, treeFileCompatible);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Tree loadTree() {
        try {
            Tree tree = new MCUPGMATreeReader().readTree(treeFileCompatible);
            tree.generateMergeIDs();
            return tree;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ClusteringExample m = new ClusteringExample();
        m.cluster();
    }

}
