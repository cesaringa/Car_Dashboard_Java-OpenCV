package design.dashboard;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opencv.core.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import design.circle.progressBar.RingProgressIndicator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import kamera.*;

import design.dashboard.*;

public class DashboardDesign extends Application implements EventHandler<ActionEvent>{	
	
	/// Here we set button and textfield to get a test value from the user (Wert von Tastatuar)
	Button setGeschwindigkeitButton;
	TextField setGeschwindigkeitText;
	int wertVonTastatur = 0;
	
	// Set a constructor to update the value given from the user
	public void DashboardDesign() {
		setGeschwindigkeit(wertVonTastatur);
	}
	
	// Here it is initialised all the design parameters and also the threads are initialised //
	@Override
	public void start(Stage primaryStage) throws Exception {
		// All the graphic components that will be displayed are set (with JavaFX)
		Label label = new Label();
		label.setText("Benutzerfoto zu überprüfen");
		
		GridPane gitter0 = new GridPane();
		gitter0.setVgap(2);
		gitter0.setHgap(2);
		
		gitter0.setAlignment(Pos.CENTER);
		gitter0.setPadding(new Insets(10,10,10,10));
		
		
		Label meldung = new Label("No faces detected");
		meldung.setTextAlignment(TextAlignment.CENTER);
		meldung.setPadding(new Insets(10));
		meldung.setStyle("-fx-font-size: 17px; -fx-text-fill: silver;-fx-text-align: center");
		
        StackPane root0 = new StackPane();
        root0.getChildren().add(label);
        
        gitter0.add(meldung, 1, 1);
        Scene scene1 = new Scene(gitter0, 600, 600);
        //Stage firstStage = new Stage();
		
		primaryStage.setTitle("User-Face Detection");
        primaryStage.setScene(scene1);
        primaryStage.show();       
        
        ////////////////////// BILDVERARBEITUNG TEIL ////////////
        /*
         * For this part, it is used OpenCV libraries
         * First step: Capture images using the camera
         * Second step: Face detection
         */
        
        //////////////////////  Image Capture ///////////////////
        Kamera meinKamera = new Kamera();
        Mat meinCapture = meinKamera.captureImage();
        
        
        ////////////////////   Face Detection   /////////////////
        FaceDetection faceDetection = new FaceDetection();
        String meinPath = faceDetection.setPathDetectedFaces();
		while (true) {
			 
			  int FacesDetected = faceDetection.faceDetection(meinCapture,meinPath);
			  if(FacesDetected<=0) {
			        meinCapture = meinKamera.captureImage();
			        meinPath = faceDetection.setPathDetectedFaces();
			        FacesDetected = faceDetection.faceDetection(meinCapture,meinPath);
			  }
			  else
				  break;
		}

		
		// Window when Image is detected
		Button btn2 = new Button();
        btn2.setText("Klick um Dashboard zu Testen");
        
		  //creating the image object
	      InputStream stream = new FileInputStream(meinPath);
	      //Mat meinFoto = getImage();
	      Image image = new Image(stream);
	      //Creating the image view
	      ImageView imageView = new ImageView();
	      //Setting image to the image view
	      imageView.setImage(image);
	      imageView.setPreserveRatio(true);
	      //Setting the Scene object
	      
	      Group root = new Group(imageView);
	      root.getChildren().add(btn2);
	      
	     // Stage secondStage = new Stage();
	      
	      Scene scene2 = new Scene(root, image.getWidth(), image.getHeight());
	      primaryStage.setTitle("Displaying Image");
	      primaryStage.setScene(scene2);
	      primaryStage.show();
		  //primaryStage.setOnCloseRequest(e -> Platform.exit());
	      
    	/////////////////////////////////////////////////////////////
		
	    //////////////////// DASHBOARD DESIGN ///////////////////////
	      /*
		   * After detecting the face from the user, it is just initialised the Dashboard Control
		   * The design is also made with JavaFX
		  */
	   
	    // Action Event is called when a faced is detected (True face detected)
        btn2.setOnAction((ActionEvent event) -> {
        	//secondStage.setOnCloseRequest(e -> Platform.exit());

		GridPane gitter = new GridPane();
		gitter.setVgap(7);
		gitter.setHgap(7);
		
		gitter.setAlignment(Pos.CENTER);
		gitter.setPadding(new Insets(10,10,10,10));
		
		setGeschwindigkeitText = new TextField("Wert");
		setGeschwindigkeitText.setStyle("-fx-font-size: 15px; -fx-text-fill: black;-fx-text-align: center");
		setGeschwindigkeitButton = new Button ("Klicken Sie hier, um zu testen !");
		setGeschwindigkeitButton.setStyle("-fx-font-size: 15px; -fx-text-fill: silver;-fx-text-align: center");
		
		setGeschwindigkeitButton.setOnAction(this);
		
		RingProgressIndicator GeschwindigkeitZeiger = new RingProgressIndicator();
		RingProgressIndicator DrehzahlZeiger = new RingProgressIndicator(); 
		RingProgressIndicator Kraftstoff = new RingProgressIndicator();
		
		Label KraftstoffLabel = new Label("    KRAFTSTOFF\n  lt");
		KraftstoffLabel.setTextAlignment(TextAlignment.CENTER);
		KraftstoffLabel.setPadding(new Insets(10));
		KraftstoffLabel.setStyle("-fx-font-size: 19px; -fx-text-fill: silver;-fx-text-align: center");
		
		Label GeschwindigkeitLabel = new Label("GESCHWINDIGKEIT\nkm/h");
		GeschwindigkeitLabel.setTextAlignment(TextAlignment.CENTER);
		GeschwindigkeitLabel.setPadding(new Insets(10));
		GeschwindigkeitLabel.setStyle("-fx-font-size: 21px; -fx-text-fill:  #0D3CFF;-fx-text-align: center");
		
		
		Label DrehzahlLabel = new Label("       DREZAHL\n      x1000rpm");
		DrehzahlLabel.setTextAlignment(TextAlignment.CENTER);
		DrehzahlLabel.setPadding(new Insets(10));
		DrehzahlLabel.setStyle("-fx-font-size: 19px; -fx-text-fill: silver; -fx-text-align: center");
		
		Label begruessung1 = new Label("Car-Dashboard");
		begruessung1.setStyle("-fx-font-family: 'Bungee Shade', cursive;"
				+ " -fx-font-size: 55");
		
		Kraftstoff.setRingWidth(200);
		GeschwindigkeitZeiger.setRingWidth(500);
		DrehzahlZeiger.setRingWidth(200);
		
		Kraftstoff.makeIndeterminate();
		GeschwindigkeitZeiger.makeIndeterminate();
		DrehzahlZeiger.makeIndeterminate();
		
		gitter.add(begruessung1, 0, 0, 6, 1);
		
		gitter.add(Kraftstoff, 1, 1);//Spalte 1, Zeile 1
		gitter.add(GeschwindigkeitZeiger, 2, 1);
		gitter.add(DrehzahlZeiger, 3, 1);
		
		gitter.add(KraftstoffLabel, 1, 2);
		gitter.add(GeschwindigkeitLabel, 2, 2);
		gitter.add(DrehzahlLabel, 3, 2);
		
		gitter.add(setGeschwindigkeitText, 2, 4);
		gitter.add(setGeschwindigkeitButton, 2, 5);
		
		Scene scene = new Scene(gitter,600,500);
		
		new KraftstoffThread(Kraftstoff).start();
		new GeschwindigkeitThread(GeschwindigkeitZeiger).start();
		new DrehzahlThread(DrehzahlZeiger).start();

		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Bungee+Shade&family=Moo+Lah+Lah&display=swap");
		scene.getStylesheets().add("/css/hauptFensterStyle.css");
		
		 //Stage secondStage = new Stage();
		primaryStage.setTitle("Auto-Dashboard");
		primaryStage.setScene(scene);
		primaryStage.show();
        });       
	}
	
