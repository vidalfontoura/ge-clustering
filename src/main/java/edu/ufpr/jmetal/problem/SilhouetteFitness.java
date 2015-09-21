package edu.ufpr.jmetal.problem;


import com.google.common.collect.Lists;

import java.util.List;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.DistanceFunction;

public class SilhouetteFitness implements FitnessFunction {

    DistanceFunction distanceFunction = null;

    public Double apply(ClusteringContext clusteringContext) {

        // If no cluster is empty && and if each point belongs to only one
        // cluster
        List<Cluster> clusters = clusteringContext.getClusters();
        // clusters.stream().forEach(p -> p.printCluster());
        List<Point> allPoints = clusteringContext.getPoints();

        distanceFunction = (DistanceFunction) clusteringContext.getDistanceFunction();

        // If exists more than 10 cluster it will be penalized hard
        if (clusters.size() > 10) {
            return -1.0;
        }

        // If exists any cluster if no points it will penalize hard the solution
        for (Cluster c : clusters) {
        List<Point> points = c.getPoints();
            if (points == null || points.size() == 0) {
                return -1.0;
            }
        }

        // If exists one point that is not clustered it will penalize hard the
        // solution
        for (Point p : allPoints) {
            if (p.getCluster() == null) {
                return -1.0;
            }
        }

        // Calculate fitness
        double fitness = calculateFitness(clusters, allPoints);
        return fitness;

    }

    public double avgD(Point point, List<Point> points) {

        double sum = 0.0;

        for (Point p : points) {
            sum += distanceFunction.apply(Lists.newArrayList(point, p));
        }

        return sum / points.size();
    }

    public double minD(Point point, List<Cluster> clusters) {

        double min = Double.MAX_VALUE;

        for (Cluster c : clusters) {
            if (c != point.getCluster()) {
                double sum = 0.0;
                for (Point p : c.getPoints())
                    sum += (point != p) ? distanceFunction.apply(Lists.newArrayList(point, p)) : 0;
                sum = sum / c.getPoints().size();
                min = (sum < min) ? sum : min;
            }
        }

        return min;
    }

    public double calculateFitness(List<Cluster> clusters, List<Point> allPoints) {

        double f = 0.0;

        for (Point p : allPoints) {
            double a = avgD(p, p.getCluster().getPoints());
            double b = minD(p, clusters);
            // System.out.println("a:" + a + "\tb:" + b + "\tf:" + ((a > b) ?
            // (b-a) / a : (b-a) / b));
            f += (a > b) ? (b - a) / a : (b - a) / b;
        }

        f = f / allPoints.size();

        return f;
    }

}