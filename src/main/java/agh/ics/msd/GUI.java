package agh.ics.msd;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class containing GUI: board + buttons
 */
public class GUI extends JPanel implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Board board;
    private JButton start_gol;
    private JButton start_rain;
    private JButton clear;
    private JSlider pred;
    private JFrame frame;
    private int iterNum = 0;
    private final int maxDelay = 500;
    private final int initDelay = 100;
    private boolean running = false;
    private int type;

    public GUI(JFrame jf) {
        frame = jf;
        timer = new Timer(initDelay, this);
        timer.stop();
    }

    /**
     * @param container to which GUI and board is added
     */
    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(1024, 768));

        JPanel buttonPanel = new JPanel();

        start_gol = new JButton("Game of Life - Start");
        start_gol.setActionCommand("Game of Life - Start");
        start_gol.setToolTipText("Starts clock");
        start_gol.addActionListener(this);

        start_rain = new JButton("Rain - Start");
        start_rain.setActionCommand("Rain - Start");
        start_rain.setToolTipText("Starts clock");
        start_rain.addActionListener(this);

        clear = new JButton("Clear");
        clear.setActionCommand("clear");
        clear.setToolTipText("Clears the board");
        clear.addActionListener(this);

        pred = new JSlider();
        pred.setMinimum(0);
        pred.setMaximum(maxDelay);
        pred.setToolTipText("Time speed");
        pred.addChangeListener(this);
        pred.setValue(maxDelay - timer.getDelay());

        buttonPanel.add(start_gol);
        buttonPanel.add(start_rain);
        buttonPanel.add(clear);
        buttonPanel.add(pred);

        board = new Board(1024, 768 - buttonPanel.getHeight());
        container.add(board, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * handles clicking on each button
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            iterNum++;
            if (type == 0)
            {
                frame.setTitle("Game of Life (" + Integer.toString(iterNum) + " iteration)");
                board.iteration(0);}
            else
            {
                frame.setTitle("Rain (" + Integer.toString(iterNum) + " iteration)");
                board.iteration(1);
            }
        } else {
            String command = e.getActionCommand();
            if (command.equals("Game of Life - Start")) {
                start_rain.setEnabled(false);
                type = 0;
                if (!running) {
                    timer.start();
                    start_gol.setText("Pause");
                } else {
                    timer.stop();
                    start_gol.setText("Resume");
                }
                running = !running;
                clear.setEnabled(true);

            }
            else if (command.equals("Rain - Start")) {
                start_gol.setEnabled(false);
                type = 1;
                if (!running) {
                    timer.start();
                    start_rain.setText("Pause");
                } else {
                    timer.stop();
                    start_rain.setText("Resume");
                }
                running = !running;
                clear.setEnabled(true);
            }
            else if (command.equals("clear")) {
                iterNum = 0;
                timer.stop();
                start_gol.setEnabled(true);
                start_rain.setEnabled(true);
                start_rain.setText("Rain - Start");
                start_gol.setText("Game of Life - Start");
                board.clear();
                frame.setTitle("Cellular Automata Toolbox");
            }

        }
    }

    /**
     * slider to control simulation speed
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        timer.setDelay(maxDelay - pred.getValue());
    }
}

