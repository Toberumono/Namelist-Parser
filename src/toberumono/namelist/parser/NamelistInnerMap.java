package toberumono.namelist.parser;

import java.util.LinkedHashMap;

public class NamelistInnerMap extends LinkedHashMap<String, NamelistInnerList> {
	private int keyWidth = 15;
	private int valueWidth = 7;
	
	@Override
	public NamelistInnerList put(String key, NamelistInnerList value) {
		if (key.length() > keyWidth)
			keyWidth = key.length();
		if (value.getValueWidth() > valueWidth)
			valueWidth = value.getValueWidth();
		return super.put(key, value);
	}
	
	public int getKeyWidth() {
		return keyWidth;
	}
	
	public void setKeyWidth(int keyWidth) {
		this.keyWidth = keyWidth;
	}
	
	public void updateKeyWidth(int keyWidth) {
		if (keyWidth > this.keyWidth)
			this.keyWidth = keyWidth;
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
