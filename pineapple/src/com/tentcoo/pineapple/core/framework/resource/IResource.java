package com.tentcoo.pineapple.core.framework.resource;

import java.io.InputStream;

public interface IResource {
	InputStream getInputStream();
	void open();
	void close();
}
