import java.util.ArrayList;
import java.util.Collection;


public class CombinedSearchState implements SearchState{

	public String currentString;
	public String lastWord;
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

	private CombinedSearchState (String string, String lastWord, int index, int lastIndex) {
		this.currentString = string;
		this.lastWord = lastWord;
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
		public CombinedSearchState makeInitialState(String s) throws IllegalArgumentException {
		    try {
		    	return new CombinedSearchState(s, null, 0, 0);
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
		ArrayList<String> actions = new ArrayList<String>(ExpansionDictionary.getInstance().lookup(currentString.substring(this.lastIndex, index + 1)));
		if (index != currentString.length() - 1) {
			actions.add(MOVE_FORWARD);
		}
		return actions;
	}

	@Override
	public double getActionCost(String action) {
		if (action != MOVE_FORWARD) {
			if (lastWord == null) {
				return SmoothedBigramModel.getInstance().cost(LangUtil.SENTENCE_BEGIN, action);
			} else {
				return SmoothedBigramModel.getInstance().cost(this.lastWord, action);
			}
		} else {
			return 0;
		}
	}

	@Override
	public SearchState applyAction(String action) {
		System.out.println(action);
		if (action != MOVE_FORWARD) {
			if (index == goal) {
				//System.out.println("End!" + index + " " + goal);
				return new CombinedSearchState(this.currentString, action, (this.index), (this.index));
			}
			//System.out.println(index + " " + goal);
			return new CombinedSearchState(this.currentString, action, (this.index + 1), (this.index + 1));
		} else {
			return new CombinedSearchState(this.currentString, this.lastWord, (this.index + 1), this.lastIndex);
		}
	}

	@Override
	public int hashCode() {
		if (this.lastWord == null) {
			return this.index ^ this.lastIndex;
		} else {
			return this.lastWord.hashCode() ^ this.index ^ this.lastIndex;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof CombinedSearchState)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		CombinedSearchState comp = (CombinedSearchState) o;
		return (this.currentString.equals(comp.currentString) && (this.index == comp.index) && (this.lastIndex == comp.lastIndex));
	}
}
