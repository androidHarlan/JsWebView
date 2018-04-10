package com.tentcoo.pineapple.core.common.factory;



public interface IBuilder<T,O>  {
	/**将要进行装配的对象*/
	public void build(T bean,O object);
}
