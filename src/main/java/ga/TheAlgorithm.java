package ga;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.AdjustableNumberGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.PoissonGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.maths.random.XORShiftRNG;
import org.uncommons.watchmaker.framework.CachingFitnessEvaluator;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.ListInversion;
import org.uncommons.watchmaker.framework.operators.ListOperator;
import org.uncommons.watchmaker.framework.operators.ListOrderCrossover;
import org.uncommons.watchmaker.framework.operators.ListOrderMutation;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.EliteSelector;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.SinglePointCrossover;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.Factory;

public class TheAlgorithm {

	private Method m;
	private Object obj;
	private GenerateData gd;
	private double prob;
	private ParamFactory pf;

	TheAlgorithm(Method m, Object obj) {
		this.m = m;
		this.obj = obj;
		this.gd = new GenerateData();
		this.prob = 0;
		this.pf = new ParamFactory(m);
	}

	public List<ParamModel> run() {

		Random rng = new XORShiftRNG();
		FitnessEvaluator<List<ParamModel>> evaluator = new CachingFitnessEvaluator<List<ParamModel>>(
				new ParamEvaluator(this.obj, this.m));
		ParamFactory factory = new ParamFactory(this.m);
		EvolutionaryOperator<List<ParamModel>> pipeline = this.createEvolutionPipeline(factory, rng);

		SelectionStrategy<Object> selection = new TournamentSelection(this.getNumberGenerator());
//		SelectionStrategy<Object> selection = new RouletteWheelSelection();
		EvolutionEngine<List<ParamModel>> engine = new GenerationalEvolutionEngine<List<ParamModel>>(factory, pipeline,
				evaluator, selection, rng);
		
		engine.addEvolutionObserver(new EvolutionLogger());
		
		return engine.evolve(Config.POPULATION_SIZE, Config.ELITISIM_COUNT, this.terminationConditions());

	}

	private EvolutionaryOperator<List<ParamModel>> createEvolutionPipeline(ParamFactory factory, Random rng) {
		List<EvolutionaryOperator<List<ParamModel>>> operators = new ArrayList<EvolutionaryOperator<List<ParamModel>>>(2);
		operators.add(new CustomListOrderCrossover<ParamModel>());
		operators.add(new CustomListOrderMutation<ParamModel>());
//		operators.add(new ListInversion<ParamModel>(getNumberGenerator()));
		return new EvolutionPipeline<List<ParamModel>>(operators);
	}
	
	private NumberGenerator getNumberGenerator() {
		return new AdjustableNumberGenerator<Probability>(new Probability(0.05));
	}
	
	private TerminationCondition[] terminationConditions() {
		TerminationCondition gc = new GenerationCount(Config.GENERATION_COUNT);
		TerminationCondition tf = new TargetFitness(Config.TARGET_FITNESS, true);
		
		TerminationCondition[] conditions = new TerminationCondition[2];
		conditions[0] = tf;
		conditions[1] = gc;
 		return conditions;
	}
	
	 /**
     * Trivial evolution observer for displaying information at the end
     * of each generation.
     */
    private static class EvolutionLogger implements EvolutionObserver<List<ParamModel>>
    {
        public void populationUpdate(PopulationData<? extends List<ParamModel>> data)
        {
        	if(Config.SHOW_LOG) {
        		System.out.printf("Generation %d:\t Best Candidate Fitness:%s\t  Best Fitness Standard Deviation: %s\t Mean Fitness: %s\t Best Candidate:%s\n",
                        data.getGenerationNumber(),
                        data.getBestCandidateFitness(),
                        data.getFitnessStandardDeviation(),
                        data.getMeanFitness(),
                        data.getBestCandidate());
        	}
            
        }
    }
}
