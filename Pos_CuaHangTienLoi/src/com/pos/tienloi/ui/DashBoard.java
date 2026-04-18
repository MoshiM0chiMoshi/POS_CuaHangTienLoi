package com.pos.tienloi.ui;

import javax.swing.*;

import com.pos.tienloi.ui.components.SideBar;

import java.awt.*;

public class DashBoard extends JFrame {

	public DashBoard() {
		setLayout(new BorderLayout());

		add(new SideBar(), BorderLayout.WEST);

		setSize(1300, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new DashBoard();
	}
}
