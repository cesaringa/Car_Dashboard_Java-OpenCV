package kamera;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
  
public class FaceDetection { 
   
   public int faceDetection(Mat img, String meinPath) {
	// Loading the OpenCV core library
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      
	      Mat src = img;

	      // Instantiating the CascadeClassifier um Face-Detection Algorithm anzufangen
	      String xmlFile = "C:/Users/ASUS/eclipse-workspace/Dashboard_ImageRecognition_1/src/xml/lbpcascade_frontalface.xml";
	      CascadeClassifier classifier = new CascadeClassifier(xmlFile);

	      // Detecting the face in the snap
	      MatOfRect faceDetections = new MatOfRect();
	      classifier.detectMultiScale(src, faceDetections);
	      System.out.println(String.format("Detected %s faces", 
	         faceDetections.toArray().length));

	      // Drawing boxes
	      for (Rect rect : faceDetections.toArray()) {
	         Imgproc.rectangle(
	            src,                                                  		// where to draw the box
	            new Point(rect.x, rect.y),                            		// bottom left
	            new Point(rect.x + rect.width, rect.y + rect.height), 		// top right
	            new Scalar(0, 0, 255),
	            3                                                     // RGB colour
	         );
	      }

	      // Writing the image
	      Imgcodecs.imwrite(meinPath, src);
	      //System.out.println("Image Processed");
		return faceDetections.toArray().length;
   }
   
   public String setPathDetectedFaces () {
	   String setpath = "C:/Users/ASUS/eclipse-workspace/Dashboard_ImageRecognition_1/res/facedetected.jpg";
	   return setpath;
   }
}