	@Override
	public void handle (ActionEvent event) {
			if(event.getSource()==setGeschwindigkeitButton) {
				
				try {
				    wertVonTastatur = Integer.parseInt(setGeschwindigkeitText.getText());
	//			    System.out.println("Klicked: "+wertVonTastatur);
				} catch (NumberFormatException e) {
					Alert alert = new Alert (Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Format Error");
					alert.showAndWait();			
				}
			}	
	}
	
	// Setter and Getter for Geschwindigkeit
	public void setGeschwindigkeit (int wertVonTastatur) {
		this.wertVonTastatur=wertVonTastatur;
	}
	
	public int getGeschwindigkeit() {
		return wertVonTastatur;
	}
	
	///////////// Geschwindigkeit Thread behaviour //////////////////////
	public class GeschwindigkeitThread extends Thread  {
		
		RingProgressIndicator ringProgressIndicatorThread;
		
		int progress;
			
		public GeschwindigkeitThread (RingProgressIndicator ringProgressIndicatorThread) {
			this.ringProgressIndicatorThread = ringProgressIndicatorThread;
		}
		
		@Override
		public void run() {
			while(true) {
				DashboardDesign dd = new DashboardDesign();
				int testGeschwindigkeit = wertVonTastatur;
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException ex) {
					Logger.getLogger(DashboardDesign.class.getName()).log(Level.SEVERE,null,ex);
				}
				
				Platform.runLater(()-> {
					ringProgressIndicatorThread.setProgress(progress);
				});
				
				ringProgressIndicatorThread.setProgress(progress);
							
				if(progress < testGeschwindigkeit)
					progress+=1;
				if (progress == testGeschwindigkeit)
					progress = testGeschwindigkeit;
				if(progress > testGeschwindigkeit)
					progress-=1;
			}
		}
	}
	//////////////////////////////////////////////////////////////////////////
	
