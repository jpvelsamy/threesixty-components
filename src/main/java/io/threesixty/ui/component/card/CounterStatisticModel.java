package io.threesixty.ui.component.card;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Optional;

public class CounterStatisticModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String type;
	private Optional<Number> value;
	private CounterFormat format = CounterFormat.INTEGER;
	private Number defaultValue = 0L;
	private String prefix = "";
	private String suffix = "";
	
	public CounterStatisticModel(String type, Optional<Number> value) {
		this(type, value, CounterFormat.INTEGER, 0L);
	}
	
	public CounterStatisticModel(String type, Number value) {
		this(type, Optional.ofNullable(value));
	}
	
	public CounterStatisticModel(String type, Optional<Number> value, CounterFormat format) {
		this(type, value, format, 0L);
	}

	public CounterStatisticModel(
			final String type, 
			final Optional<Number> value, 
			final CounterFormat format, 
			final Number defaultValue) {
		this.type = type;
		this.value = value;
		this.format = format;
		this.defaultValue = defaultValue;
	}

	public String getType() { return type; }
	public Optional<Number> getValue() { return value; }
	
	public String getFormattedValue() {
		return StringUtils.defaultString(prefix, "") + getFormattedNumberValue() + StringUtils.defaultString(suffix, "");
	}
	
	private String getFormattedNumberValue() {
		switch (format) {
		case INTEGER: return DecimalFormat.getIntegerInstance().format(getValue().orElse(defaultValue));
		case NUMBER: return DecimalFormat.getNumberInstance().format(getValue().orElse(defaultValue));
		case PERCENTAGE: return DecimalFormat.getPercentInstance().format(getValue().orElse(defaultValue));
		default: return DecimalFormat.getIntegerInstance().format(getValue().orElse(defaultValue));
		}
	}
	
	public CounterStatisticModel prefix(final String value) {
		this.prefix = value;
		return this;
	}
	
	public CounterStatisticModel suffix(final String value) {
		this.suffix = value;
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(
				"CounterStatistics [type=%s, value=%s, format=%s, defaultValue=%s]", 
				type, 
				value, 
				format,
				defaultValue);
	}

	public static enum CounterFormat {
		INTEGER,
		NUMBER,
		PERCENTAGE
	}
}
