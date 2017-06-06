package secsec.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RollCommand implements Command{

	public void action(String[] args, MessageReceivedEvent event) {

	}

	public boolean called(String[] args, MessageReceivedEvent event) {

		return false;
	}

	public String help() {

		return null;
	}

	public void executed(boolean success, String[] args, MessageReceivedEvent event) {
		
	}

}
