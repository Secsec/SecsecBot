package secsec.utils;

import java.util.ArrayList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandParser {
	
	private static String parserCommand = " (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
	
	public CommandWrapper parse(String raw, MessageReceivedEvent event) {
		String toSplit = raw;
		toSplit = toSplit.replaceFirst(Const.COMMAND_PREFIX, "");
		String[] splitted = toSplit.split(parserCommand);
		String command = splitted[0];
		
		String[] args = new String[splitted.length-1];
		
		for(int i=0;i<splitted.length-1;i++) {
			args[i] = splitted[i+1];
		}
		
		
		return new CommandWrapper(command, args, event);
	}
	
	public class CommandWrapper{
		public final String command;
		public final String[] args;
		public final MessageReceivedEvent event;
		
		public CommandWrapper(String command, String[] args, MessageReceivedEvent event) {
			this.command = command;
			this.args = args;
			this.event = event;
		}
	}
}
