package com.tentcoo.pineapple.core.framework.resource;

import java.io.IOException;
import java.io.InputStream;

/***
 *  本地文件类型资源
 */
public class LocalFileResource implements IResource{
	private InputStream inputStream;
	@Override
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	@Override
	public void open() {
	}
	@Override
	public void close() {
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
