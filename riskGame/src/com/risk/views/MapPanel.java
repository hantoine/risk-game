package com.risk.views;

import com.risk.models.Board;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class MapPanel extends JPanel{

    private final Image image;

    public MapPanel(String imgPath) {
        image= new ImageIcon(imgPath).getImage();
        //setBackground(Color.cyan);
        
    }
    
    public void inicializeMap(Board board){
        setLayout(new GridLayout(2,2));
        /**
        for (int i =0; i<4; i++){
            JLabel continent = new JLabel();
            continent.setLayout(new GridBagLayout());
            continent.setBackground(Color.green);
            continent.setOpaque(true);
            GridBagConstraints constr = new GridBagConstraints();
            
            Border lineBorder=new LineBorder(Color.BLACK);
            Border matteBorder=new MatteBorder(30, 30, 30, 30, Color.cyan);
            Border compoundBorder = new CompoundBorder(matteBorder,lineBorder);
            
            continent.setBorder(compoundBorder);
            
            int[] continentNumContries;
            continentNumContries = new int[2];
            continentNumContries[0]=2;
            continentNumContries[1]=4;
            
            
                for (int j =0; j<continentNumContries[0]; j++){
                    for(int k=0;k<continentNumContries[1];k++){
                        if(((k+1)*(j+1))<7){
                            JButton country= new JButton("*");
                            constr.gridx = j;
                            constr.gridy = k;
                            constr.weightx = 1;
                            constr.weighty = 1;
                            country.setOpaque(true);
                            country.setForeground(Color.blue);
                            country.setBackground(Color.gray);
                            //country.setContentAreaFilled(false);
                            continent.add(country,constr);
                        }
                        
                    }
                }
             
            add(continent);
        }
        */
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0,null);        
    }

}