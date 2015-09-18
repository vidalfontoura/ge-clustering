package edu.ufpr.jmetal.problem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.impl.EucledianDistanceFunction;

public class SilhouetteFitnessTest {

	FitnessFunction fitness = null;
	
	@Before
	public void setup(){
		
		fitness = new SilhouetteFitness();
		
	}
	
	@Test
	public void test() {
		
		Point p0 = new Point(Lists.newArrayList(1.0, 1.0));
		Point p1 = new Point(Lists.newArrayList(2.0, 2.0));
		Point p2 = new Point(Lists.newArrayList(3.0, 3.0));
		Point p3 = new Point(Lists.newArrayList(4.0, 4.0));
		Point p4 = new Point(Lists.newArrayList(5.0, 5.0));
		Point p5 = new Point(Lists.newArrayList(6.0, 6.0));
		
		List<Point> points = new ArrayList<>();
		points.add(p0);
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		
		Cluster c0 = new Cluster();
		Cluster c1 = new Cluster();
		Cluster c2 = new Cluster();
		
		c0.addPoint(p0);
		c0.addPoint(p1);
		c0.updateCentroid();
		
		c1.addPoint(p2);
		c1.addPoint(p3);
		c1.updateCentroid();
		
		c2.addPoint(p4);
		c2.addPoint(p5);
		c2.updateCentroid();
		
		List<Cluster> clusters = new ArrayList<>();
		clusters.add(c0);
		clusters.add(c1);
		clusters.add(c2);
		
		ClusteringContext clusteringContext = new ClusteringContext(points,  clusters, new EucledianDistanceFunction());
		
		double fit = fitness.apply(clusteringContext);
		System.out.println(fit);
	}
}
