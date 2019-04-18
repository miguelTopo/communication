package co.edu.udistrital.event.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.event.model.Event;
import co.edu.udistrital.structure.enums.State;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

	@Nullable
	@Async
	List<Event> findByUserIdAndState(String userId, State state);

	List<Event> findByUserIdAndDate(String userId, Calendar date);

}
