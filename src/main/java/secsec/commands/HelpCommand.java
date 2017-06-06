package secsec.commands;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand implements Command{
	
	private static final String HELP ="Hi, I'm SecsecBot, pleased to meet you !"
			+ "\n what I can do for the moment is only answering your ping request (see !:ping)"
			+"\n since I'm getting refactored, sorry :o)";

	public void action(String[] args, MessageReceivedEvent event) {
		
		if(event.getChannelType()==ChannelType.PRIVATE)
			event.getChannel().sendMessage(help()).queue();
	}

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public String help() {
		return HELP;
	}

	public void executed(boolean success, String[] args, MessageReceivedEvent event) {

	}

}
