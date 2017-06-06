package secsec.main;

import java.util.HashMap;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import secsec.utils.CommandParser;
import secsec.utils.Const;
import secsec.commands.AudioCommand;
import secsec.commands.Command;
import secsec.commands.HelpCommand;
import secsec.commands.PingCommand;
import secsec.commands.RollCommand;
import secsec.listeners.GenericListener;

public class MainBot {
	
	private static JDA jda;
	
	public static HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public static CommandParser parser = new CommandParser();

	public static void main(String[] args) {
		try {
			commands.put("ping", new PingCommand());
			commands.put("help", new HelpCommand());
			commands.put("roll", new RollCommand());
			commands.put("audio", new AudioCommand());
			jda = new JDABuilder(AccountType.BOT).setToken(Const.BOT_TOKEN).addEventListener(new GenericListener()).buildBlocking();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RateLimitedException e) {
			e.printStackTrace();
		}
	}
	
	public static void handleCommand(CommandParser.CommandWrapper com) {
		if(commands.containsKey(com.command)) {
			boolean safe = commands.get(com.command).called(com.args, com.event);
			if(safe) {
			commands.get(com.command).action(com.args, com.event);
			commands.get(com.command).executed(safe,com.args, com.event);
			}
		}
		
			
	}

}
