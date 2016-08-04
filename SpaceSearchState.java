import java.util.ArrayList;
import java.util.Collection;


public class SpaceSearchState implements SearchState{

	public String currentString;
	public int index;
	public int lastIndex;
	public int goal;
	
	/** action name: add a space to the string */
    private static final String ADD_SPACE = "add space";
    /** action name: move forward without adding a space to the string */
    private static final String MOVE_FORWARD = "move forward";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private SpaceSearchState (String string, int index, int lastIndex) {
		this.currentString = string;
		this.index = index;
		this.lastIndex = lastIndex;
		this.goal = string.length();
	}
	
	public static class Builder extends SearchState.Builder {
		/**
		 * Make the search space to get a specified targetQuantity of
		 * flour into the workspace by the available operations.
		 *
		 * @param targetQuantity specification of the problem instance to solve
		 * @return initial search state corresponding to this problem
		 */
		public SpaceSearchState makeInitialState(String s) throws IllegalArgumentException {
		    try {
		    	return new SpaceSearchState(s, 0, 0);
		    } catch (IllegalArgumentException ex) {
		    	throw new IllegalArgumentException("Need string for initial test state.");
		    }
		}
	}
	
	@Override
	public boolean isGoal() {
		if (index != 0 && this.lastIndex == this.goal && this.index == this.goal) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<String> getApplicableActions() {
		ArrayList<String> actions = new ArrayList<String>();
		//actions.add(ADD_SPACE);
		actions.add(currentString.substring(lastIndex, index + 1));
		if (index != currentString.length() - 1) {
			actions.add(MOVE_FORWARD);
		}
		return actions;
	}

	@Override
	public double getActionCost(String action) {
		if (action != MOVE_FORWARD) {
			return (UnigramModel.getInstance().cost(action));
		} else {
			return 0;
		}
	}

	@Override
	public SearchState applyAction(String action) {
		//System.out.println(this.currentString);
		if (action != MOVE_FORWARD) {
			return new SpaceSearchState(currentString, index + 1, index + 1);
		} else {
			return new SpaceSearchState(currentString, index + 1, lastIndex);
		} 
	}

	@Override
	public int hashCode() {
		return this.currentString.hashCode() ^ this.index ^ this.lastIndex;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SpaceSearchState)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		SpaceSearchState comp = (SpaceSearchState) o;
		return (this.currentString.equals(comp.currentString) && (this.index == comp.index) && (this.lastIndex == comp.lastIndex));
	}
}
