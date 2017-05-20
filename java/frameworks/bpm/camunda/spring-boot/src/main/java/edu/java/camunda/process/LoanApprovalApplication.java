package edu.java.camunda.process;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ProcessApplication("Loan Approval App") // id: approve-loan
public class LoanApprovalApplication implements JavaDelegate {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(final DelegateExecution execution) throws Exception {
		LOGGER.info(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
	}

}
