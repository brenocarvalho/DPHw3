package model;

import java.io.Serializable;

public abstract class Code implements Serializable{
	public abstract Status run(Object obj) throws Exception;
}
