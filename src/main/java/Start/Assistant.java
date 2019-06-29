package Start;

import java.awt.Image;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.darkprograms.speech.synthesiser.SynthesiserV2;
import com.darkprograms.speech.translator.GoogleTranslate;

import Tools.MysqlConnect;
import Tools.User;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Assistant extends Thread{

	private Hashtable<String, Boolean> session = new Hashtable<String, Boolean>(); 
	
	private ImageIcon stanby,
					  loading;
	
	private Icon imageShow;
	
	private MysqlConnect conn;
	
	private Scanner in;
	private JLabel eye;
	private JTextArea text;
	
	private final SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	
	public Assistant(Scanner in, JLabel label, JTextArea text) {
		this.stanby  = new ImageIcon(this.getClass().getResource("ojo 10.gif"));
		this.loading = new ImageIcon(this.getClass().getResource("ojonuevoCargando.gif"));
		this.imageShow = new ImageIcon(this.stanby.getImage().getScaledInstance(400, 400, Image.SCALE_REPLICATE));
		this.conn = new MysqlConnect().connect();
		this.in = in;
		this.eye = label;
		this.text = text;
		this.eye.setIcon(imageShow);
	}
	
	@Override
	public void run() {
		while(true)
		{
			String resp = readSerialPort();//"0FFFFESP0000";//readSerialPort();
			
			String status = resp.substring(0, resp.length()-11),
					id = resp.substring(1);
			//int points = Integer.valueOf(resp.substring(8));
			
			if(resp.equals("") || resp.isEmpty())
				continue;
			
			User user = this.conn.getDataUser(id);
			if(user == null)
				continue;
			
			if(status.equals("1"))
			{
				speak("Hola "+user.getCompleteName()+" gracias por tu compra.", user.getIdiom());
				delay(3000);
				if(user.getPoints() >= 100)
				{
					speak("Se te ha hecho un descuento del quince porciento a tu compra.", user.getIdiom());
					delay(3000);
					user.setPoints(user.getPoints() - 100);
					speak("Se te han restado 100 puntos a tu tajeta de puntos.", user.getIdiom());
					delay(3000);
					if(this.conn.updateUserPoints(user))
						speak("Se han guardado correctamente tus puntos, vuelve pronto.", user.getIdiom());
					else
						speak("Lo lamento pero ha ocurrido un error en tu deposito de puntos.", user.getIdiom());
					
					delay(3000);
					
					continue;
				}
				
				speak("Vuelve pronto.", user.getIdiom());
			}
			else
			{
				speak("Hola "+user.getCompleteName()+" gracias por tu compra.", user.getIdiom());
				delay(3000);
				user.setPoints(user.getPoints() + 5);
				if(this.conn.updateUserPoints(user))
					speak("Se te han depositado 5 puntos a tu tajeta de puntos.", user.getIdiom());
				else
					speak("Lo lamento pero ha ocurrido un error en tu deposito de puntos.", user.getIdiom());
				
				delay(3000);
			}
			
			
		}
	}
	
	public Hashtable<String, Boolean> getSession() {
		return session;
	}

	public void setSession(Hashtable<String, Boolean> session) {
		this.session = session;
	}

	public ImageIcon getStanby() {
		return stanby;
	}

	public void setStanby(ImageIcon stanby) {
		this.stanby = stanby;
	}

	public ImageIcon getLoading() {
		return loading;
	}

	public void setLoading(ImageIcon loading) {
		this.loading = loading;
	}

	public Icon getImageShow() {
		return imageShow;
	}

	public void setImageShow(Icon imageShow) {
		this.imageShow = imageShow;
	}

	public MysqlConnect getConn() {
		return conn;
	}

	public void setConn(MysqlConnect conn) {
		this.conn = conn;
	}

	public Scanner getIn() {
		return in;
	}

	public void setIn(Scanner in) {
		this.in = in;
	}

	public JLabel getEye() {
		return eye;
	}

	public void setEye(JLabel eye) {
		this.eye = eye;
	}

	public JTextArea getText() {
		return text;
	}

	public void setText(JTextArea text) {
		this.text = text;
	}

	public SynthesiserV2 getSynthesizer() {
		return synthesizer;
	}
	
	private void delay(int milisegundos){try {Thread.sleep(milisegundos);} catch (InterruptedException e) {}}

	private String readSerialPort()
	{
		while(this.getIn().hasNextLine()) {
			String line = this.getIn().nextLine();
			if(!line.isEmpty())
				return line;
		}
		
		return "";
	}
	
	private void speak(String text, String idiom) {
		//System.out.println(text);
			try {
				synthesizer.setLanguage(idiom);
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(GoogleTranslate.translate(idiom, text)));
				player.play();
				char[] array = text.toCharArray();
				StringBuffer Append = new StringBuffer(); 
				for(int y = 0; y < array.length; y++)
				{
					this.text.setText("");
					Append.append(this.text.getText());
					Append.append(array[y]);
					this.text.setText(Append.toString());
					try {
						Thread.sleep(90);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				//System.out.println("Successfully got back synthesizer data");
				
			} catch (IOException | JavaLayerException e) {
				
				e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)
				
			}
		
}

}
