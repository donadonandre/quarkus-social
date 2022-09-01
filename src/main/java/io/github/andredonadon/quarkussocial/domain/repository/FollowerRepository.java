package io.github.andredonadon.quarkussocial.domain.repository;

import io.github.andredonadon.quarkussocial.domain.model.Follower;
import io.github.andredonadon.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(User follower, User user) {
//        var params = new HashMap<>();
//        params.put("follower", follower);
//        params.put("user", user);

        Map<String, Object> params = Parameters.with("follower", follower).and("user", user).map();

        PanacheQuery<Follower> query = find("follower =:follower and user =:user", params);
        Optional<Follower> optionalFollower = query.firstResultOptional();

        return optionalFollower.isPresent();
    }

    public List<Follower> findByUser(Long userId) {
        PanacheQuery<Follower> query = find("user.id", userId);
        return query.list();
    }

    public void deleteBtFollowerAndUser(Long followerId, Long userId) {
        var params = Parameters.with("userId", userId).and("followerId", followerId).map();

        delete("follower.id =:followerId and user.id =:userId", params);
    }
}
