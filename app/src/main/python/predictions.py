
from keras.preprocessing import image
from keras.models import load_model
import numpy as np
import argparse
import imutils
import cv2
import pandas as pd
import numpy as np
#import keras
#from keras import backend as K

import tensorflow
from tensorflow.keras.layers import Dense, Dropout
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.metrics import categorical_crossentropy
from tensorflow.keras.preprocessing.image import ImageDataGenerator

def function(valid_path):
	# load the trained convolutional neural network
	model = load_model(args["model.h5"])

	 
	# pre-process the image for classification
	#image = cv2.resize(image, (224, 224))
	#image = image.astype("float") / 255.0
	#image = img_to_array(image)
	#image = np.expand_dims(image, axis=0)

	# Create a data generator
	datagen = ImageDataGenerator(
		rotation_range=180,
		width_shift_range=0.1,
		height_shift_range=0.1,
		zoom_range=0.1,
		horizontal_flip=True,
		vertical_flip=True,
		#brightness_range=(0.9,1.1),
		fill_mode='nearest')

	#aug_datagen = datagen.flow_from_directory(path,save_to_dir=save_path,save_format='jpg',target_size=(224,224),batch_size=50)

	datagen = ImageDataGenerator(
	    preprocessing_function= \
	    tensorflow.keras.applications.mobilenet.preprocess_input)

	

	test_batches = datagen.flow_from_directory(valid_path,
                                            target_size=(224,224),
                                            batch_size=1,
                                            shuffle=False)



	# make a prediction
	predictions = model.predict_generator(test_batches, steps=1, verbose=1)

	ans= predictions
	print(ans)


return(result)
