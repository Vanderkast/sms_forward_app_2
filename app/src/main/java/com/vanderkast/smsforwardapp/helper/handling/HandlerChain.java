package com.vanderkast.smsforwardapp.helper.handling;

public interface HandlerChain<T> {

    static <S, N> HandlerChain<N> start(Handler<S, N> first, S data) {
        return new HandlerChainImpl<>(data).next(first);
    }

    <N> HandlerChain<N> next(Handler<? super T, ? extends N> handler);

    T get();

    default Result finish(Finisher<? super T> finisher) {
        return finisher.finish(get());
    }
}
