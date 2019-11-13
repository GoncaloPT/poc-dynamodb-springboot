package pt.goncalo.poc.dynamodb.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import pt.goncalo.poc.dynamodb.entities.TestDocument;

/**
 * TestRepository
 */
@EnableScan
public interface TestRepository extends CrudRepository<TestDocument, String> {
}