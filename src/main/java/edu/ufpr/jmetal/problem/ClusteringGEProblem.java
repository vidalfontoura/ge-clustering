package edu.ufpr.jmetal.problem;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringGEProblem implements IntegerProblem {
	
	private ClusteringExpressionGrammarMapper mapper;
	
	public ClusteringGEProblem(ClusteringExpressionGrammarMapper mapper) {
		this.mapper = mapper;
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

	@Override
	public void evaluate(IntegerSolution solution) {
		int numberOfVariables = solution.getNumberOfVariables();
		
		List<Integer> clusteringSolution = new ArrayList<>();	 
		for(int i=0; i<numberOfVariables; i++) {
			clusteringSolution.add(solution.getVariableValue(i));
		}
		
		ClusteringAlgorithm clusteringAlgorithm = mapper.interpret(clusteringSolution);
		
		List<Cluster> clusters = clusteringAlgorithm.execute();
		
		
		
	}

	@Override
	public IntegerSolution createSolution() {
		return new VariableIntegerSolution(this,1, 1000);
	}

	@Override
	public Integer getLowerBound(int index) {
		return 1;
	}

	@Override
	public Integer getUpperBound(int index) {
		return 1000;
	}
	
	
	public void buildClusteringAlgorithm() {
		
	}

	 

}
