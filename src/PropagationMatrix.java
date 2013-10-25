import java.util.HashMap;
import java.util.Map;
/**
 * Provides some basic implementation of the propagable interface.
 * @author tguiller
 *
 */
public class PropagationMatrix<T>  {
	private T anyValue ;
	private Map<T, Map<T, Double>> propagationRates ;
	
	public PropagationMatrix(T anyValue){
		this.anyValue = anyValue ;
		propagationRates = new HashMap<T, Map<T, Double>>();
		propagationRates.put(anyValue, new HashMap<T, Double>());
	}
	
	public double getPropagation(T x, T y){
		Map<T, Double> level1 = null ;
		if (propagationRates.containsKey(x) ) {
			level1 = propagationRates.get(x);
		}
		else {
			level1 = propagationRates.get(anyValue);
		}
		
		if (level1.containsKey(y)) {
			return level1.get(y) ;
		}
		else {
			return level1.get(anyValue);
		}
	}
	
	public void addPropagationLink(T x, T y, double rate) {
		if ( ! propagationRates.containsKey(x)) {
			propagationRates.put(x, new HashMap<T, Double>());
		}
		propagationRates.get(x).put(y, rate);
	}
	
	public void addSourceGlobalRate(T x, double rate) {
		addPropagationLink(x, anyValue, rate);
	}
	
	public void addTargetGlobalRate(T y, double rate) {
		propagationRates.get(anyValue).put(y, rate);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(T t : propagationRates.keySet()){
			sb.append(t).append(" :\n");
			for(T y : propagationRates.get(t).keySet()) {
				sb.append("    (").append(y).append(" : ").append(propagationRates.get(t).get(y)).append(") | ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
