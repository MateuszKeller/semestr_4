package dane;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import sun.audio.*;

public class Alarm { //implements java.io.Serializable
	
	private LocalDateTime before;
	private String sound; //"alarm1.wav";
	private ContinuousAudioDataStream loop;

	public Alarm(LocalDateTime before) {
		this(before, AppParameters.getInstance().getSound());
	}
	
	public Alarm(LocalDateTime before, String sound)
	{
		this.before = before;
		this.sound = AppParameters.getInstance().getSound();
		try 
		{
			AudioStream audio = new AudioStream(new FileInputStream(sound));
			AudioData audioD = audio.getData();
			loop = new ContinuousAudioDataStream(audioD); 
			
		} catch (FileNotFoundException e) { e.printStackTrace();}
		  catch (IOException e) { e.printStackTrace(); }
	}
	 
	public LocalDateTime getBefore() { return before; }
	public void setBefore(LocalDateTime before) { this.before = before; }
	public String getSound() { return sound; }
	public void setSound(String sound) { this.sound = sound; }


	public String toString(){
		String ret = "";
		DateTimeFormatter timeFormat;
		if(before.getHour() == 0)
		{
			timeFormat = DateTimeFormatter.ofPattern("m");
			ret += before.format(timeFormat) + " minut przed"; 
		}
		else
		{
			timeFormat = DateTimeFormatter.ofPattern("H");
			ret += before.format(timeFormat) + " godzin przed";
		}	
		return ret;
	}
	
	public void playSound(){
		AudioPlayer.player.start(loop);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.stop(loop);		
	}
	
	public void stopSound()
	{
		AudioPlayer.player.stop(loop);
	}

}
