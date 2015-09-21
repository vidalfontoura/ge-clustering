/*
 * Copyright 2015, Charter Communications,  All rights reserved.
 */
package edu.ufpr.cluster.algorithms.execution.test;

import com.google.common.collect.Lists;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.Function;
import edu.ufpr.cluster.random.ClusteringRandom;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.problem.FitnessFunction;
import edu.ufpr.jmetal.problem.SimpleClusteringFitness;
import edu.ufpr.jmetal.problem.old.impl.DataInstanceReader;

/**
 *
 *
 * @author Vidal
 */
public class ExecutionTestClusteringAlgorithms {

    private ClusteringExpressionGrammarMapper mapper;

    private FitnessFunction fitnessFunction;

    @Before
    public void setup() {

        mapper = new ClusteringExpressionGrammarMapper();
        mapper.loadGrammar("/clustergrammar.bnf");
        ClusteringRandom.getNewInstance().setSeed(100);
        fitnessFunction = new SimpleClusteringFitness();

    }

    /**
     * <pre>
     *  24,120,154,179,66,23,18,32,99,43,101,229,225,146,217,124,245,195,238
     * 
     * 
     * </pre>
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * 
     *
     */
    @Test
    public void testAlgorithmGenerated1() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance =
            Lists.newArrayList(24, 120, 154, 179, 66, 23, 18, 32, 99, 43, 101, 229, 225, 146, 217, 124, 245, 195, 238);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("RandomCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ChebyshevDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(9, algorithm.getInitialK());
        Assert.assertTrue(algorithm.getFunctions().size() == 1);

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);

        Assert.assertEquals("MoveBetweenClustersFunction", function0.toString());

        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();
        List<Point> points2 = clusteringContext.getPoints();
        for (Point p : points2) {
            System.out.println(p);
            System.out.println(p.getCluster());
            System.out.println();
        }

        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated2() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance = Lists.newArrayList(14, 243, 33, 38, 243, 33, 38, 84);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ChebyshevDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(10, algorithm.getInitialK());
        Assert.assertEquals(2, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);

        Assert.assertEquals("MoveBetweenClustersFunction", function0.toString());
        Assert.assertEquals("MoveNearPointFunction", function1.toString());

        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();


