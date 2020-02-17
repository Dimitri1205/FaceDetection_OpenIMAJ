package com.company;



import org.json.JSONObject;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class FaceDetection_OpenIMAJ extends JFrame {

    public java.awt.image.BufferedImage readImage(String fileName /*, int x, int y*/ ) throws IOException {
        /*
        int width = x;
        int height = y;
         */
        BufferedImage image = null;
        File file = null;

        //read image
        try {
            file = new File(fileName); //path name
//            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Error reading image \n" + e);
            return null;
        }
        return image;
    }


    private static final HaarCascadeDetector detector = new HaarCascadeDetector();
    private BufferedImage image = null;
    private List<DetectedFace> faces = null;

    //"Constructor"
    public FaceDetection_OpenIMAJ(String[] args) throws IOException {
        /*
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

         */
        image = readImage(args[0] /*, width, height*/ );

        ImagePanel panel = new ImagePanel(image);
//        panel.setPreferredSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        add(panel);
        setTitle("Face Detector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void detectFace() {
        JFrame frame = new JFrame("Discovered faces");
        //detection functionality
        faces = detector.detectFaces(ImageUtilities.createFImage(image));
        if (faces == null) {
            System.out.println("No faces found");
            return;
        }
        Iterator<DetectedFace> detectedFaceIterator = faces.iterator();
        while (detectedFaceIterator.hasNext()) {
            DetectedFace face = detectedFaceIterator.next();
            FImage image1 = face.getFacePatch();
            ImagePanel imagePanel = new ImagePanel(ImageUtilities.createBufferedImage(image1));
            frame.add(imagePanel);
        }

        frame.setLayout(new FlowLayout());
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        //Arguments from main method can be passed to the constructor
//        if (args.length == 3) {
//            FaceDetection_OpenIMAJ faceDetector = new FaceDetection_OpenIMAJ(args);
//            faceDetector.detectFace();
//        } else {
//            System.out.println("Run imageFile.jpg, Width, Height");
//        }

        /*
        image1 - people-13305611.jpg
        width - 1300
        height - 833

        image2 - graduation.jpeg
        width - 1228
        height - 820

         */
        String filePath = "graduation.jpeg";
//        String height = "1228";
//        String width = "820";
        String[] arguments = {filePath /*, height, width*/};
        FaceDetection_OpenIMAJ faceDetector = new FaceDetection_OpenIMAJ(arguments);
        faceDetector.detectFace();

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("Number of faces detected", faceDetector.faces.size());
        System.out.println(jsonObject);
    }

}
