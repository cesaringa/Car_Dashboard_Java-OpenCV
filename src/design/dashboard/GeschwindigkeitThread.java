//package design.dashboard;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import design.circle.progressBar.RingProgressIndicator;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//
//public class GeschwindigkeitThread  extends Thread  {
//	
//	RingProgressIndicator ringProgressIndicatorThread;
//	int progress;
//	int wertVonTastaturThread;
//	
//	public GeschwindigkeitThread (RingProgressIndicator ringProgressIndicatorThread, int wertVonTastatur) {
//		this.ringProgressIndicatorThread = ringProgressIndicatorThread;
//		setGeschwindigkeitThread(wertVonTastatur);
//	}
//	
//	public int getGeschwindigkeitThread() {
//		return wertVonTastaturThread;
//	}
//	
//	public void setGeschwindigkeitThread (int wertVonTastatur) {
//		this.wertVonTastaturThread=wertVonTastatur;
//	}
//	
//	@Override
//	public void run() {
//		while(true) {
//			DashboardDesign dashboardDesign = new DashboardDesign();
//			int testGeschwindigkeit = getGeschwindigkeitThread();
//			
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException ex) {
//				Logger.getLogger(DashboardDesign.class.getName()).log(Level.SEVERE,null,ex);
//			}
//			
//			Platform.runLater(()-> {
//				ringProgressIndicatorThread.setProgress(progress);
//			});
//			
//			ringProgressIndicatorThread.setProgress(progress);
//						
//			if(progress < testGeschwindigkeit)
//				progress+=1;
//			if (progress == testGeschwindigkeit)
//				progress = testGeschwindigkeit;
//			if(progress > testGeschwindigkeit)
//				progress-=1;
//		}
//	}
//}
