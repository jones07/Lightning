/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zj.taskmanager.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import zj.taskmanager.CommandDispatcher;
import zj.taskmanager.model.Task;

/**
 *
 * @author Owner
 */
public class EditTaskDialog extends JDialog {

    private CommandDispatcher commandDispatcher;
    private JPanel contentPanel;
    private JPanel taskFieldsPanel;
    private JTextField nameText;
    private JSpinner prioritySpinner;
    private JSpinner dateSpinner;
    private JScrollPane descScrollPane;
    private JTextArea descText;
    private JPanel buttonPanel;  
    private JButton cancelButton;
    private JButton updateButton;
    private JSlider percentSlider;
    private JLabel percentText;
    private Task task;
    
    public EditTaskDialog(Frame owner, boolean modal, CommandDispatcher commandDispatcher, Task task) {
        super(owner, modal);
        this.task = task;
        this.commandDispatcher = commandDispatcher;
        initComponents();
    }

    private void initComponents() {
        
        this.setSize(new Dimension(407, 294));
        contentPanel = new JPanel();
        // Task Fields Panel
        taskFieldsPanel = new JPanel();
        JLabel nameLabel = new JLabel("Name:");
        JLabel priorityLabel = new JLabel("Priority:");
        JLabel dateLabel = new JLabel("Est. Comp. Date:");
        JLabel descLabel = new JLabel("Description:");
        JLabel percentLabel = new JLabel("Percent Complete:");
        percentText = new JLabel(task.getPercentComplete()+"");
        createDateSpinner();
        createPercentSlider();
        nameText = new JTextField(task.getName());
        createPrioritySpinner();
        descText = new JTextArea(task.getDesc());
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descScrollPane = new JScrollPane(descText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        GroupLayout taskFieldsLayout = new GroupLayout(taskFieldsPanel);
        taskFieldsPanel.setLayout(taskFieldsLayout);
        
        taskFieldsLayout.setHorizontalGroup(
                taskFieldsLayout.createSequentialGroup()
                    .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(taskFieldsLayout.createSequentialGroup()
                            .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(nameLabel)
                                .addComponent(priorityLabel)
                                .addComponent(dateLabel)
                                .addComponent(percentText)
                                .addComponent(descLabel))
                            .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameText)
                                .addComponent(prioritySpinner)
                                .addComponent(dateSpinner)
                                .addComponent(percentLabel)))
                            .addComponent(descScrollPane)
                            .addComponent(percentSlider)));
        taskFieldsLayout.setVerticalGroup(
                taskFieldsLayout.createSequentialGroup()
                    .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameText))
                    .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(priorityLabel)
                        .addComponent(prioritySpinner))
                    .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(dateLabel)
                        .addComponent(dateSpinner))
                    .addGroup(taskFieldsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(percentLabel)
                        .addComponent(percentText))
                    .addComponent(percentSlider)
                    .addComponent(descLabel)
                    .addComponent(descScrollPane));
        
        createUpdateButton();
        createCancelButton();
        
        buttonPanel = new JPanel();
        
        GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        
        buttonPanelLayout.setHorizontalGroup(
                buttonPanelLayout.createSequentialGroup()
                    .addComponent(cancelButton)
                    .addGap(0, 200, Short.MAX_VALUE)
                    .addComponent(updateButton));
        buttonPanelLayout.setVerticalGroup(
                buttonPanelLayout.createSequentialGroup()
                    .addGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(updateButton)));
        
        
        GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        
        contentPanelLayout.setHorizontalGroup(
                contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(taskFieldsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        contentPanelLayout.setVerticalGroup(
                contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(contentPanelLayout.createSequentialGroup()
                    .addComponent(taskFieldsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)));
        this.setContentPane(contentPanel);
        this.setTitle("Add Task");
    }


    private void createUpdateButton() {
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateButtonActionPerformed(e);
            }
        });
    }
    
    private void updateButtonActionPerformed(ActionEvent e){
        if(fieldsValid()){
            setVisible(false);
            task.setName(nameText.getText());
            task.setDesc(descText.getText());
            task.setEstCompleteDate((Date)dateSpinner.getValue());
            task.setPriority(Integer.parseInt(prioritySpinner.getValue().toString()));
            task.setPercentComplete(percentSlider.getValue());
            commandDispatcher.dispatch(CommandDispatcher.Command.UPDATE_TASK);
            dispose();
        }
    }

    private boolean fieldsValid() {
        boolean retVal = false;
        if(nameText.getText() == null || nameText.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Name cannot be blank.");
        else
            retVal = true;
        //TODO: finish task validation
        return retVal;
    }
    
    private void createCancelButton() {
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelButtonActionPerformed(e);
                }
            });
    }
    
    private void cancelButtonActionPerformed(ActionEvent e){
        setVisible(false);
        dispose();
    }

    private void createDateSpinner() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        Date minDate = cal.getTime();
        cal.add(Calendar.YEAR, 4);
        Date maxDate = cal.getTime();
        SpinnerDateModel model = new SpinnerDateModel(task.getEstCompleteDate(), minDate, maxDate, Calendar.YEAR);
        dateSpinner = new JSpinner(model);
    }

    private void createPrioritySpinner() {
        SpinnerModel priorityModel = new SpinnerNumberModel(task.getPriority(), 0, 10, 1);
        prioritySpinner = new JSpinner(priorityModel);
    }
    
    private void createPercentSlider() {
        percentSlider = new JSlider(0, 100, task.getPercentComplete());
        percentSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                percentText.setText(""+percentSlider.getValue());
            }
        });
    }
}

