package secsec.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import secsec.audio.AudioPlayerSendHandler;

public class AudioCommand implements Command{
	
	private static VoiceChannel myChannel;
	private static AudioManager audioManager;
	//private static AudioPlayer audioPlayer;
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(event.getChannelType() != ChannelType.PRIVATE)
		try {
		Guild guild = event.getGuild();
		myChannel = guild.getVoiceChannelsByName("General", true).get(0);
		audioManager = guild.getAudioManager();
		
		
		audioManager.openAudioConnection(myChannel);
		//audioManager.closeAudioConnection();
		
		
		} catch(Exception e) {
			e.printStackTrace();
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
