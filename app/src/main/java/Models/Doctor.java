package Models;

public class Doctor {
    String docName;
    String docEdu;
    String phone;
    LatLong loc;


    public Doctor(String docName, String docEdu, String phone, LatLong loc) {
        this.docName = docName;
        this.docEdu = docEdu;
        this.phone = phone;
        this.loc = loc;
    }

    public Doctor() {
    }


    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocEdu() {
        return docEdu;
    }

    public void setDocEdu(String docEdu) {
        this.docEdu = docEdu;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LatLong getLoc() {
        return loc;
    }

    public void setLoc(LatLong loc) {
        this.loc = loc;
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "docName='" + docName + '\'' +
                ", docEdu='" + docEdu + '\'' +
                ", phone='" + phone + '\'' +
                ", loc=" + loc +
                '}';
    }
}
