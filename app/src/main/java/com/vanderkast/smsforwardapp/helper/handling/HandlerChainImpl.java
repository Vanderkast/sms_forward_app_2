package com.vanderkast.smsforwardapp.helper.handling;

public class HandlerChainImpl<T> implements HandlerChain<T> {
    private final T data;

    public HandlerChainImpl(T current) {
        this.data = current;
    }

    @Override
    public <N> HandlerChain<N> next(Handler<? super T, ? extends N> handler) {
        return new HandlerChainImpl<>(handler.handle(data));
    }

    @Override
    public T get() {
        return data;
    }
}
