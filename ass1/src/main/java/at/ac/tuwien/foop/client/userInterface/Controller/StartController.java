package at.ac.tuwien.foop.client.userInterface.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.client.userInterface.Views.BoardFrame;
import at.ac.tuwien.foop.client.userInterface.Views.BoardPanel;
import at.ac.tuwien.foop.client.userInterface.Views.StartFrame;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.WindGust.Direction;

public class StartController implements ConnectListener, GameEventListener,
		KeyListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private static final int DEFAULT_PORT = 20150;

	private Game game;
	private GameCore core = null;
	private StartFrame startFrame;
	private GameService service = new GameService();
	private BoardFrame boardFrame = new BoardFrame();

	public StartController() {
		startFrame = new StartFrame();

		boardFrame.setBoard(new BoardPanel());
		boardFrame.addKeyListener(this);

		startFrame.addNewGameButtonListener(e -> gameStart());
		startFrame.addConnectButtonListener(e -> connect(startFrame.getServerAddress(), String.valueOf(DEFAULT_PORT)));
	}

	public void connect(String host, String port) {
		log.debug("connect to {}:{}", host, port);
		game = new Game();
		startFrame.setGame(game);
		game.addGameEventListener(this);

		NettyClient c = new NettyClient(game, host, Integer.parseInt(port));
		c.addConnectListener(this);
		new Thread(c, "Network-Layer-Thread").start();
	}

	public void gameStart() {
		service.start(game, core);
	}

	public void onConnect(NettyClient client) {
		core = client.getClientHandler();
		service.join(game, core, "A");
		startFrame.enableStartButton();
	}

	public void setCore(ClientHandler core) {
		this.core = core;
	}

	@Override
	public void onConnecitonFailure() {
		startFrame.showFailure();
	}

	public void showStart() {
		startFrame.setVisible(true);
	}

	public void showAlreadyConnected() {
		startFrame.showAlreadyConnected();
	}

	@Override
	public void onUpdate(GameEvent e) {
		if (e.type == GameEvent.Type.NEW_PLAYER) {
			startFrame.printMessage();
		} else if (e.type == GameEvent.Type.START) {
			showBoard();
		} else if (e.type == GameEvent.Type.BOARD) {
			boardFrame.getBoard().setGame(game);
		} else if (e.type == GameEvent.Type.UPDATE) {
			boardFrame.getBoard().repaint();
		}
	}

	public void showBoard() {
		boardFrame.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			service.sendWind(game, core, new WindGust(Direction.NORTH, 1));
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			service.sendWind(game, core, new WindGust(Direction.SOUTH, 1));
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			service.sendWind(game, core, new WindGust(Direction.WEST, 1));
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			service.sendWind(game, core, new WindGust(Direction.EAST, 1));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
}
