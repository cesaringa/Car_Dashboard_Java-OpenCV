package kamera;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javafx.application.Application;
import javafx.stage.Stage; 


public class Kamera {
	    
	    public Mat captureImage() {
	     	
	    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    	VideoCapture camera = new VideoCapture(0);
	    	Mat meinFoto = new Mat();
	    	if(!camera.isOpened()){
	    		System.out.println("Error");
	    	}
	    	else {
	    		meinFoto = new Mat();
	    	    while(true){
	    	    	if (camera.read(meinFoto)){
	    	    		System.out.println("Frame Obtained");
	    	    		System.out.println("Captured Frame Width " + 
	    	    				meinFoto.width() + " Height " + meinFoto.height());
	    	    		Imgcodecs.imwrite("../res/capture.jpg",meinFoto);
	    	    		System.out.println("OK");
	    	    		break;
	    	    	}
	    	    }    
	    	}
	    	camera.release();
	    	return meinFoto;  	
	    }
	    
}
