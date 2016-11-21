package wonders.simulator.wsnsimulation;

/**
 * @author ZackRauen
 * @version 1.0
 * @see Simulation
 * @see SimulationSetup
 *
 */

@SuppressWarnings("serial")
public class SetupException extends RuntimeException {
	
	public static enum ExceptionType {
		UNSPECIFIED, RICIAN_WITH_UNIFORM_ALPHA, NEGATIVE_INPUT
	}
	
	private ExceptionType cause;
	
	public SetupException(ExceptionType cause) {
		super(cause.name());
		this.cause = cause;
	}
	
	public ExceptionType getExceptionType() { return cause; }

}
