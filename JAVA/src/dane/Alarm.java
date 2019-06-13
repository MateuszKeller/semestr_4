package dane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import sun.audio.*;

public class Alarm { //implements java.io.Serializable
	
	LocalTime before;
	DateTimeFormatter timeFormat;
	String sound; //"alarm1.wav";
	AudioStream audio = null;
	AudioData audioD = null;
	ContinuousAudioDataStream loop;

	public Alarm() {}
	
	public Alarm(LocalTime before) 
	{
		this.before = before;
		this.sound = AppParameters.sound;
		try 
		{
			audio = new AudioStream(new FileInputStream(sound) );
			audioD = audio.getData();
			loop = new ContinuousAudioDataStream(audioD); 
			
		} catch (FileNotFoundException e) { e.printStackTrace();}
		  catch (IOException e) { e.printStackTrace(); }
	}
	
	public LocalTime getBefore() { return before; }
	public void setBefore(LocalTime before) { this.before = before; }

	public String toString()
	{
		String ret = "";
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
	
	public void playSound()
	{
		
		AudioPlayer.player.start(loop);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		AudioPlayer.player.stop(loop);
		
	}
	
	public void stopSound()
	{
		AudioPlayer.player.stop(loop);
	}

}
