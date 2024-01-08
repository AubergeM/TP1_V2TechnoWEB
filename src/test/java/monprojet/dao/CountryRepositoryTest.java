package monprojet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");

        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql")
        // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays");
    }

    @Test
    @Sql("test-data.sql")
    void onSaitCalculerPopulationCountry() {
        log.info("On compte la population d'un pays dont l'id est 1");
        assertEquals(12, countryDAO.popPays(1));
    }

    @Test
    @Sql("test-data.sql")
    void listePaysPopulations() {
        log.info("On teste la listes de population");
        assertEquals(3, countryDAO.popParPays().size());
    }

    @Test
    @Sql("test-data.sql")
    void listePopulationTestFr(){
        log.info("On teste la liste pour la France");
        assertEquals(12, countryDAO.popParPays().get(0).getPop());
        //assertEquals("France", countryDAO.popParPays().get(0).getNom());
    }

    @Test
    @Sql("test-data.sql")
    void listePopulationTestUk(){
        log.info("On teste la liste pour uk");
        assertEquals(18, countryDAO.popParPays().get(1).getPop());
        //assertEquals("United Kingdom", countryDAO.popParPays().get(1).getNom());
    }

    @Test
    @Sql("test-data.sql")
    void listePopulationTestUs(){
        log.info("On teste la liste pour les US");
        assertEquals(27, countryDAO.popParPays().get(2).getPop());
        //assertEquals("United States of America", countryDAO.popParPays().get(2).getNom());
    }

}
