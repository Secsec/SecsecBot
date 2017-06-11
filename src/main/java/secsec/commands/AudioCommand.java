package secsec.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
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
import secsec.utils.Const;
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
	//private static AudioPlayer audioPlayer;
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(event.getChannelType() != ChannelType.PRIVATE) {
			if(args.length == 1 && "connect".equals(args[0]) && event.getChannel().getName().equals("general") && isConnected == false) {
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
				
				return;
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if(args.length == 1 && args[0].startsWith("disconnect") && isConnected==true){
				audioManager.closeAudioConnection();
				isConnected = false;
			}
			
			if(player.isPaused()==true)
				player.setPaused(false);
			
			if(args.length == 1 && "stop".equals(args[0]) && isConnected==true && player.isPaused()==false){
				player.setPaused(true);
			}
			
			String sound;
			
			if(args.length == 1) {
				sound = tools.getFromAvailableFiles(args[0]);
				if(!sound.equals(null))
				playerManager.loadItem(getClass().getResource(sound).getPath(), new SoundPlayer(player));
			}
		}
	}

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public String help() {
		return null;
	}

	public void executed(boolean success, String[] args, MessageReceivedEvent event) {
		return;
	}
}
