package Serialization;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "intervenant")
@XmlAccessorType(XmlAccessType.FIELD)
public class IntervenantXML {

    @XmlAttribute
    private String id;

    @XmlElement
    private String nom;

    @XmlElement
    private String specialite;

    @XmlElement
    private String biographie;

    public IntervenantXML() {}

    public IntervenantXML(String id, String nom, String specialite, String biographie) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.biographie = biographie;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getBiographie() { return biographie; }
    public void setBiographie(String biographie) { this.biographie = biographie; }
}