/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.cluster.kmeans;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.DistanceFunction;
import edu.ufpr.cluster.algorithms.functions.impl.EucledianDistanceFunction;
import edu.ufpr.cluster.random.ClusteringRandom;
import edu.ufpr.jmetal.problem.FitnessFunction;
import edu.ufpr.jmetal.problem.SimpleClusteringFitness;
import edu.ufpr.jmetal.problem.old.impl.DataInstanceReader;
import edu.ufpr.math.utils.MathUtils;

/**
 *
 *
 * @author Vidal
 */
public class KMeansSimpleClusteringFitnessTest {

    private DistanceFunction distanceFunction;

    private FitnessFunction fitnessFunction;

    @Before
    public void setup() {

        distanceFunction = new EucledianDistanceFunction();
        fitnessFunction = new SimpleClusteringFitness();
    }

    @Test
    public void test11MultipleSeeds() throws FileNotFoundException, IOException {

        ClusteringRandom.getNewInstance().setSeed(100);

        int k = 3;
        List<Point> points = DataInstanceReader.readPoints("/points.data", "Double", true);
        int coordinates = points.get(0).getCoordinates().size();

        for (int i = 0; i < 30; i++) {
            System.out.println("Seed: " + i);
            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            ClusteringContext clusteringContext = algorithm.execute();

            clusteringContext.getClusters().stream().forEach(c -> c.printCluster());

            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println(fitness);
        }

    }

    @Test
    public void test11MultipleSeeds2() throws FileNotFoundException, IOException {

        int k = 2;
        List<Point> points = DataInstanceReader.readPoints("/points.data", "Double", true);
        int coordinates = points.get(0).getCoordinates().size();
        List<Double> fitnesses = new ArrayList<Double>();
        Map<Integer, Integer> mapQtdClusters = new HashMap<>();

        for (int i = 0; i < 30; i++) {

            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            System.out.println("############################################################");
            System.out.println("Seed :" + i);
            ClusteringRandom.getNewInstance().setSeed(i);

            ClusteringContext clusteringContext = algorithm.execute();

            int clustersSize = clusteringContext.getClusters().size();
            System.out.println("Clusters size: " + clustersSize);
            for (Cluster cluster : clusteringContext.getClusters()) {
                cluster.printCluster();
            }
            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println("Fitness: " + fitness);
            fitnesses.add(fitness);

            algorithm.clearClusteringContext();

            if (!mapQtdClusters.containsKey(clustersSize)) {
                mapQtdClusters.put(clustersSize, 1);
            } else {
                Integer amount = mapQtdClusters.get(clustersSize);
                mapQtdClusters.put(clustersSize, amount + 1);
            }
            System.out.println("############################################################");
        }

        List<Double> filteredFitnesses = fitnesses.stream().filter(f -> {
            if (f != Double.MAX_VALUE)
                return true;
            return false;
        }).collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics =
            filteredFitnesses.stream().collect(Collectors.summarizingDouble(x -> x));

        System.out.println("Clusters size:");
        Set<Integer> qtdClusters = mapQtdClusters.keySet();
        for (Integer qtd : qtdClusters) {
            System.out.println(qtd + ":" + mapQtdClusters.get(qtd));
        }

        System.out.println("Average: " + summaryStatistics.getAverage());
        System.out.println("Min: " + summaryStatistics.getMin());
        System.out.println("Max: " + summaryStatistics.getMax());
        System.out.println("Count: " + summaryStatistics.getCount());
        System.out.println("Std: " + MathUtils.getStdDev(filteredFitnesses, summaryStatistics.getAverage()));
    }

    @Test
    public void test12MultipleSeeds() throws FileNotFoundException, IOException {

        ClusteringRandom.getNewInstance().setSeed(100);

        int k = 3;
        List<Point> points = DataInstanceReader.readPoints("/morePoints.data", "Double", true);
        int coordinates = points.get(0).getCoordinates().size();

        List<Double> fitnesses = new ArrayList<Double>();

        for (int i = 0; i < 30; i++) {
            System.out.println("Seed: " + i);
            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            ClusteringContext clusteringContext = algorithm.execute();

            // clusteringContext.getClusters().stream().forEach(c ->
            // c.printCluster());

            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println(fitness);

            algorithm.clearClusteringContext();

            fitnesses.add(fitness);

        }

        List<Double> filteredFitnesses = fitnesses.stream().filter(f -> {
            if (f != Double.MAX_VALUE)
                return true;
            return false;
        }).collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics =
            filteredFitnesses.stream().collect(Collectors.summarizingDouble(x -> x));

        System.out.println("Average: " + summaryStatistics.getAverage());

    }

