/**
 * @(#)Blackjack.java
 *
 *
 * @author
 * @version 1.00 2020/2/28
 */

import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;

public class v3 extends java.applet.Applet {
	MediaTracker tracker = new MediaTracker(this);
    Image offScreen;
	Graphics offG;

	Image background, pic, temperory, back, hit, stand, image1, image2;
	Image userwin, comwin, bust, nbj;
	Image b100, b200, b500, b1000, deal, start, cashout, restart;
	AudioClip flip, Musicwin, Musiclose, moneysound;

	ArrayList<Integer> userhand = new ArrayList<Integer>();
	ArrayList<Integer> computerhand = new ArrayList<Integer>();

	int tempset, tempcard, tempnum, sumnum, sumnuma;
	int usersum = 0;
	int computersum = 0;
	int usersuma = 0;
	int computersuma = 0;
	int x,y;
	int turn = 1;
	int userturn = 0;
	int comturn = 0;
	int betsum = 0;
	int money = 2500;
	int highscore = 2500;


	String test, testA;
	boolean cardstate;
	boolean userstate = true;
	boolean computerstate = true;
	boolean naturalbj = true;
	boolean restartstate = false;
	boolean betstate = true;
	boolean startstate = false;
	boolean restartb = false;

	Image [] clubs = new Image[13];
    Image [] hearts = new Image[13];
    Image [] spades = new Image[13];
    Image [] diamonds = new Image[13];

    Font f1, f2;

    public void init() {
        offScreen = createImage(1900,1300);
		offG = offScreen.getGraphics();

		background = getImage(getCodeBase(), "table.jpg");
		back = getImage(getCodeBase(), "back.png");
		hit = getImage(getCodeBase(), "hit.png");
		stand = getImage(getCodeBase(), "stand.png");
		userwin = getImage(getCodeBase(), "userwin.png");
		comwin = getImage(getCodeBase(), "comwin.png");
		bust = getImage(getCodeBase(), "bust.png");
		nbj = getImage(getCodeBase(), "bj.png");
		start = getImage(getCodeBase(), "start.png");
		restart = getImage(getCodeBase(), "restart.png");

		b100 = getImage(getCodeBase(), "bet100.png");
		b200 = getImage(getCodeBase(), "bet200.png");
		b500 = getImage(getCodeBase(), "bet500.png");
		b1000 = getImage(getCodeBase(), "bet1000.png");
		deal = getImage(getCodeBase(), "deal.png");
		cashout = getImage(getCodeBase(), "cashout.png");

		f1 = new Font("Impact",Font.BOLD,50);
		f2 = new Font("Impact",Font.BOLD,25);

		  //track loading of pics
		tracker.addImage(background, 0);
		tracker.addImage(back, 0);
		tracker.addImage(hit, 0);
		tracker.addImage(stand, 0);
		tracker.addImage(image1, 0);
		tracker.addImage(image2, 0);
		tracker.addImage(userwin, 0);
		tracker.addImage(comwin, 0);
		tracker.addImage(bust, 0);
		tracker.addImage(nbj, 0);
		tracker.addImage(b100, 0);
		tracker.addImage(b200, 0);
		tracker.addImage(b500, 0);
		tracker.addImage(b1000, 0);
		tracker.addImage(deal, 0);
		tracker.addImage(start, 0);
		tracker.addImage(restart, 0);
		flip = getAudioClip(getCodeBase(), "flip.wav");
		Musicwin = getAudioClip(getCodeBase(), "win.wav");
		Musiclose = getAudioClip(getCodeBase(), "lose.wav");
		moneysound = getAudioClip(getCodeBase(), "money.wav");
		//Wait for pictures to complete loading
		while(tracker.checkAll(true) != true){ }
		//Check if trouble loading pics
		if (tracker.isErrorAny()){
			JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
		}

        offG.drawImage(start, 0, 0, this);
    }

    public void paint(Graphics g) {
        g.drawImage(offScreen,0,0,this);
    }

	public void initialize(){ // using four image arrays to store different suits
		for (int i = 0; i < clubs.length; i++ ) {
            clubs[i] = getImage( getCodeBase(), "c" + Integer.toString(i + 1) + ".png");
        }
        for (int i = 0; i < hearts.length; i++ ) {
            hearts[i] = getImage( getCodeBase(), "h" + Integer.toString(i + 1) + ".png");
        }
        for (int i = 0; i < spades.length; i++ ) {
            spades[i] = getImage( getCodeBase(), "s" + Integer.toString(i + 1) + ".png");
        }
        for (int i = 0; i < diamonds.length; i++ ) {
            diamonds[i] = getImage( getCodeBase(), "d" + Integer.toString(i + 1) + ".png");
        }
	}

