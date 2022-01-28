import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WordProcessor extends JFrame implements ActionListener
{
    // Creating Frame
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;
    JButton fontColorButton; // For changing the color of font !
    JComboBox fontbox; // will blow your mind Variety of font availble here haha.

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    WordProcessor()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ojas's WordProcessor !");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null); // for making the frame appear in the middle of the screen !

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true); // For wrapping the text in new line i am using this code.
        textArea.setFont(new Font("Arial",Font.PLAIN,50)); // Setting Font !
        // Creating ScrollPane and plugging textarea inside it !
        scrollPane = new JScrollPane(textArea); //Adding textArea to the frame.
        scrollPane.setPreferredSize(new Dimension(450,450)); // Size
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// Scrollbar Added

        // Adding Label here
        fontLabel = new JLabel("Font:");

        //
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50,25));
        fontSizeSpinner.setValue(20);
        // The Jspinner uses chnage listener so adding change listener
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
                //casting as integer as it is regarding size

            }
        });
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);//to perform something !


        // Creating Array of fonts for all the availble fonts that are available in Java !

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontbox = new JComboBox(fonts);
        //Adding actionlistener to Jcombobox fontbox kuch kareh bhai yeahbhi isliye krrha hoon yaar mmai !

        fontbox.addActionListener(this);
        fontbox.setSelectedItem("Calibri");// setting it as Default !

        // ------------------------MenuBar--------------------------- !
         menuBar = new JMenuBar();
         fileMenu = new JMenu("File");
         openItem = new JMenuItem("Open");
         saveItem = new JMenuItem("Save");
         exitItem = new JMenuItem("Exit");

         fileMenu.add(openItem);
         fileMenu.add(saveItem);
         fileMenu.add(exitItem);
         menuBar.add(fileMenu);

         // Now Adding actionlistener to give them the real worth - ( Functionality)
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);


         // ------------- /MenuBAr-----------------------------!

         // now i have to add all these menuitems in the menu & then Menu into the Menu bar then will add MenuBAr
        // to the Frame.


        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner); // Added Font Size spinner !
        this.add(fontColorButton);
        this.add(fontbox);
        this.add(scrollPane);
        this.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==fontColorButton)
        {
            JColorChooser colorChooser = new JColorChooser();
            //To get Color i will going to show in dialogbox !
            Color color = colorChooser.showDialog(null,"Choose a Color",Color.black);
            textArea.setForeground(color);
        }
        // For Keep CHanging the FOnt Style whenever we want !
        if(e.getSource()==fontbox)
        {
            textArea.setFont(new Font( (String) fontbox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }

        //
        if(e.getSource()==openItem)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION)
            {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    //checking file is proper or not
                    if(file.isFile())
                    {
                        while(fileIn.hasNextLine())
                        {
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                }
                catch (FileNotFoundException ex)
                {
                    ex.printStackTrace();
                }
                finally {
                    fileIn.close();
                }
            }

        }
        if(e.getSource()==saveItem)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            // wantto save in defualt  project folder ? type dot(.) orelse give the file path
            int response = fileChooser.showSaveDialog(null);
            if(response==JFileChooser.APPROVE_OPTION)
            {
                File file;
                PrintWriter fileout = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    try{
                        fileout = new PrintWriter(file);
                        fileout.println(textArea.getText());
                        }
                    catch(FileNotFoundException e1)
                    {
                        e1.printStackTrace();

                    }
                    finally {
                        fileout.close();
                    }
            }

        }
        if(e.getSource()==exitItem)
        {
            System.exit(0);
        }

    }
}
