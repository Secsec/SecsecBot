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
import secsec.audio.TrackScheduler;
import secsec.utils.Const;

public class AudioCommand implements Command{
	
	private static VoiceChannel myChannel;
	private static AudioManager audioManager;
	private static Guild guild;
	private static boolean isConnected;
	private static boolean isInitialized;
	//private static AudioPlayer audioPlayer;
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(event.getChannelType() != ChannelType.PRIVATE) {
			if(args.length == 1 && args[0].startsWith("connect")) {
				try {
				guild = event.getGuild();
				myChannel = guild.getVoiceChannelsByName(Const.DEFAULT_TALKING_CHANNEL, true).get(0);
				audioManager = guild.getAudioManager();
				audioManager.openAudioConnection(myChannel);
				
				isConnected = true;
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if(args.length == 1 && args[0].startsWith("disconnect") && isConnected==true){
				audioManager.closeAudioConnection();
				isConnected = false;
			}
			
			if(args.length == 1 && args[0].startsWith("ah!") && isConnected==true){
					
				AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
				AudioSourceManagers.registerRemoteSources(playerManager);
				AudioSourceManagers.registerLocalSource(playerManager);
				
				AudioPlayer player = playerManager.createPlayer();
				final AudioPlayer player2 = player;
				
				guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player2));
				//player.addListener(trackScheduler);
			
				playerManager.loadItemOrdered(player2,"https://www.youtube.com/watch?v=XE6YaLtctcI", new AudioLoadResultHandler() {
					  public void trackLoaded(AudioTrack track) {
						  player2.playTrack(track);
					  }

					  public void playlistLoaded(AudioPlaylist playlist) {
					    for (AudioTrack track : playlist.getTracks()) {
					      //trackScheduler.queue(track);
					    }
					  }

					  public void noMatches() {

					  }

					  public void loadFailed(FriendlyException throwable) {

					  }
				});
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
