package clock;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.font.*;
import javax.swing.*;

public class ClockPanel extends JPanel {
    
    Model model;
    
    public ClockPanel(Model m) {
        model = m;
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.white);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Rectangle bounds = getBounds();
        //System.out.println(bounds);
        
        Graphics2D gg = (Graphics2D) g;
        int x0 = bounds.width / 2;
        int y0 = bounds.height / 2;
        
        int size = Math.min(x0, y0);
        //gg.translate(x0, y0);
        //gg.scale(size, size);
        //gg.setStroke(new BasicStroke(0));
        
        gg.setStroke(new BasicStroke(1));
        
        // gg.draw(new Ellipse2D.Double(-1, -1, 2, 2));
        
        double radius = 0;
        double theta = 0;
        
        // Draw the tick marks around the outside
        for (int n = 0; n < 60; n++) {
            theta = (90 - n * 6) / (180 / Math.PI);
            if (n % 5 == 0) {
                radius = 0.65 * size;
            } else {
                radius = 0.7 * size;
            }
            double x1 = x0 + radius * Math.cos(theta);
            double y1 = y0 - radius * Math.sin(theta);
            radius = 0.75 * size;
            double x2 = x0 + radius * Math.cos(theta);
            double y2 = y0 - radius * Math.sin(theta);
            gg.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        
        // Draw the numbers
        // Font font = new Font("SansSerif", Font.BOLD, size / 5);
        Font font = new Font("SansSerif", Font.PLAIN, size / 5);
        gg.setFont(font);
        for (int n = 1; n <= 12; n++) {
            theta = (90 - n * 30) / (180 / Math.PI);
            radius = 0.9 * size;
            double x1 = x0 + radius * Math.cos(theta);
            double y1 = y0 - radius * Math.sin(theta);
            String s = "" + n;
            // To centre the numbers on their places, we need to get
            // the exact dimensions of the box
            FontRenderContext context = gg.getFontRenderContext();
            Rectangle2D msgbounds = font.getStringBounds(s, context);
            double ascent = -msgbounds.getY();
            double descent = msgbounds.getHeight() + msgbounds.getY();
            double height = msgbounds.getHeight();
            double width = msgbounds.getWidth();
            
            gg.drawString(s, (new Float(x1 - width/2)).floatValue(), 
                          (new Float(y1 + height/2 - descent)).floatValue());
        }
        
        // Draw the hour hand
        gg.setStroke(new BasicStroke(2.0f));
        theta = (90 - (model.hour + model.minute / 60.0) * 30) / (180 / Math.PI);
        radius = 0.5 * size;
        double x1 = x0 + radius * Math.cos(theta);
        double y1 = y0 - radius * Math.sin(theta);
        gg.draw(new Line2D.Double(x0, y0, x1, y1));
        
        // Draw the minute hand
        gg.setStroke(new BasicStroke(1.1f));
        theta = (90 - (model.minute + model.second / 60.0) * 6) / (180 / Math.PI);
        radius = 0.75 * size;
        x1 = x0 + radius * Math.cos(theta);
        y1 = y0 - radius * Math.sin(theta);
        gg.draw(new Line2D.Double(x0, y0, x1, y1));
        
        // Draw the second hand
        gg.setColor(Color.red);
        gg.setStroke(new BasicStroke(0));
        theta = (90 - model.second * 6) / (180 / Math.PI);
        x1 = x0 + radius * Math.cos(theta);
        y1 = y0 - radius * Math.sin(theta);
        gg.draw(new Line2D.Double(x0, y0, x1, y1));
    }
}
