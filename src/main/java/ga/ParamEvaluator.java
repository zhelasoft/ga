package ga;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import ga.exception.CountableException;

public class ParamEvaluator implements FitnessEvaluator<List<ParamModel>> {
	private final Object obj;
	private final Method m;

	public ParamEvaluator(Object obj, Method m) {
		this.obj = obj;
		this.m = m;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isNatural() {
		return false;
	}

	@Override
	public double getFitness(List<ParamModel> candidate, List<? extends List<ParamModel>> population) {
		int errors = 0;

		for (int i = 0; i < Config.LENGTH_OF_CHROMOSOME; i++) {

			ParamModel model = candidate.get(i);

			try {
				this.m.invoke(this.obj, model.getParams());
			} 
			catch (IllegalAccessException e) {
				 e.printStackTrace();
			} catch (IllegalArgumentException e) {
				 e.printStackTrace();
			}
			catch (InvocationTargetException e) {
				if(CountableException.class == e.getCause().getClass()) {
					errors = errors + 1;
				}
			} 
		}
		return errors;
	}

}
