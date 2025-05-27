package Observer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import modele.*;


    public abstract class EvenementObservableImplementation extends Evenement implements EvenementObservable {
        private List<ParticipantObservateur> observateurs;

        public EvenementObservableImplementation(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
            super(id, nom, date, lieu, capaciteMax);
            this.observateurs = new ArrayList<>();
        }

        @Override
        public void ajouterObservateur(ParticipantObservateur observer) {
            if (!observateurs.contains(observer)) {
                observateurs.add(observer);
            }
        }

        @Override
        public void retirerObservateur(ParticipantObservateur observer) {
            observateurs.remove(observer);
        }

        @Override
        public void notifierObservateurs(String message) {
            observateurs.forEach(observer -> observer.mettreAJour(message, this));
        }

        @Override
        public boolean ajouterParticipant(Participant participant) {
            boolean ajoute = super.ajouterParticipant(participant);
            if (ajoute && participant instanceof ParticipantObservateur) {
                ajouterObservateur((ParticipantObservateur) participant);
                notifierObservateurs("Vous êtes maintenant inscrit à l'événement: " + getNom());
            }
            return ajoute;
        }

        @Override
        public boolean retirerParticipant(Participant participant) {
            boolean retire = super.retirerParticipant(participant);
            if (retire && participant instanceof ParticipantObservateur) {
                retirerObservateur((ParticipantObservateur) participant);
            }
            return retire;
        }

        @Override
        public void annuler() {
            notifierObservateurs("⚠️ L'événement '" + getNom() + "' a été annulé !");
            super.annuler();
        }

        // Méthode pour notifier d'une modification
        public void modifierEvenement(String nouveauNom, LocalDateTime nouvelleDate, String nouveauLieu) {
            String ancienNom = this.nom;
            this.nom = nouveauNom;
            this.date = nouvelleDate;
            this.lieu = nouveauLieu;

            notifierObservateurs("📝 L'événement '" + ancienNom + "' a été modifié. Nouvelles informations: " +
                    nouveauNom + " le " + nouvelleDate + " à " + nouveauLieu);
        }
}
