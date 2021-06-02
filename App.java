import java.awt.event.*;
import javax.swing.*;

import java.awt.*;
class App extends JFrame implements ActionListener {
    
    // create a frame
    static JFrame f;
 
    // create a textfield
    static JTextField l;

    public static void main(String args[]) {
        // create a frame
        f = new JFrame("boolean calculator");
        f.setType(Type.UTILITY);
 
        try {
            // set look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
 
        // create a object of class
        App c = new App();
 
        // create a textfield
        l = new JTextField();
        l.setEditable(false);
        l.setBounds(25, 25, 150, 50);
 
        // create number buttons and some operators
        JButton b0, b1, ba, bs, bd, bm, beq, beq1, bp1, bp2, bdel;
 
        // create number buttons
        b0 = new JButton("0");
        b0.setBounds(25, 90, 40, 25);
        b0.setFocusable(false);
        b1 = new JButton("1");
        b1.setBounds(80, 90, 40, 25);
        b1.setFocusable(false);
 
        // equals button
        beq1 = new JButton("=");
        beq1.setBounds(25, 210, 95, 25);
        beq1.setFocusable(false);
        bdel = new JButton("D");
        bdel.setBounds(135, 210, 40, 25);
        bdel.setFocusable(false);
 
        // create operator buttons
        ba = new JButton("+");
        ba.setBounds(135, 90, 40, 25);
        ba.setFocusable(false);
        bs = new JButton("-");
        bs.setBounds(80, 130, 40, 25);
        bs.setFocusable(false);
        bd = new JButton("x");
        bd.setFocusable(false);
        bd.setBounds(25, 130, 40, 25);
        bm = new JButton("*");
        bm.setFocusable(false);
        bm.setBounds(135, 130, 40, 25);
        beq = new JButton("C");
        beq.setFocusable(false);
        beq.setBounds(135, 170, 40, 25);

        //create buttons "(", and ")"
        bp1 = new JButton("(");
        bp1.setFocusable(false);
        bp1.setBounds(25, 170, 40, 25);
        bp2 = new JButton(")");
        bp2.setFocusable(false);
        bp2.setBounds(80, 170, 40, 25);
 
        // create a panel
        JPanel p = new JPanel();
        p.setLayout(null);
 
        // add action listeners
        bm.addActionListener(c);
        bd.addActionListener(c);
        bs.addActionListener(c);
        ba.addActionListener(c);
        b1.addActionListener(c);
        b0.addActionListener(c);
        beq.addActionListener(c);
        beq1.addActionListener(c);
        bp1.addActionListener(c);
        bp2.addActionListener(c);
        bdel.addActionListener(c);
 
        // add elements to panel
        p.add(l);
        p.add(ba);
        p.add(b1);
        p.add(bd);
        p.add(b0);
        p.add(beq);
        p.add(beq1);
        p.add(bm);
        p.add(bs);
        p.add(bp1);
        p.add(bp2);
        p.add(bdel);
 
        // set Background of panel
        p.setBackground(Color.darkGray);
 
        // add panel to frame
        f.add(p);
 
        f.setSize(215, 300);
        f.setVisible(true);;
        f.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand() == "C") {
            l.setText("");
        } else if (e.getActionCommand() == "=") {
            int result = eval(l.getText());
            l.setText(String.valueOf(result)); 
        } else if (e.getActionCommand() == "D") {
            if (l.getText().length() != 0) {
                l.setText(l.getText().substring(0, l.getText().length() - 1));
            }
        } else {
            String text = l.getText();
            l.setText(text + e.getActionCommand());
        }
    }

    public static int eval(final String str) { //NOT MY CODE || CODE BY "Boann" ON STACKOVERFLOW
        return new Object() {
            int pos = -1, ch;
    
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
    
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
    
            int parse() {
                nextChar();
                int x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
    
            int parseExpression() {
                int x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (x > 1) { 
                        x = 1;
                        return x;
                    } else return x;
                }
                
            }
    
            int parseTerm() {
                int x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    if      (eat('x')) x += parseTerm(); // xor
                    else if (x > 1) { 
                        x = 0;
                        return x;
                    } else return x;
                }
            }
    
            int parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) {
                    return negate(parseFactor());
                } // unary minus
    
                int x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Integer.parseInt(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
        
                return x;
            }

            int negate(int x) { //mine lol

                if (x == 1.0) x = 0;
                else x = 1;
                return x;
            }
        }.parse();
    }
}
