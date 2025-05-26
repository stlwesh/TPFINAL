package Serialization;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "participant")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParticipantXML {

    @XmlAttribute
    private String id;

    @XmlElement
    private String nom;

    @XmlElement
    private String email;

    public ParticipantXML() {}

    public ParticipantXML(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
