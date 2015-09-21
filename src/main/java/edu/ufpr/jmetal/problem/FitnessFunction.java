package edu.ufpr.jmetal.problem;


import java.util.function.Function;

import edu.ufpr.cluster.algorithm.ClusteringContext;

public interface FitnessFunction extends Function<ClusteringContext, Double> {

}
