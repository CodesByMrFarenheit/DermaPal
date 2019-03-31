package DeepLearning;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.Doctor;
import Models.LatLong;
import Models.Remedy;

public class DbInsert {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public void DbRun(){

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        String diseaseName = "Melanocytic nevi";
        String description = "A melanocytic nevus (also known as nevocytic nevus, nevus-cell nevus and commonly as a mole) is a type of melanocytic tumor that contains nevus cells. The majority of moles appear during the first two decades of a person's life, with about one in every 100 babies being born with moles.";
        String remedy1  = "Visit Your Nearest Doctor ASAP";

        Remedy remedy = new Remedy(diseaseName,description ,remedy1 );
        mDatabase.child("Remedy").child("1").setValue(remedy);

        String diseaseName1 = "Melanoma";
        String description1 = "Melanoma, also known as malignant melanoma, is a type of cancer that develops from the pigment-containing cells known as melanocytes. Melanomas typically occur in the skin, but may rarely occur in the mouth, intestines, or eye.";
        String remedy2  = "Visit Your Nearest Doctor ASAP";

        Remedy remedy13 = new Remedy(diseaseName1,description1,remedy2 );
        mDatabase.child("Remedy").child("2").setValue(remedy13);

        String diseaseName2 = "Benign keratosis-like lesions";
        String description2 = "A non-cancerous skin condition that appears as a waxy brown, black or tan growth." ;
        String remedy12  = "No need to visit doctor but just in case";

        Remedy remedy4 = new Remedy(diseaseName2,description2 ,remedy12 );
        mDatabase.child("Remedy").child("3").setValue(remedy4);

        String diseaseName3 = "Basal cell carcinoma";
        String description3 ="A type of skin cancer that begins in the basal cells.";
        String remedy14  = "Visit Your Nearest Doctor ASAP";

        Remedy remedy5 = new Remedy(diseaseName3,description3 ,remedy14 );
        mDatabase.child("Remedy").child("4").setValue(remedy5);

        String diseaseName5 = "Actinic keratoses";
        String description5= "Actinic keratoses usually affects older adults. Reducing sun exposure can help reduce risk.\n" +
                "It is most common on the face, lips, ears, back of hands, forearms, scalp and neck. The rough, scaly skin patch enlarges slowly and usually causes no other signs or symptoms. A lesion may take years to develop.";
        String remedy15  = "Visit Your Nearest Doctor ASAP";

        Remedy remedy6 = new Remedy(diseaseName5,description5 ,remedy15 );
        mDatabase.child("Remedy").child("5").setValue(remedy6);

        String diseaseName7 = "Vascular lesions";
        String description7 = "Vascular lesions include acquired lesions (eg, pyogenic granuloma) and those that are present at birth or arise shortly after birth (vascular birthmarks). Vascular birthmarks include vascular tumors (eg, infantile hemangioma) and vascular malformations." ;
        String remedy17  = "Visit Your Nearest Doctor ASAP";

        Remedy remedy7 = new Remedy(diseaseName7,description7,remedy17 );
        mDatabase.child("Remedy").child("6").setValue(remedy7);


        String diseaseName8 = "Dermatofibroma";
        String description8 ="Dermatofibroma (superficial benign fibrous histiocytoma) is a common cutaneous nodule of unknown etiology that occurs more often in women. Dermatofibroma frequently develops on the extremities (mostly the lower legs) and is usually asymptomatic, although pruritus and tenderness can be present." ;
        String remedy18  = "Visit Your Nearest Doctor ASAP";

        Remedy remedy8 = new Remedy(diseaseName8,description8 ,remedy18 );
        mDatabase.child("Remedy").child("7").setValue(remedy8);


        String docName = "Dr.Ashley Lobo";
        String docEdu = "M.D. Dermatologist";
        String phone = "98776543211";
        LatLong loc = new LatLong(19.2, 70.3);

        Doctor doc = new Doctor(docName,docEdu, phone, loc);
        mDatabase.child("Doctor").child("8").setValue(doc);


        String docName1 = "Dr.Viviana Pinto";
        String docEdu1 = "M.D. Derma";
        String phone1= "9877485265";
        LatLong loc1 = new LatLong(19.4, 70.38);

        Doctor doc1 = new Doctor(docName1,docEdu1, phone1, loc1);
        mDatabase.child("Doctor").child("9").setValue(doc1);




        String docName2 = "Dr.Drake Rumore";
        String docEdu2 = "M.D. Dermatologist";
        String phone2 = "98778495111";
        LatLong loc2 = new LatLong(19.7, 71.3);

        Doctor doc2 = new Doctor(docName2,docEdu2, phone2, loc2);
        mDatabase.child("Doctor").child("10").setValue(doc2);



    }

    public List<Doctor> docs(){

        List<Doctor> l = new ArrayList<>();

        String docName = "Dr.Ashley Lobo";
        String docEdu = "M.D. Dermatologist";
        String phone = "98776543211";
        LatLong loc = new LatLong(19.2, 70.3);

        Doctor doc = new Doctor(docName,docEdu, phone, loc);


        String docName1 = "Dr.Viviana Pinto";
        String docEdu1 = "M.D. Derma";
        String phone1= "9877485265";
        LatLong loc1 = new LatLong(19.4, 70.38);

        Doctor doc1 = new Doctor(docName1,docEdu1, phone1, loc1);





        String docName2 = "Dr.Drake Rumore";
        String docEdu2 = "M.D. Dermatologist";
        String phone2 = "98778495111";
        LatLong loc2 = new LatLong(19.7, 71.3);

        Doctor doc2 = new Doctor(docName2,docEdu2, phone2, loc2);

        l.add(doc);
        l.add(doc1);
        l.add(doc2);

        return l;



    }

}
