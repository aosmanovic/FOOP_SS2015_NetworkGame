package at.ac.tuwien.foop.client.model;

import junit.framework.Assert;

import org.junit.Test;

public class GameTest {
	@Test
	public void testSetBoard_whileRunning_shouldThrowRuntimeException() {
		Game g = new Game();
		g.start();
		try {
			g.setBoard(new Board());
			Assert.fail("expecting an exception!");
		} catch (RuntimeException e) {
			Assert.assertEquals("can't set board on a running game!",
					e.getMessage());
		}
	}

	@Test
	public void testSetBoard_whithListener_shouldFireEvent() {
		Game g = new Game();
		g.addGameEventListener(e -> Assert.assertEquals(GameEvent.Type.BOARD, e.type));
		g.setBoard(new Board());
	}
}