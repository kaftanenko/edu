package osgi_bndtools.calculator.access.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import osgi_bndtools.calculator.service.api.ISimpleCalculatorService_LongType;

@Path("rest")
@Component(provide=Object.class)
public class SimpleCalculatorServiceRest {
	
	// ... properties

	private ISimpleCalculatorService_LongType calculationService;

	// ... dependency injection methods

	@Reference
	public void setCalculationService(ISimpleCalculatorService_LongType calculationService) {

		this.calculationService = calculationService;
	}

	// ... command handling methods


	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<String> list() {
		return Arrays.asList("A", "B", "C");
	  }
	  
}
