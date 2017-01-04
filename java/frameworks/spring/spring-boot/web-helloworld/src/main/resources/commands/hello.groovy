import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext

class hello {

	@Command
	@Usage("hello")
	def main(InvocationContext ic) {

		return "Hello World!"
	}
}