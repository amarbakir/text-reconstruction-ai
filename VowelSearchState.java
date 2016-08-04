import java.util.Collection;


public class VowelSearchState implements SearchState {

	public String currentString;
	public String lastWord;
	public int index;
	public int lastIndex;
	public int goal;
	
	/** action name: begin a sentence */
    private static final String START_SENTENCE = "begin sentence";
    /** action name: find the next word */
    private static final String NEW_WORD = "add word";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private VowelSearchState (String string, String lastWord, int index, int lastIndex) {
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
		public VowelSearchState makeInitialState(String s) throws IllegalArgumentException {
		    try {
		    	return new VowelSearchState(s, null, 0, 0);
		    } catch (IllegalArgumentException ex) {
		    	throw new IllegalArgumentException("Need string for initial test state.");
		    }
		}
	}
	
	@Override
	public boolean isGoal() {
		//System.out.println(index + " " + goal);
		return (this.index == this.goal);
	}

	@Override
	public Collection<String> getApplicableActions() {
		while (index != currentString.length() && currentString.charAt(index) != ' ') {
			index++;
		}
		return ExpansionDictionary.getInstance().lookup(currentString.substring(this.lastIndex, index));
	}

	@Override
	public double getActionCost(String action) {
		if (lastWord == null) {
			return SmoothedBigramModel.getInstance().cost(LangUtil.SENTENCE_BEGIN, action);
		} else {
			return SmoothedBigramModel.getInstance().cost(this.lastWord, action);
		}
	}

	@Override
	public SearchState applyAction(String action) {
		if (index == goal) {
			//System.out.println("End!" + index + " " + goal);
			return new VowelSearchState(this.currentString, action, (this.index), (this.index));
		}
		//System.out.println(index + " " + goal);
		return new VowelSearchState(this.currentString, action, (this.index + 1), (this.index + 1));
	}

	@Override
	public int hashCode() {
		if (this.lastWord == null) {
			return this.index;
		} else {
			return this.lastWord.hashCode() ^ this.index;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof VowelSearchState)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		VowelSearchState comp = (VowelSearchState) o;
		return (this.lastWord.equals(comp.lastWord) && (this.index == comp.index));
	}
}
