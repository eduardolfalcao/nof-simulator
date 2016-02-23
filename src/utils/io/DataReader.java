package utils.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.event.BarterEvent;
import model.event.Event;

public class DataReader {
	
	public static void main(String[] args) {
		String inputFile = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 5/workload-generator/tool/workload/";
		inputFile += "ordered_time_workload_clust_5spt_10ups.csv";
		DataReader dr = new DataReader();
		dr.readWorkload(inputFile);
		
		for(int i = 0; i < 100; i++){
			System.out.println((dr.events.get(i)).getPeerId()+", "+((BarterEvent)dr.events.get(i)).getJobId()+", "+
					(dr.events.get(i)).getSubmitTime());
		}
		
		dr.events = BarterEvent.generateEndEvents(dr.events);
		
		System.out.println("\n\n\n\n End Events \n");
		
		for(int i = 0; i < 100; i++){
			System.out.println((dr.events.get(i)).getPeerId()+", "+((BarterEvent)dr.events.get(i)).getJobId()+", "+
					(dr.events.get(i)).getSubmitTime());
		}	

	}

	private BufferedReader bufReader;
	private List<Event> events;
	
	public DataReader(){
		events = new ArrayList<Event>();
	}

	public void readWorkload(String file) {
		try {
			bufReader = new BufferedReader(new FileReader(file));
			// skip first line
			String line = bufReader.readLine();
			// read the rest of lines
			while ((line = bufReader.readLine()) != null)
				readTask(line);
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readTask(String line) {
		//peerId	jobId		submitTime	runTime
		//P4		U33-J1-T0	3			15

		String[] values = line.split(",");
		String peerId = values[0];
		String jobId = values[1];
		String submitTime = values[2];
		String runtime = values[3];
		
		boolean starting = true;

		events.add(new BarterEvent(peerId, jobId, Integer.parseInt(submitTime), Integer.parseInt(runtime), starting));
	}	
	
	public List<Event> getEvents(){
		return events;
	}
	
	public void setEvents(List<Event> events){
		this.events = events;
	}

}
