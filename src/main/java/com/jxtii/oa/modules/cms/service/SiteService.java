/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.cms.service;

import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.service.CrudService;
import com.jxtii.oa.modules.cms.dao.SiteDao;
import com.jxtii.oa.modules.cms.entity.Site;
import com.jxtii.oa.modules.cms.utils.CmsUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站点Service
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SiteService extends CrudService<SiteDao, Site> {

	@Override
    public Page<Site> findPage(Page<Site> page, Site site) {
//		DetachedCriteria dc = siteDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(site.getName())){
//			dc.add(Restrictions.like("name", "%"+site.getName()+"%"));
//		}
//		dc.add(Restrictions.eq(Site.FIELD_DEL_FLAG, site.getDelFlag()));
//		//dc.addOrder(Order.asc("id"));
//		return siteDao.find(page, dc);

        site.getSqlMap().put("site", dataScopeFilter(site.getCurrentUser(), "o", "u"));

        return super.findPage(page, site);
    }

	@Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(Site site) {
        if (site.getCopyright() != null) {
            site.setCopyright(StringEscapeUtils.unescapeHtml4(site.getCopyright()));
        }
        super.save(site);
        CmsUtils.removeCache("site_" + site.getId());
        CmsUtils.removeCache("siteList");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Site site, Boolean isRe) {
        //siteDao.updateDelFlag(id, isRe!=null&&isRe?Site.DEL_FLAG_NORMAL:Site.DEL_FLAG_DELETE);
        site.setDelFlag(isRe != null && isRe ? Site.DEL_FLAG_NORMAL : Site.DEL_FLAG_DELETE);
        super.delete(site);
        //siteDao.delete(id);
        CmsUtils.removeCache("site_" + site.getId());
        CmsUtils.removeCache("siteList");
    }

}
