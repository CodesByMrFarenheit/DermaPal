package DeepLearning;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.IOException;


public class DeepModel {

    MultiLayerNetwork model;
    public void loadModel() throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {

        String simpleMlp = new ClassPathResource(
                "games.h5").getFile().getPath();

         model = KerasModelImport.
                importKerasSequentialModelAndWeights(simpleMlp);


    }


    //double prediction = model.output(put input array here).getDouble(0);







}
