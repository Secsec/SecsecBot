package secsec.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import secsec.audio.AudioPlayerSendHandler;
import secsec.audio.Scheduler;
import secsec.audio.SoundPlayer;
import secsec.audio.TrackScheduler;
import secsec.utils.Tools;

public class AudioCommand implements Command{
	
	private static VoiceChannel myChannel;
	private static AudioManager audioManager;
	private static Guild guild;
	private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	private static final AudioPlayer player = playerManager.createPlayer();
	private static final TrackScheduler scheduler = new TrackScheduler();
	private static final Tools tools = new Tools();
	
	private static boolean isConnected;
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(event.getChannelType() != ChannelType.PRIVATE) {

			String sound;
			
			if(args.length == 1 && "connect".equals(args[0]) && isConnected == false){
				connect(event);
				return;
			}
			
			if(player.isPaused()==true)
				player.setPaused(false);
			
			if(args.length == 2 && isConnected == true) {
				switch(args[0]) {
					case "yt":
						if(!args[1].equals(null)) {
							playerManager.loadItem(args[1], new SoundPlayer(player));
						}
						break;
					case "volume" :
						if(Integer.parseInt(args[1]) >= 0 && Integer.parseInt(args[1]) <= 100)
							player.setVolume(Integer.parseInt(args[1]));
						break;
				}
			}

			if(args.length == 1 && isConnected == true) {
				switch(args[0]) {
					case "disconnect":
						audioManager.closeAudioConnection();
						isConnected = false;
						event.getChannel().sendMessage("Bye!").queue();
						break;
					case "help":
						event.getChannel().sendMessage(help()).queue();
						break;
					case "stop":
						if(player.isPaused()==false)
							player.setPaused(true);
						break;
					default:
						sound = tools.getFromAvailableFiles(args[0]);
						if(sound == null) {
							event.getChannel().sendMessage("Sorry, I don't know this sound :(, tip \"!:audio help\" if you need informations.").queue();
							return;
						}	
						else if(!sound.equals(null))
							playerManager.loadItem(getClass().getResource(sound).getPath(), new SoundPlayer(player));
						break;
				}
			}
		}
	}

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public String help() {
		return "Commands : !:audio connect/disconnect : enable/disable the bot in your channel. \n\n"+
	"!:audio stop : stops the currently played sound.\n\n"+
	"!:audio <soundName> plays the requested sound. Currently available sounds are :"+tools.getAllSoundsName();
	}

	public void executed(boolean success, String[] args, MessageReceivedEvent event) {
		return;
	}
	
	public void connect(MessageReceivedEvent event){
		try {
			guild = event.getGuild();
			myChannel = guild.getVoiceChannelsByName(event.getMember().getVoiceState().getChannel().getName(), true).get(0);
			audioManager = guild.getAudioManager();
			audioManager.openAudioConnection(myChannel);
			
			AudioSourceManagers.registerRemoteSources(playerManager);
			AudioSourceManagers.registerLocalSource(playerManager);
			
			guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
			
			player.addListener(scheduler);
			
			isConnected = true;
			event.getChannel().sendMessage("Connected!").queue();
			
			return;
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
}
