package com.cc21.spe.service;

public interface Ec2Service {

	public Integer getNumOfInstances();

	public Integer startInstances(Integer count, Integer nameCount);

}
