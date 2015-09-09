/*
 */
package edu.ufpr.jmetal.problem;

import com.google.common.collect.Lists;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.Function;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.problem.old.impl.DataInstanceReader;

/**
 *
 *
 * @author Vidal
 */
public class FitnessFunctionTest {

	private ClusteringExpressionGrammarMapper mapper;

	private FitnessFunction fitnessFunction;

	@Before
	public void setup() {
		mapper = new ClusteringExpressionGrammarMapper();
		mapper.loadGrammar("src/main/resources/clustergrammar.bnf");
		JMetalRandom.getInstance().setSeed(100);
		fitnessFunction = new FitnessFunction();
	}

	/**
	 * <pre>
	 *	85,204,128,162,222,31,207,1,52,
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
	public void test3() throws FileNotFoundException, IOException {
		List<Integer> grammarInstance = Lists.newArrayList(85, 204, 128, 162, 222, 31, 207, 1, 52);

		ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
		Assert.assertEquals("RandomCentroidInitilization", algorithm.getInitilization().toString());
		Assert.assertEquals("EucledianDistanceFunction", algorithm.getDistanceFunction().toString());

		Assert.assertEquals(10, algorithm.getInitialK());
		Assert.assertTrue(algorithm.getFunctions().size() == 1);

		Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);

		Assert.assertEquals("SplitClustersFunction", function0.toString());

		List<Point> points = DataInstanceReader.readPoints("src/main/resources/prima-indians-diabetes.data");
		algorithm.setPoints(points);

		ClusteringContext clusteringContext = algorithm.execute();
		List<Point> points2 = clusteringContext.getPoints();
		for (Point p : points2) {
			System.out.println(p);
			System.out.println(p.getCluster());
			System.out.println();
		}

		// List<Cluster> clusters = clusteringContext.getClusters();
		// for (Cluster cluster : clusters) {
		// System.out.println(cluster.getCentroid());
		// System.out.println(cluster.getPoints().size());
		// System.out.println();
		// }
		Double fitness = fitnessFunction.apply(clusteringContext);

		System.out.println(fitness);

	}

	/**
	 * Another Test case that was being generated on algorithm with only
	 * MoveNear and Split, and this was resulting in the end one cluster by
	 * point with Fixed Points
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void test4() throws FileNotFoundException, IOException {
		List<Integer> grammarInstance = Lists.newArrayList(213, 151, 9, 37, 135, 209, 110, 46, 185, 180, 6, 1, 40, 172,
				37, 103, 36);

		ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
		Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
		Assert.assertEquals("ManhattanDistanceFunction", algorithm.getDistanceFunction().toString());

		Assert.assertEquals(9, algorithm.getInitialK());
		Assert.assertEquals(2, algorithm.getFunctions().size());

		Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
		Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);

		Assert.assertEquals("MoveNearPointFunction", function0.toString());
		Assert.assertEquals("SplitClustersFunction", function1.toString());

		List<Point> points = DataInstanceReader.readPoints("src/main/resources/prima-indians-diabetes.data");
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

		System.out.println(clusteringContext.getClusters().size());

	}

	/**
	 * Test case that was being generated on algorithm with only MoveNear and
	 * Split, and this was resulting in the end one cluster by point
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void test5() throws FileNotFoundException, IOException {
		List<Integer> grammarInstance = Lists.newArrayList(213, 151, 9, 37, 135, 209, 110, 46, 185, 180, 6, 1, 40, 172,
				37, 103, 36);

		ClusteringAlgorithm algorithm = mapper.interpret(grammarInstance);
		Assert.assertEquals("UniformCentroidInitilization", algorithm.getInitilization().toString());
		Assert.assertEquals("ManhattanDistanceFunction", algorithm.getDistanceFunction().toString());

		Assert.assertEquals(9, algorithm.getInitialK());
		Assert.assertEquals(2, algorithm.getFunctions().size());

		Function<ClusteringContext> function0 = algorithm.getFunctions().get(0);
		Function<ClusteringContext> function1 = algorithm.getFunctions().get(1);

		Assert.assertEquals("MoveNearPointFunction", function0.toString());
		Assert.assertEquals("SplitClustersFunction", function1.toString());

		ArrayList<Double> point1 = Lists.newArrayList(0.1, 0.2);
		ArrayList<Double> point2 = Lists.newArrayList(0.2, 0.1);
		ArrayList<Double> point3 = Lists.newArrayList(0.1, 0.3);
		ArrayList<Double> point4 = Lists.newArrayList(0.2, 0.2);
		ArrayList<Double> point5 = Lists.newArrayList(0.3, 0.2);
		ArrayList<Double> point6 = Lists.newArrayList(0.5, 0.4);
		ArrayList<Double> point7 = Lists.newArrayList(0.4, 0.4);
		ArrayList<Double> point8 = Lists.newArrayList(0.4, 0.5);
		ArrayList<Double> point9 = Lists.newArrayList(0.4, 0.6);
		ArrayList<Double> point10 = Lists.newArrayList(0.5, 0.5);
		ArrayList<Double> point11 = Lists.newArrayList(0.5, 0.6);
		ArrayList<Double> point12 = Lists.newArrayList(0.8, 0.8);
		ArrayList<Double> point13 = Lists.newArrayList(0.9, 0.8);
		ArrayList<Double> point14 = Lists.newArrayList(0.8, 0.9);
		ArrayList<Double> point15 = Lists.newArrayList(0.9, 0.9);
		ArrayList<Point> points = Lists.newArrayList(new Point(point1), new Point(point2), new Point(point3),
				new Point(point4), new Point(point5), new Point(point6), new Point(point7), new Point(point8),
				new Point(point9), new Point(point10), new Point(point11), new Point(point12), new Point(point13),
				new Point(point14), new Point(point15));
		algorithm.setPoints(points);

		ClusteringContext clusteringContext = algorithm.execute();

		Double fitness = fitnessFunction.apply(clusteringContext);

		System.out.println(fitness);

		for (Cluster cluster : clusteringContext.getClusters()) {
			cluster.printCluster();
		}

		System.out.println(clusteringContext.getClusters().size());

	}

}
