package io.waterworx.pushover;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverRestClient;
import net.pushover.client.Status;

import org.junit.Before;
import org.junit.Test;

public class PushoverUtilsTest {

	private PushoverService pushover;
	private static final String API_TOKEN = "mockToken";
	private static final String USER_ID = "user1";
	private PushoverRestClient client;
	
	@Before
	public void setUp() throws Exception {
		client = mock(PushoverRestClient.class);
		pushover = new PushoverUtils(client);
	}

	@Test
	public void testPushWithMessage() throws PushoverException {
		
		Status status = mock(Status.class);
		when(client.pushMessage(anyObject())).thenReturn(status);
		
		String userID = "user1";
		pushover.push(API_TOKEN, userID, "Hello World");
		verify(client).pushMessage(anyObject());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPushWithAMessageThatIsToLong() throws PushoverException {	
		String message = String.format("%1$"+251+ "s", " ");
		pushover.push(API_TOKEN, USER_ID, message);	
	}

	
	@Test
	public void testPushWithTileAndURL() throws PushoverException {
		
		Status status = mock(Status.class);
		when(client.pushMessage(anyObject())).thenReturn(status);
		
		pushover.push(API_TOKEN, USER_ID, "Hello World", "Title", "url", "urlTitle");
		verify(client).pushMessage(anyObject());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPushWithTileAndURLAndMessageToLong() throws PushoverException {
		String message = String.format("%1$"+251+ "s", " ");
		pushover.push(API_TOKEN, USER_ID, message, "Title", "url", "urlTitle");
	}

}