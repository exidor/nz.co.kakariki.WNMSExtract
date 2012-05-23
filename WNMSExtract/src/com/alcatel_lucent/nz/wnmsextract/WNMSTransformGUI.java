package com.alcatel_lucent.nz.wnmsextract;
/*
 * This file is part of wnmsextract.
 * 
 * wnmsextract is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * wnmsextract is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.log4j.Level;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


/**
 * GUI for the transformer. Select file to parse and the Stylesheet to use. Logging options 
 * are available in the GUI to aid debugging when developing a new XSL
 * @author jnramsay
 *
 */
public class WNMSTransformGUI {
	
	
	//private JLabel label,infilelabel,intablabel,logconlabel,logfilelabel;
	private JTextField snapshot,stylesheet;
	private JCheckBox logcon,logfile;
	private JComboBox loglevel;
	private JButton start,exit;

	
	/**
	 * Initialise GUI components
	 */
	private void initComponents(){

		//---------------------------------------------------------------------
		/*
		label = new JLabel("Enter Input Parameters");     
		infilelabel = new JLabel("Spreadsheet Name");
		intablabel = new JLabel("Tab Name");
		logconlabel = new JLabel("LOG Console");
		logfilelabel = new JLabel("LOG File");
		*/
		snapshot = new JTextField();
		snapshot.setText("f:\\data\\wnms\\test.xsl");
		stylesheet = new JTextField();
		stylesheet.setText("f:\\data\\wnms\\test.xsl");

		logcon = new JCheckBox();
		logcon.setSelected(Boolean.TRUE);
		logfile = new JCheckBox();
		logfile.setSelected(Boolean.TRUE);
		
		loglevel = new JComboBox();
		loglevel.addItem(Level.FATAL);
		loglevel.addItem(Level.ERROR);
		loglevel.addItem(Level.WARN);
		loglevel.addItem(Level.INFO);
		loglevel.addItem(Level.DEBUG);
		loglevel.addItem(Level.TRACE);
		loglevel.addItem(Level.ALL);
		loglevel.addItem(Level.OFF);		
		loglevel.setSelectedItem(Level.DEBUG);

		//---------------------------------------------------------------------

		start = new JButton("START");
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String snapstr = snapshot.getText();
				String stylestr = stylesheet.getText();
				
				boolean logconbool = logcon.isSelected();
				boolean logfilebool = logfile.isSelected();
				Level loglevelenum = (Level) loglevel.getSelectedItem();
				
				String outstr = snapstr.substring(0,snapstr.length()-4)+"."+stylestr.substring(stylestr.lastIndexOf("\\")+1, stylestr.length()-4)+".csv";
				
				System.out.println("Start (snapshot="+snapstr+",stylesheet="+stylestr+"[NOTUSED],outfile="+outstr+",lc="+logconbool+",lf="+logfilebool+",ll="+loglevelenum+")");
				
				WNMSTransform w = new WNMSTransform();
				w.process(snapstr);
				
			}

		});


		//---------------------------------------------------------------------
		exit = new JButton("EXIT");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Cancel");		
				System.exit(1);
			}

		});
	}

	/**
	 * buildPanel. Layout input screen
	 * @return
	 * r1 | Input Parameters -------------------------------
	 * r2 |  XCM Path+Name   [_________]
	 * r3 |  XSL Path+Name   [_________]
	 * r4 | Logging Outputs---------------------------------
	 * r5 |    Log Console   []   Log Level [DEBUG|v]
	 * r6 |       Log File   []
	 * r6 |   
	 * r7 |                  [   START   ][    EXIT    ]
	 * ---+-------------------------------------------------
	 *    ||c1            |c2|c3  |c4     |c5|c6
	 */

	private JComponent buildPanel() {
		
		FormLayout layout = new FormLayout(
			"left:pref", // 2cols
			"pref,pref,3dlu,pref,pref,3dlu,pref");       // 3rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addSeparator("Input Parameters", cc.xywh(1, 1, 1, 1));
		builder.add(buildInputPanel(),cc.xy(1, 2));
		builder.addSeparator("Logging Outputs", cc.xywh(1, 4, 1, 1));
		builder.add(buildLogPanel(),cc.xy(1, 5));
		builder.add(buildButtonPanel(),cc.xy(1, 7,"right,center"));

		return builder.getPanel();

	}	
	private JComponent buildInputPanel() {
		
		FormLayout layout = new FormLayout(
				"right:pref, 5dlu, 75dlu",         //cols
				"pref, 3dlu, pref");     // rows

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Snapshot Name", cc.xy (1, 1));
		builder.add(snapshot, cc.xyw(3, 1, 1));
		builder.addLabel("Stylesheet Name", cc.xy (1, 3));
		builder.add(stylesheet, cc.xyw(3, 3, 1));


		return builder.getPanel();

	}
	
	private JComponent buildLogPanel(){
		
		FormLayout layout = new FormLayout(
				"right:pref, 5dlu, pref, 5dlu, right:pref, 5dlu, pref",         //cols
				"pref,pref");     // rows		
		
		/*FormLayout layout = new FormLayout(
		"right:pref, 5dlu, pref, pref, 5dlu, pref",         //cols
		"p, 15dlu, 15dlu, p, 15dlu, 3dlu, pref");     // rows
		 */

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Log Console", cc.xy (1, 1));
		builder.add(logcon, cc.xy (3, 1));
		builder.addLabel("Log File", cc.xy (1, 2));
		builder.add(logfile, cc.xy (3, 2));
		builder.addLabel("Log Level", cc.xy (5, 1));
		builder.add(loglevel, cc.xy (7, 1));
		

		return builder.getPanel();
	}

	private JComponent buildButtonPanel(){

		FormLayout layout = new FormLayout(
			"pref,pref",         //cols
			"pref");     // rows

		layout.setColumnGroups(new int[][]{{1, 2}});

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();

		//builder.addSeparator("",cc.xywh(1, 7, 5, 1));
		builder.add(start, cc.xy (1, 1));
		builder.add(exit, cc.xy (2, 1));

		return builder.getPanel();
	}
	
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		} catch (Exception e) {
			System.err.println("Can't find plastic theme");
		}
		JFrame frame = new JFrame();
		Dimension screensize = frame.getToolkit().getScreenSize();
		frame.setLocation((int)(screensize.getWidth()*0.5), (int)(screensize.getHeight()*0.35));
		frame.setTitle("UTRAN Snapshot XSL ALUParser");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		WNMSTransformGUI m = new WNMSTransformGUI();
		m.initComponents();
		JComponent panel = m.buildPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}


