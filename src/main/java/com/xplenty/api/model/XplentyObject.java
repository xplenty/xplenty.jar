/**
 * 
 */
package com.xplenty.api.model;

import com.xplenty.api.XplentyAPI;

/**
 * @author Yuriy Kovalek
 *
 */
public abstract class XplentyObject<T extends XplentyObject<T>> {
	protected static final long StatusRefreshInterval = 20*1000; //20 seconds
	private XplentyAPI parentApiInstance;
	
	protected XplentyObject(Class<T> implClazz) {
        if (!getClass().equals(implClazz)) {
            throw new IllegalArgumentException();
        }
    }
	
	protected XplentyAPI getParentApiInstance() {
		return parentApiInstance;
	}
	
	@SuppressWarnings("unchecked")
	public T withParentApiInstance(XplentyAPI instance) {
		parentApiInstance = instance;
		return (T) this;
	}
}
