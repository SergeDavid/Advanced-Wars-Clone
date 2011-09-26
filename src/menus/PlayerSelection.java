package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import engine.Game;

public class PlayerSelection implements ActionListener {
	//TODO: Scale with map size.
	//Commander Selection
	JButton[] Prev = {new JButton("Prev"),new JButton("Prev"),new JButton("Prev"),new JButton("Prev")};
	JButton[] Next = {new JButton("Next"),new JButton("Next"),new JButton("Next"),new JButton("Next")};
	JLabel[] Name = {new JLabel("Andy"),new JLabel("Andy"),new JLabel("Andy"),new JLabel("Andy")};
	int[] plyer = {0,0,0,0};
	
	//NPC Stuff
	JButton[] ManOrMachine = {new JButton("PLY"),new JButton("NPC"),new JButton("NPC"),new JButton("NPC")};
	boolean[] npc = {false,true,true,true};
	
	//Other
	JButton Return = new JButton("Return");
	JButton StartMoney = new JButton ("$ 100");int start = 100;
	JButton CityMoney = new JButton ("$ 50");int city = 50;
	JButton ThunderbirdsAreGo = new JButton ("Start");
	
	String mapname;
	
	public PlayerSelection(String map) {
		mapname = map;
		Game.gui.removeAll();
		Game.gms.OpenMenu(400,200);
		for (int i = 0; i < 4; i++) {
			Prev[i].addActionListener(this);
			Prev[i].setBounds(10+84*i, 10, 64, 32);
			Game.gms.add(Prev[i]);
			Next[i].addActionListener(this);
			Next[i].setBounds(10+84*i, 100, 64, 32);
			Game.gms.add(Next[i]);
			ManOrMachine[i].addActionListener(this);
			ManOrMachine[i].setBounds(12+84*i, 68, 58, 24);
			Game.gms.add(ManOrMachine[i]);
			
			Name[i].setBounds(10+84*i, 40, 64, 32);
			Game.gms.add(Name[i]);
		}
		ThunderbirdsAreGo.addActionListener(this);
		ThunderbirdsAreGo.setBounds(200, 170, 100, 24);
		Game.gms.add(ThunderbirdsAreGo);
		Return.addActionListener(this);
		Return.setBounds(20, 170, 100, 24);
		Game.gms.add(Return);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == Return) {
			Game.gms.CloseMenu();
			Game.gui.LoginScreen();
		}
		else if(s == ThunderbirdsAreGo) {
			Game.btl.NewGame(mapname);
			Game.btl.AddCommanders(plyer, npc, 100, 50);
			Game.gui.InGameScreen();
		}
		for (int i = 0; i < 4; i++) {
			if (s == Prev[i]) {
				plyer[i]--;
				if (plyer[i]<0) {plyer[i]=Game.displayC.size()-1;}
				Game.gms.remove(Name[i]);
				Name[i].setText(Game.displayC.get(plyer[i]).name);
				Game.gms.add(Name[i]);
			}
			else if (s == Next[i]) {
				plyer[i]++;
				if (plyer[i]>Game.displayC.size()-1) {plyer[i]=0;}
				Game.gms.remove(Name[i]);
				Name[i].setText(Game.displayC.get(plyer[i]).name);
				Game.gms.add(Name[i]);
			}
			else if (s == ManOrMachine[i]) {
				npc[i]=!npc[i];
				Game.gms.remove(ManOrMachine[i]);
				if (npc[i]) {ManOrMachine[i].setText("NPC");}
				else {ManOrMachine[i].setText("PLY");}
				Game.gms.add(ManOrMachine[i]);
			}
		}
	}
}
