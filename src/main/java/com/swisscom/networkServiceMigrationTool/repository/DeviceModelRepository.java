package com.swisscom.networkServiceMigrationTool.repository;

import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import com.swisscom.networkServiceMigrationTool.model.NetworkServiceA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel,Long> {
}
