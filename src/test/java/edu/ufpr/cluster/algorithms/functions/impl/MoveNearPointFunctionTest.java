package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.DistanceFunction;

public class MoveNearPointFunctionTest {

	private MoveNearPointFunction function;
	
	@Before
	public void init() {
		function = new MoveNearPointFunction();
		JMetalRandom.getInstance().setSeed(0);
	}

	@Test
	public void test() {
		
		DistanceFunction distanceFunction = new EucledianDistanceFunction();
		int k = 4;
		
		List<Point> points = new ArrayList<Point>();
		
		Point point0 = new Point(Lists.newArrayList(0.0, 0.0));
		Point point1 = new Point(Lists.newArrayList(2.0, 0.0));
		Point point2 = new Point(Lists.newArrayList(1.0, 1.0));
		
		points.add(point0);
		points.add(point1);
		points.add(point2);
		
		Cluster cluster0 = new Cluster();
		Cluster cluster1 = new Cluster();
		
		cluster0.addPoint(point0);
		cluster0.addPoint(point2);
		cluster0.updateCentroid();
		
		cluster1.addPoint(point1);
		cluster1.updateCentroid();
		
		List<Cluster> clusters = new ArrayList<Cluster>();
		clusters.add(cluster0);
		clusters.add(cluster1);
		
		ClusteringContext clusteringContext = new ClusteringContext(points, clusters, distanceFunction, k);
		
		for (Cluster cluster : clusters) {
			System.out.println(cluster.getPoints().size() + " " + cluster.getCentroid());
		}

		function.apply(clusteringContext);
		
		System.out.println("---");
		for (Cluster cluster : clusters) {
			System.out.println(cluster.getPoints().size() + " " + cluster);
		}
	}
}