  	public boolean mouseDown(Event evt, int X, int Y) {
		System.out.println(X + " " + Y);

		if (betstate){ //display bet screen
			bet(); //show all the chips

	  		if (X > 120 && X < 220 && Y > 180 && Y < 330){ //bet $100
	  			if(money - 100 >= 0){
	  				betsum += 100;
	  				money = money - 100;
	  				moneysound.play();
	  			}
	  		}
	  		else if (X>40 && X < 210 && Y > 480 && Y < 520){ //cash out
	  			startstate = true;
	  			if(money > highscore){
					highscore = money;
				}
	  		}
	  		else if (X > 325 && X < 420 && Y > 180 && Y < 330){ //bet $200
	  			if(money - 200 >= 0){
	  				betsum += 200;
	  				money = money - 200;
	  				moneysound.play();
	  			}
	  		}
	  		else if (X > 525 && X < 670 && Y > 180 && Y < 330){ //bet $500
	  			if(money - 500 >= 0){
	  				betsum += 500;
	  				money = money - 500;
	  				moneysound.play();
	  			}
	  		}
	  		else if (X > 720 && X < 870 && Y > 180 && Y < 330){ //bet $1000
	  			if(money - 1000 >= 0){
	  				betsum += 1000;
	  				money = money - 1000;
	  				moneysound.play();
	  			}
	  		}

	  		betdisplay();
	  		balancedisplay();

	  		if(startstate){ //if cashout, show start again and reset the bank
	  			offG.drawImage(start, 0, 0, this);
	  			betsum = 0;
	  			money = 2500;
	  			startstate = false;
	  		}

	  		if(X > 425 && X < 555 && Y > 400 && Y < 465){ //if user pressed deal
				betstate = false;
				restart();
				balancedisplay();
	  		}

	  		System.out.println(betsum);
	  		System.out.println(money);
		}

		else if(usersum < 21 && X <= 760 && X >= 630 && Y <= 300 && Y >= 230 && userstate){ //user hit
				usergive();   //give usercard
				sum();  //calculate sum
				flip.play();  //play flip sound
				if(usersum >= 21){
					computergive();   //if player goes bust, computer goes
					userstate = false;   //user busts, can't hit anymore
				}
		}

		else if(X <= 310 && X >= 180 && Y >= 230 && Y <= 300 && computerstate){ // user stands & computer gives
			computergive();
		}

		repaint();
		return true;
  	}

	public void balancedisplay(){ //show the bank balance in the right down corner
		offG.setFont(f2);
		offG.setColor(Color.orange);
		offG.drawString("Bank: $" + money, 812, 512);
	}

  	public void bet() { //display the bet screen
  		offG.drawImage(background, 0, 0, this);
  		offG.drawImage(b100, 125, 180, this); //different bet values
		offG.drawImage(b200, 325, 180, this);
		offG.drawImage(b500, 525, 180, this);
		offG.drawImage(b1000, 725, 180, this);
		offG.drawImage(deal, 430, 400, this);
		if(restartb){
			offG.drawImage(restart, 50, 480, this);
			restartb = false;
		}
		else{
			offG.drawImage(cashout, 50, 480, this);
		}
		offG.setFont(f2);
		offG.setColor(Color.orange);
		offG.drawString("Highest score: $" + highscore, 375, 512);
  	}

	public void betdisplay(){  //display the bet amount
		offG.setFont(f1);
		offG.setColor(Color.orange);
		offG.drawString("$ " + betsum, 430, 100);
	}

  	public void restart() { //restart method
  		initialize(); //reset all the cards
  		clear();
		restartstate = false;
		userstate = true;
		computerstate = true;
		userhand.clear();
		computerhand.clear();
		offG.drawImage(background, 0, 0, this);
		userturn = 0;
		comturn = 0;

		giveout(); //give both user and computer the base cards
  		sum(); //calculate user's and computer's sums
  		offG.drawImage(hit, 630, 230, this);
  		offG.drawImage(stand, 180, 230, this);
  	}

	public void comparison(){ //determine the winner
		sum();
		if(usersum > 21){   //if user busts, dealer wins
			Musiclose.play();
		}
		else if(computersum > 21){ //if user is not busted, dealer busts, user wins
			Musicwin.play();
			money += betsum * 2;
		}
		else if(computersum < usersum){  //if both are not busted, user is higer, user wins
			Musicwin.play();
			money += betsum * 2;
		}
		else if(computersum > usersum){  //if dealer is higher, dealer wins
			Musiclose.play();
		}
		else if(computersum == usersum){
			money += betsum;
		}
		System.out.println(money + " " + betsum);
		betsum = 0;

		if(money == 0){
			restartb = true;
		}
	}

	public void usergive(){  //when user hits, gives out a random card
		x = 420 + 40*(userturn + 1);
		y = 370;
		userturn ++;
		randomcard();
		while(cardstate){
			randomcard();
		}
		tempnum = tempcard + 1;
		userhand.add(tempnum);
	}

	public void computergive(){  //computer stands, gives out random cards following the dealing rules
		offG.drawImage(image1, 380, 70, this);
		offG.drawImage(image2, 420, 70, this);
		computerstate = false;
		userstate = false;
		sum();
		flip.play();
		if(computersum < 17 && usersum <= 21){ //hit when under 17 and user under 21
			while(computersum < 17){ //must hit when it's under 17
				x = 420 + 40*(comturn + 1);
				y = 70;
				randomcard();
				while(cardstate){
					randomcard();
				}
				tempnum = tempcard + 1;
				computerhand.add(tempnum);
				sum();
				System.out.println(computersum);
				comturn++;
				try{Thread.sleep(100);} catch(Exception e){}
			}
		}
		comparison(); //determine winner
		betstate = true; //return to bet screen
	}

