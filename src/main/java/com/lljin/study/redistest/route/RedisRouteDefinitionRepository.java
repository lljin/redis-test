package com.lljin.study.redistest.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lljin
 * @description TODO
 * @date 2020/7/28 23:32
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String ROUTE_DEFINITION = "route_definition";
    private final ObjectMapper objectMapper;

    public RedisRouteDefinitionRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> collect = redisTemplate.opsForHash().values(ROUTE_DEFINITION).stream().map(value -> {
            try {
                return objectMapper.readValue(value.toString(), RouteDefinition.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return Flux.fromIterable(collect);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (StringUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            try {
                redisTemplate.opsForHash().put(ROUTE_DEFINITION, r.getId(), objectMapper.writeValueAsString(r));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (StringUtils.isEmpty(id)) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            redisTemplate.opsForHash().delete(ROUTE_DEFINITION, id);
            return Mono.empty();
        });
    }
}