         List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println(cluster.getCentroid());
            System.out.println(cluster.getPoints().size());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated3() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance = Lists.newArrayList(1, 125, 239, 42, 167, 37, 39, 224, 214, 61, 151, 9, 213, 85,
            193, 87, 135, 170, 8, 94, 189);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("EucledianDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(9, algorithm.getInitialK());
        Assert.assertEquals(3, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);

        Assert.assertEquals("MoveAveragePointFunction", function0.toString());
        Assert.assertEquals("MoveNearPointFunction", function1.toString());

        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();

        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println(cluster.getCentroid());
            System.out.println(cluster.getPoints().size());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated4() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance = Lists.newArrayList(14, 243, 33, 38, 21);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ChebyshevDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(10, algorithm.getInitialK());
        Assert.assertEquals(3, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);
        Function<ClusteringContext> function2 = algorithm.getFunctions().get(2);

        Assert.assertEquals("MoveNearPointFunction", function0.toString());
        Assert.assertEquals("MoveBetweenClustersFunction", function1.toString());
        Assert.assertEquals("SplitClustersFunction", function2.toString());

        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();


        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println(cluster.getCentroid());
            System.out.println(cluster.getPoints().size());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated5() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance = Lists.newArrayList(139, 120, 194, 181, 201, 209, 167, 3, 67, 236, 206, 142, 120,
            159, 179, 66, 23, 18, 32, 99, 43, 101, 229, 225, 146, 217, 124, 107, 195, 33);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("RandomCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ManhattanDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(4, algorithm.getInitialK());
        Assert.assertEquals(4, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);
        Function<ClusteringContext> function2 = algorithm.getFunctions().get(2);
        Function<ClusteringContext> function3 = algorithm.getFunctions().get(3);

        Assert.assertEquals("MoveNearPointFunction", function0.toString());
        Assert.assertEquals("MoveBetweenClustersFunction", function1.toString());
        Assert.assertEquals("SplitClustersFunction", function2.toString());
        Assert.assertEquals("MoveAveragePointFunction", function3.toString());

        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();

        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println(cluster.getCentroid());
            System.out.println(cluster.getPoints().size());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated6Points() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance =
            Lists.newArrayList(108, 13, 119, 67, 85, 225, 205, 179, 61, 97, 9, 52, 85, 198, 87, 246, 189);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ManhattanDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(4, algorithm.getInitialK());
        Assert.assertEquals(13, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);
        Function<ClusteringContext> function2 = algorithm.getFunctions().get(2);
        Function<ClusteringContext> function3 = algorithm.getFunctions().get(3);

        for (Function f : algorithm.getFunctions()) {
            System.out.println(f.toString());
        }
        System.out.println();


        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();

        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println("Centroid: " + cluster.getCentroid());
            System.out.println("Size: " + cluster.getPoints().size());
            System.out.println("Points: " + cluster.getPoints().toString());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated6MorePoints() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance =
            Lists.newArrayList(108, 13, 119, 67, 85, 225, 205, 179, 61, 97, 9, 52, 85, 198, 87, 246, 189);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ManhattanDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(4, algorithm.getInitialK());
        Assert.assertEquals(13, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);
        Function<ClusteringContext> function2 = algorithm.getFunctions().get(2);
        Function<ClusteringContext> function3 = algorithm.getFunctions().get(3);

        for (Function f : algorithm.getFunctions()) {
            System.out.println(f.toString());
        }
        System.out.println();

        List<Point> points = DataInstanceReader.readPoints("/morePoints.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();

        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println("Centroid: " + cluster.getCentroid());
            System.out.println("Size: " + cluster.getPoints().size());
            System.out.println("Points: " + cluster.getPoints().toString());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated6Diabetes() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance =
            Lists.newArrayList(108, 13, 119, 67, 85, 225, 205, 179, 61, 97, 9, 52, 85, 198, 87, 246, 189);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("ManhattanDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(4, algorithm.getInitialK());
        Assert.assertEquals(13, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);
        Function<ClusteringContext> function2 = algorithm.getFunctions().get(2);
        Function<ClusteringContext> function3 = algorithm.getFunctions().get(3);

        for (Function f : algorithm.getFunctions()) {
            System.out.println(f.toString());
        }
        System.out.println();

        List<Point> points = DataInstanceReader.readPoints("/prima-indians-diabetes.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();

        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println("Centroid: " + cluster.getCentroid());
            System.out.println("Size: " + cluster.getPoints().size());
            System.out.println("Points: " + cluster.getPoints().toString());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

    @Test
    public void testAlgorithmGenerated7Points() throws FileNotFoundException, IOException {

        List<Integer> grammarInstance = Lists.newArrayList(205, 112, 86, 3, 157, 4, 71, 133);

        ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
        Assert.assertEquals("RandomCentroidInitilization", algorithm.getInitilization().toString());
        Assert.assertEquals("EucledianDistanceFunction", algorithm.getDistanceFunction().toString());

        Assert.assertEquals(6, algorithm.getInitialK());
        Assert.assertEquals(4, algorithm.getFunctions().size());

        Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
        Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);
        Function<ClusteringContext> function2 = algorithm.getFunctions().get(2);
        Function<ClusteringContext> function3 = algorithm.getFunctions().get(3);

        for (Function f : algorithm.getFunctions()) {
            System.out.println(f.toString());
        }
        System.out.println();

        List<Point> points = DataInstanceReader.readPoints("/points.data");
        algorithm.setPoints(points);

        ClusteringContext clusteringContext = algorithm.execute();

        List<Cluster> clusters = clusteringContext.getClusters();
        for (Cluster cluster : clusters) {
            System.out.println("Centroid: " + cluster.getCentroid());
            System.out.println("Size: " + cluster.getPoints().size());
            System.out.println("Points: " + cluster.getPoints().toString());
            System.out.println();
        }
        Double fitness = fitnessFunction.apply(clusteringContext);

        System.out.println(fitness);

    }

}
