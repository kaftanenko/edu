package edu.java.fws.log4j;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class CustomPatternLayout extends PatternLayout {

	// ... constants

	private static final String DEFAULT_CONVERSION_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";

	// ... properties

	private String fatalConversionPattern = DEFAULT_CONVERSION_PATTERN;

	private String errorConversionPattern = DEFAULT_CONVERSION_PATTERN;

	private String warnConversionPattern = DEFAULT_CONVERSION_PATTERN;

	private String infoConversionPattern = DEFAULT_CONVERSION_PATTERN;

	private String debugConversionPattern = DEFAULT_CONVERSION_PATTERN;

	private String traceConversionPattern = DEFAULT_CONVERSION_PATTERN;

	// ... business methods

	@Override
	public String format(final LoggingEvent event) {

		if (event.getLevel() == Level.FATAL) {
			setConversionPattern(fatalConversionPattern);
		}
		if (event.getLevel() == Level.ERROR) {
			setConversionPattern(errorConversionPattern);
		}
		if (event.getLevel() == Level.WARN) {
			setConversionPattern(warnConversionPattern);
		}
		if (event.getLevel() == Level.INFO) {
			setConversionPattern(infoConversionPattern);
		}
		if (event.getLevel() == Level.DEBUG) {
			setConversionPattern(debugConversionPattern);
		}
		if (event.getLevel() == Level.TRACE) {
			setConversionPattern(traceConversionPattern);
		}
		if (StringUtils.isBlank(getConversionPattern())) {
			setConversionPattern(DEFAULT_CONVERSION_PATTERN);
		}
		return super.format(event);
	}

	// ... getter/setter methods

	public String getFatalConversionPattern() {
		return fatalConversionPattern;
	}

	public void setFatalConversionPattern(final String fatalConversionPattern) {
		this.fatalConversionPattern = fatalConversionPattern;
	}

	public String getErrorConversionPattern() {
		return errorConversionPattern;
	}

	public void setErrorConversionPattern(final String errorConversionPattern) {
		this.errorConversionPattern = errorConversionPattern;
	}

	public String getWarnConversionPattern() {
		return warnConversionPattern;
	}

	public void setWarnConversionPattern(final String warnConversionPattern) {
		this.warnConversionPattern = warnConversionPattern;
	}

	public String getInfoConversionPattern() {
		return infoConversionPattern;
	}

	public void setInfoConversionPattern(final String infoConversionPattern) {
		this.infoConversionPattern = infoConversionPattern;
	}

	public String getDebugConversionPattern() {
		return debugConversionPattern;
	}

	public void setDebugConversionPattern(final String debugConversionPattern) {
		this.debugConversionPattern = debugConversionPattern;
	}

	public String getTraceConversionPattern() {
		return traceConversionPattern;
	}

	public void setTraceConversionPattern(final String traceConversionPattern) {
		this.traceConversionPattern = traceConversionPattern;
	}

}
