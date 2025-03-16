package org.example;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import java.io.*;

public class ModelTrainer {

    static ModelTrainer trainer = new ModelTrainer();
    public static void main(String[] args) {
        try {
            trainer.retrainModelWithNewData();
            System.out.println("Model training completed successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred during model training: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void retrainModelWithNewData() throws IOException {
        // Load existing training data
        InputStreamFactory factory = new MarkableFileInputStreamFactory(
                new File("training-data.txt")
        );

        ObjectStream<String> lineStream =
                new PlainTextByLineStream(factory, "UTF-8");

        ObjectStream<DocumentSample> sampleStream =
                new DocumentSampleStream(lineStream);

        // Training parameters
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 3);

        // Train new model
        DoccatModel model = DocumentCategorizerME.train(
                "en",
                sampleStream,
                params,
                new DoccatFactory()
        );
        String userDire = System.getProperty("user.dir");

        // Save updated model
        try (OutputStream modelOut =
                     new BufferedOutputStream(
                             new FileOutputStream(userDire +"\\src\\main\\resources\\models\\failure-classifier.bin")
                     )) {
            model.serialize(modelOut);
        }
    }
}