	//////////////////////  Kraftstoff Thread behaviour //////////////////////
	public class KraftstoffThread extends Thread {
		RingProgressIndicator ringProgressIndicatorThread;
//		int testGeschwindigkeit =10;
		int progress =100;
		
		public KraftstoffThread (RingProgressIndicator ringProgressIndicatorThread) {
			this.ringProgressIndicatorThread = ringProgressIndicatorThread;
		}
		
		@Override
		public void run() {
			
			while(true) {
				DashboardDesign dd = new DashboardDesign();
				int testGeschwindigkeit = wertVonTastatur;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(DashboardDesign.class.getName()).log(Level.SEVERE,null,ex);
				}
				
				Platform.runLater(()-> {
					ringProgressIndicatorThread.setProgress(progress);
				});
				
				ringProgressIndicatorThread.setProgress(progress);
				if (testGeschwindigkeit > 0 && testGeschwindigkeit <= 20)
				progress-=1;
				if (testGeschwindigkeit > 20 && testGeschwindigkeit <= 40)
					progress-=2;
				if (testGeschwindigkeit > 40 && testGeschwindigkeit <= 60)
					progress-=3;
				if (testGeschwindigkeit > 60 && testGeschwindigkeit <= 80)
					progress-=4;
				if (testGeschwindigkeit > 80 && testGeschwindigkeit <= 100)
					progress-=4;
				if (testGeschwindigkeit > 100)
					progress-=5;
				if(progress <= 0)
					progress = 0;
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	
	//////////////////////// Drehzahl Thread behaviour //////////////////////////
	public class DrehzahlThread extends Thread {
		RingProgressIndicator ringProgressIndicatorThread;

		int progress = 0;
		
		public DrehzahlThread (RingProgressIndicator ringProgressIndicatorThread) {
			this.ringProgressIndicatorThread = ringProgressIndicatorThread;
		}
		
		@Override
		public void run() {
			while(true) {
				DashboardDesign dd = new DashboardDesign();
				int testGeschwindigkeit = wertVonTastatur;
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Logger.getLogger(DashboardDesign.class.getName()).log(Level.SEVERE,null,ex);
				}
				
				Platform.runLater(()-> {
					ringProgressIndicatorThread.setProgress(progress);
				});
				
				ringProgressIndicatorThread.setProgress(progress);
				if (testGeschwindigkeit > 0 && testGeschwindigkeit <= 25)
				progress=1;
				if (testGeschwindigkeit > 25 && testGeschwindigkeit <= 40)
					progress=2;
				if (testGeschwindigkeit > 40 && testGeschwindigkeit <= 60)
					progress=2;
				if (testGeschwindigkeit > 60 && testGeschwindigkeit <= 80)
					progress=4;
				if (testGeschwindigkeit > 80)
					progress=5;
				if(progress <= 0)
					progress = 0;
			}
		}
	}
	
}
