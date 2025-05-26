package Serialization;
import jakarta.xml.bind.annotation.*;
import modele.Evenement;

import java.util.List;
@XmlRootElement(name = "evenements")
@XmlAccessorType(XmlAccessType.FIELD)
public class EvenementWrapper {

    @XmlElement(name = "evenement")
    private List<Evenement> evenements;

    public EvenementWrapper() {}

    public EvenementWrapper(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public EvenementWrapper(List<EvenementXML> evenementsXML) {
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
