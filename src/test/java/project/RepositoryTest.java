package project;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RepositoryTest {

    private Repository repository;
    private final Entity entity1 = new Entity(
            1,"1",true,
            LocalDate.now(), LocalDateTime.now());

    @BeforeEach
    public void init() throws IOException {
        DataSourceProvider dataSourceProvider;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException exception) {
            System.out.println("Failed to init dataSourceProvider!");
            System.out.println(exception.getMessage());
            throw exception;
        }
        repository = new Repository(
                dataSourceProvider.getDataSource());
    }

    @AfterEach
    public void drop() {
        repository.dropTable();
    }


    @Test
    public void insertAndSelectTest() {
        repository.insert(entity1);
        Assertions.assertEquals(entity1, repository.select(1));
    }

    @Test
    public void updateTest() {
        repository.insert(entity1);
        Assertions.assertEquals(entity1, repository.select(1));
        entity1.setBooleanField(false);
        repository.update(entity1);
        Assertions.assertEquals(entity1, repository.select(1));
    }

    @Test
    public void deleteTest() {
        repository.insert(entity1);
        Assertions.assertEquals(entity1, repository.select(1));
        repository.delete(entity1);
        Assertions.assertNull(repository.select(1));
    }
}
