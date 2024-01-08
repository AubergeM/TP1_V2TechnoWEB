package monprojet.dao;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;
//import monprojet.dto.PopPays;
// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query (value = "SELECT SUM(population) "
            + "FROM city"
            + "WHERE country_id = :idPays",
            nativeQuery = true)

    public Integer popPays(Integer idPays);

    @Query (value = "SELECT country.name AS name, SUM(city.population) AS pop"
            + "FROM country INNER JOIN city USING (id)"
            + "GROUP BY name, country.id",
            nativeQuery = true)

    public List<DTO> popParPays();
}
