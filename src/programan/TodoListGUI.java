package programan;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class TodoListGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private ArrayList<String> tasks = new ArrayList<>();
	private ArrayList<Date> dates = new ArrayList<>();

	private JTextArea taskListTextArea;
	private JTextField newTaskTextField;
	private JButton addTaskButton;
	private JButton removeTaskButton;
	private JButton selectTaskButton;

	public TodoListGUI() {
		super("Todo List");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel(new BorderLayout());

		taskListTextArea = new JTextArea(10, 30);
		taskListTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(taskListTextArea);

		JPanel inputPanel = new JPanel(new FlowLayout());

		JLabel newTaskLabel = new JLabel("New task:");
		newTaskTextField = new JTextField(20);
		addTaskButton = new JButton("Add");
		addTaskButton.addActionListener(this);
		removeTaskButton = new JButton("Remove");
		removeTaskButton.addActionListener(this);
		selectTaskButton = new JButton("Select");
		selectTaskButton.addActionListener(this);

		inputPanel.add(newTaskLabel);
		inputPanel.add(newTaskTextField);
		inputPanel.add(addTaskButton);
		inputPanel.add(removeTaskButton);
		inputPanel.add(selectTaskButton);

		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(inputPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addTaskButton) {
			String task = newTaskTextField.getText();
			tasks.add(task);
			dates.add(new Date());
			updateTaskList();
			newTaskTextField.setText("");
		} else if (e.getSource() == removeTaskButton) {
			try {
				int taskToRemove = taskListTextArea.getLineOfOffset(taskListTextArea.getSelectionStart());
				if (taskToRemove >= 0 && taskToRemove < tasks.size()) {
					tasks.remove(taskToRemove);
					dates.remove(taskToRemove);
					updateTaskList();
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == selectTaskButton) {
			try {
				int taskToSelect = taskListTextArea.getLineOfOffset(taskListTextArea.getSelectionStart());
				if (taskToSelect >= 0 && taskToSelect < tasks.size()) {
					String task = tasks.get(taskToSelect);
					Date date = dates.get(taskToSelect);
					String message = "Task: " + task + "\nDate: " + date;
					JOptionPane.showMessageDialog(this, message);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

	}

	private void updateTaskList() {
		taskListTextArea.setText("");
		for (int i = 0; i < tasks.size(); i++) {
			String task = tasks.get(i);
			Date date = dates.get(i);
			String line = String.format("%d. %s (%s)\n", i + 1, task, date);
			taskListTextArea.append(line);
		}
	}

	public static void main(String[] args) {
		new TodoListGUI();
	}
}
