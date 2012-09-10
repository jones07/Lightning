/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zj.taskmanager.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import zj.taskmanager.model.SubTask;
import zj.taskmanager.model.Task;
import zj.taskmanager.model.Agenda;


public class TestingJAXB {

  private static final String BOOKSTORE_XML = "./bookstore-jaxb.xml";

  public static void main(String[] args) throws JAXBException, IOException {

    ArrayList<Task> tasks = new ArrayList<>();

    
    // create books
    Task task1 = new Task();
    task1.setName("task 1");
    task1.setDesc("task 1 desc");
    task1.setPriority(1);
    task1.setPercentComplete(44);
    
    ArrayList<SubTask> subtasks = new ArrayList<>();
    SubTask stask1 = new SubTask();
    stask1.setName("task 1.1");
    stask1.setDesc("task 1.1 desc");
    stask1.setPriority(6);
    subtasks.add(stask1);
    SubTask stask2 = new SubTask();
    stask2.setName("task 1.2");
    stask2.setDesc("task 1.2 desc");
    stask2.setPriority(6);
    subtasks.add(stask2);
    task1.setSubtaskList(subtasks);            
    tasks.add(task1);
    
    Task task2 = new Task();
    task2.setName("task 2");
    task2.setDesc("task 2 desc");
    task2.setPriority(1);
    task2.setPercentComplete(88);
    
    ArrayList<SubTask> subtasks2 = new ArrayList<SubTask>();
    SubTask stask21 = new SubTask();
    stask21.setName("task 2.1");
    stask21.setDesc("task 2.1 desc");
    stask21.setPriority(8);
    subtasks2.add(stask21);
    SubTask stask22 = new SubTask();
    stask22.setName("task 2.2");
    stask22.setDesc("task 2.2 desc");
    stask22.setPriority(7);
    subtasks2.add(stask22);
    SubTask stask23 = new SubTask();
    stask23.setName("task 2.3");
    stask23.setDesc("task 2.3 desc");
    stask23.setPriority(6);
    subtasks2.add(stask23);
    task2.setSubtaskList(subtasks2);
    tasks.add(task2);
    
    Agenda tlist = new Agenda();
    tlist.setName("Task List");
    tlist.setTaskList(tasks);
            
    
       // create JAXB context and instantiate marshaller
    JAXBContext context = JAXBContext.newInstance(Agenda.class);
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.marshal(tlist, System.out);

    Writer w = null;
    try {
      w = new FileWriter(BOOKSTORE_XML);
      m.marshal(tlist, w);
    } finally {
      try {
        w.close();
      } catch (Exception e) {
      }
    }
System.out.println(System.getProperty("user.dir"));

    // get variables from our xml file, created before
    System.out.println();
    System.out.println("Output from our XML File: ");
    Unmarshaller um = context.createUnmarshaller();
    Agenda bookstore2 = (Agenda) um.unmarshal(new FileReader(BOOKSTORE_XML));

//    for (int i = 0; i < bookstore2.getBooksList().toArray().length; i++) {
//      System.out.println("Book " + (i + 1) + ": "
//          + bookstore2.getBooksList().get(i).getName() + " from "
//          + bookstore2.getBooksList().get(i).getAuthor());
//    }

  }
} 