	public void giveout(){ //give out initial hands
		x = 380;
		y = 370;
		randomcard();
		while(cardstate){
			randomcard();
		}
		tempnum = tempcard + 1;
		userhand.add(tempnum); //add to the arrayList that is used to calculate sum

		x = 420;
		y = 370;
		randomcard();
		while(cardstate){
			randomcard();
		}
		tempnum = tempcard + 1;
		userhand.add(tempnum);

		x = 380;
		y = 70;
		randomcard();
		while(cardstate){
			randomcard();
		}
		image1 = temperory;
		offG.drawImage(back, x, y, this);
		tempnum = tempcard + 1;
		computerhand.add(tempnum);

		x = 420;
		y = 70;
		randomcard();
		while(cardstate){
			randomcard();
		}
		image2 = temperory;
		tempnum = tempcard + 1;
		computerhand.add(tempnum);
	}

	public void sum(){  //calculate the sum of user's and dealer's cards
		sumnum = 0;
		sumnuma = 0;
		usersum = 0;
		usersuma = 0;
		computersum = 0;
		computersuma = 0;
		for(int i = 0; i<userhand.size(); i++){  //get every value out from the arrayLists to calculate user sum
			sumnum = userhand.get(i);
			if(sumnum >= 10){
				sumnum = 10;
				usersum += sumnum;
				usersuma += sumnum;
			}

			else if(sumnum == 1){ //consider the special case for ace, using one or eleven
				sumnuma = 11;
				usersum += sumnum;
				usersuma += sumnuma;
			}

			else{
				usersum += sumnum;
				usersuma += sumnum;
			}
		}

		for(int i = 0; i<computerhand.size(); i++){ //sum of dealer's cards
			sumnum = computerhand.get(i);
			if(sumnum >= 10){
				sumnum = 10;
				computersum += sumnum;
				computersuma += sumnum;
			}

			else if(sumnum == 1){
				sumnuma = 11;
				computersum += sumnum;
				computersuma += sumnuma;
			}

			else{
				computersum += sumnum;
				computersuma += sumnum;
			}
		}

		if(usersuma > usersum && usersuma <= 21){
			usersum = usersuma;
		}

		if(computersuma > computersum && computersuma <= 21){
			computersum = computersuma;
		}
	}

	public void clear(){  //reset all the value in sum function
		sumnum = 0;
		sumnuma = 0;
		usersum = 0;
		usersuma = 0;
		computersum = 0;
		computersuma = 0;
	}

  	public void randomcard(){  //random card generator
  		Random ran = new Random();
		tempset = ran.nextInt(4); //random number to determine suite
		tempcard = ran.nextInt(13);  //random number to determine card value
		//System.out.println(tempset + " " + tempcard);

		if(tempset == 0) {
			if(clubs[tempcard] != null){ // suite is club
				temperory = clubs[tempcard]; //store the card as a temporary image
				clubs[tempcard] = null; //make the card unavailable when it is triggered again

				tracker.addImage(temperory, 0);
				while(tracker.checkAll(true) != true){ }
				if (tracker.isErrorAny()){
					JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
				}

				offG.drawImage(temperory, x, y, this);
				//System.out.println("clubs");
				cardstate = false;
			}
			else if(clubs[tempcard] == null){
				cardstate = true;
			}
		}
		else if(tempset == 1) { //suite is heart
			if(hearts[tempcard] != null){
				temperory = hearts[tempcard];
				hearts[tempcard] = null;

				tracker.addImage(temperory, 0);
				while(tracker.checkAll(true) != true){ }
				if (tracker.isErrorAny()){
					JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
				}

				offG.drawImage(temperory, x, y, this);
				//System.out.println("hearts");
				cardstate = false;
			}
			else if(hearts[tempcard] == null){
				cardstate = true;
			}
		}
		else if(tempset == 2) { //suite is spade
			if(spades[tempcard] != null){
				temperory = spades[tempcard];
				spades[tempcard] = null;

				tracker.addImage(temperory, 0);
				while(tracker.checkAll(true) != true){ }
				if (tracker.isErrorAny()){
					JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
				}

				offG.drawImage(temperory, x, y, this);
				//System.out.println("spades");
				cardstate = false;
			}
			else if(spades[tempcard] == null){
				cardstate = true;
			}
		}
		else if(tempset == 3) { //suite is diamond
			if(diamonds[tempcard] != null){
				temperory = diamonds[tempcard];
				diamonds[tempcard] = null;

				tracker.addImage(temperory, 0);
				while(tracker.checkAll(true) != true){ }
				if (tracker.isErrorAny()){
					JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
				}

				offG.drawImage(temperory, x, y, this);
				//System.out.println("diamonds");
				cardstate = false;
			}
			else if(diamonds[tempcard] == null){
				cardstate = true;
			}
		}

		repaint();
		//System.out.println(x + " " + y);
  	}
}
