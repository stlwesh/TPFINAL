package Serialization;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import modele.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class XMLSerializationService {
    public void sauvegarderEvenementsXML(List<Evenement> evenements, String fichier) throws JAXBException {
        // Convertir les événements vers le format XML
        List<EvenementXML> evenementsXML = convertirVersXML(evenements);
        EvenementWrapper wrapper = new EvenementWrapper(evenementsXML);

        JAXBContext context = JAXBContext.newInstance(EvenementWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        marshaller.marshal(wrapper, new File(fichier));
        System.out.println("✅ Événements sauvegardés en XML: " + fichier);
    }

    /**
     * Charger les événements depuis XML
     */
    public List<Evenement> chargerEvenementsXML(String fichier) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EvenementWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        EvenementWrapper wrapper = (EvenementWrapper) unmarshaller.unmarshal(new File(fichier));
        return convertirDepuisXML(wrapper.getEvenements());
    }

    /**
     * Convertir les événements vers le format XML
     */
    private List<EvenementXML> convertirVersXML(List<Evenement> evenements) {
        return evenements.stream()
                .map(this::convertirEvenementVersXML)
                .collect(Collectors.toList());
    }

    /**
     * Convertir un événement vers XML
     */
    private EvenementXML convertirEvenementVersXML(Evenement evenement) {
        if (evenement instanceof Conference) {
            Conference conf = (Conference) evenement;
            ConferenceXML confXML = new ConferenceXML(
                    conf.getId(), conf.getNom(), conf.getDate(),
                    conf.getLieu(), conf.getCapaciteMax(), conf.getTheme()
            );

            // Convertir les participants
            List<ParticipantXML> participantsXML = conf.getParticipants().stream()
                    .map(p -> new ParticipantXML(p.getId(), p.getNom(), p.getEmail()))
                    .collect(Collectors.toList());
            confXML.setParticipants(participantsXML);

            // Convertir les intervenants
            List<IntervenantXML> intervenantsXML = conf.getIntervenants().stream()
                    .map(i -> new IntervenantXML(i.getId(), i.getNom(),
                            i.getSpecialite(), i.getBiographie()))
                    .collect(Collectors.toList());
            confXML.setIntervenants(intervenantsXML);

            return confXML;

        } else if (evenement instanceof Concert) {
            Concert concert = (Concert) evenement;
            ConcertXML concertXML = new ConcertXML(
                    concert.getId(), concert.getNom(), concert.getDate(),
                    concert.getLieu(), concert.getCapaciteMax(),
                    concert.getArtiste(), concert.getGenreMusical()
            );

            // Convertir les participants
            List<ParticipantXML> participantsXML = concert.getParticipants().stream()
                    .map(p -> new ParticipantXML(p.getId(), p.getNom(), p.getEmail()))
                    .collect(Collectors.toList());
            concertXML.setParticipants(participantsXML);

            return concertXML;
        }

        throw new IllegalArgumentException("Type d'événement non supporté: " + evenement.getClass());
    }

    /**
     * Convertir depuis le format XML vers les objets normaux
     */
    private List<Evenement> convertirDepuisXML(List<EvenementXML> evenementsXML) {
        return evenementsXML.stream()
                .map(this::convertirEvenementDepuisXML)
                .collect(Collectors.toList());
    }

    /**
     * Convertir un événement depuis XML
     */
    private Evenement convertirEvenementDepuisXML(EvenementXML evenementXML) {
        if (evenementXML instanceof ConferenceXML) {
            ConferenceXML confXML = (ConferenceXML) evenementXML;
            Conference conf = new Conference(
                    confXML.getId(), confXML.getNom(), confXML.getDate(),
                    confXML.getLieu(), confXML.getCapaciteMax(), confXML.getTheme()
            );

            // Restaurer les participants
            confXML.getParticipants().forEach(pXML -> {
                Participant p = new Participant(pXML.getId(), pXML.getNom(), pXML.getEmail());
                conf.ajouterParticipant(p);
            });

            // Restaurer les intervenants
            confXML.getIntervenants().forEach(iXML -> {
                Intervenant i = new Intervenant(iXML.getId(), iXML.getNom(),
                        iXML.getSpecialite(), iXML.getBiographie());
                conf.ajouterIntervenant(i);
            });

            return conf;

        } else if (evenementXML instanceof ConcertXML) {
            ConcertXML concertXML = (ConcertXML) evenementXML;
            Concert concert = new Concert(
                    concertXML.getId(), concertXML.getNom(), concertXML.getDate(),
                    concertXML.getLieu(), concertXML.getCapaciteMax(),
                    concertXML.getArtiste(), concertXML.getGenreMusical()
            );

            // Restaurer les participants
            concertXML.getParticipants().forEach(pXML -> {
                Participant p = new Participant(pXML.getId(), pXML.getNom(), pXML.getEmail());
                concert.ajouterParticipant(p);
            });

            return concert;
        }

        throw new IllegalArgumentException("Type d'événement XML non supporté: " + evenementXML.getClass());
    }
}
