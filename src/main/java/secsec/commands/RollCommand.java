package secsec.commands;

import java.util.Random;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import secsec.utils.Tools;

public class RollCommand implements Command{
	
	public static final String HELP ="To use the command '!:roll', either you use it without any argument, which will give you a random number between 0 and 100"
			+", with one argument, 0 and your number and two arguments, between the two numbers you gave in input ( a <= b )";
	private static Random random = new Random();

	
	public void action(String[] args, MessageReceivedEvent event) {
			if(event.getChannelType()==ChannelType.PRIVATE && args.length == 0)
				event.getChannel().sendMessage(String.valueOf(random.nextInt(101))).queue();
			
			else if(event.getChannelType()==ChannelType.PRIVATE && args.length == 1 && Tools.isInteger(args[0])) 
					event.getChannel().sendMessage(String.valueOf(random.nextInt(Integer.parseInt(args[0])))).queue();

			else if(event.getChannelType()==ChannelType.PRIVATE && args.length == 2 && Tools.isInteger(args[0]) && Tools.isInteger(args[1]) && (Integer.parseInt(args[0]) <= Integer.parseInt(args[1])))
						event.getChannel().sendMessage(String.valueOf(random.nextInt(((Integer.parseInt(args[1])-Integer.parseInt(args[0]))+Integer.parseInt(args[0]))))).queue();
			
			else if(event.getChannelType()==ChannelType.PRIVATE && args.length == 1 && args[0]=="help")
				event.getChannel().sendMessage(HELP).queue();
			
			else
				event.getChannel().sendMessage(HELP).queue();
	}

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public String help() {
		return HELP;
	}

	public void executed(boolean success, String[] args, MessageReceivedEvent event) {
		return;
	}
}
