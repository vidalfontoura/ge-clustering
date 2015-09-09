package edu.ufpr.jmetal.algorithm.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.ge.mapper.impl.SymbolicExpressionGrammarMapper;
import edu.ufpr.ge.operators.crossover.SinglePointCrossoverVariableLength;
import edu.ufpr.ge.operators.mutation.DuplicationMutation;
import edu.ufpr.ge.operators.mutation.IntegerMutation;
import edu.ufpr.ge.operators.mutation.PruneMutation;
import edu.ufpr.jmetal.algorithm.impl.GrammaticalEvolutionAlgorithm;
import edu.ufpr.jmetal.problem.ClusteringProblem;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringExperiment extends AbstractAlgorithmRunner {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String outputDirName = "result";
		File outDir = new File(outputDirName);
		if (!outDir.exists()) {
			outDir.mkdir();
		}
		String grammarFile = "/clustergrammar.bnf";
		String dataClassificationFile = "/prima-indians-diabetes.data";
		double crossoverProbability = 1.0;
		double mutationProbability = 0.1;
		double pruneMutationProbability = 0.05;
		double duplicationProbability = 0.05;
		int pruneIndex = 4;
		int maxEvaluations = 10;
		int populationSize = 10;

		for (int i = 0; i < 2; i++) {

			String outputFolder = outDir + File.separator + i;

			ClusteringProblem problem = new ClusteringProblem(grammarFile, dataClassificationFile, 1, 20);

			CrossoverOperator<VariableIntegerSolution> crossoverOperator = new SinglePointCrossoverVariableLength(
					crossoverProbability);
			MutationOperator<VariableIntegerSolution> mutationOperator = new IntegerMutation(mutationProbability);
			SelectionOperator selectionOperator = new BinaryTournamentSelection<>();

			PruneMutation pruneMutationOperator = new PruneMutation(pruneMutationProbability, pruneIndex);
			DuplicationMutation duplicationMutationOperator = new DuplicationMutation(duplicationProbability);

			GrammaticalEvolutionAlgorithm algorithm = new GrammaticalEvolutionAlgorithm(problem, maxEvaluations,
					populationSize, crossoverOperator, mutationOperator, selectionOperator, pruneMutationOperator,
					duplicationMutationOperator, new SequentialSolutionListEvaluator());

			AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
			List<VariableIntegerSolution> population = algorithm.getResult();
			long computingTime = algorithmRunner.getComputingTime();

			SymbolicExpressionGrammarMapper mapper = new SymbolicExpressionGrammarMapper();
			mapper.loadGrammar(grammarFile);

			System.out.println("Total time of execution: " + computingTime);
			System.out.println("Solution: " + population.get(0).getObjective(0));
			System.out.println("Variables: " + mapper.interpret(population.get(0)));

			File outputFolderFile = new File(outputFolder);
			outputFolderFile.mkdir();
			printFinalSolutionSet(population, outputFolder);

		}

	}

	public static void printFinalSolutionSet(List<? extends Solution<?>> population, String outputFolder) {

		new SolutionSetOutput.Printer(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext(outputFolder + File.separator + "VAR.csv"))
				.setFunFileOutputContext(new DefaultFileOutputContext(outputFolder + File.separator + "FUN.csv"))
				.print();

		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
		JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
		JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
	}

}
