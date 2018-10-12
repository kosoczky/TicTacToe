import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {

    private int size;
    private JButton[][] buttons;
    private boolean firstPlayer;
    private int stepCounter;
    private int toWin;

    public Window(int size, int toWin) {
        size = Math.max(size, 3); // max method visszaadja a két megadott érték közül a nagyobbat

        this.size = size;
        this.toWin = toWin;
        stepCounter = 0;
        firstPlayer = true;
        buttons = new JButton[size][size];

        setTitle("Amoeba");
        setSize(100 + size * 50, 100 + size * 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // mi történjen ha kilépsz a programból
        setLayout(new GridLayout(size,size));

        generateButtons();
    }

    private void generateButtons() {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                Font font = new Font(Font.SANS_SERIF, Font.BOLD, 50);
                Insets margin = new Insets(0, 0, 0, 0);

                JButton button = new JButton();
                button.setText("");
                button.setFont(font);
                button.setMargin(margin);

               // button.setBounds(50 * x + 50,50 * y + 50,40,40);

                button.setActionCommand(y + " " + x);
                button.addActionListener(this);
                this.add(button);
                buttons[y][x] = button;
            }
        }
    }

/*
    private boolean checkWinner(String player) {
        for (int y = 0; y < size; y++) {
            int sameNextToEachOther = 0;
            for (int x = 0; x < size; x++) {
                if (buttons[y][x].getText().equals(player)){
                    sameNextToEachOther++;
                } else {
                    sameNextToEachOther = 0;
                }
                if (sameNextToEachOther == toWin) {
                    return true;
                }
            }

        }

        for (int y = 0; y < size; y++) {
            int sameNextToEachOther = 0;
            for (int x = 0; x < size; x++) {
                if (buttons[x][y].getText().equals(player)){
                    sameNextToEachOther++;
                } else {
                    sameNextToEachOther = 0;
                }
                if (sameNextToEachOther == toWin) {
                    return true;
                }
            }

        }
        return false;
      }
      */

      private void clear() {
          for (int y = 0; y < size; y++) {
              for (int x = 0; x < size; x++) {
                  buttons[y][x].setText("");
                  stepCounter = 0;
              }
          }
          this.repaint();
      }

      // innentől jön Janis rész

 //   int size = 10;
    String[][] table;

    private boolean amIWinner(int x, int y, String player) {
        int count = 1 +
                count(x, y, player, -1, 1) +
                count(x, y, player, 1, -1);

        if (count >= toWin) {
            return true;
        }

        count = 1 +
                count(x, y, player, -1, -1) +
                count(x, y, player, 1, 1);

        if (count >= toWin) {
            return true;
        }

        count = 1 +
                count(x, y, player, 0, 1) +
                count(x, y, player, 0, -1);

        if (count >= toWin) {
            return true;
        }


        count = 1 +
                count(x, y, player, 1, 0) +
                count(x, y, player, -1, 0);

        if (count >= toWin) {
            return true;
        }

        return false;
    }

    int count(int x, int y, String player, int stepX, int stepY) {
        int i = y + stepY;
        int j = x + stepX;
        int counter = 0;

        while (coordsInBounds(i, j) && buttons[i][j].getText().equals(player)) {
            ++counter;
            i += stepY;
            j += stepX;
        }

        return counter;
    }

    // ellenőrzi benne van e a táblában
    private boolean coordsInBounds(int x, int y) { return x >= 0 && x < size && y >= 0 && y < size; }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        String[] coordinates = e.getActionCommand().split(" ");
        int y = Integer.valueOf(coordinates[0]);
        int x = Integer.valueOf(coordinates[1]);

        if (buttons[y][x].getText().isEmpty()) {
            if (firstPlayer) {
                buttons[y][x].setText("X");
                firstPlayer = false;
            } else {
                buttons[y][x].setText("O");
                firstPlayer = true;
            }
            stepCounter++;
        }
        if (amIWinner(x, y, buttons[y][x].getText())) {
            JOptionPane.showMessageDialog(this, "You have won '" + buttons[y][x].getText() + "' !", "WIN", JOptionPane.WARNING_MESSAGE);
            clear();
        } else if (stepCounter == size * size) {
            JOptionPane.showMessageDialog(this, "Yo'all are loosers!");
            clear();
        }
    }
}
