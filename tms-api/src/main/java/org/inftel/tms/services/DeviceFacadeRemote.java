package org.inftel.tms.services;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import org.inftel.tms.domain.Device;

/**
 *
 * @author ibaca
 */
@Local
public interface DeviceFacadeRemote {

  void create(Device devices);

  void edit(Device devices);

  void remove(Device devices);

  Device find(Object id);

  List<Device> findAll();

  List<Device> findRange(int[] range);

  int count();

  public Device findByMobile(String mobile);
}
