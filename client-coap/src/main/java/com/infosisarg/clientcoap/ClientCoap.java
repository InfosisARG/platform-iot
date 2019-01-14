package com.infosisarg.clientcoap;

import java.util.Random;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientCoap extends CoapServer {

	final static String[] firstNames = { "Kenneth","Shaylee","Marlon","Paola","Cade","Annalise","Avery","Michelle","Jennifer","Nikolai","Jaelyn","Darien","Hezekiah","Arjun","Alexus","Davon","Gianni","Jessie","Reese","Kane","Willow","Camden","Dylan","Valerie","Cassius","Annabelle","Athena","Dayana","Ben","Lee","Davian","Zaiden","Kayden","Lilyana","Khloe","Sage","Lucia","Jordin","Everett","Reina","Madisyn","Elle","Charlie","Danika","Alyssa","Liana","Morgan","Joshua","Charlotte","Kadin","Niko","Aaron","Leonard","Alvin","Davin","Gabriela","Adalynn","Natalie","Rosemary","Nicolas" };
	final static String[] lastNames = { "Hahn","Sawyer","Buck","Berg","Massey","Duran","Flynn","Booth","Ewing","Brewer","Obrien","Ortiz","Burgess","Pope","Ballard","Watkins","Daniels","Roy","Peterson","Cain","Meyer","Howell","Lewis","Conrad","Hubbard","Ritter","Gay","Mooney","Rowland","Baldwin","Shea","Oconnell","Zuniga","Vargas","Dickson","Butler","Reilly","Costa","Cochran","Jones","King","Terry","Kaiser","Simpson","Patton","Garrett","Tran","Hayden","Barton","Huber","Gaines","Spence","Scott","Maynard","Bailey","Callahan","Tanner","Hobbs","Carpenter","Contreras" };

	public static void main(String[] args) {
		CoapClient client = new CoapClient("coap://localhost:5683/message");
		Random random = new Random();
		int ejecuciones = 0;
		int i = 0;
		if(args.length > 0) {
			ejecuciones = Integer.parseInt(args[0]);
		}
		while(i<ejecuciones) {
			try {
				Thread.sleep(random.nextInt(6000));
				System.out.println(client.put(firstNames[random.nextInt(60)] + " " + lastNames[random.nextInt(60)] , 0).getResponseText());
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
