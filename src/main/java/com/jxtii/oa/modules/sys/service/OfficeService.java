/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.sys.service;

import com.jxtii.oa.common.service.TreeService;
import com.jxtii.oa.modules.sys.dao.OfficeDao;
import com.jxtii.oa.modules.sys.entity.Office;
import com.jxtii.oa.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class OfficeService extends TreeService<OfficeDao, Office> {

    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {
            return UserUtils.getOfficeList();
        }
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<Office> findList(Office office) {
        if (office != null) {
            office.setParentIds(office.getParentIds() + "%");
            return dao.findByParentIdsLike(office);
        }
        return new ArrayList<Office>();
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void save(Office office) {
        super.save(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void delete(Office office) {
        super.delete(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

}
