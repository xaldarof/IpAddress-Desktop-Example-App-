package com.company.ui;

import com.company.data.ConnectionCallBack;
import com.company.data.IpIdentifier;
import com.company.utils.TextCopier;
import com.company.utils.Words;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Date;

public class UserInterface extends JFrame implements ConnectionCallBack {

    private JLabel ipLabel;
    private JButton buttonStart;
    private boolean isSuccessIp = false;

    public UserInterface() {
        setTitle(Words.title);
        setSize(350,100);

        setUpClickers();
        setUpLabels();
        setUpActions();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }

    private void setUpLabels(){
        ipLabel = new JLabel();
        ipLabel.setFont(new Font("bold",Font.BOLD,18));
        ipLabel.setBounds(36,12,130,32);
        add(ipLabel);
    }

    private void setUpClickers() {
        buttonStart = new JButton(Words.start_btn_txt);
        buttonStart.setBounds(200,12,130,32);
        add(buttonStart);
    }

    private void setUpActions() {
        ipLabel.setToolTipText(Words.click_to_copy);
        IpIdentifier ipIdentifier = new IpIdentifier();
        ipIdentifier.registerCallBack(this);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStart.setEnabled(false);
                ipIdentifier.getIpByConnection();
            }
        });

        ipLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isSuccessIp) TextCopier.copyText(ipLabel.getText());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ipLabel.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ipLabel.setForeground(Color.BLACK);
            }
        });
    }

    @Override
    public void onSuccess(String ip) {
        ipLabel.setText(ip);
        buttonStart.setEnabled(true);
        isSuccessIp = true;
    }

    @Override
    public void onError(Exception e) {
        JOptionPane.showMessageDialog(null,Words.connnect_error,Words.error_title, JOptionPane.ERROR_MESSAGE);
        buttonStart.setEnabled(true);
    }
}
