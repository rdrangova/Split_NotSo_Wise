import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.command.RegisterCommand;
import com.splitnotsowise.communication.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

public class RegisterCommandTest {

    private RegisterCommand registerCommandUnderTest;

    @Before
    public void setUp() {
        registerCommandUnderTest = new RegisterCommand();
    }

    @Test
    public void testExecute() throws Exception {
        // Setup
        final String clientUsername = "clientUsername";
        final String[] content = new String[]{"strongPassword"};
        final Server server = new Server();
        //final PrintWriter writer = new PrintWriter();

        // Run the test
        //registerCommandUnderTest.execute(clientUsername, content, server, writer);

        // Verify the results
    }

    @Test(expected = InvalidArgumentCountException.class)
    public void testExecute_ThrowsInvalidInputException() throws Exception {
        // Setup
        final String clientUsername = "clientUsername";
        final String[] content = new String[]{};
        final Server server = null;
        final PrintWriter writer = null;

        // Run the test
        registerCommandUnderTest.execute(clientUsername, content, server, writer);
    }
}
