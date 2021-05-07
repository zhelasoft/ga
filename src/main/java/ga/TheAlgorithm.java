package ga;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.AdjustableNumberGenerator;
import org.uncommons.maths.number.NumberGenerator;
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
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;


public class TheAlgorithm {

	private Method m;
	private Object obj;

	
	TheAlgorithm(Method m, Object obj) {
		this.m = m;
		this.obj = obj;
	}

	public void run(int round) throws IOException {

		Random rng = new XORShiftRNG();
		FitnessEvaluator<List<ParamModel>> evaluator = new CachingFitnessEvaluator<List<ParamModel>>(
				new ParamEvaluator(this.obj, this.m));
		ParamFactory factory = new ParamFactory(this.m);
		EvolutionaryOperator<List<ParamModel>> pipeline = this.createEvolutionPipeline(factory, rng);

		SelectionStrategy<Object> selection = new TournamentSelection(this.getNumberGenerator());
//		SelectionStrategy<Object> selection = new RouletteWheelSelection();
		EvolutionEngine<List<ParamModel>> engine = new GenerationalEvolutionEngine<List<ParamModel>>(factory, pipeline,
				evaluator, selection, rng);
		
		EvolutionLogger el = new EvolutionLogger();
		
		engine.addEvolutionObserver(el);
		
		engine.evolve(Config.POPULATION_SIZE, Config.ELITISIM_COUNT, this.terminationConditions());
		
		if(Config.EXPORT) {
			Export.toCsv(round, this.methodInfo(), EvolutionLogger.listOfLog);
		}

	}

	private EvolutionaryOperator<List<ParamModel>> createEvolutionPipeline(ParamFactory factory, Random rng) {
		List<EvolutionaryOperator<List<ParamModel>>> operators = new ArrayList<EvolutionaryOperator<List<ParamModel>>>(2);
		operators.add(new CustomListOrderCrossover<ParamModel>());
		operators.add(new CustomListOrderMutation<ParamModel>());
//		operators.add(new ListInversion<ParamModel>(getNumberGenerator()));
		return new EvolutionPipeline<List<ParamModel>>(operators);
	}
	
	private NumberGenerator<Probability> getNumberGenerator() {
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
    	public static ArrayList<String[]> listOfLog;
    	
    	EvolutionLogger() {
    		listOfLog = new ArrayList<String[]>();
    	}
    	
		public void populationUpdate(PopulationData<? extends List<ParamModel>> data)
        {
			int generationNumber = data.getGenerationNumber();
			double bestCandidateFitness = data.getBestCandidateFitness();
			double fitnessStandardDeviation = data.getFitnessStandardDeviation();
			double meanFitness = data.getMeanFitness();
			double elapsedTime = data.getElapsedTime();
			List<ParamModel> bestCandidate = data.getBestCandidate();
			
			String[] log = new String[6];
			log[0] = generationNumber + "";
			log[1] = bestCandidateFitness + "";
			log[2] = fitnessStandardDeviation + "";
			log[3] = meanFitness + "";
			log[4] = elapsedTime + "";
			log[5] = bestCandidate.toString();
			
			EvolutionLogger.listOfLog.add(log);
			
        	if(Config.SHOW_LOG) {
        		System.out.printf("Generation %d:\t Best Candidate Fitness:%s\t  Best Fitness Standard Deviation: %s\t Mean Fitness: %s\t Elapsed time(Milli Second): %s\t Best Candidate:%s\n",
                        generationNumber,
                        bestCandidateFitness,
                        fitnessStandardDeviation,
                        meanFitness,
                        elapsedTime,
                        bestCandidate.toString());
        	}
            
        }
    }
    
    public String[] methodInfo() {
    	String[] methodInfo = new String[4];
		methodInfo[0] = this.obj.getClass().toString();
		methodInfo[1] = this.m.getName();
		methodInfo[2] = this.m.getReturnType() + "";
		methodInfo[3] = "";
		
		Class<?>[] params = this.m.getParameterTypes();
		for(int i = 0; i < params.length; i++) {
			methodInfo[3] += params[i];
			if(i + 1 != params.length) {
				methodInfo[3] += ", ";
			}
		}
		
		return methodInfo;
    }
}
