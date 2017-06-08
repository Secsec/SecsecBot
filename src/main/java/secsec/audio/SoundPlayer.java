package secsec.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class SoundPlayer implements AudioLoadResultHandler{
	
	public SoundPlayer(AudioPlayer player) {
		this.player = player;
	}

	private AudioPlayer player;
	
	public void loadFailed(FriendlyException arg0) {

	}

	public void noMatches() {

	}

	public void playlistLoaded(AudioPlaylist arg0) {

	}

	public void trackLoaded(AudioTrack arg0) {
		player.playTrack(arg0);
	}

}
