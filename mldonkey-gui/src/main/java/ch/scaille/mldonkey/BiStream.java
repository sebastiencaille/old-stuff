/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class BiStream<X, Y> {
	private final Collection<X> collectionX;
	private final Function<X, Collection<Y>> xToY;

	public BiStream(final Collection<X> collectionX, final Function<X, Collection<Y>> xToY) {
		this.collectionX = collectionX;
		this.xToY = xToY;
	}

	void forEach(final BiConsumer<X, Y> consumer) {
		this.collectionX.forEach(x -> this.xToY.apply(x).forEach(y -> consumer.accept(x, y)));
	}

	void close() {
		this.collectionX.clear();
	}

	public static <X, Y> Collector<X, ?, BiStream<X, Y>> entries(final Function<X, Collection<Y>> xToY) {
		return new CollectorFunction<>(xToY);
	}

	private static class CollectorFunction<X, Y>
			implements Collector<X, List<X>, BiStream<X, Y>>, BiConsumer<List<X>, X>, BinaryOperator<List<X>> {
		private final Function<X, Collection<Y>> xToY;

		public CollectorFunction(final Function<X, Collection<Y>> xToY) {
			this.xToY = xToY;
		}

		@Override
		public <V> BiFunction<List<X>, List<X>, V> andThen(final Function<? super List<X>, ? extends V> after) {
			return BinaryOperator.super.andThen(after);
		}

		@Override
		public List<X> apply(final List<X> t, final List<X> u) {
			t.addAll(u);
			return t;
		}

		@Override
		public void accept(final List<X> t, final X u) {
			t.add(u);
		}

		@Override
		public Supplier<List<X>> supplier() {
			return ArrayList::new;
		}

		@Override
		public BiConsumer<List<X>, X> accumulator() {
			return this;
		}

		@Override
		public BinaryOperator<List<X>> combiner() {
			return this;
		}

		@Override
		public Function<List<X>, BiStream<X, Y>> finisher() {
			return t -> new BiStream<>(t, CollectorFunction.this.xToY);
		}

		@Override
		public Set<Collector.Characteristics> characteristics() {
			return Collections.emptySet();
		}

	}

}
