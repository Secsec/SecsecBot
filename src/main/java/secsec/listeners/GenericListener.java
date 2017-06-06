package secsec.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import secsec.main.MainBot;
import secsec.utils.Const;

public class GenericListener extends ListenerAdapter{
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		if(event.getMessage().getContent().startsWith(Const.COMMAND_PREFIX)) {
			if(!event.getAuthor().isBot()) {
				MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContent(), event));
			}
		}
			
	}
}
