package at.ac.tuwien.foop.server.domain;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;

public class GameTest {

	@Test
	public void testjoin_noStartingCoordinate_expectNull() {
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-w-wwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		Assert.assertNull(game.join("player1"));
	}

	@Test
	public void testjoin_1StartingCoordinate1Player_expectNull() {
		String b = 
				  "wwwwwwww"
				+ "wm---Cww"
				+ "w-w-wwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("player1");
		Assert.assertNull(game.join("player2"));
	}

	@Test
	public void testjoin_playerWithSameName_expectNull() {
		String b = 
				  "wwwwwwww"
				+ "wm---Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("player1");
		Assert.assertNull(game.join("player1"));
	}
	
	@Test
	public void testjoin_freeStartingField_expectPlayer() {
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		Player actual = game.join("player1");
		Assert.assertEquals("player1", actual.name());
		Assert.assertEquals(new Coordinates(3, 2), actual.coordinates());
	}

	@Test
	public void testMovePlayer_noPlayer_expectIllegalArgumentException() {
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		try {
			game.movePlayer("unknown", new Coordinates(1, 1));
			Assert.fail("expect exception, as no user joined the game!");
		} catch (IllegalArgumentException e) {}
	}

	@Test
	public void testMovePlayer_unknownPlayer_expectIllegalArgumentException() {
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("player1");
		try {
			game.movePlayer("unknown", new Coordinates(1, 1));
			Assert.fail("expect exception, as player 'unknown' did not join the game!");
		} catch (IllegalArgumentException e) {}
	}

	@Test
	public void testMovePlayer_move_expectNewCoordinates() {
		String playerName = "player1";
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join(playerName);
		Coordinates expected = new Coordinates(1, 1);
		game.movePlayer(playerName, expected);
		Assert.assertEquals(expected, game.getPlayer(playerName).coordinates());
	}

	@Test
	public void testMovePlayer_moveSecondPlayer_expectFirstPlayerNotMoved() {
		String playerName1 = "player1";
		String playerName2 = "player2";
		String b = 
				  "wwwwwwww"
				+ "w-m--Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join(playerName1);
		game.join(playerName2);
		Coordinates coord1 = new Coordinates(2, 1);
		Coordinates coord2 = new Coordinates(3, 2);
		Assert.assertEquals(coord1, game.getPlayer(playerName1).coordinates());
		Assert.assertEquals(coord2, game.getPlayer(playerName2).coordinates());

		Coordinates expected = new Coordinates(1, 1);
		game.movePlayer(playerName2, expected);
		Assert.assertEquals(coord1, game.getPlayer(playerName1).coordinates());
		Assert.assertEquals(expected, game.getPlayer(playerName2).coordinates());
	}
}