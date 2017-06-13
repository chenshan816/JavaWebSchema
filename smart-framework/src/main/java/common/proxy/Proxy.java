package common.proxy;

/**
 * 代理接口
 * @author cs
 * @since 1.0.0
 */
public interface Proxy {
	/**
	 * 执行链式代理
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
}
