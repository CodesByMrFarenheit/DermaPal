package Models;

public class Remedy {

    String diseaseName;
    String description;
    String remedy;

    public Remedy(String diseaseName, String description, String remedy) {
        this.diseaseName = diseaseName;
        this.description = description;
        this.remedy = remedy;
    }

    public Remedy() {
    }


    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemedy() {
        return remedy;
    }

    public void setRemedy(String remedy) {
        this.remedy = remedy;
    }

    @Override
    public String toString() {
        return "Remedy{" +
                "diseaseName='" + diseaseName + '\'' +
                ", description='" + description + '\'' +
                ", remedy='" + remedy + '\'' +
                '}';
    }
}
