package model;
import java.awt.Color;

public class Config {
	public static int gameRowCount=3;
	public static int gameColumnCount=3;
	
	public static int gemsToWin=3;
	
	public static Color player1Color = Color.red;
	public static Color player1ForeColor=Color.black;
	public static Color player2Color = Color.black;
	public static Color player2ForeColor=Color.white;
	public static Color playerNoneColor=Color.white;
	public static Color playerNoneForeColor = Color.black;
	
	public static String player1String = "X";
	public static String player2String = "O";
	
	/**
	 * Validates the config
	 */
	public static void validate(){
		if (gemsToWin<Math.max(gameRowCount,gameColumnCount)){
			throw new InvalidConfigException("The player cannot win the game since gemsToWin is smaller than all game table dimensions");
		}
		
		if (player1String==""){
			throw new InvalidConfigException("player1String is an empty string");
		}
		
		if (player2String==""){
			throw new InvalidConfigException("player2String is an empty string");
		}
	}
}