    @Test
    public void test50RandomPoints30Seeds() throws FileNotFoundException, IOException {

        ClusteringRandom.getNewInstance().setSeed(100);

        int k = 8;
        List<Point> points = DataInstanceReader.readPoints("/50-random-points.data", "Double", true);
        int coordinates = points.get(0).getCoordinates().size();

        List<Double> fitnesses = new ArrayList<Double>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            System.out.println("Seed: " + i);
            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            ClusteringContext clusteringContext = algorithm.execute();

            // clusteringContext.getClusters().stream().forEach(c ->
            // c.printCluster());

            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println(fitness);

            algorithm.clearClusteringContext();

            fitnesses.add(fitness);

        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time (seconds): " + elapsedTime / 1000);

        List<Double> filteredFitnesses = fitnesses.stream().filter(f -> {
            if (f != Double.MAX_VALUE)
                return true;
            return false;
        }).collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics =
            filteredFitnesses.stream().collect(Collectors.summarizingDouble(x -> x));

        System.out.println("Average: " + summaryStatistics.getAverage());
        System.out.println("Min: " + summaryStatistics.getMin());
        System.out.println("Max: " + summaryStatistics.getMax());
        System.out.println("Count: " + summaryStatistics.getCount());
        System.out.println("Std: " + MathUtils.getStdDev(filteredFitnesses, summaryStatistics.getAverage()));

    }

    @Test
    public void test500RandomPoints30Seeds() throws FileNotFoundException, IOException {

        ClusteringRandom.getNewInstance().setSeed(100);

        int k = 8;
        List<Point> points = DataInstanceReader.readPoints("/500-random-points.data", "Double", true);
        int coordinates = points.get(0).getCoordinates().size();

        List<Double> fitnesses = new ArrayList<Double>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {

            // ClusteringRandom.getInstance().setSeed(i);
            System.out.println("Seed: " + i);
            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            ClusteringContext clusteringContext = algorithm.execute();

            // clusteringContext.getClusters().stream().forEach(c ->
            // c.printCluster());

            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println(fitness);

            algorithm.clearClusteringContext();

            fitnesses.add(fitness);

        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time (seconds): " + elapsedTime / 1000);

        List<Double> filteredFitnesses = fitnesses.stream().filter(f -> {
            if (f != Double.MAX_VALUE)
                return true;
            return false;
        }).collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics =
            filteredFitnesses.stream().collect(Collectors.summarizingDouble(x -> x));

        System.out.println("Average: " + summaryStatistics.getAverage());
        System.out.println("Min: " + summaryStatistics.getMin());
        System.out.println("Max: " + summaryStatistics.getMax());
        System.out.println("Count: " + summaryStatistics.getCount());
        System.out.println("Std: " + MathUtils.getStdDev(filteredFitnesses, summaryStatistics.getAverage()));

    }

    @Test
    public void test1000RandomPoints30Seeds() throws FileNotFoundException, IOException {

        ClusteringRandom.getNewInstance().setSeed(100);

        int k = 3;
        List<Point> points = DataInstanceReader.readPoints("/1000-random-points.data", "Double", true);
        int coordinates = points.get(0).getCoordinates().size();

        List<Double> fitnesses = new ArrayList<Double>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            System.out.println("Seed: " + i);
            ClusteringRandom.getInstance().setSeed(i);

            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            ClusteringContext clusteringContext = algorithm.execute();

            // clusteringContext.getClusters().stream().forEach(c ->
            // c.printCluster());

            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println(fitness);

            algorithm.clearClusteringContext();

            fitnesses.add(fitness);

        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time (seconds): " + elapsedTime / 1000);

        List<Double> filteredFitnesses = fitnesses.stream().filter(f -> {
            if (f != Double.MAX_VALUE)
                return true;
            return false;
        }).collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics =
            filteredFitnesses.stream().collect(Collectors.summarizingDouble(x -> x));

        System.out.println("Average: " + summaryStatistics.getAverage());
        System.out.println("Min: " + summaryStatistics.getMin());
        System.out.println("Max: " + summaryStatistics.getMax());
        System.out.println("Count: " + summaryStatistics.getCount());
        System.out.println("Std: " + MathUtils.getStdDev(filteredFitnesses, summaryStatistics.getAverage()));

    }

    @Test
    public void testDiabetes30Seeds() throws FileNotFoundException, IOException {

        ClusteringRandom.getNewInstance().setSeed(100);

        int k = 7;
        List<Point> points =
            MathUtils.normalizeData(DataInstanceReader.readPoints("/prima-indians-diabetes.data", "Double", true));
        int coordinates = points.get(0).getCoordinates().size();

        List<Double> fitnesses = new ArrayList<Double>();

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            System.out.println("Seed: " + i);

            ClusteringRandom.getInstance().setSeed(i);
            KMeansClusteringAlgorithm algorithm =
                new KMeansClusteringAlgorithm(points, distanceFunction, k, coordinates);

            ClusteringRandom.getInstance().setSeed(i);

            ClusteringContext clusteringContext = algorithm.execute();

            Double fitness = fitnessFunction.apply(clusteringContext);

            System.out.println(fitness);

            algorithm.clearClusteringContext();

            fitnesses.add(fitness);

        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time (seconds): " + elapsedTime / 1000);

        List<Double> filteredFitnesses = fitnesses.stream().filter(f -> {
            if (f != Double.MAX_VALUE)
                return true;
            return false;
        }).collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics =
            filteredFitnesses.stream().collect(Collectors.summarizingDouble(x -> x));

        System.out.println("Average: " + summaryStatistics.getAverage());
        System.out.println("Min: " + summaryStatistics.getMin());
        System.out.println("Max: " + summaryStatistics.getMax());
        System.out.println("Count: " + summaryStatistics.getCount());
        System.out.println("Std: " + MathUtils.getStdDev(filteredFitnesses, summaryStatistics.getAverage()));

    }
}
