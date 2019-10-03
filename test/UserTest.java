import com.splitnotsowise.utilities.Obligation;
import com.splitnotsowise.utilities.User;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private User userUnderTest;

    @Before
    public void setUp() {
        userUnderTest = new User("username", "password");
    }

    @Test
    public void testReceivePaymentFromFriend() {
        // Setup
        final String username = "username";
        final double amount = 0.0;
        final String reason = "reason";

        // Run the test
        userUnderTest.receivePaymentFromFriend(username, amount);

        // Verify the results
    }

    @Test
    public void testPayFriend() {
        // Setup
        final String username = "username";
        final double amount = 0.0;
        final String reason = "reason";

        // Run the test
        userUnderTest.payFriend(username, amount);

        // Verify the results
    }

    @Test
    public void testReceivePaymentFromGroupMember() {
        // Setup
        final String username = "username";
        final String groupName = "groupName";
        final double amount = 0.0;
        final String reason = "reason";

        // Run the test
        userUnderTest.receivePaymentFromGroupMember(username, groupName, amount);

        // Verify the results
    }

    @Test
    public void testPayGroup() {
        // Setup
        final String username = "username";
        final String groupName = "groupName";
        final double amount = 0.0;
        final String reason = "reason";

        // Run the test
        userUnderTest.payGroup(username, groupName, amount);

        // Verify the results

    }

    @Test
    public void testAddObligationsOfCurrentUser() {
        // Setup
        final String creditor = "creditor";
        final Obligation newObligation = null;

        // Run the test
        userUnderTest.addObligationOfCurrentUser(creditor, newObligation);

        // Verify the results
    }

    @Test
    public void testAddObligationsToCurrentUser() {
        // Setup
        final String debtor = "debtor";
        final Obligation newObligation = null;

        // Run the test
        userUnderTest.addObligationToCurrentUser(debtor, newObligation);

        // Verify the results
    }

    @Test
    public void testGetCurrentUserObligationsToFriend() {
        // Setup
        final String friend = "friend";
        final double expectedResult = 0.0;

        // Run the test
        final double result = userUnderTest.getCurrentUserObligationsToFriend(friend);

        // Verify the results
        assertEquals(expectedResult, result, 0.0001);
    }

    @Test
    public void testGetUsersObligationToGroupMember() {
        // Setup
        final String friend = "friend";
        final double expectedResult = 0.0;

        // Run the test
        final double result = userUnderTest.getUsersObligationToGroupMember(friend);

        // Verify the results
        assertEquals(expectedResult, result, 0.0001);
    }

    @Test
    public void testIsPasswordCorrect() {
        // Setup
        final String password = "password";

        // Run the test
        final boolean result = userUnderTest.isPasswordCorrect(password);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testPrintNotifications() {
        // Setup
        final PrintWriter writer = new PrintWriter(System.out) ;

        // Run the test
        userUnderTest.printNotifications(writer);

        // Verify the results
    }
}
