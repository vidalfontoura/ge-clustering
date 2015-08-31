package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		
		
	}




	@Test
	public void test() {
		DistanceFunction distanceFunction = new EucledianDistanceFunction();
		int k = 4;
		
		List<Point> points = new ArrayList<Point>();
		Point point0 = new Point();
		point0.setCoordinates(Lists.newArrayList(0.0,0.0));
		
		Point point1 = new Point();
		point1.setCoordinates(Lists.newArrayList(0.0,0.2));
		
		Point point2 = new Point();
		point2.setCoordinates(Lists.newArrayList(2.0,2.0));
		
		Point point3 = new Point();
		point3.setCoordinates(Lists.newArrayList(1.0,0.0));
		
		points.add(point0);
		points.add(point1);
		points.add(point2);
		points.add(point3);
		
		Cluster cluster0 = new Cluster(point0);
		Cluster cluster1 = new Cluster(point2);
		
		cluster0.addPoint(point3);
		
		
		
		
		ClusteringContext clusteringContext = new ClusteringContext(points, distanceFunction, k);
		
		
	}
}
