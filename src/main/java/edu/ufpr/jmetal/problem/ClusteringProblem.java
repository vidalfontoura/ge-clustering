package edu.ufpr.jmetal.problem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.IntegerSolution;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.ge.mapper.AbstractGrammarMapper;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringProblem extends AbstractGrammaticalEvolutionProblem {
	
	private static final long serialVersionUID = 1L;

	private List<Point> points;
	
	private int minCondons;
	private int maxCondons;
	
	public ClusteringProblem(String grammarFile, String dataClassificationFile, int minCondons, int maxCondons) throws FileNotFoundException, IOException {
		
		super(new ClusteringExpressionGrammarMapper(),grammarFile);
		this.maxCondons = maxCondons;
		this.minCondons = minCondons;
				
	}

	@Override
	public int getNumberOfVariables() {
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public String getName() {
		return "Clustering GE Integer Problem";
	}
	
	public void buildClusteringAlgorithm() {
		
	}

	@Override
	public void evaluate(VariableIntegerSolution solution) {
		int numberOfVariables = solution.getNumberOfVariables();
		
		List<Integer> clusteringSolution = new ArrayList<>();	 
		for(int i=0; i<numberOfVariables; i++) {
			clusteringSolution.add(solution.getVariableValue(i));
		}
		
		ClusteringAlgorithm clusteringAlgorithm = (ClusteringAlgorithm) mapper.interpret(clusteringSolution);
		clusteringAlgorithm.setPoints(points);
		
		ClusteringContext clusteringContext = clusteringAlgorithm.execute();
		
		//check constraints 
		//If no cluster is empty && and if each point belongs to only one cluster
		List<Cluster> clusters = clusteringContext.getClusters();
		List<Point> pointsClustered = clusteringContext.getPoints();
		List<Cluster> emptyClusters = clusters.stream().filter(c -> {
										List<Point> p = c.getPoints(); 
										if (p == null || p.size() == 0) {
											return true;
										}
										return false;
		}).collect(Collectors.toList());
		
		if (emptyClusters.size() > 0) {
			solution.setObjective(0, Double.MAX_VALUE);
		}
		
		List<Point> pointsNoCluster = pointsClustered.stream().filter(p -> p.getCluster()==null?true:false).collect(Collectors.toList());
		if (pointsNoCluster.size() > 0) {
			solution.setObjective(0, Double.MAX_VALUE);
		}
		// Calculate fitness
		double fitness = calculateFitness(clusters, pointsClustered);
		
		solution.setObjective(0, fitness);
		
		
	}
	public double calculateFitness(List<Cluster> clusters, List<Point> allPoints) {
		
		double J = Double.MAX_VALUE;
		for(int i=0; i<allPoints.size(); i++) {
			Point x = allPoints.get(i);
			
			double sumCluster = 0.0 ;
			for(int j=0; j<clusters.size(); j++) {
				Point centroid = clusters.get(j).getCentroid();
			
				List<Double> coordinates = x.getCoordinates();
				
				//For each dimension, check the difference from point Xi to the centroid Cj
				double sumCoordinates = 0.0;
				for (int a=0; a<coordinates.size(); a++) {
					Double xA = coordinates.get(a);
					Double cA = centroid.getCoordinates().get(a);
					sumCoordinates += Math.pow(xA - cA, 2);
				}
				
				double sqrtSumCoordinates = Math.sqrt(sumCoordinates);
				
				int wij=0;
				if (clusters.contains(x)) {
					wij=1;
				}
				sumCluster+= wij * sqrtSumCoordinates;
			}
			J+=sumCluster;
		}
		return J;
	}


	@Override
	public VariableIntegerSolution createSolution() {
		return new VariableIntegerSolution(this, minCondons, maxCondons);
	}

	 

}
