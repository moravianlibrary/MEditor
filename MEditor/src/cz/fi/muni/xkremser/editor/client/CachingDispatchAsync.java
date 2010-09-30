package cz.fi.muni.xkremser.editor.client;

import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * Dispatcher which support caching of data in memory
 * 
 */
public class CachingDispatchAsync implements DispatchAsync {
	private final DispatchAsync dispatcher;
	private final Map<Action<Result>, Result> cache = new HashMap<Action<Result>, Result>();

	@Inject
	public CachingDispatchAsync(final DispatchAsync dispatcher) {
		this.dispatcher = dispatcher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.customware.gwt.dispatch.client.DispatchAsync#execute(A,
	 * com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	public <A extends Action<R>, R extends Result> void execute(final A action, final AsyncCallback<R> callback) {
		dispatcher.execute(action, callback);
	}

	/**
	 * Execute the give Action. If the Action was executed before it will get
	 * fetched from the cache
	 * 
	 * @param <A>
	 *          Action implementation
	 * @param <R>
	 *          Result implementation
	 * @param action
	 *          the action
	 * @param callback
	 *          the callback
	 */
	@SuppressWarnings("unchecked")
	public <A extends Action<R>, R extends Result> void executeWithCache(final A action, final AsyncCallback<R> callback) {
		final Result r = cache.get(action);

		if (r != null) {
			callback.onSuccess((R) r);
		} else {
			dispatcher.execute(action, new AsyncCallback<R>() {

				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}

				@Override
				public void onSuccess(R result) {
					cache.put((Action) action, (Result) result);
					callback.onSuccess(result);
				}

			});
		}
	}

	/**
	 * Clear the cache
	 */
	public void clear() {
		cache.clear();
	}
}