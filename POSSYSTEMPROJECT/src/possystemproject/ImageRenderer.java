/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package possystemproject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

    // Custom renderer for displaying images
    class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Check if the value is an instance of ImageIcon
            if (value instanceof ImageIcon imageIcon) {
                JLabel label = new JLabel();
                label.setIcon(imageIcon); // Set the image icon
                label.setHorizontalAlignment(JLabel.CENTER); // Center align the image
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }