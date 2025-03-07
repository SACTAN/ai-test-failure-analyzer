package org.example;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import java.io.*;

public class ModelTrainer {
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

        // Save updated model
        try (OutputStream modelOut =
                     new BufferedOutputStream(
                             new FileOutputStream("models/updated-model.bin")
                     )) {
            model.serialize(modelOut);
        }
    }
}
