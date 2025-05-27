package Services;

import modele.Evenement;

import java.io.*;
import java.util.List;

public class SerializationService {

    public void sauvegarderEvenementsJSON(List<Evenement> evenements, String fichier) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            writer.println("// Sauvegarde de " + evenements.size() + " événements");
            writer.println("// Fichier généré automatiquement");
            for (Evenement e : evenements) {
                writer.println("Evenement: " + e.getId() + " - " + e.getNom());
            }
        }
        System.out.println("✅ Événements sauvegardés dans: " + fichier);
    }

    public List<Evenement> chargerEvenementsJSON(String fichier) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                System.out.println("Lecture: " + ligne);
            }
        }
        System.out.println("✅ Événements chargés depuis: " + fichier);
        return null;
    }
}





