package co.edu.udistrital.user.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import co.edu.udistrital.structure.api.AttrName;
import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired private MongoTemplate mongoTemplate;

	public UserRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<User> findByIdIn(List<String> idList) {
		Query query = new Query(Criteria.where(AttrName.ID).in(idList));
		return mongoTemplate.find(query, User.class);
	}

	private List<User> findByMobilePhoneInAndStateIn(List<String> mobilePhones, List<State> states) {
		Query query = new Query(Criteria.where(AttrName.MOBILE_PHONE).in(mobilePhones).and(AttrName.STATE).in(states));
		return mongoTemplate.find(query, User.class);
	}

	public User test() {
		Query q1 = new Query(Criteria.where("mobilePhone").is("(314) 279-1956"));
		User u = mongoTemplate.findOne(q1, User.class);
		System.out.println(u.getName());

		Query q2 = new Query(Criteria.where("mobilePhone").in(Arrays.asList("(314) 441-2432", "(314) 279-1956")));
		List<User> userList = mongoTemplate.find(q2, User.class);
		System.out.println(userList.isEmpty());

		Query q3 = new Query(
			Criteria.where("mobilePhone").in(Arrays.asList("(314) 441-2432", "(314) 279-1956")).and("state").in(Arrays.asList(State.INACTIVE)));
		userList = mongoTemplate.find(q3, User.class);
		System.out.println(userList.isEmpty());

		userList = loadByMobilePhoneActiveState(Arrays.asList("(314) 279-1956", "(311) 447-2101"));
		System.out.println(userList.isEmpty());

		return u;
	}


	@Override
	public List<User> loadByMobilePhoneActiveState(List<String> mobilePhones) {
		return findByMobilePhoneInAndStateIn(mobilePhones, Arrays.asList(State.ACTIVE));
	}

	@Override
	public List<User> loadByMobilePhoneInactiveState(List<String> mobilePhones) {
		return findByMobilePhoneInAndStateIn(mobilePhones, Arrays.asList(State.INACTIVE));
	}

	@Override
	public List<User> loadByMobilePhoneIn(List<String> mobilePhones) {
		return findByMobilePhoneInAndStateIn(mobilePhones, Arrays.asList(State.ACTIVE, State.INACTIVE));
	}

}
