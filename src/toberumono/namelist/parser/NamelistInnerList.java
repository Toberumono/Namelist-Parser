package toberumono.namelist.parser;

import java.util.ArrayList;

import toberumono.customStructures.tuples.Pair;

public class NamelistInnerList extends ArrayList<Pair<NamelistType, Object>> {
	int valueWidth = 7;
	
	@Override
	public boolean add(Pair<NamelistType, Object> value) {
		String v = value.getX().stringValue(value.getY());
		if (v.length() > valueWidth)
			valueWidth = v.length();
		return super.add(value);
	}
	
	public int getValueWidth() {
		return valueWidth;
	}
	
	public void setValueWidth(int valueWidth) {
		this.valueWidth = valueWidth;
	}
	
	public void updateValueWidth(int valueWidth) {
		if (valueWidth > this.valueWidth)
			this.valueWidth = valueWidth;
	}
}
