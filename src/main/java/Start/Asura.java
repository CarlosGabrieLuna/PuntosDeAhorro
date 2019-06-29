package Start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.fazecast.jSerialComm.SerialPort;

public class Asura extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	_____________________________________________
	|				|text area en color negro	|
	|	imagen		|tipo consola para escribir	|
	|  del ojo bien |el texto que pronuncia el 	|
	|	perron		|programa					|
	|_______________|___________________________|
	 */

	private static JTextArea texto;
	private static JLabel	  Ojolb;
	private JComboBox<String> portList;
	private JButton conectar;
	public static Asura obj;
	
	public Asura(){
		super("Asura.");
		texto 		= new JTextArea();
		conectar 	= new JButton("conectar");
		SerialPort[] portNames = SerialPort.getCommPorts();
		portList = new JComboBox<String>();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());

		ImageIcon imagen		= new ImageIcon(this.getClass().getResource("ojo 10.gif"));
		Ojolb 		= new JLabel();
		conectar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    SerialPort chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
				chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
				if(chosenPort.openPort())
    				System.err.println("puerto abierto");
				if(!chosenPort.isOpen())
				{
					System.out.println("no hay inputstream");
					return;
				}
				
				Assistant t = new Assistant(new Scanner(chosenPort.getInputStream()), Ojolb, texto);
				t.start();
					
			}
		});
        
			
		
		texto.setBackground(Color.BLACK);
		texto.setForeground(Color.LIGHT_GRAY);
		texto.setCaretColor(Color.LIGHT_GRAY);
		texto.setFont(new Font("Monospaced",0,15));
		texto.setEditable(false);
		
		GroupLayout 		layout 	= new GroupLayout(this.getContentPane());
		GroupLayout.
		SequentialGroup 	h 		= layout.createSequentialGroup();
		GroupLayout.
		SequentialGroup 	v 		= layout.createSequentialGroup();
		
		h.addGroup(layout.createSequentialGroup()
						 .addGap(2)
						 .addComponent(Ojolb)
						 .addGap(10)
						 .addGroup(layout.createParallelGroup()
										 .addComponent(texto)
										 .addGap(10)
										 .addComponent(portList)
										 .addGap(10)
										 .addComponent(conectar)
										 .addGap(10)
								  )
				);
		v.addGroup(layout.createSequentialGroup()
						 .addGap(10)
						 .addGroup(layout.createParallelGroup()
										 .addGap(2)
										 .addComponent(Ojolb)
										 .addGap(10)
										 .addGroup(layout.createSequentialGroup()
												 .addComponent(texto)
												 .addGap(10) 
												 .addComponent(portList)
												 .addGap(10)
												 .addComponent(conectar)
												 .addGap(10)
										 )
								 )
						 .addGap(10)
				);
		
		layout.setHorizontalGroup(h);
		layout.setVerticalGroup(v);
		Icon imagen2 = new ImageIcon(imagen.getImage().getScaledInstance(400, 400, Image.SCALE_REPLICATE));
		Ojolb.setIcon(imagen2);
		this.setLayout(layout);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBounds(50,50,1000,440);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Asura.");
		
		
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		obj = new Asura();
		obj.show();
	} 
	
}
