package com.zy.cs.strategy;

import java.util.function.BiPredicate;
import java.util.regex.Pattern;

/**
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/29.
 */
public enum RouteStrategy {
    CONTAIN(1, new Contain()),
    END_WITH(2, new EndWith()),
    PATTERN(3, new PATTERN());

    private final int code;
    private final AbstractRoutePredicate routePredicate;

    RouteStrategy(int code, AbstractRoutePredicate routePredicate) {
        this.code = code;
        this.routePredicate = routePredicate;
    }

    public AbstractRoutePredicate getRoutePredicate() {
        return routePredicate;
    }

    public static RouteStrategy parse(int code) {
        for (RouteStrategy strategy : RouteStrategy.values()) {
            if (strategy.code == code) {
                return strategy;
            }
        }
        return null;
    }

    public static abstract class AbstractRoutePredicate implements BiPredicate<String, String> {
        @Override
        public boolean test(String s, String s2) {
            return routeCheck(s, s2);
        }

        protected abstract boolean routeCheck(String route, String pattern);
    }

    public static class Contain extends AbstractRoutePredicate {

        @Override
        protected boolean routeCheck(String route, String pattern) {
            return route.contains(pattern);
        }
    }

    public static class EndWith extends AbstractRoutePredicate {

        @Override
        protected boolean routeCheck(String route, String pattern) {
            return route.endsWith(pattern);
        }
    }

    public static class PATTERN extends AbstractRoutePredicate {

        @Override
        protected boolean routeCheck(String route, String pattern) {
            return Pattern.matches(pattern, route);
        }
    }
}
