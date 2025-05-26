package Serialization;
import jakarta.xml.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement(name = "conference")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConferenceXML extends EvenementXML {

    @XmlElement
    private String theme;

    @XmlElementWrapper(name = "intervenants")
    @XmlElement(name = "intervenant")
    private List<IntervenantXML> intervenants;

    public ConferenceXML() {
        this.intervenants = new ArrayList<>();
    }

    public ConferenceXML(String id, String nom, LocalDateTime date, String lieu,
                         int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public List<IntervenantXML> getIntervenants() { return intervenants; }
    public void setIntervenants(List<IntervenantXML> intervenants) { this.intervenants = intervenants; }
}

