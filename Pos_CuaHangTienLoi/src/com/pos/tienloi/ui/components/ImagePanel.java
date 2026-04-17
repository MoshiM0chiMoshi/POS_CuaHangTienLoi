package com.pos.tienloi.ui.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            image = new ImageIcon(url).getImage();
        } else {
            System.out.println("Không tìm thấy ảnh: " + path);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}