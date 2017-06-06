package secsec.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PingCommand implements Command {
	public static final String HELP = "Use : '!:ping' and I will answer you pong";

	public void action(String[] args, MessageReceivedEvent event) {

		if(args.length==1 && args[0].contains("help")) {
			event.getChannel().sendMessage(help()).queue();
			return;
		}

		event.getChannel().sendMessage("pong").queue();
	}

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public String help() {
		return HELP;
	}

	public void executed(boolean success,String[] args, MessageReceivedEvent event) {
		return;
	}

}
