package collecti.on.misc;

public class BetterBoolean {
	
	private Boolean val;
	
	public BetterBoolean(String val) {
		if (val == null || val.equals("") || val.equals(null) || val.equals("null") || 
				val.equals("None") || val.equals("none") || val.equals("false") || val.equals("0") || val.equals(0))
			this.val = false;
		else
			this.val = true;
	}
	
	public Boolean toBoolean() {
		return this.val;
	}
}