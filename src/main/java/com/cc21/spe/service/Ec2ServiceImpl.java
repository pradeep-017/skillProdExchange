package com.cc21.spe.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.amazonaws.services.ec2.model.InstanceStatus;
import com.amazonaws.services.ec2.model.Reservation;
import com.cc21.spe.constants.Constants;
import com.cc21.spe.repo.Ec2Repo;

@Service
public class Ec2ServiceImpl implements Ec2Service {

	@Autowired
	private Ec2Repo ec2Repo;
	
	@Override
	public Integer startInstances(Integer count, Integer nameCount) {
		return ec2Repo.createInstance(Constants.IMAGE_ID, count, nameCount);
	}

	@Override
	public Integer getNumOfInstances() {
//		DescribeInstanceStatusRequest describeRequest = new DescribeInstanceStatusRequest();
//		describeRequest.setIncludeAllInstances(true);
//		DescribeInstanceStatusResult describeInstances = ec2Repo.describeInstance(describeRequest);
//		List<InstanceStatus> instanceStatusList = describeInstances.getInstanceStatuses();
//		Integer countOfRunningInstances = 0;
//		for (InstanceStatus instanceStatus : instanceStatusList) {
//			InstanceState instanceState = instanceStatus.getInstanceState();
//			if (instanceState.getName().equals(InstanceStateName.Running.toString()) 
//					|| instanceState.getName().equals(InstanceStateName.Pending.toString())
//				) {
//				countOfRunningInstances++;
//			}
//		}
		
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		
		Filter runningInstancesFilter = new Filter();
		runningInstancesFilter.setName("instance-state-name");
		runningInstancesFilter.setValues(Arrays.asList(new String[] {"running", "pending"}));
		
		describeInstancesRequest.setFilters( Arrays.asList(new Filter[] {runningInstancesFilter}));
		
		describeInstancesRequest.setMaxResults(1000);
		
		DescribeInstancesResult result = ec2Repo.describeInstances(describeInstancesRequest);
		
		int countOfRunningInstances = 0;
		for(Reservation r : result.getReservations()) {
			countOfRunningInstances += r.getInstances().size();
		}
		
		return countOfRunningInstances;
	}
	
}
