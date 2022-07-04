package cn.monkey.game.repository;

import cn.monkey.game.data.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    User get(String token);
}
