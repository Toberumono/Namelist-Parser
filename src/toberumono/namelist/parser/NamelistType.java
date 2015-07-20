package toberumono.namelist.parser;

public enum NamelistType {
	Number {
		@Override
		public java.lang.String stringValue(Object o) {
			return o.toString();
		}
	},
	Boolean {
		@Override
		public java.lang.String stringValue(Object o) {
			return "." + (((Boolean) o).booleanValue() ? "true" : "false") + ".";
		}
	},
	String {
		@Override
		public java.lang.String stringValue(Object o) {
			return "'" + o.toString() + "'";
		}
	};
	
	public abstract String stringValue(Object o);
}
