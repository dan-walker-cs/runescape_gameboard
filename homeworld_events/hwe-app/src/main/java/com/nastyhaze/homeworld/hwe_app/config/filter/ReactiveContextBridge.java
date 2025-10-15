package com.nastyhaze.homeworld.hwe_app.config.filter;

import jakarta.annotation.PreDestroy;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

/**
 *  Hooks into the Mapped Diagnostic Context for Reactive Endpoints - Webflux garbage.
 */
@Component
public class ReactiveContextBridge {
    private static final String HOOK_KEY = "MDC_CONTEXT_BRIDGE";

    public ReactiveContextBridge() {
        Hooks.onEachOperator(HOOK_KEY,
            Operators.lift((scannable, actual) -> new MdcLifter<>(actual))
        );
    }

    @PreDestroy
    public void cleanup() {
        Hooks.resetOnEachOperator(HOOK_KEY);
    }

    /**
     * Wraps the downstream subscriber and injects MDC around each signal.
     * @param <T>
     */
    static final class MdcLifter<T> implements CoreSubscriber<T> {
        private final CoreSubscriber<? super T> actual;

        MdcLifter(CoreSubscriber<? super T> actual) {
            this.actual = actual;
        }

        @Override public void onSubscribe(Subscription s) { actual.onSubscribe(s); }

        @Override public void onNext(T t) { withMdc(() -> actual.onNext(t)); }

        @Override public void onError(Throwable t) { withMdc(() -> actual.onError(t)); }

        @Override public void onComplete() { withMdc(actual::onComplete); }

        @Override public Context currentContext() { return actual.currentContext(); }

        private void withMdc(Runnable r) {
            String logRequestId = currentContext().getOrDefault(LogRequestIdConfig.REQUEST_ID, null);
            if (logRequestId != null) MDC.put(LogRequestIdConfig.REQUEST_ID, logRequestId);

            try {
                r.run();
            } finally {
                if (logRequestId != null) MDC.remove(LogRequestIdConfig.REQUEST_ID);
            }
        }
    }
}
