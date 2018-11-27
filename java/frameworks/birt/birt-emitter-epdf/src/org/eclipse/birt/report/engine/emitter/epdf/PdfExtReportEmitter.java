package org.eclipse.birt.report.engine.emitter.epdf;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.emitter.ContentEmitterAdapter;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.presentation.ContentEmitterVisitor;

public class PdfExtReportEmitter extends ContentEmitterAdapter { // PDFLayoutEmitter {

	private ContentEmitterVisitor contentVisitor = new ContentEmitterVisitor(this);

	private IEmitterServices services;
	private IReportContent report;

	@Override
	public void initialize(IEmitterServices services) throws BirtException {

		super.initialize(services);
		this.services = services;
		
		System.out.println("CsvReportEmitter # initialize(..)");
	}

	@Override
	public void start(IReportContent report) throws BirtException {

		super.start(report);
		this.report = report;
		
		System.out.println("CsvReportEmitter # start(..)");
	}

}
