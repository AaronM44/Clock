# Clock
A simple analogue clock, using the MVC architecture, written for Assignment 2 for UG409765 Software Construction, part of the
BSc (Hons) Computing degree at the University of the Highlands and Islands.

Written in Java, using Swing and the Graphics2D part of AWT.

Based on an original "one class wonder". This version splits the code across five classes:

1. The root class is Clock, which just creates the Model, View and Controller and links them.
2. Model extends Java.util.Observable. When its update() method is called, it creates a java.util.Calendar object and checks whether
   the time has changed since this last happened. If so, it notifies its observers.
3. View implements the java.util.Observer interface. This is where the code for the main application window is. It uses a ClockPanel
   to do the hard work of actually drawing the clock face and hands.
4. Controller handles the process of waiting a little while and then getting the Model to check whether the time has changed, using a
   javax.swing.Timer and a java.awt.event.ActionListener. The timer is set for 1/10 of a second. There is almost certainly a better way
   to do this bit.
5. ClockPanel extends javax.swing.JPanel. It draws the tick marks and numbers for the clock face, and then uses information requested
   from the Model to draw the hour, minute and second hands.

The assignment is basically to turn this into a multi-alarm clock, using a Priority Queue of alarm objects, and incorporating a feature
where the clock can save its alarms to file using the iCalendar format, and read an iCalendar file back to load up alarms.
