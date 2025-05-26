package Services;

import Observer.ConcertObservable;
import Observer.ConferenceObservable;
import Observer.EvenementObservable;
import com.fasterxml.jackson.databind.ObjectMapper;
import modele.Concert;
import modele.Conference;
import modele.Evenement;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;


    /**
     * Service pour la sérialisation/désérialisation JSON
     */
    public class Serialisation {
        private final ObjectMapper objectMapper;

        public Serialisation() {
            this.objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        /**
         * Sauvegarder la liste des événements en JSON
         */
        public static void sauvegarderEvenementsJSON(List<Evenement> evenements, String fichier) throws IOException {
            objectMapper.writeValue(new File(fichier), evenements);
            System.out.println("✅ Événements sauvegardés dans: " + fichier);
        }

        /**
         * Charger la liste des événements depuis JSON
         */
        public List<Evenement> chargerEvenementsJSON(String fichier) throws IOException {
            return objectMapper.readValue(new File(fichier),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Evenement.class));
        }

        /**
         * Sauvegarder un événement spécifique
         */
        public void sauvegarderEvenementJSON(Evenement evenement, String fichier) throws IOException {
            objectMapper.writeValue(new File(fichier), evenement);
        }

        /**
         * Charger un événement spécifique
         */
        public Evenement chargerEvenementJSON(String fichier) throws IOException {
            return objectMapper.readValue(new File(fichier), Evenement.class);
        }
    }

// ============= FACTORY PATTERN POUR LES ÉVÉNEMENTS =============





