import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.command.ClaimReceivedPaymentCommand;
import com.splitnotsowise.communication.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

public class ClaimReceivedPaymentCommandTest {

    private ClaimReceivedPaymentCommand claimReceivedPaymentCommandUnderTest;

    @Before
    public void setUp() {
        claimReceivedPaymentCommandUnderTest = new ClaimReceivedPaymentCommand();
    }

    @Test
    public void testExecute() throws Exception {
        // Setup
        final String clientUsername = "clientUsername";
        final String[] content = new String[]{};
        final Server server = null;
        final PrintWriter writer = null;

        // Run the test
        claimReceivedPaymentCommandUnderTest.execute(clientUsername, content, server, writer);

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
        claimReceivedPaymentCommandUnderTest.execute(clientUsername, content, server, writer);
    }
}
