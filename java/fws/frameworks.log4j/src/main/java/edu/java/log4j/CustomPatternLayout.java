package edu.java.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class CustomPatternLayout extends PatternLayout {

	private String fatalConversionPattern;

	private String errorConversionPattern;

	private String warnConversionPattern;

	private String infoConversionPattern;

	private String debugConversionPattern;

	private String traceConversionPattern;

	public void setFatalConversionPattern(String fatalConversionPattern) {

		this.fatalConversionPattern = fatalConversionPattern;
	}

	public void setErrorConversionPattern(String errorConversionPattern) {

		this.errorConversionPattern = errorConversionPattern;
	}

	public void setWarnConversionPattern(String warnConversionPattern) {

		this.warnConversionPattern = warnConversionPattern;
	}

	public void setInfoConversionPattern(String infoConversionPattern) {

		this.infoConversionPattern = infoConversionPattern;
	}

	public void setDebugConversionPattern(String debugConversionPattern) {

		this.debugConversionPattern = debugConversionPattern;
	}

	public void setTraceConversionPattern(String traceConversionPattern) {

		this.traceConversionPattern = traceConversionPattern;
	}

	@Override
	public String format(LoggingEvent event) {

		if (event.getLevel() == Level.DEBUG) {

			setConversionPattern(debugConversionPattern);
		}
		return super.format(event);
	}

}
