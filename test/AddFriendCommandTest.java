import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.command.AddFriendCommand;
import com.splitnotsowise.communication.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

public class AddFriendCommandTest {

    private AddFriendCommand addFriendCommandUnderTest;

    @Before
    public void setUp() {
        addFriendCommandUnderTest = new AddFriendCommand();
    }

    @Test
    public void testExecute() throws Exception {
        // Setup
        final String clientUsername = "clientUsername";
        final String[] content = new String[]{};
        //final Mock server = null;
        final PrintWriter writer = null;

        // Run the test
        //addFriendCommandUnderTest.execute(clientUsername, content, server, writer);

        // Verify the results
    }

    @Test(expected = InvalidArgumentCountException.class)
    public void testExecute_ThrowsInvalidInputException() throws Exception {
        // Setup
        final String clientUsername = "clientUsername";
        final String[] content = new String[]{};
        final Server server = new Server();
        final PrintWriter writer = new PrintWriter(System.out);

        // Run the test
        addFriendCommandUnderTest.execute(clientUsername, content, server, writer);
    }
}
