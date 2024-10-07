package platform;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<Code, Long> {
    Code findCodeById(String id);
    @Query(value = "SELECT * FROM codes WHERE RESTRICTED_BY_BOTH=FALSE AND RESTRICTED_BY_TIME=FALSE " +
            "AND RESTRICTED_BY_VIEWS=FALSE ORDER BY DATE DESC LIMIT 10", nativeQuery = true)
    List<Code> getTenLatestUnCodes();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM codes WHERE id=?1", nativeQuery = true)
    void deleteRow(String id);
}