package cn.monkey.game.repository;

import cn.monkey.data.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> get(String token);
}
