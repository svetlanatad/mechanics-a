import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class TimeTable extends JFrame implements ActionListener {

    private JPanel screen = new JPanel(), tools = new JPanel();
    private JButton tool[];
    private JTextField field[];
    private CourseArray courses;
    private Color CRScolor[] = {Color.RED, Color.GREEN, Color.BLACK};
    private Autoassociator autoassociator;

    public TimeTable() {
        super("Dynamic Time Table");
        setSize(500, 800);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setPreferredSize(new Dimension(400, 800));
        add(screen);

        setTools();
        add(tools);

        setVisible(true);
    }

    public void loadCourses() {
        int slots = Integer.parseInt(field[0].getText());
        int numCourses = Integer.parseInt(field[1].getText()) + 1;
        String clashFile = field[2].getText();
        courses = new CourseArray(numCourses, slots);
        courses.readClashes(clashFile);
        autoassociator = new Autoassociator(courses);
        draw();
    }

public void trainAndLog(int[] pattern, int iterations) {
    Autoassociator autoassociator = new Autoassociator(courses);

    for (int iteration = 1; iteration <= iterations; iteration++) {
	autoassociator.training(pattern);
        autoassociator.chainUpdate(pattern, 1);

       // System.out.println("Iteration: " + iteration);
       // System.out.print("Timeslots: ");
      for (int i = 1; i < courses.length(); i++) {
                courses.setSlot(i, pattern[i - 1]);
            }


        int clashes = courses.clashesLeft();
	//  for (int i = 1; i < pattern.length; i++) {
         //   System.out.print(pattern[i] + " ");
       // }
        //System.out.println();
	
       // System.out.println("Clashes: " + clashes);
	 System.out.println("Iteration: " + iteration);
            System.out.println("Clashes: " + clashes);
            if (clashes == 0) {
                System.out.println("No clashes found.");
                return;
            }

        System.out.println("Timeslot Assignments:");
        for (int i = 1; i < courses.length(); i++) {
            System.out.println("Course " + i + ": Slot " + courses.slot(i));
        }

        System.out.println("----------------------------------------");
    }
}


    public void setTools() {
        String capField[] = {"Slots:", "Courses:", "Clash File:", "Iters:", "Shift:"};
        field = new JTextField[capField.length];

        String capButton[] = {"Load", "Start", "Step", "Print", "Exit", "Continue", "Train"};
        tool = new JButton[capButton.length];

        tools.setLayout(new GridLayout(capField.length + capButton.length, 1));

        for (int i = 0; i < field.length; i++) {
            tools.add(new JLabel(capField[i]));
            field[i] = new JTextField(5);
            tools.add(field[i]);
        }

        for (int i = 0; i < tool.length; i++) {
            tool[i] = new JButton(capButton[i]);
            tool[i].addActionListener(this);
            tools.add(tool[i]);
        }

        field[0].setText("17");
        field[1].setText("190");
        field[2].setText("ear-f-83.stu");
        field[3].setText("1");
    }

    public void draw() {
        Graphics g = screen.getGraphics();
        int width = Integer.parseInt(field[0].getText()) * 10;
        if (courses == null) {
            System.err.println("Courses array is not initialized.");
            return;
        }
        for (int courseIndex = 1; courseIndex < courses.length(); courseIndex++) {
            g.setColor(CRScolor[courses.status(courseIndex) > 0 ? 0 : 1]);
            g.drawLine(0, courseIndex, width, courseIndex);
            g.setColor(CRScolor[CRScolor.length - 1]);
            g.drawLine(10 * courses.slot(courseIndex), courseIndex, 10 * courses.slot(courseIndex) + 10, courseIndex);
        }
    }

    private int getButtonIndex(JButton source) {
        int result = 0;
        while (source != tool[result])
            result++;
        return result;
    }

    public void actionPerformed(ActionEvent click) {
        int min, step, clashes;

        switch (getButtonIndex((JButton) click.getSource())) {
            case 0:
                loadCourses();
                break;
            case 1:
                min = Integer.MAX_VALUE;
                step = 0;
                for (int i = 1; i < courses.length(); i++)
                    courses.setSlot(i, 0);

                for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
                    courses.iterate(Integer.parseInt(field[4].getText()));
                    draw();
                    clashes = courses.clashesLeft();
                    if (clashes < min) {
                        min = clashes;
                        step = iteration;
                    }
                }
                System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
                setVisible(true);
                break;
            case 2:
                courses.iterate(Integer.parseInt(field[4].getText()));
                draw();
                break;
            case 3:
                System.out.println("Exam\tSlot\tClashes");
                for (int i = 1; i < courses.length(); i++)
                    System.out.println(i + "\t" + courses.slot(i) + "\t" + courses.status(i));
                break;
	    case 4: 
  		System.exit(0);
		break;
            case 5:
                min = Integer.MAX_VALUE;
                step = 0;
                for (int i = 1; i < courses.length(); i++) {
                    courses.setSlot(i, 0);
                }

                for (int iteration = 0; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
                    courses.iterate(Integer.parseInt(field[4].getText()));
                    draw();
                    clashes = courses.clashesLeft();
                    if (clashes < min) {
                        min = clashes;
                        step = iteration;
                    }
                }
                System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
                setVisible(true);
                break;

		case 6:
    			int slots = Integer.parseInt(field[0].getText());
    			courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, slots);
    			courses.readClashes(field[2].getText());
    			draw();
    			int[] pattern = new int[courses.length()];
   		 	for (int i = 1; i < courses.length(); i++) {
       			 	pattern[i - 1] = courses.slot(i);
   								 }
    			trainAndLog(pattern, Integer.parseInt(field[3].getText()));
   		 break;

        }
    }

    public static void main(String[] args) {
        new TimeTable();
    }
}

