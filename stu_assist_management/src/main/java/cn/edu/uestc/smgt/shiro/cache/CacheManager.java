package cn.edu.uestc.smgt.shiro.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.ehcache.EhCacheManager;

public class CacheManager <K, V> implements Cache<K, V> {
	private EhCacheManager cacheManager;
	private Cache<K, V> cache = null;
	
	public Cache<K, V> getCache() {
		try {
			if(null == cache){
				cache = cacheManager.getCache("shiro_cache");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cache;
	}
	public void clear() throws CacheException {
		getCache().clear();
	}
	public V get(K key) throws CacheException {
		return getCache().get(key);
	}
	public Set<K> keys() {
		
		return getCache().keys();
	}
	public V put(K key, V value) throws CacheException {
		return getCache().put(key, value);
	}
	public V remove(K key) throws CacheException {
		return getCache().remove(key);
	}
	public int size() {
		return getCache().size();
	}
	public Collection<V> values() {
		return getCache().values();
	}
	public EhCacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(EhCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	public void setCache(Cache<K, V> cache) {
		this.cache = cache;
	}
}
